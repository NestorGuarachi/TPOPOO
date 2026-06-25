package states;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import graficos.Assets;
import input.KeyBoard;
import main.Ventana;

public class MenuControlador {

    private int opcionSeleccionada = 0;
    private final int TOTAL_OPCIONES = 2;

    private boolean iniciarJuego    = false;
    private boolean irASkinSelector = false;

    private boolean arribaPresionada = false;
    private boolean abajoPresionada  = false;
    private boolean enterPresionada  = false;

    // Bloquea ENTER hasta que se suelte al menos una vez
    // Evita que un ENTER de la pantalla anterior entre directo acá
    private boolean enterBloqueado = true;

    public void update() {
        // Si ENTER sigue apretado del estado anterior, esperar que se suelte
        if (enterBloqueado) {
            if (!KeyBoard.ENTER) enterBloqueado = false;
            return;
        }

        if (KeyBoard.UP) {
            if (!arribaPresionada) {
                opcionSeleccionada = (opcionSeleccionada - 1 + TOTAL_OPCIONES) % TOTAL_OPCIONES;
                arribaPresionada = true;
            }
        } else { arribaPresionada = false; }

        if (KeyBoard.DOWN) {
            if (!abajoPresionada) {
                opcionSeleccionada = (opcionSeleccionada + 1) % TOTAL_OPCIONES;
                abajoPresionada = true;
            }
        } else { abajoPresionada = false; }

        if (KeyBoard.ENTER) {
            if (!enterPresionada) {
                if (opcionSeleccionada == 0) iniciarJuego    = true;
                if (opcionSeleccionada == 1) irASkinSelector = true;
                enterPresionada = true;
            }
        } else { enterPresionada = false; }
    }

    public void draw(Graphics g) {
        int cx = Ventana.WIDTH / 2;

        // Fondo semitransparente para que el texto se lea sobre cualquier imagen de fondo
        g.setColor(new Color(0, 0, 0, 160));
        g.fillRect(0, 0, Ventana.WIDTH, Ventana.HEIGHT);

        // Título
        g.setFont(new Font("Courier New", Font.BOLD, 64));
        g.setColor(Color.WHITE);
        String titulo = "SKY DEFENSE";
        g.drawString(titulo, cx - g.getFontMetrics().stringWidth(titulo) / 2, 180);

        // Subtítulo
        g.setFont(new Font("Courier New", Font.PLAIN, 18));
        g.setColor(new Color(150, 150, 150));
        String sub = "usá las flechas y ENTER para navegar";
        g.drawString(sub, cx - g.getFontMetrics().stringWidth(sub) / 2, 230);

        // Opciones
        String[] opciones = { "► JUGAR", "► SELECCIONAR NAVE" };
        for (int i = 0; i < opciones.length; i++) {
            boolean seleccionado = (i == opcionSeleccionada);
            int y = 380 + i * 90;

            if (seleccionado) {
                g.setColor(new Color(0, 200, 255, 80));
                g.fillRoundRect(cx - 200, y - 34, 400, 50, 10, 10);
                g.setColor(Color.CYAN);
            } else {
                g.setColor(new Color(180, 180, 180));
            }

            g.setFont(new Font("Courier New", Font.BOLD, seleccionado ? 32 : 28));
            g.drawString(opciones[i], cx - g.getFontMetrics().stringWidth(opciones[i]) / 2, y);
        }

        // Nave actual
        g.setFont(new Font("Courier New", Font.PLAIN, 14));
        g.setColor(new Color(100, 200, 100));
        String naveInfo = "Nave actual: " + Assets.nombreSkinActual();
        g.drawString(naveInfo, cx - g.getFontMetrics().stringWidth(naveInfo) / 2, 620);
    }

    public boolean debeIniciarJuego()    { return iniciarJuego; }
    public boolean debeIrASkinSelector() { return irASkinSelector; }

    public void resetFlags() {
        iniciarJuego    = false;
        irASkinSelector = false;
    }
}