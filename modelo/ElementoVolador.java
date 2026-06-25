package modelo;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import math.Vector2D;

/**
 * Clase base de todos los objetos del juego.
 * Unifica lo que antes estaba repartido en ElementoVolador + MovingObject:
 * - posición y textura (de ElementoVolador)
 * - velocidad y dimensiones del sprite (de MovingObject)
 *
 * MovingObject fue eliminado porque era una capa intermedia sin lógica propia,
 * solo pasaba campos a las subclases.
 */
public abstract class ElementoVolador {

    protected BufferedImage texture;  // imagen del sprite
    protected Vector2D position;      // posición en pantalla (x, y) en píxeles
    protected Vector2D velocity;      // desplazamiento por frame (píxeles)
    protected int width;              // ancho del sprite en píxeles
    protected int height;             // alto del sprite en píxeles

    public ElementoVolador(Vector2D position, Vector2D velocity, BufferedImage texture) {
        this.position = position;
        this.velocity = velocity;
        this.texture  = texture;
        this.width    = texture.getWidth();
        this.height   = texture.getHeight();
    }

    public abstract void update();
    public abstract void draw(Graphics g);

    public Vector2D getPosition() { return position; }
    public void setPosition(Vector2D position) { this.position = position; }
}