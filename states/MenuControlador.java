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

    private boolean iniciarJuego = false;
    private boolean irASkinSelector = false;

    private boolean arribaPresionada = false;
    private boolean abajoPresionada = false;
    private boolean enterPresionada = false;

    public void update() {
        if (KeyBoard.UP) {
            if (!arribaPresionada) {
                opcionSeleccionada = (opcionSeleccionada - 1 + TOTAL_OPCIONES) % TOTAL_OPCIONES;
                arribaPresionada = true;
            }
        } else {
            arribaPresionada = false;
        }

        if (KeyBoard.DOWN) {
            if (!abajoPresionada) {
                opcionSeleccionada = (opcionSeleccionada + 1) % TOTAL_OPCIONES;
                abajoPresionada = true;
            }
        } else {
            abajoPresionada = false;
        }

        if (KeyBoard.ENTER) {
            if (!enterPresionada) {
                if (opcionSeleccionada == 0) iniciarJuego = true;
                if (opcionSeleccionada == 1) irASkinSelector = true;
                enterPresionada = true;
            }
        } else {
            enterPresionada = false;
        }
    }

    public void draw(Graphics g) {
        int cx = Ventana.WIDTH / 2;

        g.setFont(new Font("Courier New", Font.BOLD, 64));
        g.setColor(Color.WHITE);
        String titulo = "SKY DEFENSE";
        int anchoTitulo = g.getFontMetrics().stringWidth(titulo);
        g.drawString(titulo, cx - anchoTitulo / 2, 500);

        g.setFont(new Font("Courier New", Font.PLAIN, 18));
        g.setColor(new Color(150, 150, 150));
        String sub = "usá las flechas y ENTER para navegar";
        int anchoSub = g.getFontMetrics().stringWidth(sub);
        g.drawString(sub, cx - anchoSub / 2, 240);

        String[] opciones = { "► JUGAR", "► SELECCIONAR NAVE" };

        for (int i = 0; i < opciones.length; i++) {
            boolean seleccionado = (i == opcionSeleccionada);

            if (seleccionado) {
                g.setColor(new Color(0, 200, 255, 40));
                g.fillRoundRect(cx - 180, 300 + i * 80 - 30, 360, 48, 10, 10);
                g.setColor(Color.CYAN);
            } else {
                g.setColor(new Color(180, 180, 180));
            }

            g.setFont(new Font("Courier New", Font.BOLD, seleccionado ? 32 : 28));
            int anchoOpcion = g.getFontMetrics().stringWidth(opciones[i]);
            g.drawString(opciones[i], cx - anchoOpcion / 2, 300 + i * 80);
        }

        g.setFont(new Font("Courier New", Font.PLAIN, 14));
        g.setColor(new Color(100, 200, 100));
        // Usar Assets directamente gracias al import, en lugar del nombre completo graficos.Assets
        String naveInfo = "Nave actual: " + Assets.nombreSkinActual();
        int anchoInfo = g.getFontMetrics().stringWidth(naveInfo);
        g.drawString(naveInfo, cx - anchoInfo / 2, 600);
    }

    public boolean debeIniciarJuego()    { return iniciarJuego; }
    public boolean debeIrASkinSelector() { return irASkinSelector; }

    public void resetFlags() {
        iniciarJuego = false;
        irASkinSelector = false;
    }
}