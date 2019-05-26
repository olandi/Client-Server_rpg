package com.company;

import com.company.GuiUtils.ImageLoader;
import com.company.Hero.EmptyCell;
import com.company.Hero.FieldItem;
import com.company.Hero.Hero;
import com.company.Hero.TurnState;
import com.company.combatLog.CombatLog;
import com.company.ex2.Hexagon;
import com.company.gameField.GameField;
import com.company.gameField.HexagonItem;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class GameFieldGUI extends JPanel {

    public static List<HexagonItem> list = GameField.getGameField();
    public static CombatLog combatLog = new CombatLog();

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        // this.setSize(800, 600);

        list.forEach(u -> {

            Hexagon h = u.getHexagon();
            FieldItem fi = u.getCellContent();

            if (!fi.isSelected()) {

                if ("Hero".equals(fi.getContentType())
                        && ((Hero) fi).getTurnState().equals(TurnState.TurnIsFinished)) {

                    h.draw(g2, 0, 0, 2, Color.blue.getRGB(), false);
                } else
                    h.draw(g2, 0, 0, 1, 30, false);

            } else
                h.draw(g2, 0, 0, 2, Color.green.getRGB(), false);


            //TODO Переделать хардкод HERO
            if (fi.getContentType().equals("Hero")) {

                g.drawImage(ImageLoader.loadImage(
                        ((Hero) fi).getView()),
                        h.getCenter().x - 35,
                        h.getCenter().y - 35,
                        null);
            }
        });

    }
}
