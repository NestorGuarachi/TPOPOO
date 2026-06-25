package input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyBoard implements KeyListener {

    private boolean[] keys = new boolean[256];

    // Teclas disponibles para el juego
    public static boolean UP, DOWN, LEFT, RIGHT;
    public static boolean ENTER, ESC;

    public void update() {
        UP    = keys[KeyEvent.VK_UP];
        DOWN  = keys[KeyEvent.VK_DOWN];
        LEFT  = keys[KeyEvent.VK_LEFT];
        RIGHT = keys[KeyEvent.VK_RIGHT];
        ENTER = keys[KeyEvent.VK_ENTER];
        ESC   = keys[KeyEvent.VK_ESCAPE];
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() < 256)
            keys[e.getKeyCode()] = true;
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() < 256)
            keys[e.getKeyCode()] = false;
    }

    @Override
    public void keyTyped(KeyEvent e) {}
}