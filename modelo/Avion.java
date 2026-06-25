package modelo;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import input.KeyBoard;
import main.Ventana;
import math.Vector2D;

public class Avion extends ElementoVolador {

    private int altitud; // valor abstracto entre 1000 y 5000 metros
    private int energia; // porcentaje de energía (0-100)

    public Avion(Vector2D position, BufferedImage texture) {
        super(position, new Vector2D(), texture); // sin velocidad propia, se mueve por teclado
        altitud = 3000;
        energia = 100;
    }

    public void subir() {
        if (altitud < 5000) altitud += 100;
    }

    public void bajar() {
        if (altitud > 1000) altitud -= 100;
    }

    public int getAltitud() { return altitud; }
    public int getEnergia() { return energia; }

    public void perderEnergia(int porcentaje) {
        energia -= porcentaje;
        if (energia < 0) energia = 0;
    }

    public void resetEnergia() { energia = 100; }

    @Override
    public void update() {
        if (KeyBoard.RIGHT) position.setX(position.getX() + 5);
        if (KeyBoard.LEFT)  position.setX(position.getX() - 5);
        if (KeyBoard.UP)    subir();
        if (KeyBoard.DOWN)  bajar();

        // Wraparound horizontal
        if (position.getX() > Ventana.WIDTH) position.setX(0);
        if (position.getX() < 0)             position.setX(Ventana.WIDTH);

        // Altitud → posición Y en pantalla
        double porcentaje = (5000 - altitud) / 4000.0;
        position.setY(porcentaje * (Ventana.HEIGHT - height));
    }

    @Override
    public void draw(Graphics g) {
        g.drawImage(texture, (int) position.getX(), (int) position.getY(), null);
        g.drawString("Altitud: " + altitud + "m", 20, 20);
        g.drawString("Energia: " + energia + "%", 20, 40);
    }
}