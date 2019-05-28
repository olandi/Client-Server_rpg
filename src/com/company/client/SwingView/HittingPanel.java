package com.company.client.SwingView;



import com.company.model.BodyParts;
import com.company.client.utils.ImageLoader;
import com.company.client.model.HeroImages;
import com.company.client.utils.ClientUtils;
import com.company.model.damageTO.DamageForClient;
import com.company.client.model.BattleHexagon;
import com.company.client.model.HexSection;
import com.company.client.model.Hexagon;

import javax.swing.*;
import javax.swing.plaf.LayerUI;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;

public class HittingPanel extends LayerUI<JPanel> {


    private static final int ACTION_COUNT = 2;

    private MouseListener mouseListener;

    private Deque<HexSection> sectionsAttackList = new ArrayDeque<>();
    private Deque<HexSection> sectionsDefenseList = new ArrayDeque<>();

    private Hexagon go = new Hexagon(new Point(420, 400), 40);
    private Hexagon cancel = new Hexagon(new Point(300, 400), 40);


    private List<BodyParts> secAttack = new ArrayList<>();
    private List<BodyParts> secDef = new ArrayList<>();

    private MainPanel mainGamePanel;

    public HittingPanel(MainPanel mainGamePanel) {
        this.mainGamePanel = mainGamePanel;
        initAttackAndDefense();
        initBattleMouseListener();

        //TODO is it good design?
        mainGamePanel.HittingPanelMouseListener = mouseListener;
    }


    public MouseListener getBattleMouseListener() {
        return mouseListener;
    }


    public void addHeroSelection(HexSection hexSection, List<BodyParts> sectionsList) {
        if (sectionsList.size() < ACTION_COUNT)
            sectionsList.add(hexSection.getBodyParts());
    }

    public void removeHeroSelection(HexSection hexSection, List<BodyParts> sectionsList) {
        if (!sectionsList.isEmpty())
            sectionsList.remove(hexSection.getBodyParts());
    }

    public void resetBattleActions() {
        secAttack = new ArrayList<>();
        secDef = new ArrayList<>();

        sectionsAttackList.forEach(i -> i.setFilled(false));
        sectionsDefenseList.forEach(i -> i.setFilled(false));
    }


