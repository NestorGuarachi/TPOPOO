package graficos;

import java.awt.image.BufferedImage;

public class Assets {

    public static BufferedImage jugador; // se mantiene por compatibilidad con tests
    public static BufferedImage dron;
    public static BufferedImage misil;
    public static BufferedImage fondo;

    // Array con las 3 skins disponibles
    public static BufferedImage[] skins = new BufferedImage[3];

    // Índice y referencia de la skin elegida actualmente
    public static int skinIndex = 0;
    public static BufferedImage skinActual;

    public static void init() {
        // Sprites del juego
        dron  = Loader.ImageLoader("recursos/ships/spaceStation_030.png");
        misil = Loader.ImageLoader("recursos/ships/spaceMissiles_006.png");
        fondo = Loader.ImageLoader("recursos/fondos/fondo.jpg");

        // Cargar las 3 skins jugables
        skins[0] = Loader.ImageLoader("recursos/ships/playerShip3_red.png");
        skins[1] = Loader.ImageLoader("recursos/ships/player.png");
        skins[2] = Loader.ImageLoader("recursos/ships/ship_L.png");

        // La skin activa empieza siendo la primera
        skinActual = skins[skinIndex];

        // jugador apunta a skinActual para no romper compatibilidad
        jugador = skinActual;
    }

    // Devuelve el nombre legible de la skin actual (para mostrar en el menú)
    public static String nombreSkinActual() {
        String[] nombres = { "Red Fighter", "Blue Cruiser", "Sky Wing" };
        return nombres[skinIndex];
    }

    public static void initParaTests() {
        jugador    = new BufferedImage(50, 30, BufferedImage.TYPE_INT_ARGB);
        dron       = new BufferedImage(50, 30, BufferedImage.TYPE_INT_ARGB);
        misil      = new BufferedImage(10, 20, BufferedImage.TYPE_INT_ARGB);
        skins[0]   = jugador;
        skins[1]   = jugador;
        skins[2]   = jugador;
        skinActual = jugador;
    }
}