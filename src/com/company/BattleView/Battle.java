package com.company.BattleView;

import com.company.GuiUtils.ImageLoader;
import com.company.Hero.FieldItem;
import com.company.Hero.Hero;
import com.company.ex2.BattleHexagon;
import com.company.ex2.HexSection;
import com.company.ex2.Hexagon;
import com.company.gameField.GameField;
import com.company.gameField.HexagonItem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

public class Battle extends JPanel {

    private static final int ACTION_COUNT = 2;
    private Deque<BattleHexagon> hexagonAttackList = new ArrayDeque<>();
    private Deque<BattleHexagon> hexagonDefenseList = new ArrayDeque<>();

    private Deque<HexSection> sectionsAttackList = new ArrayDeque<>();
    private Deque<HexSection> sectionsDefenseList = new ArrayDeque<>();

    private Hexagon go = new Hexagon(new Point(400,400),40);

    private Deque<HexSection> secAttack = new ArrayDeque<>();
    private Deque<HexSection> secDef = new ArrayDeque<>();

    public void addHeroSelection(HexSection hexSection, Deque<HexSection> sectionsList) {
        if (sectionsList.size() < ACTION_COUNT)
            sectionsList.add(hexSection);
    }

    public void removeHeroSelection(HexSection hexSection, Deque<HexSection> sectionsList) {
        if (!sectionsList.isEmpty())
            sectionsList.remove(hexSection);
    }

