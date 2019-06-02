package com.singlePlayer.company.client.swing;


import com.singlePlayer.company.model.Hero.Hero;
import com.singlePlayer.company.server.ServerUtils;


public class MainModel {

    private Hero currentHero;
    private Hero enemy;

    private boolean isHittingPanelVisible = false;
    private ServerUtils serverUtils = new ServerUtils();




    public ServerUtils getServerUtils() {
        return serverUtils;
    }

    public boolean isHittingPanelVisible() {
        return isHittingPanelVisible;
    }

    public void setHittingPanelVisible(boolean hittingPanelVisible) {
        isHittingPanelVisible = hittingPanelVisible;
    }

    public Hero getCurrentHero() {
        return currentHero;
    }

    public void setCurrentHero(Hero currentHero) {
       this.currentHero = currentHero;
    }

    public Hero getEnemy() {
        return enemy;
    }

    public void setEnemy(Hero enemy) {
        this.enemy = enemy;
    }

    public void refresh() {
        currentHero = null;
        enemy = null;
    }
}
