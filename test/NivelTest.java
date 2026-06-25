package test;

import modelo.Nivel;
import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class NivelTest {
    private Nivel nivel;

    @Before
    public void setUp() {
        nivel = new Nivel(1, 3.0, 5.0, 60, 15);
    }

    @Test
    // verifica que la velocidad de los drones aumenta un 15% al incrementar el nivel
    public void testVelocidadDronAumentaUnQuince() {
        double velocidadAntes = nivel.getVelocidadDron();
        nivel.incrementarVelocidades();
        assertEquals(velocidadAntes * 1.15, nivel.getVelocidadDron(), 0.001);
    }

    @Test
    // verifica que la velocidad de los misiles aumenta un 15% al incrementar el nivel
    public void testVelocidadMisilAumentaUnQuince() {
        double velocidadAntes = nivel.getVelocidadMisil();
        nivel.incrementarVelocidades();
        assertEquals(velocidadAntes * 1.15, nivel.getVelocidadMisil(), 0.001);
    }

    @Test
    // verifica que la frecuencia de disparo BAJA un 15% al incrementar el nivel
    // (menos frames entre disparos = los drones disparan más seguido)
    public void testFrecuenciaDisparoDisminuyeUnQuince() {
        int frecuenciaAntes = nivel.getFrecuenciaDisparo();
        nivel.incrementarVelocidades();
        assertEquals((int)(frecuenciaAntes * 0.85), nivel.getFrecuenciaDisparo());
    }

    @Test
    // verifica que el numero de nivel se incrementa en uno al avanzar de nivel
    public void testNumeroNivelIncrementa() {
        int nivelAntes = nivel.getNumeroNivel();
        nivel.incrementarVelocidades();
        assertEquals(nivelAntes + 1, nivel.getNumeroNivel());
    }
}