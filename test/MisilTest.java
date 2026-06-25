package test;

import graficos.Assets;
import modelo.Avion;
import modelo.Jugador;
import modelo.Misil;
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
        Assets.initParaTests();
        BufferedImage imagenFalsa = new BufferedImage(50, 30, BufferedImage.TYPE_INT_ARGB);
        avion = new Avion(new Vector2D(400, 300), imagenFalsa);
        jugador = new Jugador();

        // Fijar la posición Y del avión manualmente ya que update() necesita KeyBoard
        // El avión empieza en altitud 3000, lo que equivale a:
        // (5000 - 3000) / 4000.0 * (1000 - 30) = 0.5 * 970 = 485px desde arriba
        avion.getPosition().setY(485);
    }

    // Crea un misil cuya explosión ocurre exactamente en la misma Y que el avión
    // (altitud 3000 → targetY = 485px), luego lo avanza hasta que explota
    private Misil crearMisilYExplotar(double offsetY) {
        // El misil explota en altitud 3000 → targetY = 485px (mismo que el avión)
        // Para testear distintas distancias, desplazamos la X del misil
        int altitudExplosion = 3000;
        BufferedImage textura = new BufferedImage(10, 20, BufferedImage.TYPE_INT_ARGB);

        // Crear misil desde una posición por encima del punto de explosión
        Misil misil = new Misil(
                new Vector2D(400 + offsetY, 0), // offsetY simula distancia horizontal
                textura,
                altitudExplosion,
                10.0 // velocidad alta para que llegue rápido en el test
        );

        // Avanzar el misil hasta que explote
        for (int i = 0; i < 200; i++) {
            misil.update();
            if (misil.isExplotado()) break;
        }

        return misil;
    }

    @Test
    // verifica que una explosión lejos del avión otorga 40 puntos sin daño
    public void testExplosionLejos_ObtienePuntosSinDano() {
        Misil misil = crearMisilYExplotar(250); // 250px de distancia horizontal
        misil.aplicarEfecto(avion, jugador);
        assertEquals(40, jugador.getPuntos());
        assertEquals(100, avion.getEnergia());
    }

    @Test
    // verifica que una explosión a distancia media otorga 20 puntos y reduce energía 20%
    public void testExplosionMediana_ObtienePuntosYPierdeEnergia() {
        Misil misil = crearMisilYExplotar(150); // 150px de distancia horizontal
        misil.aplicarEfecto(avion, jugador);
        assertEquals(20, jugador.getPuntos());
        assertEquals(80, avion.getEnergia());
    }

    @Test
    // verifica que una explosión muy cerca no da puntos y reduce energía 40%
    public void testExplosionCerca_SinPuntosYPierdeEnergia() {
        Misil misil = crearMisilYExplotar(70); // 70px de distancia horizontal
        misil.aplicarEfecto(avion, jugador);
        assertEquals(0, jugador.getPuntos());
        assertEquals(60, avion.getEnergia());
    }

    @Test
    // verifica que una explosión encima del avión hace perder una vida
    public void testExplosionMuyCerca_PierdeVida() {
        Misil misil = crearMisilYExplotar(0); // misma X que el avión → distancia mínima
        int vidasAntes = jugador.getVidas();
        misil.aplicarEfecto(avion, jugador);
        assertEquals(vidasAntes - 1, jugador.getVidas());
    }
}