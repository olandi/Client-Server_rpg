package com.multiPlayer.client;


import com.multiPlayer.client.View.LoginView;
import com.multiPlayer.client.View.MainLayoutView;
import com.multiPlayer.client.View.PickBattleView;
import com.multiPlayer.client.swing.Controller;
import com.multiPlayer.connection.Connection;

import java.io.IOException;
import java.net.Socket;

public class MainLayoutController {

    private Controller battleFieldController = new Controller(this);

    private LoginView loginView = new LoginView(this);
    private PickBattleView pickBattleView = new PickBattleView(this);
    private MainLayoutView mainLayoutView = new MainLayoutView(this);
    private MainLayoutModel mainLayoutModel = new MainLayoutModel();

    public MainLayoutModel getMainLayoutModel() {
        return mainLayoutModel;
    }

    public Controller getBattleFieldController() {
        return battleFieldController;
    }

    public MainLayoutView getMainLayoutView() {
        return mainLayoutView;
    }


    public LoginView getLoginView() {
        return loginView;
    }


    public PickBattleView getPickBattleView() {
        return pickBattleView;
    }


    public void switchToPickBattleView(){
        updatePlayerLabels();

        //ресет кнопки "в бой"
        mainLayoutModel.setInQueueToArena(false);
        pickBattleView.resetButton();

        mainLayoutView.switchToBattle();

    }


    public void switchToFightView(){
        mainLayoutView.switchToFight();
    }

    private void updatePlayerLabels() {
        pickBattleView.updatePlayerLabel(mainLayoutModel.getPlayer());
        pickBattleView.updateServerLabel(mainLayoutModel.getConnection().getSocket().getRemoteSocketAddress().toString());
    }

    public void createConnection(Socket socket)throws IOException {
        mainLayoutModel.setConnection(new Connection(socket));
    }

    public Connection getConnection() {
        return mainLayoutModel.getConnection();
    }

    public void createPlayer(String clientName) {
        mainLayoutModel.setPlayer(clientName);
    }

    public String getPlayer() {
        return mainLayoutModel.getPlayer();
    }


}
