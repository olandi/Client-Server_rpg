package com.multiPlayer.server;

import com.multiPlayer.client.p.Connection;
import com.multiPlayer.client.p.Message;
import com.multiPlayer.client.p.MessageType;

import static com.multiPlayer.other.ServerConstants.SERVER_PORT;

import com.singlePlayer.company.model.Hero.Hero;
import com.myDrafts.differentExamples.chat.ConsoleHelper;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


public class Server {


    private static Map<String, /*Connection*/Hero> connectionMap = new ConcurrentHashMap<>();


    public void RunServer() {

        try (ServerSocket serverSocket = new ServerSocket(SERVER_PORT)) {

            ConsoleHelper.writeMessage("The Server is running..");

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

            try (Connection connection = new Connection(socket);

            ) {


                while (true) {
                    Message message;
                    if ((message = connection.receive()) != null) {

                        if (message.getType() == MessageType.PLAYER_NAME) {
                            connection.send(new Message(MessageType.PLAYER_NAME_ACCEPTED));
                        }
                        System.out.println(message);
                        // break;
                    }
                }


            } catch (IOException e) {
                e.printStackTrace();
                ConsoleHelper.writeMessage("Ошибка при обмене данными с удаленным адресом");
            } catch (ClassNotFoundException e) {
                ConsoleHelper.writeMessage("Ошибка при обмене данными с удаленным адресом");
                e.printStackTrace();
            }

            /*if (userName != null) {
                //После того как все исключения обработаны, удаляем запись из connectionMap
                connectionMap.remove(userName);
                //и отправлялем сообщение остальным пользователям
            }
            ConsoleHelper.writeMessage("Соединение с удаленным адресом закрыто");*/


        }



/*        private String serverHandshake(Connection connection) throws IOException, ClassNotFoundException {
            while (true) {
                // Сформировать и отправить команду запроса имени пользователя
                connection.send(new Message(MessageType.NAME_REQUEST));
                // Получить ответ клиента
                Message message = connection.receive();

                // Проверить, что получена команда с именем пользователя
                if (message.getType() == MessageType.USER_NAME) {

                    //Достать из ответа имя, проверить, что оно не пустое
                    //if (message.getData() != null && !message.getData().isEmpty()) {
                    if (!message.getData().isEmpty()) {

                        // и пользователь с таким именем еще не подключен (используй connectionMap)
                        if (connectionMap.get(message.getData()) == null) {

                            // Добавить нового пользователя и соединение с ним в connectionMap
                            connectionMap.put(message.getData(), connection);
                            // Отправить клиенту команду информирующую, что его имя принято
                            connection.send(new Message(MessageType.NAME_ACCEPTED));

                            // Вернуть принятое имя в качестве возвращаемого значения
                            return message.getData();
                        }
                    }
                }
            }
        }*/

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

/*        private void serverMainLoop(Connection connection, String userName) throws IOException, ClassNotFoundException {
            while (true) {

                Message message = connection.receive();
                // Если принятое сообщение – это текст (тип TEXT)
                if (message.getType() == MessageType.TEXT) {

                    String s = userName + ": " + message.getData();

                    Message formattedMessage = new Message(MessageType.TEXT, s);

                } else {
                    ConsoleHelper.writeMessage("Error");
                }
            }
        }*/
}

