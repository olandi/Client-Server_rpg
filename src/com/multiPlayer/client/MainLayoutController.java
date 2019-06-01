package com.multiPlayer.client;

import javax.swing.*;

public class MainLayoutController {

    private LoginView loginView = new LoginView(this);
    private PickBattleView pickBattleView = new PickBattleView(this);
    private MainLayoutView mainLayoutView = new MainLayoutView(this);

    private MainLayoutModel mainLayoutModel = new MainLayoutModel();

    public MainLayoutModel getMainLayoutModel() {
        return mainLayoutModel;
    }

    public MainLayoutView getMainLayoutView() {
        return mainLayoutView;
    }

    public void setMainLayoutView(MainLayoutView mainLayoutView) {
        this.mainLayoutView = mainLayoutView;
    }

    public LoginView getLoginView() {
        return loginView;
    }


    public PickBattleView getPickBattleView() {
        return pickBattleView;
    }


    public void updatePlayerLabels() {
        pickBattleView.updatePlayerLabel(mainLayoutModel.getPlayer());
        pickBattleView.updateServerLabel(mainLayoutModel.getConnection().getSocket().getRemoteSocketAddress().toString());
    }



}
