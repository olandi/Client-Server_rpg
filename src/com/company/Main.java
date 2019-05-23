package com.company;

import com.company.Hero.FieldItem;
import com.company.Hero.Hero;
import com.company.Hero.TurnState;
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

    static TurnManager turnManager = new TurnManager();
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
            frame.add(new Timer(10), BorderLayout.NORTH);


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
                                Timer.actions.add(new MoveHero(turnItem, i));
                                turnManager.getCurrentHero().setTurnState(TurnState.TurnIsFinished);

                                turnManager.setCurrentHero(null);

                            }


                            System.out.println(turnManager);
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