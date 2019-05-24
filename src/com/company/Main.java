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

    public static int timerDuration = 10;
    public static TurnManager turnManager = new TurnManager();
    static HexagonItem turnItem;


    static GameFieldGUI gameFieldGUI = new GameFieldGUI();
    public static JFrame frame;

    public static void main(String[] args) {
        EventQueue.invokeLater(() ->
        {
            frame = new JFrame("W");
            frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            frame.setSize(800, 600);
            frame.setResizable(false);


            //frame.add(BorderLayout)

            frame.setLayout(new BorderLayout());
            frame.add(new Timer(timerDuration), BorderLayout.NORTH);


            frame.add(gameFieldGUI, BorderLayout.CENTER);

            gameFieldGUI.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    //super.mouseClicked(e);

                    //e.getPoint();

                    GameFieldGUI.list.forEach(i -> {
                        Hexagon hexagon = i.getHexagon();
                        FieldItem fieldItem = i.getCellContent();

                        //если вообще попадаем в хексагон
                        if (hexagon.contains(e.getPoint())) {

                            System.out.println(
                                    hexagon.getCenter() + " -- " +
                                            fieldItem);
                        /*

                            //если гексагон не пустой(если гексагон - герой) и герой может ходить
                           // if (!EmptyCell.EMPTY_CELL.equals(fieldItem) && (((Hero) fieldItem).getTurnState().equals(TurnState.ReadyForTurn))) {
                            if ("Hero".equals(fieldItem.getContentType()) && (((Hero) fieldItem).getTurnState().equals(TurnState.ReadyForTurn))) {

                                    //если повторно выделяется герой
                                if (((Hero) fieldItem).equals(turnManager.getCurrentHero())) {

                                    turnItem = null;
                                    turnManager.setCurrentHero(null);
                                    fieldItem.turnSelect();
                                    frame.repaint();

                                } else
                                    //если нет активного героя
                                    if (turnManager.getCurrentHero() == null) {

                                        turnItem = i;
                                        turnManager.setCurrentHero((Hero) fieldItem);

                                        fieldItem.turnSelect();
                                        frame.repaint();
                                    }

                            }

                           // if (EmptyCell.EMPTY_CELL.equals(fieldItem) &&
                            if ("EmptyCell".equals(fieldItem.getContentType()) &&
                                    turnManager.getCurrentHero() != null &&
                                    turnManager.getCurrentHero().getTurnState().equals(TurnState.ReadyForTurn)) {


                                System.out.println("------------->"+hexagon);

                                fieldItem.turnSelect();
                                frame.repaint();
                                //тут герой ходит на клетку вперед, кидает в стэк действие хождения на клетку
                                //отмечает перса как походившего
                                Timer.movementActions.add(new MoveHero(turnItem, i));
                                turnManager.getCurrentHero().setTurnState(TurnState.TurnIsFinished);

                                turnManager.setCurrentHero(null);

                            }


                            System.out.println(turnManager);
                            */

                            //Если нет активного игрока(выделенного)
                            if (turnManager.getCurrentHero() != null) {

                                //Если кликнули не на игрока //  текущий игрок может ходить
                                if (!"Hero".equals(fieldItem.getContentType())
                                      //  && turnManager.getCurrentHero().getTurnState().equals(TurnState.ReadyForTurn)
                                ) {

                                    //move
                                    fieldItem.turnSelect();
                                    frame.repaint();
                                    //тут герой ходит на клетку вперед, кидает в стэк действие хождения на клетку
                                    //отмечает перса как походившего
                                    ServerUtils.movementActions.add(new MoveHero(turnItem, i));
                                    turnManager.getCurrentHero().setTurnState(TurnState.TurnIsFinished);
                                    turnManager.setCurrentHero(null);


                                } else {
                                    //если кликнули в игрока и этот игрок является активным
                                    if ("Hero".equals(fieldItem.getContentType()) &&
                                            ((Hero) fieldItem).equals(turnManager.getCurrentHero())) {


                                        //remove selection
                                        turnItem = null;
                                        turnManager.setCurrentHero(null);
                                        fieldItem.turnSelect();
                                        frame.repaint();


                                    } else {
                                        //hit
                                        System.out.println("hit");

                                        Main1.mainn(turnManager.getCurrentHero(),(Hero) fieldItem);
                                    }
                                }
                            } else {
                                //если кликнули в игрока и он может ходить
                                if ("Hero".equals(fieldItem.getContentType())
                                        && (((Hero) fieldItem).getTurnState().equals(TurnState.ReadyForTurn))) {

                                    //select, made current
                                    turnItem = i;
                                    turnManager.setCurrentHero((Hero) fieldItem);
                                    fieldItem.turnSelect();
                                    frame.repaint();


                                } else {
                                    //doNothing
                                }

                            }


                        }
                    });
                }
            });

            frame.setVisible(true);
        });
    }
}

/*/


1) Если вообще попадаем в хексагон
    2) если попадаем в героя
        3)если герой не установлен




 */