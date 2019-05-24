package com.company.ex2;

import com.company.BattleView.BodyParts;

import java.awt.*;
import java.util.Arrays;

public class HexSection extends Polygon {
    // private int[] xpoints;
    //  private int[] ypoints;
    private static final int SIDES = 4;
    private BodyParts bodyParts = null;

    public BodyParts getBodyParts() {
        return bodyParts;
    }

    public void setBodyParts(BodyParts bodyParts) {
        this.bodyParts = bodyParts;
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

    private boolean isFilled = false;

    public HexSection(int[] xpoints, int[] ypoints) {
        super(xpoints, ypoints, SIDES);
    }


    public void draw(Graphics2D g, int x, int y, int lineThickness, int colorValue, boolean filled) {
        // Store before changing.
        Stroke tmpS = g.getStroke();
        Color tmpC = g.getColor();

        g.setColor(new Color(colorValue));
        g.setStroke(new BasicStroke(lineThickness, BasicStroke.CAP_SQUARE, BasicStroke.JOIN_MITER));

        if (filled)
            g.fillPolygon(xpoints, ypoints, npoints);
        else
            g.drawPolygon(xpoints, ypoints, npoints);

        // Set values to previous when done.
        g.setColor(tmpC);
        g.setStroke(tmpS);
    }

    @Override
    public String toString() {
        return "HexSection: " + getClass().getName() + "@" + Integer.toHexString(hashCode()) + " {" +
                "bodyParts=" + bodyParts +
                '}';
    }
}
