package states;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import graficos.Assets;
import input.KeyBoard;
import main.Ventana;

public class SkinSelectorControlador {

    private int skinSeleccionada = Assets.skinIndex; // arranca en la que ya está elegida
    private final int TOTAL_SKINS = 3;

    private boolean volverAlMenu = false;
    private boolean confirmar = false;

    private boolean izqPresionada = false;
    private boolean derPresionada = false;
    private boolean enterPresionada = false;
    private boolean escPresionada = false;

    // Nombres para mostrar en pantalla
    private final String[] nombres = { "Red Fighter", "Blue Cruiser", "Sky Wing" };

    public void update() {
        // Navegar entre skins con izquierda/derecha
        if (KeyBoard.LEFT) {
            if (!izqPresionada) {
                skinSeleccionada = (skinSeleccionada - 1 + TOTAL_SKINS) % TOTAL_SKINS;
                izqPresionada = true;
            }
        } else {
            izqPresionada = false;
        }

        if (KeyBoard.RIGHT) {
            if (!derPresionada) {
                skinSeleccionada = (skinSeleccionada + 1) % TOTAL_SKINS;
                derPresionada = true;
            }
        } else {
            derPresionada = false;
        }

        // Confirmar selección con ENTER
        if (KeyBoard.ENTER) {
            if (!enterPresionada) {
                Assets.skinIndex = skinSeleccionada;
                Assets.skinActual = Assets.skins[skinSeleccionada];
                confirmar = true;
                enterPresionada = true;
            }
        } else {
            enterPresionada = false;
        }

        // Cancelar con ESC
        if (KeyBoard.ESC) {
            if (!escPresionada) {
                volverAlMenu = true;
                escPresionada = true;
            }
        } else {
            escPresionada = false;
        }
    }

    public void draw(Graphics g) {
        int cx = Ventana.WIDTH / 2;

        // Título
        g.setFont(new Font("Courier New", Font.BOLD, 36));
        g.setColor(Color.CYAN);
        String titulo = "SELECCIONAR NAVE";
        g.drawString(titulo, cx - g.getFontMetrics().stringWidth(titulo) / 2, 100);

        // Instrucciones
        g.setFont(new Font("Courier New", Font.PLAIN, 16));
        g.setColor(new Color(150, 150, 150));
        String inst = "← → para cambiar   |   ENTER para confirmar   |   ESC para volver";
        g.drawString(inst, cx - g.getFontMetrics().stringWidth(inst) / 2, 135);

        // Dibujar las 3 naves en fila
        int espaciadoX = Ventana.WIDTH / (TOTAL_SKINS + 1);

        for (int i = 0; i < TOTAL_SKINS; i++) {
            int x = espaciadoX * (i + 1);
            boolean esActual = (i == skinSeleccionada);

            // Marco de selección
            if (esActual) {
                g.setColor(new Color(0, 200, 255, 60));
                g.fillRoundRect(x - 70, 180, 140, 200, 14, 14);
                g.setColor(Color.CYAN);
                g.drawRoundRect(x - 70, 180, 140, 200, 14, 14);
            } else {
                g.setColor(new Color(80, 80, 80));
                g.drawRoundRect(x - 70, 180, 140, 200, 14, 14);
            }

            // Nave (centrada en el marco)
            if (Assets.skins[i] != null) {
                int imgW = 80, imgH = 80;
                g.drawImage(Assets.skins[i], x - imgW / 2, 230, imgW, imgH, null);
            }

            // Nombre de la skin
            g.setFont(new Font("Courier New", Font.BOLD, esActual ? 16 : 14));
            g.setColor(esActual ? Color.CYAN : new Color(150, 150, 150));
            String nombre = nombres[i];
            g.drawString(nombre, x - g.getFontMetrics().stringWidth(nombre) / 2, 360 + (esActual ? 0 : 2));
        }

        // Flechas navegación
        g.setFont(new Font("Courier New", Font.BOLD, 36));
        g.setColor(new Color(0, 200, 255, 150));
        g.drawString("◄", 30, 290);
        g.drawString("►", Ventana.WIDTH - 60, 290);

        // Confirmar / volver
        g.setFont(new Font("Courier New", Font.BOLD, 20));
        g.setColor(Color.GREEN);
        String conf = "[ ENTER ] Confirmar";
        g.drawString(conf, cx - g.getFontMetrics().stringWidth(conf) / 2, 430);

        g.setColor(new Color(200, 100, 100));
        String esc = "[ ESC ] Volver al menú";
        g.drawString(esc, cx - g.getFontMetrics().stringWidth(esc) / 2, 460);
    }

    public boolean debeVolverAlMenu() {
        return volverAlMenu || confirmar;
    }

}