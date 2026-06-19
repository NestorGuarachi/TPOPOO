package test;

import modelo.Avion;
import modelo.Jugador;
import java.awt.image.BufferedImage;
import math.Vector2D;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class MisilTest {
    private Avion avion;
    private Jugador jugador;

    @Before
    public void setUp() {
        BufferedImage imagenFalsa = new BufferedImage(50, 30, BufferedImage.TYPE_INT_ARGB);
        avion = new Avion(new Vector2D(400, 300), imagenFalsa);
        jugador = new Jugador();
    }

    // metodo auxiliar que simula la evaluacion del daño segun la distancia entre
    // la explosion y el avion, aplicando puntos y daño segun las reglas del juego
    private void evaluarExplosion(int altitudExplosion) {
        double distancia = Math.abs(altitudExplosion - avion.getAltitud());

        if (distancia > 150) {
            jugador.sumarPuntos(40);
        } else if (distancia > 80) {
            jugador.sumarPuntos(20);
            avion.perderEnergia(20);
        } else if (distancia > 20) {
            avion.perderEnergia(40);
        } else {
            jugador.perderVida();
            avion.resetEnergia();
        }
    }

    @Test
    // verifica que una explosion a mas de 150 metros otorga 40 puntos sin causar daño
    public void testExplosionLejos_ObtienePuntosSinDano() {
        evaluarExplosion(3200);
        assertEquals(40, jugador.getPuntos());
        assertEquals(100, avion.getEnergia());
    }

    @Test
    // verifica que una explosion entre 80 y 150 metros otorga 20 puntos y reduce la energia un 20%
    public void testExplosionMediana_ObtienePuntosYPierdeEnergia() {
        evaluarExplosion(3100);
        assertEquals(20, jugador.getPuntos());
        assertEquals(80, avion.getEnergia());
    }

    @Test
    // verifica que una explosion entre 20 y 80 metros no otorga puntos y reduce la energia un 40%
    public void testExplosionCerca_SinPuntosYPierdeEnergia() {
        evaluarExplosion(3050);
        assertEquals(0, jugador.getPuntos());
        assertEquals(60, avion.getEnergia());
    }

    @Test
    // verifica que una explosion a menos de 20 metros hace perder una vida al jugador
    public void testExplosionMuyCerca_PierdeVida() {
        int vidasAntes = jugador.getVidas();
        evaluarExplosion(3010);
        assertEquals(vidasAntes - 1, jugador.getVidas());
    }
}
