package com.multiPlayer.server;

import com.multiPlayer.both.Hero.Hero;
import com.multiPlayer.both.battleField.BattleField;
import com.multiPlayer.connection.Connection;
import com.multiPlayer.connection.Message;
import com.multiPlayer.connection.MessageType;

import static com.multiPlayer.other.ServerConstants.SERVER_PORT;

import com.multiPlayer.other.MessageObjects.BattleFieldInstance;
import com.multiPlayer.other.MessageObjects.HeroBattleAction;
import com.multiPlayer.other.MessageObjects.HeroMovementAction;

import com.myDrafts.differentExamples.chat.ConsoleHelper;


import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


public class Server {


    private static Map<String, Connection> onlinePlayers = new ConcurrentHashMap<>();
    private static BattleManager battleManager = new BattleManager();


    public void RunServer() {

        try (ServerSocket serverSocket = new ServerSocket(SERVER_PORT)) {

            ConsoleHelper.writeMessage("The Server is running..");

            battleManager.start();

            while (true) {
                //Listen
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
            System.err.println("Could not listen on port " + SERVER_PORT);
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
            super.run();

            ConsoleHelper.writeMessage("Установленно соединение с адресом " + socket.getRemoteSocketAddress());

            String playerName = null;

            try (Connection connection = new Connection(socket)) {


                playerName = serverHandshake(connection);
                serverMainLoop(connection, playerName);


            } catch (IOException e) {
                e.printStackTrace();
                ConsoleHelper.writeMessage("Ошибка при обмене данными с удаленным адресом");
                // ConsoleHelper.writeMessage("Disconnect from server");
            } catch (ClassNotFoundException e) {
                ConsoleHelper.writeMessage("Ошибка при обмене данными с удаленным адресом");
                e.printStackTrace();
            }

            if (playerName != null) {
                //После того как все исключения обработаны, удаляем запись из connectionMap
                onlinePlayers.remove(playerName);
               // waitingForBattle.remove(playerName);
                //и отправлялем сообщение остальным пользователям
            }
            ConsoleHelper.writeMessage("Соединение с удаленным адресом закрыто");


        }

        private void serverMainLoop(Connection connection, String userName) throws IOException, ClassNotFoundException {
            while (true) {
                Message message;
                if ((message = connection.receive()) != null) {

                    /**печать всех сообщений*/
                    System.out.println("received: " + message);


                    if (message.getType() == MessageType.JOIN_TO_BATTLE_REQUEST) {

                        //должен проверять достаточно ли игроков для старта боя
                        battleManager.addToBattleQueue(userName, connection);
                       // waitingForBattle.put(userName, connection);
                        connection.send(new Message(MessageType.JOIN_TO_BATTLE_RESPONSE));

                        /*----------------------------------------------------*/
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

                /**печать всех сообщений*/
                if (message != null) System.out.println("received: " + message);


                // Проверить, что получена команда с именем пользователя
                if (message.getType() == MessageType.PLAYER_NAME) {

                    //Достать из ответа имя, проверить, что оно не пустое
                    //if (message.getData() != null && !message.getData().isEmpty()) {
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

/*        private void sendListOfUsers(Connection connection, String userName) {
            for (Map.Entry<String, Connection> pair : connectionMap.entrySet()) {
                // Команду с именем равным userName отправлять не нужно, пользователь и так имеет информацию о себе
                if (pair.getKey().equals(userName))
                    break;

                try {
                    connection.send(new Message(MessageType.USER_ADDED, pair.getKey()));
                } catch (IOException e) {
                    ConsoleHelper.writeMessage("There is an error while sending messages");
                }
            }*/

    }

}