    public Battle() {
        super();
        initAttackAndDefense();

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
               // e.getPoint();

                //если кликаем по атака хексам
                sectionsAttackList.forEach(i -> {
                    if (i.contains(e.getPoint())) {
                        System.out.println(i);

                        if (i.isFilled()) {
                            removeHeroSelection(i, secAttack);
                            i.turn();
                        } else {
                            if (secAttack.size() < ACTION_COUNT) {
                                addHeroSelection(i, secAttack);
                                i.turn();
                            }
                        }
                        repaint();

                    }

                });

                //если кликаем по блок хексам
                sectionsDefenseList.forEach(i -> {
                    if (i.contains(e.getPoint())) {
                        System.out.println(i);

                        if (i.isFilled()) {
                            removeHeroSelection(i, secDef);
                            i.turn();
                        } else {
                            if (secDef.size() < ACTION_COUNT) {
                                addHeroSelection(i, secDef);
                                i.turn();
                            }
                        }
                        repaint();

                    }

                });


                if (go.contains(e.getPoint())) {
                    System.out.println(secAttack);
                    System.out.println(secDef);
                }



            }
        });
    }

    public Deque<BattleHexagon> getHexagonAttackList() {
        return hexagonAttackList;
    }

    public Deque<BattleHexagon> getHexagonDefenseList() {
        return hexagonDefenseList;
    }


    public void initAttackAndDefense() {
        int xOffset = 200;
        int yOffset = 100;


        BattleHexagon head = new BattleHexagon(new Point(xOffset + 40, yOffset + 40), 40);
        BattleHexagon body = new BattleHexagon(new Point(xOffset + 40, yOffset + 114), 40);
        BattleHexagon leg = new BattleHexagon(new Point(xOffset + 40, yOffset + 188), 40);
        BattleHexagon rightArm = new BattleHexagon(new Point(xOffset - 24, yOffset + 77), 40);
        BattleHexagon leftArm = new BattleHexagon(new Point(xOffset + 104, yOffset + 77), 40);

        head.setRotation(60);
        body.setRotation(60);
        leg.setRotation(60);
        rightArm.setRotation(60);
        leftArm.setRotation(60);

/*        hexagonAttackList.add(head);
        hexagonAttackList.add(body);
        hexagonAttackList.add(leg);
        hexagonAttackList.add(rightArm);
        hexagonAttackList.add(leftArm);*/

        sectionsAttackList.add(head.getLeftSegment());head.getLeftSegment().setBodyParts(BodyParts.HeadR);
        sectionsAttackList.add(head.getRightSegment());head.getRightSegment().setBodyParts(BodyParts.HeadL);
        sectionsAttackList.add(body.getLeftSegment());body.getLeftSegment().setBodyParts(BodyParts.BodyR);
        sectionsAttackList.add(body.getRightSegment());body.getRightSegment().setBodyParts(BodyParts.BodyL);
        sectionsAttackList.add(leg.getLeftSegment());leg.getLeftSegment().setBodyParts(BodyParts.LegsR);
        sectionsAttackList.add(leg.getRightSegment());leg.getRightSegment().setBodyParts(BodyParts.LegsL);
        sectionsAttackList.add(rightArm.getLeftSegment());rightArm.getLeftSegment().setBodyParts(BodyParts.RightArmR);
        sectionsAttackList.add(rightArm.getRightSegment());rightArm.getRightSegment().setBodyParts(BodyParts.RightArmL);
        sectionsAttackList.add(leftArm.getLeftSegment());leftArm.getLeftSegment().setBodyParts(BodyParts.LeftArmR);
        sectionsAttackList.add(leftArm.getRightSegment());leftArm.getRightSegment().setBodyParts(BodyParts.LeftArmL);


        /*---------------------------------------------*/

//int xOffset1 = 200+200;
        xOffset += 250;

        BattleHexagon head1 = new BattleHexagon(new Point(xOffset + 40, yOffset + 40), 40);
        BattleHexagon body1 = new BattleHexagon(new Point(xOffset + 40, yOffset + 114), 40);
        BattleHexagon leg1 = new BattleHexagon(new Point(xOffset + 40, yOffset + 188), 40);
        BattleHexagon rightArm1 = new BattleHexagon(new Point(xOffset - 24, yOffset + 77), 40);
        BattleHexagon leftArm1 = new BattleHexagon(new Point(xOffset + 104, yOffset + 77), 40);

        head1.setRotation(60);
        body1.setRotation(60);
        leg1.setRotation(60);
        rightArm1.setRotation(60);
        leftArm1.setRotation(60);

/*        hexagonDefenseList.add(head1);
        hexagonDefenseList.add(body1);
        hexagonDefenseList.add(leg1);
        hexagonDefenseList.add(rightArm1);
        hexagonDefenseList.add(leftArm1);*/

/*
        sectionsDefenseList.add(head1.getLeftSegment());
        sectionsDefenseList.add(head1.getRightSegment());
        sectionsDefenseList.add(body1.getLeftSegment());
        sectionsDefenseList.add(body1.getRightSegment());
        sectionsDefenseList.add(leg1.getLeftSegment());
        sectionsDefenseList.add(leg1.getRightSegment());
        sectionsDefenseList.add(rightArm1.getLeftSegment());
        sectionsDefenseList.add(rightArm1.getRightSegment());
        sectionsDefenseList.add(leftArm1.getLeftSegment());
        sectionsDefenseList.add(leftArm1.getRightSegment());*/


        sectionsDefenseList.add(head1.getLeftSegment());head1.getLeftSegment().setBodyParts(BodyParts.HeadR);
        sectionsDefenseList.add(head1.getRightSegment());head1.getRightSegment().setBodyParts(BodyParts.HeadL);
        sectionsDefenseList.add(body1.getLeftSegment());body1.getLeftSegment().setBodyParts(BodyParts.BodyR);
        sectionsDefenseList.add(body1.getRightSegment());body1.getRightSegment().setBodyParts(BodyParts.BodyL);
        sectionsDefenseList.add(leg1.getLeftSegment());leg1.getLeftSegment().setBodyParts(BodyParts.LegsR);
        sectionsDefenseList.add(leg1.getRightSegment());leg1.getRightSegment().setBodyParts(BodyParts.LegsL);
        sectionsDefenseList.add(rightArm1.getLeftSegment());rightArm1.getLeftSegment().setBodyParts(BodyParts.RightArmR);
        sectionsDefenseList.add(rightArm1.getRightSegment());rightArm1.getRightSegment().setBodyParts(BodyParts.RightArmL);
        sectionsDefenseList.add(leftArm1.getLeftSegment());leftArm1.getLeftSegment().setBodyParts(BodyParts.LeftArmR);
        sectionsDefenseList.add(leftArm1.getRightSegment());leftArm1.getRightSegment().setBodyParts(BodyParts.LeftArmL);








    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        /*hexagonAttackList.forEach(i -> {
            i.getLeftSegment().draw(g2, 0, 0, 1, Color.RED.getRGB(), i.getLeftSegment().isFilled());
            i.getRightSegment().draw(g2, 0, 0, 1, Color.RED.getRGB(), i.getRightSegment().isFilled());
        });

        hexagonDefenseList.forEach(i -> {
            i.getLeftSegment().draw(g2, 0, 0, 1, new Color(12, 119, 31).getRGB(), i.getLeftSegment().isFilled());
            i.getRightSegment().draw(g2, 0, 0, 1, new Color(12, 119, 31).getRGB(), i.getRightSegment().isFilled());
        });*/

        sectionsAttackList.forEach(i -> i.draw(g2, 0, 0, 1, Color.RED.getRGB(), i.isFilled()));
        sectionsDefenseList.forEach(i -> i.draw(g2, 0, 0, 1, new Color(12, 119, 31).getRGB(), i.isFilled()));

        go.draw(g2, 0, 0, 1, Color.BLACK.getRGB(), true);

    }


}
