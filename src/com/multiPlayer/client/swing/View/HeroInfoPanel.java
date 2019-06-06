package com.multiPlayer.client.swing.View;

import com.multiPlayer.both.Hero.Hero;
import com.multiPlayer.both.ImageLoader;
import com.multiPlayer.client.swing.Controller;
import com.multiPlayer.client.swing.model.HeroImages;

import javax.swing.*;
import java.awt.*;

public class HeroInfoPanel {

    private JPanel heroInfoPanel = new JPanel();
    private Controller controller;
    private JLabel heroPicture = new JLabel();
    private JLabel heroName;
    private JLabel heroHP;

    public JLabel getHeroHP() {
        return heroHP;
    }

    public void setHeroHP(int heroHP) {

        this.heroHP.setText(heroHP + " HP");

    }

    public JPanel getHeroInfoPanel() {
        return heroInfoPanel;
    }

    public JLabel getHeroName() {
        return heroName;
    }

    public void setHeroName(JLabel heroName) {
        this.heroName = heroName;
    }

    public HeroInfoPanel(Controller controller) {
        this.controller = controller;
    }


    public void destroyPlayerInfo(){
        heroInfoPanel = new JPanel();
    }

    public void initPlayerInfo(){

        JPanel mainPanel = heroInfoPanel;
        BoxLayout a = new BoxLayout(mainPanel, BoxLayout.X_AXIS);
        mainPanel.setLayout(a);

        JPanel portretAndNamePanel = new JPanel();
        BoxLayout b = new BoxLayout(portretAndNamePanel, BoxLayout.Y_AXIS);
        portretAndNamePanel.setLayout(b);

        heroName = new JLabel();
        heroName.setFont(new Font("Arial", Font.PLAIN, 18));

        heroHP = new JLabel();
        Font f = new Font("Arial", Font.PLAIN, 30);
        heroHP.setFont(f);
        heroHP.setPreferredSize(new Dimension(100, 100));




        heroPicture = new JLabel(new ImageIcon(controller.getModel().getImageMap().get(controller.getModel().getPlayersHero().getPortretId())));
        heroName.setText(controller.getModel().getPlayersHero().getName());
        heroHP.setText(controller.getModel().getPlayersHero().getHealth() + " HP");
        // initPlayerInfo();
        //heroPicture = new JLabel(new ImageIcon(ImageLoader.loadImage(HeroImages.KNIGHT_HEAD_PATH)));
        //  heroName.setText("Player1852");
        //  heroHP.setText("50 HP");


        portretAndNamePanel.add(heroPicture);
        portretAndNamePanel.add(heroName);


        mainPanel.add(portretAndNamePanel);
        mainPanel.add(heroHP);
        heroInfoPanel = mainPanel;
    }

    public static void main(String[] args) {
        JFrame jFrame = new JFrame();
        jFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        jFrame.setSize(800, 800);

        JPanel p = new HeroInfoPanel(null).getHeroInfoPanel();

        jFrame.add(p);
        jFrame.setVisible(true);

    }
}
