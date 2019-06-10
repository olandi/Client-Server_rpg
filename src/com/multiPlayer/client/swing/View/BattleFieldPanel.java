package com.multiPlayer.client.swing.View;

import com.multiPlayer.both.Hero.Hero;
import com.multiPlayer.both.Hero.TurnState;
import com.multiPlayer.connection.MessageObjects.HeroMovementAction;
import com.multiPlayer.client.swing.BattleFieldController;
import com.multiPlayer.client.swing.model.Hexagon;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class BattleFieldPanel extends JPanel {
    private static final Logger LOGGER = LoggerFactory.getLogger(BattleFieldPanel.class);

    private MouseListener mouseListener;
    private BattleFieldController battleFieldController;

    public BattleFieldPanel(BattleFieldController battleFieldController) {
        this.battleFieldController = battleFieldController;
        initMouseListener();
    }

    public MouseListener getMouseListener() {
        return mouseListener;
    }

    private void initMouseListener() {
        mouseListener = new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {

                LOGGER.trace("clicked at {}",e.getPoint());

                battleFieldController.getBattleField().forEach(i -> {
                    Hexagon hexagon = i.getHexagon();

                    //Todo: Косяк с некорректными координатами. У каждой панели своя система координат.
                    //гексагоны рисуются в BattleFieldPanel, а MouseListener устанавливается в MainPanel
                    Point clickPoint = e.getPoint();
                    clickPoint.y -= 40;

                    //предполагается, что игрок не может быть равен null
                    //если игрок может ходить и вообще попадаем в хексагон
                    if (battleFieldController.getPlayerHero().getTurnState().equals(TurnState.ReadyForTurn)
                            && hexagon.contains(clickPoint)) {

                        LOGGER.trace("Hexagon with center at {}", hexagon.getCenter());

                        if (battleFieldController.getModel().getHeroRangeSet().contains(i.getIndex())) {
                            LOGGER.trace("in range");

                            //Если попадаем в хексагон с героем (любым)
                            if (battleFieldController.getHeroes().values().stream()
                                    .anyMatch(hero -> hero.getPosition() == i.getIndex())) {

                                Hero targetHero = battleFieldController.getModel().getHeroByIndex(i.getIndex());

                                //5) Если попадаем не в себя
                                if (!battleFieldController.getPlayerHero().equals(targetHero)) {
                                    //становится видимой панель атаки
                                    battleFieldController.setEnemy(targetHero);
                                    battleFieldController.openHittingPanel();
                                    battleFieldController.setHittingPanelMouseListener();
                                    battleFieldController.repaintAllView();

                                }
                            } else {
                                // ПЕРСОНАЖ ХОДИТ НА СВОБОДНУЮ КЛЕТКУ (КуррентХиро = NULL  обнуляется)
                                //убираю область действия перса
                                battleFieldController.getModel().setHeroMovementRangeSelected(false);
                                i.setSelected(true);
                                battleFieldController.repaintAllView();

                                try {
                                    battleFieldController.sendMovementActionToServer(new HeroMovementAction(i.getIndex()));
                                } catch (Exception error) {
                                    LOGGER.trace("sendingMovementActionToServer error");
                                    error.printStackTrace();
                                }
                            }
                        }
                    }
                });
            }
        };
    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;


        battleFieldController.getBattleField().forEach(u -> {

            Hexagon h = u.getHexagon();

            if (!u.isSelected())
                h.draw(g2, 0, 0, 1, 30, false);
            else
                h.draw(g2, 0, 0, 2, Color.green.getRGB(), false);
        });

        battleFieldController.getHeroes().forEach((heroName, hero) -> {

            Hexagon h = battleFieldController.getBattleField().get(hero.getPosition()).getHexagon();

            if (hero.getTurnState().equals(TurnState.TurnIsFinished)) {
                h.draw(g2, 0, 0, 2, Color.blue.getRGB(), false);
            }

            g.drawImage(
                    battleFieldController.getImageDataBase().get(hero.getViewId()),
                    h.getCenter().x - 35,
                    h.getCenter().y - 35,
                    null);
        });
    }
}