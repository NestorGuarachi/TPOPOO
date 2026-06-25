package modelo;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import main.Ventana;
import math.Vector2D;

public class Misil extends ElementoVolador implements Destructible {

    private int altitudExplosion;
    private double targetY;
    private boolean explotado;
    private int tiempoExplosion;
    private boolean evaluado;

    public Misil(Vector2D position, BufferedImage texture, int altitudExplosion, double velocidadMisil) {
        super(position, new Vector2D(0, velocidadMisil), texture);
        this.altitudExplosion = altitudExplosion;
        this.explotado        = false;
        this.evaluado         = false;
        this.tiempoExplosion  = 30;
        this.targetY = ((5000.0 - altitudExplosion) / 4000.0) * (Ventana.HEIGHT - 50);
    }

    // Implementación de Destructible
    @Override
    public void explotar() {
        if (!explotado) {
            explotado = true;
            position.setY(targetY);
        }
    }

    public double distancia(Avion avion) {
        double distX = Math.abs(position.getX() - avion.getPosition().getX());
        double distY = Math.abs(position.getY() - avion.getPosition().getY());
        return Math.sqrt(distX * distX + distY * distY);
    }

    // Retorna true si como resultado de la explosión el avión pierde una vida.
    public boolean aplicarEfecto(Avion avion, Jugador jugador) {
        double d = distancia(avion);
        boolean perdioVida = false;

        if (d > 200) {
            jugador.sumarPuntos(40);
        } else if (d > 100) {
            jugador.sumarPuntos(20);
            avion.perderEnergia(20);
            if (avion.getEnergia() == 0) {
                jugador.perderVida();
                avion.resetEnergia();
                perdioVida = true;
            }
        } else if (d > 40) {
            avion.perderEnergia(40);
            if (avion.getEnergia() == 0) {
                jugador.perderVida();
                avion.resetEnergia();
                perdioVida = true;
            }
        } else {
            jugador.perderVida();
            avion.resetEnergia();
            perdioVida = true;
        }

        setEvaluado(true);
        return perdioVida;
    }

    public boolean fueraDePantalla()   { return position.getY() > Ventana.HEIGHT; }
    public boolean isExplotado()       { return explotado; }
    public boolean isEvaluado()        { return evaluado; }
    public void setEvaluado(boolean e) { evaluado = e; }
    public int getTiempoExplosion()    { return tiempoExplosion; }
    public int getAltitudExplosion()   { return altitudExplosion; }

    @Override
    public void update() {
        if (!explotado) {
            position = position.add(velocity);
            if (position.getY() >= targetY) {
                explotar(); // usa el método de Destructible
            }
        }
        if (explotado && tiempoExplosion > 0) tiempoExplosion--;
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