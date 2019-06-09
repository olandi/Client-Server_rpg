package com.multiPlayer.both;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

public class ImageLoader {
    private final static Logger LOGGER = LoggerFactory.getLogger(ImageLoader.class);

    public static Image loadImage(URL url) {
        BufferedImage img = null;
        try {
            img = ImageIO.read(url);
            LOGGER.debug("{}, is loaded", url);

        } catch (IOException e) {
            LOGGER.error("Image loading error:", e);
            e.printStackTrace();
        }

        return img;
    }
}
