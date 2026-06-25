package states;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import graficos.Assets;
import input.KeyBoard;
import main.Ventana;

public class SkinSelectorControlador {

    private int skinSeleccionada   = Assets.skinIndex;
    private final int TOTAL_SKINS  = Assets.skins.length;

    private boolean volverAlMenu    = false;
    private boolean confirmar       = false;
    private boolean izqPresionada   = false;
    private boolean derPresionada   = false;
    private boolean enterPresionada = false;
    private boolean escPresionada   = false;

    // Bloquea ENTER hasta que se suelte (viene apretado del menú)
    private boolean enterBloqueado = true;

    public void update() {
        if (enterBloqueado) {
            if (!KeyBoard.ENTER) enterBloqueado = false;
            return;
        }

        if (KeyBoard.LEFT) {
            if (!izqPresionada) {
                skinSeleccionada = (skinSeleccionada - 1 + TOTAL_SKINS) % TOTAL_SKINS;
                izqPresionada = true;
            }
        } else { izqPresionada = false; }

        if (KeyBoard.RIGHT) {
            if (!derPresionada) {
                skinSeleccionada = (skinSeleccionada + 1) % TOTAL_SKINS;
                derPresionada = true;
            }
        } else { derPresionada = false; }

        if (KeyBoard.ENTER) {
            if (!enterPresionada) {
                Assets.skinIndex  = skinSeleccionada;
                Assets.skinActual = Assets.skins[skinSeleccionada];
                Assets.jugador    = Assets.skinActual;
                confirmar = true;
                enterPresionada = true;
            }
        } else { enterPresionada = false; }

        if (KeyBoard.ESC) {
            if (!escPresionada) {
                volverAlMenu = true;
                escPresionada = true;
            }
        } else { escPresionada = false; }
    }

    public void draw(Graphics g) {
        int cx = Ventana.WIDTH / 2;

        // Fondo oscuro para que se vea sobre cualquier imagen de fondo
        g.setColor(new Color(0, 0, 0, 160));
        g.fillRect(0, 0, Ventana.WIDTH, Ventana.HEIGHT);

        // Título
        g.setFont(new Font("Courier New", Font.BOLD, 36));
        g.setColor(Color.CYAN);
        String titulo = "SELECCIONAR NAVE";
        g.drawString(titulo, cx - g.getFontMetrics().stringWidth(titulo) / 2, 100);

        // Instrucciones
        g.setFont(new Font("Courier New", Font.PLAIN, 16));
        g.setColor(new Color(150, 150, 150));
        String inst = "← → para cambiar   |   ENTER para confirmar   |   ESC para volver";
        g.drawString(inst, cx - g.getFontMetrics().stringWidth(inst) / 2, 140);

        // Las 3 naves en fila
        int espaciadoX = Ventana.WIDTH / (TOTAL_SKINS + 1);
        int marcoW = 140, marcoH = 200;

        for (int i = 0; i < TOTAL_SKINS; i++) {
            int x = espaciadoX * (i + 1);
            int y = 180;
            boolean esActual = (i == skinSeleccionada);

            if (esActual) {
                g.setColor(new Color(0, 200, 255, 60));
                g.fillRoundRect(x - marcoW / 2, y, marcoW, marcoH, 14, 14);
                g.setColor(Color.CYAN);
                g.drawRoundRect(x - marcoW / 2, y, marcoW, marcoH, 14, 14);
            } else {
                g.setColor(new Color(80, 80, 80));
                g.drawRoundRect(x - marcoW / 2, y, marcoW, marcoH, 14, 14);
            }

            if (Assets.skins[i] != null) {
                int imgW = 90, imgH = 90;
                g.drawImage(Assets.skins[i], x - imgW / 2, y + 30, imgW, imgH, null);
            } else {
                g.setColor(Color.RED);
                g.setFont(new Font("Courier New", Font.BOLD, 12));
                g.drawString("? imagen", x - 30, y + 80);
            }

            g.setFont(new Font("Courier New", Font.BOLD, esActual ? 16 : 13));
            g.setColor(esActual ? Color.CYAN : new Color(150, 150, 150));
            String nombre = Assets.skinNombres[i];
            g.drawString(nombre, x - g.getFontMetrics().stringWidth(nombre) / 2, y + 160);
        }

        // Flechas
        g.setFont(new Font("Courier New", Font.BOLD, 36));
        g.setColor(new Color(0, 200, 255, 180));
        g.drawString("◄", 10, 295);
        g.drawString("►", Ventana.WIDTH - 50, 295);

        // Botones
        g.setFont(new Font("Courier New", Font.BOLD, 20));
        g.setColor(Color.GREEN);
        String conf = "[ ENTER ] Confirmar";
        g.drawString(conf, cx - g.getFontMetrics().stringWidth(conf) / 2, 440);
        g.setColor(new Color(200, 100, 100));
        String esc = "[ ESC ] Volver al menu";
        g.drawString(esc, cx - g.getFontMetrics().stringWidth(esc) / 2, 480);
    }

    public boolean debeVolverAlMenu() {
        return volverAlMenu || confirmar;
    }
}