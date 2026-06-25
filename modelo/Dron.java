package modelo;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import graficos.Assets;
import main.Ventana;
import math.Vector2D;

public class Dron extends ElementoVolador {

    private String direccion;  // "derecha" o "izquierda"
    private boolean activo;
    private boolean destruido;

    public Dron(Vector2D position, BufferedImage texture, String direccion, double velocidadDron) {
        super(position, new Vector2D(velocidadDron, 0), texture);
        this.direccion = direccion;
        this.activo    = true;
        this.destruido = false;
    }

    public boolean isActivo()    { return activo; }
    public boolean isDestruido() { return destruido; }
    public void setActivo(boolean activo) { this.activo = activo; }

    public void completarRecorrido() {
        destruido = true;
        activo    = false;
    }

    public Misil disparar(double velocidadMisil, int altitudExplosion) {
        return new Misil(
                new Vector2D(position.getX() + width / 2, position.getY() + height),
                Assets.misil,
                altitudExplosion,
                velocidadMisil
        );
    }

    @Override
    public void update() {
        if (direccion.equals("derecha")) {
            position.setX(position.getX() + velocity.getX());
            if (position.getX() > Ventana.WIDTH) completarRecorrido();
        } else {
            position.setX(position.getX() - velocity.getX());
            if (position.getX() < -width)        completarRecorrido();
        }
    }

    @Override
    public void draw(Graphics g) {
        g.drawImage(texture, (int) position.getX(), (int) position.getY(), null);
    }
}