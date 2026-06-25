package graficos;

import java.awt.image.BufferedImage;

public class Assets {

    public static BufferedImage jugador; // compatibilidad con tests
    public static BufferedImage dron;
    public static BufferedImage misil;
    public static BufferedImage fondo;

    // Las 3 skins jugables — agregá o cambiá rutas acá
    public static BufferedImage[] skins = new BufferedImage[3];
    public static String[] skinNombres  = {
            "Red Fighter",
            "Blue Cruiser",
            "Sky Wing"
    };

    public static int skinIndex = 0;
    public static BufferedImage skinActual;

    public static void init() {
        dron  = Loader.ImageLoader("recursos/ships/enemy_C.png");
        misil = Loader.ImageLoader("recursos/ships/spaceMissiles_006.png");
        fondo = Loader.ImageLoader("recursos/fondos/fondo.jpg");

        // ── Skins jugables ──────────────────────────────────────────
        skins[0] = Loader.ImageLoader("recursos/ships/nave1.png");
        skins[1] = Loader.ImageLoader("recursos/ships/playerShip3_red.png");
        skins[2] = Loader.ImageLoader("recursos/ships/ship_L.png");
        // ────────────────────────────────────────────────────────────

        skinActual = skins[skinIndex];
        jugador    = skinActual;
    }

    public static String nombreSkinActual() {
        return skinNombres[skinIndex];
    }

    public static void initParaTests() {
        jugador     = new BufferedImage(50, 30, BufferedImage.TYPE_INT_ARGB);
        dron        = new BufferedImage(50, 30, BufferedImage.TYPE_INT_ARGB);
        misil       = new BufferedImage(10, 20, BufferedImage.TYPE_INT_ARGB);
        skins[0]    = jugador;
        skins[1]    = jugador;
        skins[2]    = jugador;
        skinActual  = jugador;
    }
}