package com.company.GuiUtils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImageLoader {

    public static Image loadImage(String uri){
        BufferedImage img = null;
        try {
            img = ImageIO.read(new File(uri));
        } catch (IOException e) {e.printStackTrace();
        }

        return img;
    }

}
