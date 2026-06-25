package modelo;

public class Jugador {

    private int puntos;
    private int vidas;
    private int umbralVidaExtra;
    private Avion avion; // referencia al avión del jugador (según diagrama UML)

    public Jugador(Avion avion) {
        this.avion          = avion;
        this.puntos         = 0;
        this.vidas          = 3;
        this.umbralVidaExtra = 1000;
    }

    // Constructor sin avion — mantiene compatibilidad con tests existentes
    public Jugador() {
        this(null);
    }

    public int getPuntos() { return puntos; }
    public int getVidas()  { return vidas; }
    public Avion getAvion() { return avion; }
    public void setAvion(Avion avion) { this.avion = avion; }

    public void sumarPuntos(int cantidad) {
        this.puntos += cantidad;
        while (this.puntos >= umbralVidaExtra) {
            vidas++;
            umbralVidaExtra += 1000;
        }
    }

    public void perderVida() {
        if (vidas > 0) vidas--;
    }

    public boolean estaVivo() {
        return vidas > 0;
    }
}