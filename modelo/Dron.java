package modelo;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import math.Vector2D;
import main.Ventana;
import graficos.Assets;

public class Dron extends MovingObject{

    private String direccion;
    private boolean activo;
    private boolean destruido;
    

    public Dron(Vector2D position, BufferedImage texture, String direccion, double velocidadDron) {
        super(position, new Vector2D(velocidadDron, 0), velocidadDron, texture); // velocidad fija
        this.direccion = direccion;
        this.activo = true;
        this.destruido = false;
    
    }
    public boolean isActivo() {
        return activo;
    }
    public boolean isDestruido(){
        return destruido;
    }
    public void completarRecorrido(){
        destruido = true;
        activo = false;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }
    
    public Misil disparar(double velocidadMisil, int altitudExplosion){
        //int altitudExplosion = 1200 + (int)(Math.random() * (4500 - 1200)); // altitud aleatoria
        return new Misil(new Vector2D(position.getX() + width / 2, position.getY() + height), Assets.misil, altitudExplosion, velocidadMisil);
    }
    
    
    

    @Override
    public void update (){
        if (direccion.equals("derecha")) {
            position.setX(position.getX() + velocity.getX());
            if (position.getX()> Ventana.WIDTH){
                completarRecorrido();
            }
        }else{
            position.setX(position.getX()- velocity.getX());
            if (position.getX()< -width){
                completarRecorrido();
            }
        }
    }
    
    @Override
    public void draw(Graphics g) {
        g.drawImage(texture, (int) position.getX(), (int) position.getY(), null);
    }
}

