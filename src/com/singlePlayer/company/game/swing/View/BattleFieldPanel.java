package com.singlePlayer.company.game.swing.View;

import com.singlePlayer.company.game.swing.Controller;
import com.singlePlayer.company.game.utils.ImageLoader;
import com.singlePlayer.company.game.Hero.Hero;
import com.singlePlayer.company.game.Hero.TurnState;

import com.singlePlayer.company.game.swing.model.Hexagon;
import com.singlePlayer.company.game.swing.model.HexagonItem;
import com.singlePlayer.company.game.Hero.heroActions.HeroMovementAction;


import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;
import java.util.Map;

public class BattleFieldPanel extends JPanel {

    private List<HexagonItem> battleField;
    private Map<Hero, Integer> heroes;

    private MouseListener mouseListener;

    private Controller controller;

    public BattleFieldPanel(Controller controller) {
        this.controller = controller;

        battleField = controller.getBattleField();
        heroes = controller.getHeroes();

        initMouseListener();

    }

    public MouseListener getMouseListener() {
        return mouseListener;
    }


    private void initMouseListener() {

        mouseListener = new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {

                System.out.println(e.getPoint());

                battleField.forEach(i -> {
                    Hexagon hexagon = i.getHexagon();

                    //Todo: Косяк с некорректными координатами. У каждой панели своя система координат.
                    //гексагоны рисуются в BattleFieldPanel, а MouseListener устанавливается в MainPanel
                    Point clickPoint = e.getPoint();
                    clickPoint.y -= 40;

                    //если вообще попадаем в хексагон
                    if (hexagon.contains(clickPoint)) {

                        System.out.println("Hexagon with center at " + hexagon.getCenter());

                        //2) Если существует куррентХеро (Если персонаж уже выделен)
                        if (controller.getCurrentHero() != null) {

                            //3) Если куррентХеро может ходить
                            if (controller.getCurrentHero().getTurnState().equals(TurnState.ReadyForTurn)) {

                                  if (heroes.containsValue(i.getIndex())){
                                      Hero targetHero = controller.getServerUtils().getHeroByIndex(i.getIndex());

                                      //5) Если попадаем не в себя
                                    if (!controller.getCurrentHero().equals(targetHero)) {

                                        //БЬЕМ ВРАГА (КуррентХиро = NULL  обнуляется)
                                        //обнуляется только после успешной атаки
                                        System.out.println("hit");

                                        //становится видимой панель атаки
                                        controller.setCurrentHero(controller.getCurrentHero());
                                        controller.setEnemy(targetHero);

                                        controller.openHittingPanel();
                                        controller.setHittingPanelMouseListener();
                                        controller.repaintAllView();



                                    } else {
                                        //ДЕЛАЕМ куррентХеро НЕ АКТИВНЫМ (КуррентХиро = NULL  обнуляется)
                                        controller.setCurrentHero(null);

                                        i.setSelected(false);
                                        controller.repaintAllView();

                                    }
                                } else {
                                    // ПЕРСОНАЖ ХОДИТ НА СВОБОДНУЮ КЛЕТКУ (КуррентХиро = NULL  обнуляется)
                                    battleField.get(heroes.get(controller.getCurrentHero())).setSelected(false);
                                    i.setSelected(true);
                                    controller.repaintAllView();
                                    //тут герой ходит на клетку вперед, кидает в стэк действие хождения на клетку
                                    //отмечает перса как походившего
                                    controller.sendMovementActionToServer(new HeroMovementAction(i.getIndex()));
                                }
                            } else {
                                controller.setCurrentHero(null);
                                //!!! ТЕОРЕТИЧЕСКИ УСЛОВИЕ НИКОГДА НЕ БУДЕТ ДОСТИГНУТО(????????)
                                //на практике нет)
                            }
                        } else {
                            // 6) Если попадаем в игрока, который может ходить
                           if (heroes.containsValue(i.getIndex())&&(
                                   controller.getServerUtils().getHeroByIndex(i.getIndex()).getTurnState().equals(TurnState.ReadyForTurn)
                                   )){
                                //Делаем этого игрока - КуррентХиро
                                controller.setCurrentHero(controller.getServerUtils().getHeroByIndex(i.getIndex()));
                                // hexagonContent.turnSelect();
                                i.setSelected(true);
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


        battleField.forEach(u -> {

            Hexagon h = u.getHexagon();

            if (!u.isSelected())
                h.draw(g2, 0, 0, 1, 30, false);
            else
                h.draw(g2, 0, 0, 2, Color.green.getRGB(), false);
        });


        heroes.forEach((hero, index) -> {

            Hexagon h = battleField.get(index).getHexagon();

            if (hero.getTurnState().equals(TurnState.TurnIsFinished)) {
                h.draw(g2, 0, 0, 2, Color.blue.getRGB(), false);
            }

            g.drawImage(ImageLoader.loadImage(
                    hero.getView()),
                    h.getCenter().x - 35,
                    h.getCenter().y - 35,
                    null);
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