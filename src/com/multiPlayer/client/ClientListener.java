package com.multiPlayer.client;

import com.multiPlayer.client.swing.model.util.TimeUtil;
import com.multiPlayer.connection.Message;
import com.multiPlayer.connection.MessageType;
import com.multiPlayer.connection.MessageObjects.BattleFieldInstance;
import com.multiPlayer.connection.MessageObjects.UpdateBattleField;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.io.IOException;
import java.net.Socket;

import static com.multiPlayer.both.ServerConstants.SERVER_ADDRESS;
import static com.multiPlayer.both.ServerConstants.SERVER_PORT;


public class ClientListener extends Thread {
    private static final Logger LOGGER = LoggerFactory.getLogger(ClientListener.class);
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
            LOGGER.error("connect error", e);
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void clientMainLoop() throws IOException, ClassNotFoundException {
        LOGGER.debug("Client main Loop");
        while (true) {

            Message message;
            if ((message = controller.getConnection().receive()) != null) {

                if (message.getType() == MessageType.JOIN_TO_BATTLE_RESPONSE) {
                    LOGGER.debug("received: {}", message);
                    controller.joinToBattle();
                }

                if (message.getType() == MessageType.LEAVE_BATTLE_RESPONSE) {
                    LOGGER.debug("received: {}", message);
                    controller.leaveBattle();
                }

                if (message.getType() == MessageType.BATTLE_FIELD_INSTANCE) {

                    LOGGER.debug("received: {}", message);
                    controller.getBattleFieldController().initBattle((BattleFieldInstance) message.getData());
                    controller.switchToFightView();
                }

                if (message.getType() == MessageType.TIMER) {
                    controller.getBattleFieldController()
                            .updateTimer(TimeUtil.getTime((long) message.getData()));
                }

                //todo удалить
                if (message.getType() == MessageType.ANIMATION) {
                   controller.getBattleFieldController().updateTimer("Анимация боя");
                }


                if (message.getType() == MessageType.UPDATE_BATTLEFIELD) {
                    LOGGER.debug("received: {}", message);
                    controller.getBattleFieldController().updateBattleField(((UpdateBattleField) message.getData()));
                }

                if (message.getType() == MessageType.FINISH_BATTLE) {

                    JOptionPane.showMessageDialog(
                            controller.getBattleFieldController().getMainGamePanel(),
                            controller.getBattleFieldController().getModel().isHeroPlayerHeroAlive()? "Victory": "Defeat",
                            "Бой закончен",
                            JOptionPane.INFORMATION_MESSAGE);

                    controller.switchToPickBattleView();
                    controller.getBattleFieldController().resetBattle();
                }
            }
        }
    }

    public void clientHandshake() throws IOException, ClassNotFoundException {
        LOGGER.debug("clientHandshake start");
        while (true) {
            Message message = controller.getConnection().receive();


            if (message.getType() == MessageType.PLAYER_NAME_REQUEST) {
                String userName = controller.getPlayer();
                controller.getConnection().send(new Message(MessageType.PLAYER_NAME, userName));

            } else if (message.getType() == MessageType.PLAYER_NAME_ACCEPTED) {
                // notifyConnectionStatusChanged(true);

                LOGGER.debug("clientHandshake is successful");
                return;
            } else {
                throw new IOException("Unexpected MessageType");
            }
        }

    }


}