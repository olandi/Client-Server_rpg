package com.multiPlayer.client;

import com.multiPlayer.both.Hero.Hero;
import com.multiPlayer.client.swing.Controller;
import com.multiPlayer.client.swing.model.util.TimeUtil;
import com.multiPlayer.connection.Message;
import com.multiPlayer.connection.MessageType;
import com.multiPlayer.other.MessageObjects.BattleFieldInstance;
import com.multiPlayer.other.MessageObjects.UpdateBattleField;
//import com.multiPlayer.other.MessageObjects.TimerMessage;

import javax.swing.*;
import java.io.IOException;
import java.net.Socket;
import java.util.Date;
import java.util.Map;

import static com.multiPlayer.other.ServerConstants.SERVER_ADDRESS;
import static com.multiPlayer.other.ServerConstants.SERVER_PORT;


public class ClientListener extends Thread {

    private MainLayoutController controller;

    public ClientListener(MainLayoutController controller) {

        this.controller = controller;
    }


    @Override
    public void run() {

        Socket socket;

        try {
            socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
            controller.createConnection(socket);


            clientHandshake();

            controller.switchToPickBattleView();

            clientMainLoop();

        } catch (IOException e) {
            System.out.println("connect error");
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void clientMainLoop() throws IOException, ClassNotFoundException {
        System.out.println("Client main Loop");
        while (true) {

            Message message;
            if ((message = controller.getConnection().receive()) != null) {

                /**печать всех сообщений*/
                //  System.out.println("received: "+message);

                if (message.getType() == MessageType.JOIN_TO_BATTLE_RESPONSE) {

                    System.out.println("received: " + message);

                    System.out.println("JOIN_TO_BATTLE_RESPONSE");
                }

                if (message.getType() == MessageType.BATTLE_FIELD_INSTANCE) {

                    System.out.println("received: " + message);

                    BattleFieldInstance bfi = (BattleFieldInstance) message.getData();

                    //controller.setBattleFieldController(new Controller(controller));
                    controller.getBattleFieldController().initBattle(bfi.getBattleField(), bfi.getHeroes());
                    /*
                    controller.getBattleFieldController().getModel().initBattle(bfi.getBattleField(), bfi.getHeroes());
                    controller.getBattleFieldController().initPlayerHero();*/

                    System.out.println("BATTLE_FIELD_INSTANCE");


                    controller.getBattleFieldController().setBattleFieldPanelMouseListener();
                    controller.switchToFightView();
                }

                if (message.getType() == MessageType.TIMER) {
                    //long l = ((TimerMessage) message.getData()).getTime();
                    long l = (long) message.getData();
                    controller.getBattleFieldController()
                            .getTimerPanel().getjLabel().setText(TimeUtil.getTime(l));
                }

                if (message.getType() == MessageType.ANIMATION) {
                    System.out.println("received: "+message);

                    controller.getBattleFieldController().getTimerPanel().getjLabel().setText("Анимация боя");}


                if (message.getType() == MessageType.UPDATE_BATTLEFIELD) {

                    System.out.println("received: "+message);


                    controller.getBattleFieldController().uptateBattleField(
                            ((UpdateBattleField) message.getData()).getHeroes()
                    );
                }

                if (message.getType() == MessageType.FINISH_BATTLE) {
                    String m = "Victory";
                    if (!controller.getBattleFieldController().getModel().isHeroPlayerHeroAlive()){
                        m = "Defeat";
                    }

                    JOptionPane.showMessageDialog(
                            controller.getBattleFieldController().getMainGamePanel(),
                            m,
                            "Бой закончен",
                            JOptionPane.INFORMATION_MESSAGE);

                    controller.switchToPickBattleView();
                    controller.getBattleFieldController().getModel().resetAlldata();
                    //controller.getBattleFieldController().getHeroInfoPanel().destroyPlayerInfo();
                }
            }
        }
    }

    public void clientHandshake() throws IOException, ClassNotFoundException {
        while (true) {
            Message message = controller.getConnection().receive();

            /**печать всех сообщений*/
            if (message != null) System.out.println("received: " + message);


            if (message.getType() == MessageType.PLAYER_NAME_REQUEST) {
                String userName = controller.getPlayer();
                controller.getConnection().send(new Message(MessageType.PLAYER_NAME, userName));

            } else if (message.getType() == MessageType.PLAYER_NAME_ACCEPTED) {
                // notifyConnectionStatusChanged(true);

                return;
            } else {
                throw new IOException("Unexpected MessageType");
            }
        }

    }


}