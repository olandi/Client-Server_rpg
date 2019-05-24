package com.company.GuiUtils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;

public class ImageLoader {

    public static Image loadImage(URL url){
        BufferedImage img = null;
        try {
            System.out.println(url);
            img = ImageIO.read(url);
        } catch (IOException e) {e.printStackTrace();
        }

        return img;
    }

}
