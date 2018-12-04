package lc.usualTest;

import com.sun.imageio.plugins.png.PNGImageReader;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public class genAsciiImage {
    public static void main(String[] args) throws IOException {
        // ImageReader ir=new PNGImageader(new Im)

        BufferedImage bufferedImage=ImageIO.read(new File("src/main/cn/1.png"));
        System.out.println(bufferedImage);
    }
}
