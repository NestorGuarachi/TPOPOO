package main;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;

import javax.swing.JFrame;

import graficos.Assets;
import input.KeyBoard;
import states.MenuControlador;
import states.SkinSelectorControlador;
import states.SkyDefenseControlador;

public class Ventana extends JFrame implements Runnable {

    public static final int WIDTH = 800, HEIGHT = 1000;

    private Canvas canvas;
    private Thread thread;
    private boolean running = false;

    private BufferStrategy bs;
    private Graphics g;

    private final int FPS = 60;
    private double TARGETTIME = 1000000000.0 / FPS;
    private double delta = 0;
    private int AVERGEFPS = FPS;

    // Máquina de estados
    public enum Estado { MENU, SKIN_SELECTOR, JUGANDO }
    private Estado estadoActual = Estado.MENU;

    private MenuControlador menuState;
    private SkinSelectorControlador skinState;
    private SkyDefenseControlador gameState;
    private KeyBoard keyBoard;

    public Ventana() {
        canvas = new Canvas();
        keyBoard = new KeyBoard();

        canvas.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        add(canvas);
        canvas.addKeyListener(keyBoard);
        canvas.setMaximumSize(new Dimension(WIDTH, HEIGHT));
        canvas.setFocusable(true);

        setTitle("Sky Defense");
        setSize(WIDTH, HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);
        canvas.requestFocus();
    }

    public static void main(String[] args) {
        new Ventana().start();
    }

    private void update() {
        keyBoard.update();

        switch (estadoActual) {

            case MENU:
                menuState.update();
                if (menuState.debeIniciarJuego()) {
                    gameState = new SkyDefenseControlador();
                    estadoActual = Estado.JUGANDO;
                    menuState.resetFlags();
                } else if (menuState.debeIrASkinSelector()) {
                    skinState = new SkinSelectorControlador();
                    estadoActual = Estado.SKIN_SELECTOR;
                    menuState.resetFlags();
                }
                break;

            case SKIN_SELECTOR:
                skinState.update();
                if (skinState.debeVolverAlMenu()) {
                    menuState = new MenuControlador();
                    estadoActual = Estado.MENU;
                }
                break;

            case JUGANDO:
                gameState.update();
                if (gameState.debeVolverAlMenu()) {
                    menuState = new MenuControlador();
                    estadoActual = Estado.MENU;
                }
                break;
        }
    }

    private void draw() {
        bs = canvas.getBufferStrategy();
        if (bs == null) {
            canvas.createBufferStrategy(3);
            return;
        }

        g = bs.getDrawGraphics();

        // Fondo: imagen si cargó, negro si no
        if (Assets.fondo != null)
            g.drawImage(Assets.fondo, 0, 0, WIDTH, HEIGHT, null);
        else {
            g.setColor(Color.BLACK);
            g.fillRect(0, 0, WIDTH, HEIGHT);
        }

        switch (estadoActual) {
            case MENU:          menuState.draw(g);  break;
            case SKIN_SELECTOR: skinState.draw(g);  break;
            case JUGANDO:       gameState.draw(g);  break;
        }

        g.dispose();
        bs.show();
    }

    private void init() {
        Assets.init();
        menuState = new MenuControlador();
        estadoActual = Estado.MENU;
    }

    @Override
    public void run() {
        long now = 0;
        long lastTime = System.nanoTime();
        int frames = 0;
        long time = 0;

        init();

        while (running) {
            now = System.nanoTime();
            delta += (now - lastTime) / TARGETTIME;
            time  += (now - lastTime);
            lastTime = now;

            if (delta >= 1) {
                update();
                draw();
                delta--;
                frames++;
            }

            if (time >= 1000000000) {
                AVERGEFPS = frames;
                frames = 0;
                time = 0;
            }
        }

        stop();
    }

    private void start() {
        running = true;
        thread = new Thread(this);
        thread.start();
    }

    private void stop() {
        try {
            thread.join();
            running = false;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}