    public void initBattleMouseListener() {


        mouseListener = new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                // e.getPoint();

                Point clickPoint = e.getPoint();
                clickPoint.y -= 26;

                //если кликаем по атака хексам
                sectionsAttackList.forEach(i -> {
                    if (i.contains(clickPoint)) {
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
                        mainGamePanel.repaint();

                    }

                });

                //если кликаем по блок хексам
                sectionsDefenseList.forEach(i -> {
                    if (i.contains(clickPoint)) {
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
                        mainGamePanel.repaint();

                    }

                });

                //Если кликаем на ОК
                if (go.contains(clickPoint)) {

                    //передать атаки и блоки в метод отправки данных на сервер


                    ClientUtils.SendDamageToServer(new DamageForClient(secAttack, secDef));
                    resetBattleMenu();

                }
                //Если кликаем на отмену
                if (cancel.contains(clickPoint)) {
                    //обнулить форму и скрыть ее
                    resetBattleMenu();
                }
            }
        };
    }


    public void resetBattleMenu() {
        mainGamePanel.closeHittingPanel();
        resetBattleActions();
        mainGamePanel.repaint();
        //mainGamePanel.removeCurrentMouseListener(mouseListener);
       // mainGamePanel.removeCurrentMouseListener();
        mainGamePanel.removeAllMouseListeners();

        mainGamePanel.addMouseListener(mainGamePanel.BattleFieldMouseListener);

        System.out.println("we");
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

        sectionsAttackList.add(head.getLeftSegment());
        head.getLeftSegment().setBodyParts(BodyParts.HeadR);
        sectionsAttackList.add(head.getRightSegment());
        head.getRightSegment().setBodyParts(BodyParts.HeadL);
        sectionsAttackList.add(body.getLeftSegment());
        body.getLeftSegment().setBodyParts(BodyParts.BodyR);
        sectionsAttackList.add(body.getRightSegment());
        body.getRightSegment().setBodyParts(BodyParts.BodyL);
        sectionsAttackList.add(leg.getLeftSegment());
        leg.getLeftSegment().setBodyParts(BodyParts.LegsR);
        sectionsAttackList.add(leg.getRightSegment());
        leg.getRightSegment().setBodyParts(BodyParts.LegsL);
        sectionsAttackList.add(rightArm.getLeftSegment());
        rightArm.getLeftSegment().setBodyParts(BodyParts.RightArmR);
        sectionsAttackList.add(rightArm.getRightSegment());
        rightArm.getRightSegment().setBodyParts(BodyParts.RightArmL);
        sectionsAttackList.add(leftArm.getLeftSegment());
        leftArm.getLeftSegment().setBodyParts(BodyParts.LeftArmR);
        sectionsAttackList.add(leftArm.getRightSegment());
        leftArm.getRightSegment().setBodyParts(BodyParts.LeftArmL);


        /*---------------------------------------------*/

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

        sectionsDefenseList.add(head1.getLeftSegment());
        head1.getLeftSegment().setBodyParts(BodyParts.HeadR);
        sectionsDefenseList.add(head1.getRightSegment());
        head1.getRightSegment().setBodyParts(BodyParts.HeadL);
        sectionsDefenseList.add(body1.getLeftSegment());
        body1.getLeftSegment().setBodyParts(BodyParts.BodyR);
        sectionsDefenseList.add(body1.getRightSegment());
        body1.getRightSegment().setBodyParts(BodyParts.BodyL);
        sectionsDefenseList.add(leg1.getLeftSegment());
        leg1.getLeftSegment().setBodyParts(BodyParts.LegsR);
        sectionsDefenseList.add(leg1.getRightSegment());
        leg1.getRightSegment().setBodyParts(BodyParts.LegsL);
        sectionsDefenseList.add(rightArm1.getLeftSegment());
        rightArm1.getLeftSegment().setBodyParts(BodyParts.RightArmR);
        sectionsDefenseList.add(rightArm1.getRightSegment());
        rightArm1.getRightSegment().setBodyParts(BodyParts.RightArmL);
        sectionsDefenseList.add(leftArm1.getLeftSegment());
        leftArm1.getLeftSegment().setBodyParts(BodyParts.LeftArmR);
        sectionsDefenseList.add(leftArm1.getRightSegment());
        leftArm1.getRightSegment().setBodyParts(BodyParts.LeftArmL);


    }

    @Override
    public void paint(Graphics g, JComponent c) {
        super.paint(g, c);
        Graphics2D g2 = (Graphics2D) g.create();

        int w = c.getWidth();
        int h = c.getHeight();

        if (mainGamePanel.isVisibleBattleFrame()) {


            // Gray it out.
            Composite urComposite = g2.getComposite();
            g2.setComposite(AlphaComposite.getInstance(
                    AlphaComposite.SRC_OVER, .5f * /*fade*/1f));
            g2.fillRect(0, 0, w, h);
            g2.setComposite(urComposite);


            //sectionsAttackList.forEach(i -> i.draw(g2, 0, 0, 1, Color.RED.getRGB(),i.isFilled()));
            //sectionsDefenseList.forEach(i -> i.draw(g2, 0, 0, 1, new Color(12, 119, 31).getRGB(), i.isFilled()));


            sectionsAttackList.forEach(i -> {
                i.draw(g2, 0, 0, 1,
                        i.isFilled() ?
                                new Color(127, 0, 0).getRGB() :
                                Color.RED.getRGB()
                        , true);

            });


            sectionsDefenseList.forEach(i -> i.draw(g2, 0, 0, 1,
                    i.isFilled() ?
                            new Color(12, 119, 31).getRGB() :
                            Color.green.getRGB()

                    , true));


            //TODO сделать нормальный контур
            sectionsAttackList.forEach(i -> i.draw(g2, 0, 0, 1, Color.BLACK.getRGB(), false));
            sectionsDefenseList.forEach(i -> i.draw(g2, 0, 0, 1, Color.BLACK.getRGB(), false));

            go.draw(g2, 0, 0, 2, Color.PINK.getRGB(), true);
            g.drawImage(ImageLoader.loadImage(
                    HeroImages.OK_PATH),
                    go.getCenter().x - 35,
                    go.getCenter().y - 35,
                    null);

            cancel.draw(g2, 0, 0, 2, Color.PINK.getRGB(), true);
            g.drawImage(ImageLoader.loadImage(
                    HeroImages.CANCEL_PATH),
                    cancel.getCenter().x - 28,
                    cancel.getCenter().y - 30,
                    null);
        }
    }

}
