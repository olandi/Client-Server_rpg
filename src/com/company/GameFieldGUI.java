package com.company;

import com.company.GuiUtils.ImageLoader;
import com.company.Hero.EmptyCell;
import com.company.Hero.FieldItem;
import com.company.Hero.Hero;
import com.company.ex2.Hexagon;
import com.company.gameField.GameField;
import com.company.gameField.HexagonItem;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class GameFieldGUI extends JPanel {

    //public static List<HexagonItem> list = new GameField().getGameField();
    public static List<HexagonItem> list = GameField.getGameField();


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;


        list.forEach(u -> {

            Hexagon h = u.getHexagon();
            FieldItem fi = u.getCellContent();


            if (!fi.isSelected())
                h.draw(g2, 0, 0, 1, 30, false);
            else
                h.draw(g2, 0, 0, 1, Color.green.getRGB(), false);

           // if (!EmptyCell.EMPTY_CELL.equals(fi)) {
            //TODO Переделать хардкод
            if (fi.getContentType().equals("Hero")) {

                g.drawImage(ImageLoader.loadImage(
                        ((Hero) fi).getView())

                        // HeroImages.PIRATE_PATH)
                        //   HeroImages.KNIGHT_PATH)
                        , h.getCenter().x - 35, h.getCenter().y - 35, null);

             /*   g.drawString(
                        ((Hero) u.getCellContent()).getName()
                        , h.getCenter().x - 15, h.getCenter().y);*/

            }
        });



       /* list = new ArrayList<>();
        int hexRadius = 40;
        int fieldOffset = 30;

        for (int i = 0; i < 48; i++) {

            int x = i % 8;
            int y = i / 8;

            int xx = (y % 2 == 0) ? 40 + 80 * x : 80 + 80 * x;
            int yy = 40 + 70 * y;

            list.add(new Hexagon(new Point(fieldOffset + xx, fieldOffset + yy), hexRadius));
        }
        list.forEach(u -> u.draw(g2, 0, 0, 1, 30, false));*/

    }
}
