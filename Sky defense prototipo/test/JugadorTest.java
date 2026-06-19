package test;

import modelo.Jugador;
import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class JugadorTest {
    private Jugador jugador;

    @Before
    public void setUp() {
        jugador = new Jugador();
    }
    @Test
    // verifica que sumar puntos incrementa correctamente el puntaje
    public void testSumarPuntosNormal() {
        jugador.sumarPuntos(40);
        assertEquals(40, jugador.getPuntos());
    }
    @Test
    // verifica que al alcanzar 1000 puntos se otorga una vida extra
    public void testVidaExtraAl1000Puntos() {
        int vidasAntes = jugador.getVidas();
        jugador.sumarPuntos(1000);
        assertEquals(vidasAntes + 1, jugador.getVidas());
    }
    @Test
    // verifica que al alcanzar 2000 puntos se otorgan dos vidas extra en total
    public void testVidaExtraAl2000Puntos() {
        int vidasAntes = jugador.getVidas();
        jugador.sumarPuntos(2000);
        assertEquals(vidasAntes + 2, jugador.getVidas());
    }

    @Test
    // verifica que los puntos no se descuentan al otorgar una vida extra
        public void testPuntosNoSeDescuentanAlDarVidaExtra() {
        jugador.sumarPuntos(1000);
        assertEquals(1000, jugador.getPuntos());
    }

    @Test
    // verifica que perder una vida decrementa el contador correctamente
    public void testPerderVida() {
        int vidasAntes = jugador.getVidas();
        jugador.perderVida();
        assertEquals(vidasAntes - 1, jugador.getVidas());
    }
    @Test
    // verifica que el jugador esta vivo cuando tiene vidas positivas
    public void testEstaVivoConVidasPositivas() {
        assertTrue(jugador.estaVivo());
    }

    @Test
    // verifica que el jugador no esta vivo cuando sus vidas llegan a 0
    public void testNoEstaVivoSinVidas() {
        jugador.perderVida();
        jugador.perderVida();
        jugador.perderVida();
        assertFalse(jugador.estaVivo());
    }
    @Test
    // verifica que las vbidas no pueden bajar por debajo de 0
    public void testVidasNoBajanDeCero() {
        jugador.perderVida();
        jugador.perderVida();
        jugador.perderVida();
        jugador.perderVida(); // una de mas
        assertEquals(0, jugador.getVidas());
    }
}
