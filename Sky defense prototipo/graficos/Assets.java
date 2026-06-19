package graficos;

import java.awt.image.BufferedImage;

public class Assets {

    public static BufferedImage jugador;
    public static BufferedImage dron;
    public static BufferedImage misil;
    

  
    public static void init() {
        jugador = Loader.ImageLoader("recursos/ships/playerShip3_red.png");
        dron = Loader.ImageLoader("recursos/ships/spaceStation_030.png");
        misil = Loader.ImageLoader("recursos/ships/spaceMissiles_006.png");

    }
    public static void initParaTests(){
        jugador = new BufferedImage(50, 30, BufferedImage.TYPE_INT_ARGB);
        dron = new BufferedImage(50, 30, BufferedImage.TYPE_INT_ARGB);
        misil = new BufferedImage(10, 20, BufferedImage.TYPE_INT_ARGB);
    }
}
