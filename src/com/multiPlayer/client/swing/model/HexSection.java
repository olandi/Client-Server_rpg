package com.multiPlayer.client.swing.model;



import com.multiPlayer.both.Hero.BodyParts;

import java.awt.*;
import java.util.Arrays;
import java.util.List;

public class HexSection extends Polygon {

    private static final int SIDES = 4;
    private boolean isFilled = false;
    private BodyParts bodyPart;

    private HexSection(BodyParts bodyPart, int[] xpoints, int[] ypoints) {
        super(xpoints, ypoints, SIDES);
        this.bodyPart = bodyPart;
    }

    public BodyParts getBodyPart() {
        return bodyPart;
    }

    public boolean isFilled() {
        return isFilled;
    }

    public void turn() {
        isFilled = !isFilled;
    }

    public void setFilled(boolean filled) {
        isFilled = filled;
    }


    //просто рисует полу-гексагон опр. цветом
    public void draw(Graphics2D g, int x, int y, int lineThickness, int colorValue, boolean filled) {
        // Store before changing.
        Stroke tmpS = g.getStroke();
        Color tmpC = g.getColor();

        g.setColor(new Color(colorValue));
        g.setStroke(new BasicStroke(lineThickness, BasicStroke.CAP_SQUARE, BasicStroke.JOIN_MITER));

        if (filled) {
            g.fillPolygon(xpoints, ypoints, npoints);
        } else
            g.drawPolygon(xpoints, ypoints, npoints);

        //контур
        g.setColor(Color.BLACK);
        g.drawPolygon(xpoints, ypoints, npoints);

        // Set values to previous when done.
        g.setColor(tmpC);
        g.setStroke(tmpS);
    }

    @Override
    public String toString() {
        return "HexSection: " + getClass().getName() + "@" + Integer.toHexString(hashCode());
    }


    public static List<HexSection> createTwoHexSections(BodyParts bodyPart, Point center, int radius, int rotation) {
        Hexagon h = new Hexagon(center, radius, rotation);
        Point[] points = h.getPoints();

        return Arrays.asList(
                new HexSection(bodyPart,
                        new int[]{points[1].x, points[2].x, points[3].x, points[4].x},
                        new int[]{points[1].y, points[2].y, points[3].y, points[4].y}),
                new HexSection(bodyPart,
                        new int[]{points[4].x, points[5].x, points[0].x, points[1].x},
                        new int[]{points[4].y, points[5].y, points[0].y, points[1].y})
        );
    }

}
