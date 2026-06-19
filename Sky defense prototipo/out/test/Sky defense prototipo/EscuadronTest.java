package test;

import modelo.Dron;
import modelo.Escuadron;
import modelo.Nivel;
import org.junit.Test;
import org.junit.Before;
import graficos.Assets;
import static org.junit.Assert.*;


public class EscuadronTest {
    private Escuadron escuadron;
    private Nivel nivel;

    @Before
    public void setUp() {
        Assets.initParaTests();
        nivel = new Nivel(1, 3.0, 5.0, 60, 15);
        escuadron = new Escuadron(nivel);
    }

    @Test
    // verifica que el escuadron se genera con exactamente 10 drones
    public void testSeCreanor10Drones() {
        assertEquals(10, escuadron.getDrones().size());
    }

    @Test
    // verifica que al iniciar nunca hay mas de 4 drones activos simultaneamente
    public void testMaximoCuatroDronesActivos() {
        int activos = 0;
        for (Dron dron : escuadron.getDrones()) {
            if (dron.isActivo()) activos++;
        }
        assertTrue(activos <= 4);
    }

    @Test
    // verifica que activarDrones nunca supera el limite de 4 drones activos
    public void testActivarDronesNoPasaDeCuatro() {
        escuadron.activarDrones();
        int activos = 0;
        for (Dron dron : escuadron.getDrones()) {
            if (dron.isActivo()) activos++;
        }
        assertTrue(activos <= 4);
    }

    @Test
    // verifica que el escuadron no se considera completado si hay drones activos
    public void testEscuadronNoCompletadoSiHayDronesActivos() {
        assertFalse(escuadron.escuadronCompletado());
    }

    @Test
    // verifica que el escuadron se considera completado cuando todos los drones terminaron su recorrido
    public void testEscuadronCompletadoCuandoTodosTerminan() {
        for (Dron dron : escuadron.getDrones()) {
            dron.completarRecorrido();
        }
        assertTrue(escuadron.escuadronCompletado());
    }

    @Test
    // verifica que al reiniciar el escuadron se generan 10 drones nuevos y no esta completado
    public void testReiniciarGeneraDronesNuevos() {
        for (Dron dron : escuadron.getDrones()) {
            dron.completarRecorrido();
        }
        escuadron.reiniciar();
        assertFalse(escuadron.escuadronCompletado());
        assertEquals(10, escuadron.getDrones().size());
    }

    @Test
    // verifica que al completar un dron activo se activa automaticamente el siguiente en espera
    public void testAlCompletarUnDronSeActivaElSiguiente() {
        // completa unos de los 4 activos
        escuadron.getDrones().get(0).completarRecorrido();
        escuadron.activarDrones();
        int activos = 0;
        for (Dron dron : escuadron.getDrones()) {
            if (dron.isActivo()) activos++;
        }
        assertEquals(4, activos);
    }
}
