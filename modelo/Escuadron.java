package modelo;

import java.util.ArrayList;
import graficos.Assets;
import math.Vector2D;
import main.Ventana;

public class Escuadron {
    private ArrayList<Dron> drones;
    private Nivel nivel;

    public Escuadron(Nivel nivel) {
        this.nivel = nivel;
        drones = new ArrayList<>();
        generarDrones();
    }

    public void generarDrones() {
        drones.clear(); // limpia antes de regenerar
        for (int i = 0; i < 10; i++){
            String direccion = Math.random() < 0.5 ? "derecha" : "izquierda";
            double posX = direccion.equals("derecha") ? 0 : Ventana.WIDTH;
            Dron dron = new Dron(new Vector2D(posX, 50 + Math.random() * 100), Assets.dron, direccion, nivel.getVelocidadDron());
            dron.setActivo(false);
            drones.add(dron);
        }
        for (int i = 0; i < 4; i++) {
            drones.get(i).setActivo(true);
        }
    }
    public ArrayList<Dron> getDrones() {
        return drones;
    }
    // verifica si todos los drones estan inactivos (si el jugador sobrevivio al escuadron)
    public boolean escuadronCompletado() {

        for (Dron dron : drones) {

            if (!dron.isDestruido()) {
                return false;
            }
        }
        System.out.println("TODOS COMPLETADOS");
        return true;
    }
    // reiniciar el escuadron para el siguiente nivel
    public void reiniciar () {
        generarDrones();
    }
    
    public void activarDrones() {

        int activos = 0;

        for (Dron dron : drones) {
            if (dron.isActivo())
                activos++;
            
        }
        // activa nuevos drones si hay menos de 4, si ya hay 4 o mas, no se activa ninguno
        for (Dron dron : drones){

            if (activos >= 4) break;

            if (!dron.isActivo() && !dron.isDestruido()){
                dron.setActivo(true);
                activos++;
            }
            
        }
    }

    
    
}