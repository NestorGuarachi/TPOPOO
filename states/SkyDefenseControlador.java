package states;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import graficos.Assets;
import input.KeyBoard;
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
    private boolean volverAlMenu; // se activa solo cuando el jugador presiona ESC

    // Notificación temporal "¡Vida perdida!"
    private boolean mostrarAvisoVida;
    private int timerAvisoVida;

    public SkyDefenseControlador() {
        avion   = new Avion(new Vector2D(400, 300), Assets.jugador);
        jugador = new Jugador(avion);

        misiles         = new ArrayList<>();
        contadorDisparo = 0;
        gameOver        = false;
        volverAlMenu    = false;
        mostrarAvisoVida = false;
        timerAvisoVida   = 0;

        nivel     = new Nivel(1, 3.0, 5.0, 60, 15);
        escuadron = new Escuadron(nivel);
    }

    public void realizarNivel() {
        if (escuadron.escuadronCompletado()) {
            jugador.sumarPuntos(300);
            nivel.incrementarVelocidades();
            escuadron.reiniciar();
            avion.resetEnergia();
            misiles.clear();
        }
    }

    public void update() {

        // En game over solo esperar ESC para volver al menú
        if (gameOver) {
            if (KeyBoard.ESC) volverAlMenu = true;
            return;
        }

        // Actualizar avión
        avion.update();

        // Actualizar drones activos
        for (Dron dron : escuadron.getDrones()) {
            if (dron.isActivo()) dron.update();
        }
        escuadron.activarDrones();

        // Actualizar misiles
        for (Misil misil : misiles) {
            misil.update();
        }

        // Evaluar explosiones
        for (Misil misil : misiles) {
            if (misil.isExplotado() && !misil.isEvaluado()) {
                boolean perdioVida = misil.aplicarEfecto(avion, jugador);
                if (perdioVida) {
                    mostrarAvisoVida = true;
                    timerAvisoVida   = 120; // 2 segundos a 60 FPS
                }
            }
        }

        // Descontar timer del aviso
        if (mostrarAvisoVida) {
            timerAvisoVida--;
            if (timerAvisoVida <= 0) mostrarAvisoVida = false;
        }

        // Eliminar misiles terminados
        misiles.removeIf(misil -> misil.fueraDePantalla() ||
                (misil.isExplotado() && misil.getTiempoExplosion() <= 0 && misil.isEvaluado()));

        // Disparos de drones
        contadorDisparo++;
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

        // Verificar game over
        if (!jugador.estaVivo()) {
            gameOver = true;
        }

        realizarNivel();
    }

    public void draw(Graphics g) {

        // Pantalla de game over
        if (gameOver) {
            g.setColor(new Color(0, 0, 0, 180));
            g.fillRect(0, 0, 800, 1000);

            g.setColor(Color.RED);
            g.setFont(new Font("Courier New", Font.BOLD, 60));
            g.drawString("GAME OVER", 185, 380);

            g.setColor(Color.WHITE);
            g.setFont(new Font("Courier New", Font.BOLD, 28));
            g.drawString("Puntos: " + jugador.getPuntos(), 305, 450);

            g.setColor(Color.YELLOW);
            g.setFont(new Font("Courier New", Font.PLAIN, 20));
            g.drawString("Presiona ESC para volver al menu", 175, 520);
            return;
        }

        // Juego normal
        avion.draw(g);

        for (Dron dron : escuadron.getDrones()) {
            if (dron.isActivo()) dron.draw(g);
        }

        for (Misil misil : misiles) {
            misil.draw(g);
        }

        // HUD
        g.setColor(new Color(0, 0, 0, 150));
        g.fillRect(10, 40, 200, 120);

        g.setFont(new Font("Courier New", Font.BOLD, 20));
        g.setColor(Color.RED);
        g.drawString("Vidas: " + jugador.getVidas(), 20, 60);
        g.setColor(Color.YELLOW);
        g.drawString("Puntos: " + jugador.getPuntos(), 20, 80);
        g.setColor(Color.CYAN);
        g.drawString("Nivel: " + nivel.getNumeroNivel(), 20, 100);
        g.setColor(Color.GREEN);
        g.drawString("Energia: " + avion.getEnergia() + "%", 20, 120);

        // Aviso vida perdida
        if (mostrarAvisoVida) {
            g.setFont(new Font("Courier New", Font.BOLD, 30));
            g.setColor(Color.RED);
            g.drawString("!VIDA PERDIDA!", 240, 500);
        }
    }

    public boolean debeVolverAlMenu() {
        return volverAlMenu;
    }
}