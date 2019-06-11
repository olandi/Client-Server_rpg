package com.multiPlayer.server;

import com.multiPlayer.connection.Connection;
import com.multiPlayer.connection.Message;
import com.multiPlayer.connection.MessageType;

import static com.multiPlayer.both.ServerConstants.SERVER_PORT;

import com.multiPlayer.connection.MessageObjects.HeroBattleAction;
import com.multiPlayer.connection.MessageObjects.HeroMovementAction;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


public class Server {
    private static final Logger  LOGGER = LoggerFactory.getLogger(Server.class);

    private static Map<String, Connection> onlinePlayers = new ConcurrentHashMap<>();
    private static BattleManager battleManager = new BattleManager();


    public void RunServer() {

        try (ServerSocket serverSocket = new ServerSocket(SERVER_PORT)) {
       // InetAddress address = InetAddress.getLocalHost();
        //address.getHostAddress()
           // try (ServerSocket serverSocket = new ServerSocket(InetAddress.getLocalHost().)) {
            LOGGER.info("The Server is running..");

            battleManager.start();

            while (true) {

                Socket socket = null;
                try {
                    socket = serverSocket.accept();
                } catch (IOException e) {
                    e.printStackTrace();
                    break;
                }
                Handler handler = new Handler(socket);

                handler.start();
            }
        } catch (IOException e) {
            LOGGER.error("Could not listen on port {} ", SERVER_PORT);

            System.exit(-1);
        }
    }

    private static class Handler extends Thread {
        private Socket socket;

        public Handler(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            LOGGER.info("Установленно соединение с адресом {}", socket.getRemoteSocketAddress());

            String playerName = null;

            try (Connection connection = new Connection(socket)) {

                playerName = serverHandshake(connection);
                serverMainLoop(connection, playerName);

            } catch (IOException e) {
                e.printStackTrace();
                LOGGER.error("Ошибка при обмене данными с удаленным адресом", e);

            } catch (ClassNotFoundException e) {
                LOGGER.error("Ошибка при обмене данными с удаленным адресом", e);
                e.printStackTrace();
            }

            if (playerName != null) {
                //После того как все исключения обработаны, удаляем запись из connectionMap
                onlinePlayers.remove(playerName);
                // waitingForBattle.remove(playerName);
                //и отправлялем сообщение остальным пользователям
            }
            LOGGER.info("Соединение с удаленным адресом закрыто");


        }

        private void serverMainLoop(Connection connection, String userName) throws IOException, ClassNotFoundException {
            while (true) {
                Message message;
                if ((message = connection.receive()) != null) {

                    LOGGER.debug("received: {}", message);

                    if (message.getType() == MessageType.JOIN_TO_BATTLE_REQUEST) {
                        //должен проверять достаточно ли игроков для старта боя
                        battleManager.addToBattleQueue(userName, connection);
                        connection.send(new Message(MessageType.JOIN_TO_BATTLE_RESPONSE));
                    }

                    if (message.getType() == MessageType.LEAVE_BATTLE_REQUEST) {
                        battleManager.removeFromBattleQueue(userName, connection);
                        connection.send(new Message(MessageType.LEAVE_BATTLE_RESPONSE));
                    }


                    if (message.getType() == MessageType.PLAYER_MOVEMENT_MESSAGE) {
                        battleManager.getBattleByConnection(connection).moveHero(userName, (HeroMovementAction) message.getData());
                    }


                    if (message.getType() == MessageType.PLAYER_BATTLE_MESSAGE) {
                        battleManager.getBattleByConnection(connection).hitHero(userName, connection, (HeroBattleAction) message.getData());
                    }
                }
            }
        }

        private String serverHandshake(Connection connection) throws IOException, ClassNotFoundException {
            while (true) {
                // Сформировать и отправить команду запроса имени пользователя
                connection.send(new Message(MessageType.PLAYER_NAME_REQUEST));
                // Получить ответ клиента
                Message message = connection.receive();

                if (message != null)  LOGGER.debug("received: {}", message);

                // Проверить, что получена команда с именем пользователя
                if (message.getType() == MessageType.PLAYER_NAME) {

                    //Достать из ответа имя, проверить, что оно не пустое
                    if (!((String) message.getData()).isEmpty()) {

                        // и пользователь с таким именем еще не подключен (используй connectionMap)
                        if (onlinePlayers.get(message.getData()) == null) {

                            // Добавить нового пользователя и соединение с ним в connectionMap
                            onlinePlayers.put(((String) message.getData()), connection);
                            // Отправить клиенту команду информирующую, что его имя принято
                            connection.send(new Message(MessageType.PLAYER_NAME_ACCEPTED));

                            // Вернуть принятое имя в качестве возвращаемого значения
                            return ((String) message.getData());
                        }
                    }
                }
            }
        }
    }

}

