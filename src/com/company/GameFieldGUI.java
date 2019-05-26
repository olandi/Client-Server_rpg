package com.company;

import com.company.BattleView.Battle;
import com.company.GuiUtils.ImageLoader;
import com.company.Hero.EmptyCell;
import com.company.Hero.FieldItem;
import com.company.Hero.Hero;
import com.company.Hero.TurnState;
import com.company.Timer.ServerUtils;
import com.company.clientUtils.ClientUtils;
import com.company.combatLog.CombatLog;
import com.company.ex2.Hexagon;
import com.company.gameField.GameField;
import com.company.gameField.HexagonItem;
import com.company.heroActions.MoveHero;
import com.company.turnManager.TurnManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;

public class GameFieldGUI extends JPanel {

    public static List<HexagonItem> list = GameField.getGameField();
    public static CombatLog combatLog = new CombatLog();
    public static TurnManager turnManager = new TurnManager();
    public static ClientUtils clientUtils = new ClientUtils();

    private MouseListener mouseListener = initFieldMouseListener();
    private JPanel battleFieldPanel;
    private Battle battle;


    public GameFieldGUI(JPanel battleFieldPanel,Battle battle ) {
        this.battleFieldPanel = battleFieldPanel;
        this.battle = battle;
        //TODO
        ClientUtils.setMouseListener(mouseListener);
    }


    public static TurnManager getTurnManager() {
        return turnManager;
    }

    public MouseListener getMouseListener() {
        return mouseListener;
    }

    private MouseListener initFieldMouseListener() {
        return new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {

                GameFieldGUI.list.forEach(i -> {
                    Hexagon hexagon = i.getHexagon();
                    FieldItem fieldItem = i.getCellContent();

                    //Todo:26
                    Point clickPoint = e.getPoint();
                    clickPoint.y-=26;


                    //если вообще попадаем в хексагон
                    if (hexagon.contains(clickPoint)) {

                        System.out.println(
                                hexagon.getCenter() + " -- " +
                                        fieldItem);

                        //2) Если существует куррентХеро
                        if (turnManager.getCurrentHero() != null){

                            //3) Если куррентХеро может ходить
                            if (turnManager.getCurrentHero().getTurnState().equals(TurnState.ReadyForTurn)){

                                //4) Если попадаем в Героя
                                if ("Hero".equals(fieldItem.getContentType())){

                                    //5) Если попадаем не в себя
                                    if (!turnManager.getCurrentHero().equals(fieldItem)){

                                        //БЬЕМ ВРАГА (КуррентХиро = NULL  обнуляется)
                                        //обнуляется только после успешной атаки
                                        System.out.println("hit");

                                        /**-------------*/

                                        //становится видимой панель атаки
                                        clientUtils.setHero(turnManager.getCurrentHero());
                                        clientUtils.setEnemy((Hero) fieldItem);
                                        battle.enableBattleFrame();
                                        removeMouseListeners();
                                        battleFieldPanel.repaint();

                                        battleFieldPanel.addMouseListener(battle.getBattleMouseListener());

                                        /**------------*/

                                        //Main1.mainn(turnManager.getCurrentHero(),(Hero) fieldItem);
                                        //turnManager.setCurrentHero(null);


                                    }
                                    else {
                                        //ДЕЛАЕМ куррентХеро НЕ АКТИВНЫМ (КуррентХиро = NULL  обнуляется)
                                        turnManager.setCurrentHero(null);

                                        fieldItem.setSelected(false);
                                        /*fieldItem.turnSelect();*/
                                        battleFieldPanel.repaint();

                                    }
                                }
                                else {
                                    // ПЕРСОНАЖ ХОДИТ НА СВОБОДНУЮ КЛЕТКУ (КуррентХиро = NULL  обнуляется)

                                    turnManager.getCurrentHero().setSelected(false);

                                    fieldItem.setSelected(true);/*fieldItem.turnSelect();*/
                                    battleFieldPanel.repaint();
                                    //тут герой ходит на клетку вперед, кидает в стэк действие хождения на клетку
                                    //отмечает перса как походившего
                                    ServerUtils.movementActions.add(new MoveHero(
                                            turnManager.getCurrentHero()
                                            , i));

                                    System.err.println(turnManager.getCurrentHero()+" "+ i);
                                    turnManager.getCurrentHero().setTurnState(TurnState.TurnIsFinished);

                                    turnManager.setCurrentHero(null);


                                }
                            }
                            else {
                                turnManager.setCurrentHero(null);
                                //!!! ТЕОРЕТИЧЕСКИ УСЛОВИЕ НИКОГДА НЕ БУДЕТ ДОСТИГНУТО(????????)
                                //на практике нет)
                            }
                        }
                        else {
                            // 6) Если попадаем в игрока, который может ходить
                            if ("Hero".equals(fieldItem.getContentType())&&
                                    ((Hero)fieldItem).getTurnState().equals(TurnState.ReadyForTurn)){
                                //Делаем этого игрока - КуррентХиро
                                turnManager.setCurrentHero((Hero) fieldItem);
                                // fieldItem.turnSelect();
                                fieldItem.setSelected(true);
                                battleFieldPanel.repaint();

                            }
                            else {
                                //НИЧЕГО НЕ ДЕЛАЕМ
                            }
                        }

                    }// end if (hexagon.contains(e.getPoint()))
                });
            }
        };
    }



public void removeMouseListeners(){

        battleFieldPanel.removeMouseListener(battleFieldPanel.getMouseListeners()[0]);

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