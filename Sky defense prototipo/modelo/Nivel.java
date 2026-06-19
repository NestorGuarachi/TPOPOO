package modelo;

public class Nivel {
    private int numeroNivel; 
    private double velocidadDron; // velocidad de desplazamiento
    private double velocidadMisil; // velocidad de caida de los misiles
    private int frecuenciaDisparo; // cada cuantos frames se dispara un misil
    private int incremento; // porcentaje de incremento (ej: 15)

    public Nivel (int numeroNivel, double velocidadDron, double velocidadMisil, int frecuenciaDisparo, int incremento){
        this.numeroNivel = numeroNivel;
        this.velocidadDron = velocidadDron;
        this.velocidadMisil = velocidadMisil;
        this.frecuenciaDisparo = frecuenciaDisparo;
        this.incremento = incremento;
    }


    //metodo para avanzar de nivel y aumentar velocidades
    public void incrementarVelocidades () {
        numeroNivel ++;
        velocidadDron *= 1 + (incremento / 100.0);
        velocidadMisil *= 1 + (incremento / 100.0);
        frecuenciaDisparo = (int)(frecuenciaDisparo * (1 - (incremento / 100.0)));
        // dispara mas seguido (ej: si era 60 frames, ahora sera 51 frames con un incremento del 15%)
    }

    // getters para que el controlador consulte valores 
    public int getNumeroNivel() {
        return numeroNivel;
    }
    public double getVelocidadDron() {
        return velocidadDron;
    }
    public double getVelocidadMisil() {
        return velocidadMisil;
    }
    public int getFrecuenciaDisparo() {
        return frecuenciaDisparo;
    }
}
