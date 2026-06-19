package test;

import modelo.Avion;
import math.Vector2D;
import java.awt.image.BufferedImage;
import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class AvionTest {
    private Avion avion;

    @Before
    public void setUp() {
        BufferedImage imagenFalsa = new BufferedImage(50, 30, BufferedImage.TYPE_INT_ARGB);
        avion = new Avion(new Vector2D(400, 300), imagenFalsa);
    }

    @Test
    // verifica que el avion comineza en la altitud inicial de 3000 mts
    public void testAltitudInicialEs3000() {
        assertEquals(3000, avion.getAltitud());
    }

    @Test
    // verifica que el avion no puede superar la altura maxima de 5000 mts
    public void testNoSuperaAltitudMaxima() {
        for (int i = 0; i < 30; i++) {
            avion.subir();
        }
        assertEquals(5000, avion.getAltitud());
    }

    @Test
    // verifica que el avion no puede bajar de la altitud minima de 1000 mts
    public void testNoBajaDeAltitudMinima() {
        for (int i = 0; i < 30; i++) {
            avion.bajar();
        }
        assertEquals(1000, avion.getAltitud());
    }

    @Test
    // verifica que perder energia descuenta correctamente el porcentaje indicado
    public void testPerderEnergia() {
        avion.perderEnergia(20);
        assertEquals(80, avion.getEnergia());
    }

    @Test
    // verifica que la energia no puede bajar por debajo de 0
    public void testEnergiaNoBajaDeCero() {
        avion.perderEnergia(200);
        assertEquals(0, avion.getEnergia());
    }

    @Test
    // verifica que resetEnergia restaura la energia al 100%
    public void testResetEnergia() {
        avion.perderEnergia(50);
        avion.resetEnergia();
        assertEquals(100, avion.getEnergia());
    }
}
