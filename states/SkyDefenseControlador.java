package states;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import graficos.Assets;
import math.Vector2D;
import modelo.Avion;
import modelo.Dron;
import modelo.Escuadron;
import modelo.Misil;
import modelo.Nivel;
import modelo.Jugador;


import java.util.ArrayList;

public class SkyDefenseControlador {
    private Avion avion;
    private Escuadron escuadron;
    private ArrayList<Misil> misiles;
    private int contadorDisparo;
    private Jugador jugador;
    private Nivel nivel;
    private boolean gameOver;

    // constructor
    public SkyDefenseControlador() {
        avion = new Avion(new Vector2D(400, 300), Assets.jugador);

        jugador = new Jugador();

        misiles = new ArrayList<>();
        contadorDisparo = 0;

        gameOver = false;

        nivel = new Nivel(1, 3.0, 5.0, 60, 15); // valores iniciales para el nivel 1

        escuadron = new Escuadron(nivel);

    }

    public void realizarNivel() {
        // cuando el jugador sobrevive a todos los drones/misiles
        if (escuadron.escuadronCompletado()) {
            jugador.sumarPuntos(300);
            nivel.incrementarVelocidades();
            escuadron.reiniciar();
            avion.resetEnergia();
            misiles.clear();
        }
    }

    public void update() {

        if (gameOver)
            return;


        // actualizar avion
        avion.update();

        // actualizar drones activos
        for (Dron dron : escuadron.getDrones()) {
            if (dron.isActivo())
                dron.update();
        }
        // asegurar max 4 drones activos
        escuadron.activarDrones();

        // actualizar todos los misiles
        for (Misil misil : misiles) {
            misil.update();
        }
        // evaluar explosiones
        for (Misil misil : misiles) {
            if (misil.isExplotado() && !misil.isEvaluado()) {
                misil.aplicarEfecto(avion, jugador);
            }
        }

        // eliminar misiles fuera de pantalla
        misiles.removeIf(misil -> misil.fueraDePantalla() ||
                (misil.isExplotado() && misil.getTiempoExplosion() <= 0 && misil.isEvaluado()));


        // contador de disparos
        contadorDisparo++;

        // disparo de drones segun frecuencia de nivel
        if (contadorDisparo >= nivel.getFrecuenciaDisparo()) {
            contadorDisparo = 0;
            ArrayList<Dron> dronesActivos = new ArrayList<>();
            for (Dron dron : escuadron.getDrones()) {
                if (dron.isActivo()) dronesActivos.add(dron);
            }
            if (!dronesActivos.isEmpty()) {
                Dron dron = dronesActivos.get((int) (Math.random() * dronesActivos.size()));
                int altitudExplosion = 1200 + (int) (Math.random() * (4500 - 1200));
                misiles.add(dron.disparar(nivel.getVelocidadMisil(), altitudExplosion));
            }
        }

        // verificar fin de partida
        if (!jugador.estaVivo()) {
            gameOver = true;
        }

        // verificar si se completo el nivel
        realizarNivel();

    }

    public void draw(Graphics g){

        if (gameOver){
            g.setColor(Color.RED);
            g.setFont(new Font("Arial", Font.BOLD, 50));
            g.drawString("GAME OVER", 220, 300);
            return;
        }

        // dibujar avion
        avion.draw(g);

        // dibujar drones activos
        for (Dron dron : escuadron.getDrones()) {
            if (dron.isActivo()) {
                dron.draw(g);
            }
        }

        // dibujar misiles
        for (Misil misil : misiles) {
            misil.draw(g);
        }


        // HUD (informacion del jugador y nivel)

        // fondo semitransparente para el hud
        g.setColor(new Color(0,0,0,150));
        g.fillRect(10, 40, 200, 120);

        // fuente retro arcade
        g.setFont(new Font("Courier New", Font.BOLD, 20));

        g.setColor(Color.RED); // color del texto del hud
        g.drawString("Vidas: " + jugador.getVidas(), 20, 60);
        g.setColor(Color.YELLOW);
        g.drawString("Puntos: " + jugador.getPuntos(), 20, 80);
        g.setColor(Color.CYAN);
        g.drawString("Nivel: " + nivel.getNumeroNivel(), 20, 100);
        g.setColor(Color.GREEN);
        g.drawString("Energia: " + avion.getEnergia() + "%", 20, 120);
        
    }
}
