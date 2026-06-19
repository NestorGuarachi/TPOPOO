package modelo;

public class Jugador {
    
    private int puntos;
    private int vidas;
    private int umbralVidaExtra = 1000;

    public Jugador() {
        puntos = 0;
        vidas = 3;
        umbralVidaExtra = 1000;
    }
    public int getPuntos() {
        return puntos;
    }
    public int getVidas() {
        return vidas;
    }
    public void sumarPuntos(int cantidad) {
        this.puntos += cantidad;
        while (this.puntos >= umbralVidaExtra) {
            vidas++;
            umbralVidaExtra += 1000;
        }  
    }
    public void perderVida() {
        if (vidas > 0)
            vidas--;
    }
    public boolean estaVivo(){
        return vidas > 0;
    }
}
