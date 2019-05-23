package com.company.ex2;

import java.awt.*;

public class BattleHexagon extends Hexagon {

    private HexSection leftSegment;
    private HexSection rightSegment;


    public BattleHexagon(Point center, int radius) {
        super(center, radius);

        Point[] points = getPoints();

        leftSegment = new HexSection(
                new int[]{points[1].x, points[2].x, points[3].x, points[4].x},
                new int[]{points[1].y, points[2].y, points[3].y, points[4].y});

        rightSegment = new HexSection(
                new int[]{points[4].x, points[5].x, points[0].x, points[1].x},
                new int[]{points[4].y, points[5].y, points[0].y, points[1].y});
    }
    public HexSection getLeftSegment() {
        return leftSegment;
    }

    public HexSection getRightSegment() {
        return rightSegment;
    }
    @Override
    protected void updatePoints() {
        super.updatePoints();
        Point[] points = getPoints();
        leftSegment = new HexSection(
                new int[]{points[1].x, points[2].x, points[3].x, points[4].x},
                new int[]{points[1].y, points[2].y, points[3].y, points[4].y});

        rightSegment = new HexSection(
                new int[]{points[4].x, points[5].x, points[0].x, points[1].x},
                new int[]{points[4].y, points[5].y, points[0].y, points[1].y});

    }


}
