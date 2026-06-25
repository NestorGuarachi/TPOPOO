package modelo;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import main.Ventana;
import math.Vector2D;

public class Misil extends MovingObject {

    private int altitudExplosion;
    private int altitudActual;
    private boolean explotado;
    private int tiempoExplosion = 30;
    private boolean evaluado;

    public Misil(Vector2D position, BufferedImage texture, int altitudExplosion, double velocidadMisil) {
        super(position, new Vector2D(0, velocidadMisil), velocidadMisil, texture);
        this.altitudExplosion = altitudExplosion;
        this.altitudActual = 5000;
        this.explotado = false;
        this.evaluado = false;
    }

    // Distancia en PÍXELES entre la explosión y el avión
    public double distancia(Avion avion) {
        double distX = Math.abs(position.getX() - avion.getPosition().getX());
        double distY = Math.abs(position.getY() - avion.getPosition().getY());
        return Math.sqrt(distX * distX + distY * distY);
    }

    public void aplicarEfecto(Avion avion, Jugador jugador) {
        double distancia = distancia(avion);

        if (distancia > 200) {
            jugador.sumarPuntos(40);
        } else if (distancia > 100) {
            jugador.sumarPuntos(20);
            avion.perderEnergia(20);
        } else if (distancia > 40) {
            avion.perderEnergia(40);
        } else {
            jugador.perderVida();
            avion.resetEnergia();
        }

        setEvaluado(true);
    }

    public boolean fueraDePantalla()       { return position.getY() > Ventana.HEIGHT; }
    public boolean isExplotado()           { return explotado; }
    public boolean isEvaluado()            { return evaluado; }
    public void setEvaluado(boolean e)     { evaluado = e; }
    public int getTiempoExplosion()        { return tiempoExplosion; }
    public int getAltitudExplosion()       { return altitudExplosion; }

// En Misil.java

    @Override
    public void update() {
        if (!explotado) {
            position = position.add(velocity);

            // Derivar altitud directamente de la posición visual (fuente de verdad)
            // Invertimos la fórmula de Avion: posY = (5000 - altitud) / 4000.0 * (HEIGHT - height)
            // Usamos HEIGHT - 50 igual que el snap de explosión
            altitudActual = (int) (5000.0 - (position.getY() / (Ventana.HEIGHT - 50)) * 4000.0);

            if (altitudActual <= altitudExplosion) {
                explotado = true;
                double targetY = ((5000.0 - altitudExplosion) / 4000.0) * (Ventana.HEIGHT - 50);
                position.setY(targetY);
            }
        }

        if (explotado && tiempoExplosion > 0) {
            tiempoExplosion--;
        }
    }
    @Override
    public void draw(Graphics g) {
        if (!explotado) {
            g.drawImage(texture, (int) position.getX(), (int) position.getY(), null);
        } else if (tiempoExplosion > 0) {
            g.setColor(Color.RED);
            g.fillOval((int) position.getX() - 20, (int) position.getY() - 20, 40, 40);
            g.setColor(Color.ORANGE);
            g.drawOval((int) position.getX() - 25, (int) position.getY() - 25, 50, 50);
        }
    }
}