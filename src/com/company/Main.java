package com.company;

import com.company.BattleView.Main1;
import com.company.Hero.FieldItem;
import com.company.Hero.Hero;
import com.company.Hero.TurnState;
import com.company.Timer.ServerUtils;
import com.company.Timer.Timer;
import com.company.ex2.Hexagon;
import com.company.gameField.HexagonItem;
import com.company.heroActions.MoveHero;
import com.company.turnManager.TurnManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Main {

    public static int timerDuration = 40;
    public static TurnManager turnManager = new TurnManager();

    static GameFieldGUI gameFieldGUI = new GameFieldGUI();
    public static JFrame frame;

    public static TurnManager getTurnManager() {
        return turnManager;
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() ->
        {
            frame = new JFrame("W");
            frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            frame.setSize(800, 800);
            frame.setResizable(true);

            frame.setLayout(new BorderLayout());
            frame.add(new Timer(timerDuration), BorderLayout.NORTH);

            frame.add(gameFieldGUI, BorderLayout.CENTER);

            frame.add(GameFieldGUI.combatLog.getMiddlePanel(),BorderLayout.SOUTH);

            gameFieldGUI.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {

                    GameFieldGUI.list.forEach(i -> {
                        Hexagon hexagon = i.getHexagon();
                        FieldItem fieldItem = i.getCellContent();

                        //если вообще попадаем в хексагон
                        if (hexagon.contains(e.getPoint())) {

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

                                            Main1.mainn(turnManager.getCurrentHero(),(Hero) fieldItem);
                                            //turnManager.setCurrentHero(null);


                                        }
                                        else {
                                            //ДЕЛАЕМ куррентХеро НЕ АКТИВНЫМ (КуррентХиро = NULL  обнуляется)
                                            turnManager.setCurrentHero(null);

                                           fieldItem.setSelected(false);
                                            /*fieldItem.turnSelect();*/
                                            frame.repaint();

                                        }
                                    }
                                    else {
                                        // ПЕРСОНАЖ ХОДИТ НА СВОБОДНУЮ КЛЕТКУ (КуррентХиро = NULL  обнуляется)

                                        turnManager.getCurrentHero().setSelected(false);

                                        fieldItem.setSelected(true);/*fieldItem.turnSelect();*/
                                        frame.repaint();
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
                                    frame.repaint();

                                }
                                else {
                                    //НИЧЕГО НЕ ДЕЛАЕМ
                                }
                            }

                        }// end if (hexagon.contains(e.getPoint()))
                    });
                }
            });

            //battleFrame.pack();
            frame.setVisible(true);
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