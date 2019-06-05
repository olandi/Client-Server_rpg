package com.multiPlayer.client.swing.View;

import com.multiPlayer.both.Hero.Hero;
import com.multiPlayer.both.Hero.TurnState;
import com.multiPlayer.other.MessageObjects.HeroMovementAction;
import com.multiPlayer.both.ImageLoader;
import com.multiPlayer.client.swing.Controller;
import com.multiPlayer.client.swing.model.Hexagon;
import com.multiPlayer.client.swing.model.HexagonItem;


import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;
import java.util.Map;

public class BattleFieldPanel extends JPanel {

   // private List<HexagonItem> battleField;
  //  private Map<Hero, Integer> heroes;

    private MouseListener mouseListener;

    private Controller controller;

    public BattleFieldPanel(Controller controller) {
        this.controller = controller;

      //  battleField = controller.getBattleField();
     //   heroes = controller.getHeroes();

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

                controller.getBattleField().forEach(i -> {
                    Hexagon hexagon = i.getHexagon();

                    //Todo: Косяк с некорректными координатами. У каждой панели своя система координат.
                    //гексагоны рисуются в BattleFieldPanel, а MouseListener устанавливается в MainPanel
                    Point clickPoint = e.getPoint();
                    clickPoint.y -= 40;

                    //предполагается, что игрок не может быть равен null
                    //если игрок может ходить и вообще попадаем в хексагон
                    if (controller.getPlayerHero().getTurnState().equals(TurnState.ReadyForTurn)
                            && hexagon.contains(clickPoint)) {

                        System.out.println("Hexagon with center at " + hexagon.getCenter());

                        if (controller.getModel().getHeroRangeSet().contains(i.getIndex())){
                            System.out.println("in range");

                                    //Если попадаем в персонажа, и этот персонаж - не игрока
                                  if (controller.getHeroes().containsValue(i.getIndex())){
                                      Hero targetHero = controller.getModel().getHeroByIndex(i.getIndex());

                                      //5) Если попадаем не в себя
                                    if (!controller.getPlayerHero().equals(targetHero)) {
                                        //становится видимой панель атаки

                                        controller.setCurrentHero(controller.getPlayerHero());
                                        controller.setEnemy(targetHero);
                                        controller.openHittingPanel();
                                        controller.setHittingPanelMouseListener();
                                        controller.repaintAllView();

                                    }
                                } else {
                                    // ПЕРСОНАЖ ХОДИТ НА СВОБОДНУЮ КЛЕТКУ (КуррентХиро = NULL  обнуляется)
                                      controller.getBattleField().get(controller.getHeroes().get(controller.getPlayerHero())).setSelected(false);
                                    i.setSelected(true);
                                    controller.repaintAllView();

                                      try {
                                          controller.sendMovementActionToServer(new HeroMovementAction(i.getIndex()));
                                      } catch (Exception error){
                                          System.out.println("sendMovementActionToServer error");
                                          error.printStackTrace();
                                      }
                                }
                    }}
                });
            }
        };
    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;


        controller.getBattleField().forEach(u -> {

            Hexagon h = u.getHexagon();

            if (!u.isSelected())
                h.draw(g2, 0, 0, 1, 30, false);
            else
                h.draw(g2, 0, 0, 2, Color.green.getRGB(), false);
        });

        System.out.println(controller.getHeroes()+"---------------------------------------------------");

        controller.getHeroes().forEach((hero, index) -> {

            Hexagon h = controller.getBattleField().get(index).getHexagon();

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