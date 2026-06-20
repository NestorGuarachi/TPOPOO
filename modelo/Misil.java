package modelo;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import main.Ventana;
import math.Vector2D;

public class Misil extends MovingObject{

    private int altitudExplosion;
    private int altitudActual;
    private boolean explotado;
    private int tiempoExplosion = 30; // frames que dura la explosion
    private boolean evaluado;

    public Misil(Vector2D position, BufferedImage texture, int altitudExplosion, double velocidadMisil) {
        super(position, new Vector2D(0, velocidadMisil), velocidadMisil, texture);

        this.altitudExplosion = altitudExplosion;
        this.altitudActual = 5000;
        this.explotado = false;
        this.evaluado = false;
    }
    
    public double distancia (Avion avion) {
        return Math.abs(altitudExplosion - avion.getAltitud());
    }

    public void aplicarEfecto(Avion avion, Jugador jugador){
        double distancia = distancia (avion);
        if (distancia > 150){
            jugador.sumarPuntos(40);
        } else if (distancia > 80) {
            jugador.sumarPuntos(20);
            avion.perderEnergia(20);
        } else if (distancia > 20) {
            avion.perderEnergia(40);
        }
        else {
            jugador.perderVida();
            avion.resetEnergia();
        }
        this.evaluado = true;
    }
    public boolean fueraDePantalla() {
        return position.getY() > Ventana.HEIGHT;
    }
    public boolean isExplotado() {
        return explotado;
    }
    public boolean isEvaluado() {
        return evaluado;
    }
    public void setEvaluado(boolean evaluado) {
        this.evaluado = evaluado;
    }
    public int getTiempoExplosion() {
        return tiempoExplosion;
    }
    public int getAltitudExplosion(){
        return altitudExplosion;
    }

    @Override
    public void update() {
        if (!explotado) {
                // mueve el misil en pantalla 
            position = position.add(velocity);

            // sincroniza altitudActual con la posicion Y
            altitudActual -= 20;

            // explota cuando llega a la altitudExplosion
            if (altitudActual <= altitudExplosion) {
                explotado = true;
            }
        }
        
        if (explotado && tiempoExplosion > 0){
            tiempoExplosion--;
        }
    }

    @Override
    public void draw(Graphics g) {
        if (!explotado){
            // misil normal
        g.drawImage(texture, (int) position.getX(), (int) position.getY(), null);
        } 
        else if (tiempoExplosion > 0){
            // explosion visual: circulo rojo
            g.setColor(Color.RED);
            g.fillOval((int) position.getX() - 20, (int) position.getY() - 20, 40, 40);

            g.setColor(Color.ORANGE);
            g.drawOval((int) position.getX() - 25, (int)position.getY() - 25, 50, 50);
        }   
    }
}
