package modelo;

import java.util.ArrayList;
import graficos.Assets;
import math.Vector2D;
import main.Ventana;

public class Escuadron {
    private ArrayList<Dron> drones;
    private Nivel nivel;
    private static final int MAX_ACTIVOS = 4;
    private static final double SEPARACION_MIN_Y = 80; // píxeles mínimos entre drones

    public Escuadron(Nivel nivel) {
        this.nivel = nivel;
        drones = new ArrayList<>();
        generarDrones();
    }

    public void generarDrones() {
        drones.clear();
        for (int i = 0; i < 10; i++) {
            String direccion = Math.random() < 0.5 ? "derecha" : "izquierda";
            double posX = direccion.equals("derecha") ? 0 : Ventana.WIDTH;
            // Rango Y ampliado: 30 a 300px para más variedad visual
            double posY = 30 + Math.random() * 270;
            Dron dron = new Dron(new Vector2D(posX, posY), Assets.dron, direccion, nivel.getVelocidadDron());
            dron.setActivo(false);
            drones.add(dron);
        }
        // Activar los primeros 4 con separación garantizada
        int activados = 0;
        for (Dron dron : drones) {
            if (activados >= MAX_ACTIVOS) break;
            if (hayEspacioSuficiente(dron)) {
                dron.setActivo(true);
                activados++;
            }
        }
    }

    // Verifica que el candidato tenga al menos SEPARACION_MIN_Y con cada dron ya activo
    private boolean hayEspacioSuficiente(Dron candidato) {
        for (Dron dron : drones) {
            if (dron.isActivo()) {
                double distY = Math.abs(dron.getPosition().getY() - candidato.getPosition().getY());
                if (distY < SEPARACION_MIN_Y) return false;
            }
        }
        return true;
    }

    public ArrayList<Dron> getDrones() {
        return drones;
    }

    public boolean escuadronCompletado() {
        for (Dron dron : drones) {
            if (!dron.isDestruido()) return false;
        }
        return true;
    }

    public void reiniciar() {
        generarDrones();
    }

    public void activarDrones() {
        int activos = 0;
        for (Dron dron : drones) {
            if (dron.isActivo()) activos++;
        }
        if (activos >= MAX_ACTIVOS) return;

        for (Dron dron : drones) {
            if (activos >= MAX_ACTIVOS) break;
            if (!dron.isActivo() && !dron.isDestruido()) {
                // Solo activa si hay separación visual suficiente
                if (hayEspacioSuficiente(dron)) {
                    dron.setActivo(true);
                    activos++;
                }
            }
        }
    }
}