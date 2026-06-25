package graficos;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

// Utilidad para cargar imágenes desde disco.
// Si el archivo no existe o hay error, imprime el stack trace y devuelve null.
// Assets verifica null antes de usar cualquier imagen.
public class Loader {
    public static BufferedImage ImageLoader(String path) {
        try {
            return ImageIO.read(new File(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}