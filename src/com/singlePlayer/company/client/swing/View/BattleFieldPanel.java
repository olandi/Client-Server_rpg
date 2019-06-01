package com.singlePlayer.company.client.swing.View;

import com.singlePlayer.company.client.swing.Controller;
import com.singlePlayer.company.client.utils.ImageLoader;
import com.singlePlayer.company.model.Hero.FieldItem;
import com.singlePlayer.company.model.Hero.Hero;
import com.singlePlayer.company.model.Hero.TurnState;
import com.singlePlayer.company.server.ServerUtils;

import com.singlePlayer.company.client.model.Hexagon;
import com.singlePlayer.company.model.gameField.GameField;
import com.singlePlayer.company.model.gameField.HexagonItem;
import com.singlePlayer.company.model.heroActions.MoveHero;


import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;

public class BattleFieldPanel extends JPanel {

    public static List<HexagonItem> list = GameField.getGameField();

    private MouseListener mouseListener;

    private Controller controller;

    public BattleFieldPanel(Controller controller) {
        this.controller = controller;
        initMouseListener();
    }




    public MouseListener getMouseListener() {
        return mouseListener;
    }

    public void initMouseListener() {

        mouseListener = new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {

                BattleFieldPanel.list.forEach(i -> {
                    Hexagon hexagon = i.getHexagon();
                    FieldItem fieldItem = i.getCellContent();

                    //Todo:26 Косяк с некорректными координатами
                    Point clickPoint = e.getPoint();
                    clickPoint.y -= 26;


                    //если вообще попадаем в хексагон
                    if (hexagon.contains(clickPoint)) {

                        System.out.println(
                                hexagon.getCenter() + " -- " +
                                        fieldItem);

                        //2) Если существует куррентХеро
                        if (controller.getCurrentHero() != null) {

                            //3) Если куррентХеро может ходить
                            if (controller.getCurrentHero().getTurnState().equals(TurnState.ReadyForTurn)) {

                                //4) Если попадаем в Героя
                                if ("Hero".equals(fieldItem.getContentType())) {

                                    //5) Если попадаем не в себя
                                    if (!controller.getCurrentHero().equals(fieldItem)) {

                                        //БЬЕМ ВРАГА (КуррентХиро = NULL  обнуляется)
                                        //обнуляется только после успешной атаки
                                        System.out.println("hit");

                                        /**-------------*/

                                        //становится видимой панель атаки
                                        controller.setCurrentHero(controller.getCurrentHero());
                                        //clientUtils.setHero(turnManager.getCurrentHero());
                                        controller.setEnemy((Hero) fieldItem);
                                       // clientUtils.setEnemy((Hero) fieldItem);

                                        controller.openHittingPanel();

                                       // mainGamePanel.removeCurrentMouseListener(mouseListener); // del own
                                       // mainGamePanel.removeCurrentMouseListener();

                                        controller.setHittingPanelMouseListener();

                                        controller.repaintAllView();

                                        /**------------*/

                                        //Main1.mainn(turnManager.getCurrentHero(),(Hero) fieldItem);
                                        //turnManager.setCurrentHero(null);


                                    } else {
                                        //ДЕЛАЕМ куррентХеро НЕ АКТИВНЫМ (КуррентХиро = NULL  обнуляется)
                                        controller.setCurrentHero(null);

                                        fieldItem.setSelected(false);
                                        /*fieldItem.turnSelect();*/
                                        controller.repaintAllView();

                                    }
                                } else {
                                    // ПЕРСОНАЖ ХОДИТ НА СВОБОДНУЮ КЛЕТКУ (КуррентХиро = NULL  обнуляется)

                                    controller.getCurrentHero().setSelected(false);

                                    fieldItem.setSelected(true);/*fieldItem.turnSelect();*/
                                    controller.repaintAllView();
                                    //тут герой ходит на клетку вперед, кидает в стэк действие хождения на клетку
                                    //отмечает перса как походившего
                                    ServerUtils.movementActions.add(new MoveHero(
                                            controller.getCurrentHero()
                                            , i));

                                    System.err.println(controller.getCurrentHero() + " " + i);
                                    controller.getCurrentHero().setTurnState(TurnState.TurnIsFinished);

                                    controller.setCurrentHero(null);


                                }
                            } else {
                                controller.setCurrentHero(null);
                                //!!! ТЕОРЕТИЧЕСКИ УСЛОВИЕ НИКОГДА НЕ БУДЕТ ДОСТИГНУТО(????????)
                                //на практике нет)
                            }
                        } else {
                            // 6) Если попадаем в игрока, который может ходить
                            if ("Hero".equals(fieldItem.getContentType()) &&
                                    ((Hero) fieldItem).getTurnState().equals(TurnState.ReadyForTurn)) {
                                //Делаем этого игрока - КуррентХиро
                                controller.setCurrentHero((Hero) fieldItem);
                                // fieldItem.turnSelect();
                                fieldItem.setSelected(true);
                                controller.repaintAllView();

                            } else {
                                //НИЧЕГО НЕ ДЕЛАЕМ
                            }
                        }

                    }// end if (hexagon.contains(e.getPoint()))
                });
            }
        };
    }




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

/*/


1) Если вообще попадаем в хексагон
1) то
     2) Если существует куррентХеро
     2) то
          3) Если куррентХеро может ходить
          3) то
                4) Если попадаем в Героя
                4) то
                      5) Если попадаем не в себя
                      5) то
                            БЬЕМ ВРАГА (КуррентХиро = NULL  обнуляется)
                      5) иначе
                            ДЕЛАЕМ куррентХеро НЕ АКТИВНЫМ (КуррентХиро = NULL  обнуляется)

                4) иначе
                        ПЕРСОНАЖ ХОДИТ НА СВОБОДНУЮ КЛЕТКУ (КуррентХиро = NULL  обнуляется)
          3) иначе
                   !!! ТЕОРЕТИЧЕСКИ УСЛОВИЕ НИКОГДА НЕ БУДЕТ ДОСТИГНУТО
     2) иначе
             6) Если попадаем в игрока
             6) то
                  Делаем этого игрока - КуррентХиро
             6) иначе
                  НИЧЕГО НЕ ДЕЛАЕМ
1) иначе
        НИЧЕГО НЕ ДЕЛАЕМ
 */