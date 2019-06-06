package com.multiPlayer.client.swing.View;

import com.multiPlayer.both.Hero.BodyParts;
import com.multiPlayer.other.MessageObjects.HeroBattleAction;
import com.multiPlayer.both.ImageLoader;
import com.multiPlayer.client.swing.Controller;
import com.multiPlayer.client.swing.model.HeroImages;
import com.multiPlayer.client.swing.model.HexSection;
import com.multiPlayer.client.swing.model.Hexagon;


import javax.swing.*;
import javax.swing.plaf.LayerUI;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HittingPanel extends LayerUI<JPanel> {
    private static final int ACTION_COUNT = 2;

    private MouseListener mouseListener;

    private List<HexSection> sectionsAttackList = new ArrayList<>();
    private List<HexSection> sectionsDefenseList = new ArrayList<>();

    private Hexagon go = new Hexagon(new Point(420, 400), 40);
    private Hexagon cancel = new Hexagon(new Point(300, 400), 40);

    private List<BodyParts> secAttack = new ArrayList<>();
    private List<BodyParts> secDef = new ArrayList<>();

    private Color attackColor = Color.RED;
    private Color attackColorSelected = new Color(127, 0, 0);
    private Color defenseColor = Color.green;
    private Color defenseColorSelected = new Color(12, 119, 31);

    private Controller controller;

    public HittingPanel(Controller controller) {
        this.controller = controller;
        initAttackAndDefense();
        initBattleMouseListener();

    }

    public MouseListener getBattleMouseListener() {
        return mouseListener;
    }

    public void addHeroSelection(HexSection hexSection, List<BodyParts> sectionsList) {
        if (sectionsList.size() < ACTION_COUNT)
            sectionsList.add(hexSection.getBodyPart());
    }

    public void removeHeroSelection(HexSection hexSection, List<BodyParts> sectionsList) {
        if (!sectionsList.isEmpty())
            sectionsList.remove(hexSection.getBodyPart());
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
                clickPoint.y -= 40;

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
                        controller.repaintAllView();

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
                        controller.repaintAllView();

                    }

                });

                //Если кликаем на ОК
                if (go.contains(clickPoint)) {


                    try {
                        controller.sendBattleActionToServer(new HeroBattleAction(controller.getEnemy(), secAttack, secDef));
                    } catch (IOException error) {
                        System.out.println("sendBattleActionToServer failed");
                        error.printStackTrace();
                    }
                    //передать атаки и блоки в метод отправки данных на сервер


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
        controller.closeHittingPanel();
        resetBattleActions();
        controller.repaintAllView();

        controller.setBattleFieldPanelMouseListener();
    }


    private List<HexSection> createHeroSelector(int x, int y, int hexagonRadius, int hexagonRotation) {
        int xOffset = x;
        int yOffset = y;

        List<HexSection> result = new ArrayList<>();
        result.addAll(HexSection.createTwoHexSections(BodyParts.Head, new Point(xOffset + 40, yOffset + 40), hexagonRadius, hexagonRotation));
        result.addAll(HexSection.createTwoHexSections(BodyParts.Body, new Point(xOffset + 40, yOffset + 114), hexagonRadius, hexagonRotation));
        result.addAll(HexSection.createTwoHexSections(BodyParts.Legs, new Point(xOffset + 40, yOffset + 188), hexagonRadius, hexagonRotation));
        result.addAll(HexSection.createTwoHexSections(BodyParts.RightArm, new Point(xOffset - 24, yOffset + 77), hexagonRadius, hexagonRotation));
        result.addAll(HexSection.createTwoHexSections(BodyParts.LeftArm, new Point(xOffset + 104, yOffset + 77), hexagonRadius, hexagonRotation));

        return result;
    }


    public void initAttackAndDefense() {
        //todo убрать константы из всего класса
        sectionsAttackList = createHeroSelector(200, 100, 40, 60);
        sectionsDefenseList = createHeroSelector(200 + 250, 100, 40, 60);
    }


    private Map<String, Image> map = new HashMap<String, Image>() {{
        put("BUTTON_OK", ImageLoader.loadImage(HeroImages.DATA_BASE.get("BUTTON_OK")));
        put("BUTTON_CANCEL", ImageLoader.loadImage(HeroImages.DATA_BASE.get("BUTTON_CANCEL")));
    }};

    @Override
    public void paint(Graphics g, JComponent c) {
        super.paint(g, c);
        Graphics2D g2 = (Graphics2D) g.create();

        int w = c.getWidth();
        int h = c.getHeight();

        if (controller.isVisibleBattleFrame()) {

            // Gray it out.
            Composite urComposite = g2.getComposite();
            g2.setComposite(AlphaComposite.getInstance(
                    AlphaComposite.SRC_OVER, .5f * /*fade*/1f));
            g2.fillRect(0, 0, w, h);
            g2.setComposite(urComposite);


            sectionsAttackList.forEach(i -> {
                i.draw(g2, 0, 0, 1,
                        i.isFilled() ?
                                attackColorSelected.getRGB() :
                                attackColor.getRGB()
                        , true);
            });

            sectionsDefenseList.forEach(i -> i.draw(g2, 0, 0, 1,
                    i.isFilled() ?
                            defenseColorSelected.getRGB() :
                            defenseColor.getRGB()
                    , true));

            //todo Картинки где то сохранять, и не загружать каждый раз
            go.draw(g2, 0, 0, 2, Color.PINK.getRGB(), true);
            g.drawImage(
                    map.get("BUTTON_OK"),
                    //ImageLoader.loadImage(HeroImages.OK_PATH),
                    go.getCenter().x - 35,
                    go.getCenter().y - 35,
                    null);

            cancel.draw(g2, 0, 0, 2, Color.PINK.getRGB(), true);

            g.drawImage(
                    map.get("BUTTON_CANCEL"),
                    //ImageLoader.loadImage(HeroImages.CANCEL_PATH),
                    cancel.getCenter().x - 28,
                    cancel.getCenter().y - 30,
                    null);
        }
    }

}