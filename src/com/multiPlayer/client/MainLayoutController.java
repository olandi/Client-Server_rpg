package com.multiPlayer.client;


import com.multiPlayer.client.View.LoginView;
import com.multiPlayer.client.View.MainLayoutView;
import com.multiPlayer.client.View.PickBattleView;
import com.multiPlayer.client.swing.BattleFieldController;
import com.multiPlayer.connection.Connection;

import java.io.IOException;
import java.net.Socket;

public class MainLayoutController {

    private BattleFieldController battleFieldController = new BattleFieldController(this);

    private LoginView loginView = new LoginView(this);
    private PickBattleView pickBattleView = new PickBattleView(this);
    private MainLayoutView mainLayoutView = new MainLayoutView(this);
    private MainLayoutModel mainLayoutModel = new MainLayoutModel();


    public BattleFieldController getBattleFieldController() {
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
        mainLayoutView.switchToBattle();
    }


    public void switchToFightView(){
        mainLayoutView.switchToFight();
        pickBattleView.setJoinButton();
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


    public void joinToBattle() {
        pickBattleView.setLeaveButton();
    }

    public void leaveBattle() {
        pickBattleView.setJoinButton();
    }
}
