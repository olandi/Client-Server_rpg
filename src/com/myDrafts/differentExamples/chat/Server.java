package com.myDrafts.differentExamples.chat;

import com.myDrafts.differentExamples.chat.client.*;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;

/**
     * Чат (21)
     У меня есть отличнейшая новость для тебя. Компонент представление (View) уже готов. Я добавил класс ClientGuiView. Он использует библиотеку javax.swing. Ты должен как следует разобраться в каждой строчке этого класса. Если тебе все понятно – это замечательно, если нет – обязательно найди ответы на свои вопросы с помощью дебага, документации или поиска в Интернет.

     Осталось написать компонент контроллер (Controller):
     1. Создай класс ClientGuiController унаследованный от Client.
     2. Создай и инициализируй поле, отвечающее за модель ClientGuiModel model.
     3. Создай и инициализируй поле, отвечающее за представление ClientGuiView view. Подумай, что нужно передать в конструктор при инициализации объекта.
     4. Добавь внутренний класс GuiSocketThread унаследованный от SocketThread. Класс GuiSocketThread должен быть публичным. В нем переопредели следующие методы:
     а) void processIncomingMessage(String message) – должен устанавливать новое сообщение у модели и вызывать обновление вывода сообщений у представления.
     б) void informAboutAddingNewUser(String userName) – должен добавлять нового пользователя в модель и вызывать обновление вывода пользователей у отображения.
     в) void informAboutDeletingNewUser(String userName) – должен удалять пользователя из модели и вызывать обновление вывода пользователей у отображения.
     г) void notifyConnectionStatusChanged(boolean clientConnected) – должен вызывать аналогичный метод у представления.
     5. Переопредели методы в классе ClientGuiController:
     а) SocketThread getSocketThread() – должен создавать и возвращать объект типа GuiSocketThread.
     б) void run() – должен получать объект SocketThread через метод getSocketThread() и вызывать у него метод run(). Разберись, почему нет необходимости вызывать метод run в отдельном потоке, как мы это делали для консольного клиента.
     в) getServerAddress(), getServerPort(), getUserName(). Они должны вызывать одноименные методы из представления (view).
     6. Реализуй метод ClientGuiModel getModel(), который должен возвращать модель.
     7. Реализуй метод main(), который должен создавать новый объект ClientGuiController и вызывать у него метод run().
     Запусти клиента с графическим окном, нескольких консольных клиентов и убедись, что
     все работает корректно.
 * ========================================
     * Чат (20)
     Консольный клиент мы уже реализовали, чат бота тоже сделали, почему бы не сделать клиента с графическим интерфейсом? Он будет так же работать с нашим сервером, но иметь графическое окно, кнопки и т.д.
     Итак, приступим. При написании графического клиента будет очень уместно воспользоваться паттерном MVC (Model-View-Controller). Ты уже должен был с ним сталкиваться, если необходимо, освежи свои знания про MVC с помощью Интернет. В нашей задаче самая простая реализация будет у класса, отвечающего за модель (Model).

     Давай напишем его:
     1) Создай класс ClientGuiModel в пакете client. Все классы клиента должны быть созданы в этом пакете.
     2) Добавь в него множество(set) строк в качестве final поля allUserNames. В нем будет храниться список всех участников чата. Проинициализируй его.
     3) Добавь поле String newMessage, в котором будет храниться новое сообщение, которое получил клиент.
     4) Добавь геттер для allUserNames, запретив модифицировать возвращенное множество. Разберись, как это можно сделать с помощью метода класса Collections.
     5) Добавь сеттер и геттер для поля newMessage.
     6) Добавь метод void addUser(String newUserName), который должен добавлять имя участника во множество, хранящее всех участников.
     7) Добавь метод void deleteUser(String userName), который будет удалять имя участника из множества.
 * ==========================================
     * Чат (19)
     Сейчас будем реализовывать класс BotSocketThread, вернее переопределять некоторые его методы, весь основной функционал он уже унаследовал от SocketThread.

     1. Переопредели метод clientMainLoop():
     а) С помощью метода sendTextMessage() отправь сообщение с текстом «Привет чатику. Я бот. Понимаю команды: дата, день, месяц, год, время, час, минуты, секунды.»
     б) Вызови реализацию clientMainLoop() родительского класса.
     2. Переопредели метод processIncomingMessage(String message). Он должен следующим образом обрабатывать входящие сообщения:
     а) Вывести в консоль текст полученного сообщения message.
     б) Получить из message имя отправителя и текст сообщения. Они разделены «: «.
     в) Отправить ответ в зависимости от текста принятого сообщения.
     Если текст сообщения:
     «дата» – отправить сообщение содержащее текущую дату в формате «d.MM.YYYY«;
     «день» – в формате «d«;
     «месяц» — «MMMM«;
     «год» — «YYYY«;
     «время» — «H:mm:ss«;
     «час» — «H«;
     «минуты» — «m«;
     «секунды» — «s«.
     Указанный выше формат используй для создания объекта SimpleDateFormat. Для получения текущей даты необходимо использовать класс Calendar и метод getTime().
     Ответ должен содержать имя клиента, который прислал запрос и ожидает ответ, например, если Боб отправил запрос «время«, мы должны отправить ответ «Информация для Боб: 12:30:47«.
     Наш бот готов. Запусти сервер, запусти бота, обычного клиента и убедись, что все работает правильно.
     Помни, что message бывают разных типов и не всегда содержат «:«
 * =============================================
     * Чат (18)
     Иногда бывают моменты, что не находится достойного собеседника. Не общаться же с самим собой :). Давай напишем бота, который будет представлять собой клиента, который автоматически будет отвечать на некоторые команды. Проще всего реализовать бота, который сможет отправлять текущее время или дату, когда его кто-то об этом попросит.

     С него и начнем:
     1) Создай новый класс BotClient в пакете client. Он должен быть унаследован от Client.
     2) В классе BotClient создай внутренний класс BotSocketThread унаследованный от SocketThread. Класс BotSocketThread должен быть публичным.
     3) Переопредели методы:
     а) getSocketThread(). Он должен создавать и возвращать объект класса BotSocketThread.
     б) shouldSendTextFromConsole(). Он должен всегда возвращать false. Мы не хотим, чтобы бот отправлял текст введенный в консоль.
     в) getUserName(), метод должен генерировать новое имя бота, например: date_bot_XX, где XX – любое число от 0 до 99. Для генерации XX используй метод Math.RANDOM().
     4) Добавь метод main. Он должен создавать новый объект BotClient и вызывать у него метод run().
 * ==========================================
     * Чат (17)
     Последний, но самый главный метод класса SocketThread – это метод void run(). Добавь его. Его реализация с учетом уже созданных методов выглядит очень просто.

     Давай напишем ее:
     1) Запроси адрес и порт сервера с помощью методов getServerAddress() и getServerPort().
     2) Создай новый объект класса java.net.Socket, используя данные, полученные в предыдущем пункте.
     3) Создай объект класса Connection, используя сокет из п.17.2.
     4) Вызови метод, реализующий «рукопожатие» клиента с сервером (clientHandshake()).
     5) Вызови метод, реализующий основной цикл обработки сообщений сервера.
     6) При возникновении исключений IOException или ClassNotFoundException сообщи главному потоку о проблеме, используя notifyConnectionStatusChanged и false в качестве параметра.

     Клиент готов, можешь запустить сервер, несколько клиентов и проверить как все работает.
 * =============================================================
     * Чат (16)
     Теперь все готово, чтобы дописать необходимые методы класса SocketThread.

     1) Добавь protected метод clientHandshake() throws IOException, ClassNotFoundException. Этот метод будет представлять клиента серверу.
     Он должен:
     а) В цикле получать сообщения, используя соединение connection.
     б) Если тип полученного сообщения NAME_REQUEST (сервер запросил имя), запросить ввод имени пользователя с помощью метода getUserName(), создать новое сообщение с типом MessageType.USER_NAME и введенным именем, отправить сообщение серверу.
     в) Если тип полученного сообщения MessageType.NAME_ACCEPTED (сервер принял имя), значит сервер принял имя клиента, нужно об этом сообщить главному потоку, он этого очень ждет. Сделай это с помощью метода notifyConnectionStatusChanged(), передав в него true. После этого выйди из метода.
     г) Если пришло сообщение с каким-либо другим типом, кинь исключение IOException(«Unexpected MessageType«).

     2) Добавь protected метод void clientMainLoop() throws IOException, ClassNotFoundException. Этот метод будет реализовывать главный цикл обработки сообщений сервера. Внутри метода:
     а) Получи сообщение от сервера, используя соединение connection.
     б) Если это текстовое сообщение (тип MessageType.TEXT), обработай его с помощью метода processIncomingMessage().
     в) Если это сообщение с типом MessageType.USER_ADDED, обработай его с помощью метода informAboutAddingNewUser().
     г) Если это сообщение с типом MessageType.USER_REMOVED, обработай его с помощью метода informAboutDeletingNewUser().
     д) Если клиент получил сообщение какого-либо другого типа, брось исключение IOException(«Unexpected MessageType«).
     е) Размести код из предыдущих пунктов внутри бесконечного цикла. Цикл будет завершен автоматически если произойдет ошибка (будет брошено исключение) или поток, в котором работает цикл, будет прерван.
 * =============================================
     * Чат (15)
     Напишем реализацию класса SocketThread. Начнем с простых вспомогательных методов.

     Добавь методы, которые будут доступны классам потомкам и не доступны остальным классам вне пакета:
     1) void processIncomingMessage(String message) – должен выводить текст message в консоль.
     2) void informAboutAddingNewUser(String userName) – должен выводить в консоль информацию о том, что участник с именем userName присоединился к чату.
     3) void informAboutDeletingNewUser(String userName) – должен выводить в консоль, что участник с именем userName покинул чат.
     4) void notifyConnectionStatusChanged(boolean clientConnected) – этот метод должен:
     а) Устанавливать значение поля clientConnected внешнего объекта Client в соответствии с переданным параметром.
     б) Оповещать (пробуждать ожидающий) основной поток класса Client.

     Подсказка: используй синхронизацию на уровне текущего объекта внешнего класса и метод notify. Для класса SocketThread внешним классом является класс Client.
 * =============================================
     * Чат (14)
     Приступим к написанию главного функционала класса Client.

     1. Добавь метод void run(). Он должен создавать вспомогательный поток SocketThread, ожидать пока тот установит соединение с сервером, а после этого в цикле считывать сообщения с консоли и отправлять их серверу. Условием выхода из цикла будет отключение клиента или ввод пользователем команды ‘exit‘.
     Для информирования главного потока, что соединение установлено во
     вспомогательном потоке, используй методы wait и notify объекта класса Client.

     Реализация метода run должна:
     а) Создавать новый сокетный поток с помощью метода getSocketThread.
     б) Помечать созданный поток как daemon, это нужно для того, чтобы при выходе из программы вспомогательный поток прервался автоматически.
     в) Запустить вспомогательный поток.
     г) Заставить текущий поток ожидать, пока он не получит нотификацию из другого потока. Подсказка: используй wait и синхронизацию на уровне объекта. Если во время ожидания возникнет исключение, сообщи об этом пользователю и выйди из программы.
     д) После того, как поток дождался нотификации, проверь значение clientConnected. Если оно true – выведи «Соединение установлено. Для выхода наберите команду ‘exit’.«. Если оно false – выведи «Произошла ошибка во время работы клиента.».
     е) Считывай сообщения с консоли пока клиент подключен. Если будет введена команда ‘exit‘, то выйди из цикла.
     ж) После каждого считывания, если метод shouldSendTextFromConsole() возвращает true, отправь считанный текст с помощью метода sendTextMessage().

     2. Добавь метод main(). Он должен создавать новый объект типа Client и вызывать у него метод run().
 * ==========================================
     * Чат (13)
     Продолжаем реализацию вспомогательных методов класса Client.

     Добавь в класс методы, которые будут доступны классам потомкам, но не доступны из других классов вне пакета:
     1. String getServerAddress() – должен запросить ввод адреса сервера у пользователя и вернуть введенное значение. Адрес может быть строкой, содержащей ip, если клиент и сервер запущен на разных машинах или ‘localhost’, если клиент и сервер работают на одной машине.
     2. int getServerPort() – должен запрашивать ввод порта сервера и возвращать его.
     3. String getUserName() – должен запрашивать и возвращать имя пользователя.
     4. boolean shouldSendTextFromConsole() – в данной реализации клиента всегда должен возвращать true (мы всегда отправляем текст введенный в консоль). Этот метод может быть переопределен, если мы будем писать какой-нибудь другой клиент, унаследованный от нашего, который не должен отправлять введенный в консоль текст.
     5. SocketThread getSocketThread() – должен создавать и возвращать новый объект класса SocketThread.
     6. void sendTextMessage(String text) – создает новое текстовое сообщение, используя переданный текст и отправляет его серверу через соединение connection.
     Если во время отправки произошло исключение IOException, то необходимо вывести
     информацию об этом пользователю и присвоить false полю clientConnected.
 * ============================================
     * Чат (12)
     Приступим к написанию клиента. Клиент, в начале своей работы, должен запросить у пользователя адрес и порт сервера, подсоединиться к указанному адресу, получить запрос имени от сервера, спросить имя у пользователя, отправить имя пользователя серверу, дождаться принятия имени сервером. После этого клиент может обмениваться текстовыми сообщениями с сервером. Обмен сообщениями будет происходить в двух параллельно работающих потоках. Один будет заниматься чтением из консоли и отправкой прочитанного серверу, а второй поток будет получать данные от сервера и выводить их в консоль.

     Начнем реализацию клиента:
     1) Создай пакет client. В дальнейшем все классы, отвечающие за реализацию клиентов, создавай в этом пакете.
     2) Создай класс Client.
     3) Создай внутренний класс SocketThread унаследованный от Thread в классе Client. Он будет отвечать за поток, устанавливающий сокетное соединение и читающий сообщения сервера. Класс должен иметь публичный модификатор доступа.
     4) Создай поле Connection connection в классе Client. Используй модификатор доступа, который позволит обращаться к этому полю из класса потомков, но запретит обращение из других классов вне пакета.
     5) Добавь приватное поле-флаг boolean clientConnected в класс Client. Проинициализируй его значением false. В дальнейшем оно будет устанавливаться в true, если клиент подсоединен к серверу или в false в противном случае. При объявлении этого поля используй ключевое слово, которое позволит гарантировать что каждый поток, использующий поле clientConnected, работает с актуальным, а не кэшированным его значением.
 * ===============================================
     * Чат (11)
     Пришло время написать главный метод класса Handler, который будет вызывать все
     вспомогательные методы, написанные ранее. Реализуем метод void run() в классе Handler.

     Он должен:
     1. Выводить сообщение, что установлено новое соединение с удаленным адресом, который можно получить с помощью метода getRemoteSocketAddress.
     2. Создавать Connection, используя поле socket.
     3. Вызывать метод, реализующий рукопожатие с клиентом, сохраняя имя нового клиента.
     4. Рассылать всем участникам чата информацию об имени присоединившегося участника (сообщение с типом USER_ADDED). Подумай, какой метод подойдет для этого лучше всего.
     5. Сообщать новому участнику о существующих участниках.
     6. Запускать главный цикл обработки сообщений сервером.
     7. Обеспечить закрытие соединения при возникновении исключения.
     8. Отловить все исключения типа IOException и ClassNotFoundException, вывести в консоль информацию, что произошла ошибка при обмене данными с удаленным адресом.
     9. После того как все исключения обработаны, если п.11.3 отработал и возвратил нам имя, мы должны удалить запись для этого имени из connectionMap и разослать всем остальным участникам сообщение с типом USER_REMOVED и сохраненным именем.
     10. Последнее, что нужно сделать в методе run() – вывести сообщение, информирующее что соединение с удаленным адресом закрыто.

     Наш сервер полностью готов. Попробуй его запустить.
 * ================================================
     * Чат (10)
     Этап третий – главный цикл обработки сообщений сервером.
     Добавь приватный метод void serverMainLoop(Connection connection, String userName) throws IOException, ClassNotFoundException, где значение параметров такое же, как и у метода
     sendListOfUsers.

     Он должен:
     1. Принимать сообщение клиента
     2. Если принятое сообщение – это текст (тип TEXT), то формировать новое текстовое сообщение путем конкатенации: имени клиента, двоеточия, пробела и текста сообщения. Например, если мы получили сообщение с текстом «привет чат» от пользователя «Боб«, то нужно сформировать сообщение «Боб: привет чат«.
     3. Отправлять сформированное сообщение всем клиентам с помощью метода sendBroadcastMessage.
     4. Если принятое сообщение не является текстом, вывести сообщение об ошибке
     5. Организовать бесконечный цикл, внутрь которого перенести функционал пунктов 10.1-10.4.
 * ==========================================
     * Чат (9)
     Этап второй, но не менее важный – отправка клиенту (новому участнику) информации об
     остальных клиентах (участниках) чата.

     Для этого:
     1) Добавь приватный метод void sendListOfUsers(Connection connection, String userName) throws IOException, где connection – соединение с участником, которому будем слать информацию, а userName – его имя. Метод должен:
     2) Пройтись по connectionMap.
     3) У каждого элемента из п.2 получить имя клиента, сформировать команду с типом USER_ADDED и полученным именем.
     4) Отправить сформированную команду через connection.
     5) Команду с типом USER_ADDED и именем равным userName отправлять не нужно, пользователь и так имеет информацию о себе.
 * ==========================================
     * Чат (8)
     Класс Handler должен реализовывать протокол общения с клиентом.
     Выделим из протокола отдельные этапы и реализуем их с помощью отдельных методов:

     Этап первый – это этап рукопожатия (знакомства сервера с клиентом). Реализуем его с
     помощью приватного метода String serverHandshake(Connection connection) throws IOException, ClassNotFoundException. Метод в качестве параметра принимает соединение connection, а возвращает имя нового клиента.

     Реализация метода должна:
     1) Сформировать и отправить команду запроса имени пользователя
     2) Получить ответ клиента
     3) Проверить, что получена команда с именем пользователя
     4) Достать из ответа имя, проверить, что оно не пустое и пользователь с таким именем еще не подключен (используй connectionMap)
     5) Добавить нового пользователя и соединение с ним в connectionMap
     6) Отправить клиенту команду информирующую, что его имя принято
     7) Если какая-то проверка не прошла, заново запросить имя клиента
     8) Вернуть принятое имя в качестве возвращаемого значения
 *  ==========================================
     * Чат (7)
     Т.к. сервер может одновременно работать с несколькими клиентами, нам понадобится
     метод для отправки сообщения сразу всем.

     Добавь в класс Server:
     1. Статическое поле Map<String, Connection> connectionMap, где ключом будет имя
     клиента, а значением — соединение с ним.
     2. Инициализацию поля из п.7.1 с помощью подходящего Map из библиотеки
     java.util.concurrent, т.к. работа с этим полем будет происходить из разных потоков и
     нужно обеспечить потокобезопасность.
     3. Статический метод void sendBroadcastMessage(Message message), который должен
     отправлять сообщение message всем соединениям из connectionMap. Если при
     отправке сообщение произойдет исключение IOException, нужно отловить его и
     сообщить пользователю, что не смогли отправить сообщение.
 * ==========================================
     * Чат (6)
     Приступим к самому важному – написанию класса Server. Сервер должен поддерживать

     множество соединений с разными клиентами одновременно. Это можно реализовать с

     помощью следующего алгоритма:

     - Сервер создает серверное сокетное соединение.

     - В цикле ожидает, когда какой-то клиент подключится к сокету.

     - Создает новый поток обработчик Handler, в котором будет происходить обмен

     сообщениями с клиентом.

     - Ожидает следующее соединение.

     Добавь:

     1)	В класс Server приватный статический вложенный класс Handler, унаследованный от

     Thread.

     2)	В класс Handler поле socket типа Socket.

     3)	В класс Handler конструктор, принимающий в качестве параметра Socket и

     инициализирующий им соответствующее поле класса.

     4)	Метод main класса Server, должен:

     а) Запрашивать порт сервера, используя ConsoleHelper.

     б) Создавать серверный сокет java.net.ServerSocket, используя порт из предыдущего пункта.

     в) Выводить сообщение, что сервер запущен.

     г) В бесконечном цикле слушать и принимать входящие сокетные соединения только что созданного

     серверного сокета.

     д) Создавать и запускать новый поток Handler, передавая в конструктор сокет из предыдущего пункта.

     е) После создания потока обработчика Handler переходить на новый шаг цикла.

     ж) Предусмотреть закрытие серверного сокета в случае возникновения исключения.

     з) Если исключение Exception все же произошло, поймать его и вывести сообщение

     об ошибке.
 * ========================================================
    Чат (5)
    Клиент и сервер будут общаться через сокетное соединение. Одна сторона будет
    записывать данные в сокет, а другая читать. Их общение представляет собой обмен
    сообщениями Message. Класс Connection будет выполнять роль обертки над классом
    java.net.Socket, которая должна будет уметь сериализовать и десериализовать объекты
    типа Message в сокет. Методы этого класса должны быть готовы к вызову из разных
    потоков.

    Добавь в класс Connection:
    1) Final поля:
    а) Socket socket
    б) ObjectOutputStream out
    в) ObjectInputStream in
    2) Конструктор, который должен принимать Socket в качестве параметра и
    инициализировать поля класса. Для инициализации полей in и out используй
    соответствующие потоки сокета. Конструктор может бросать исключение IOException.
    Создать объект класса ObjectOutputStream нужно до того, как будет создаваться объект
    класса ObjectInputStream, иначе может возникнуть взаимная блокировка потоков,
    которые хотят установить соединение через класс Connection. Более подробно об этом
    ты можешь прочитать в спецификации класса ObjectInputStream.
    3) Метод void send(Message message) throws IOException. Он должен записывать
    (сериализовать) сообщение message в ObjectOutputStream. Этот метод будет
    вызываться из нескольких потоков. Позаботься, чтобы запись в объект
    ObjectOutputStream была возможна только одним потоком в определенный момент
    времени, остальные желающие ждали завершения записи. При этом другие методы
    класса Connection не должны быть заблокированы.
    4) Метод Message receive() throws IOException, ClassNotFoundException. Он должен читать
    (десериализовать) данные из ObjectInputStream. Сделай так, чтобы операция чтения
    не могла быть одновременно вызвана несколькими потоками, при этом вызов других
    методы класса Connection не блокировать.
    5) Метод SocketAddress getRemoteSocketAddress(), возвращающий удаленный адрес
    сокетного соединения.
    6) Метод void close() throws IOException, который должен закрывать все ресурсы класса.

    Класс Connection должен поддерживать интерфейс Closeable.
    ============================
    Чат (4)
    Сообщение Message – это данные, которые одна сторона отправляет, а вторая принимает.
    Каждое сообщение должно иметь тип MessageType, а некоторые и дополнительные
    данные, например, текстовое сообщение должно содержать текст. Т.к. сообщения будут
    создаваться в одной программе, а читаться в другой, удобно воспользоваться механизмом
    сериализации для перевода класса в последовательность битов и наоборот.

    Добавь в класс Message:
    1) Поддержку интерфейса Serializable.
    2) final поле типа MessageType type, которое будет содержать тип сообщения.
    3) final поле типа String data, которое будет содержать данные сообщения.
    4) Геттеры для этих полей.
    5) Конструктор, принимающий только MessageType, он должен проинициализировать
    поле type переданным параметром, а поле data оставить равным null.
    6) Конструктор, принимающий MessageType type и String data. Он должен также
    инициализировать все поля класса.
    ================================
    Чат (3)
    Прежде, чем двигаться дальше, нужно разработать протокол общения клиента и сервера.

    Сформулируем основные моменты протокола:
    — Когда новый клиент хочет подсоединиться к серверу, сервер должен запросить имя
    клиента.
    — Когда клиент получает запрос имени от сервера он должен отправить свое имя серверу.
    — Когда сервер получает имя клиента он должен принять это имя или запросить новое.
    — Когда новый клиент добавился к чату, сервер должен сообщить остальным участникам о
    новом клиенте.
    — Когда клиент покидает чат, сервер должен сообщить остальным участникам об этом.
    — Когда сервер получает текстовое сообщение от клиента, он должен переслать его всем
    остальным участникам чата.

    Добавь для каждого пункта вышеописанного протокола соответствующее значение в enum
    MessageType:
    1) NAME_REQUEST – запрос имени.
    2) USER_NAME – имя пользователя.
    3) NAME_ACCEPTED – имя принято.
    4) TEXT – текстовое сообщение.
    5) USER_ADDED – пользователь добавлен.
    6) USER_REMOVED – пользователь удален.
    =============================================
    Чат (2)
    Первым делом, для удобства работы с консолью реализуем класс ConsoleHelper. В
    дальнейшем, вся работа с консолью должна происходить через этот класс.

    Добавь в него:
    1. Статическое поле типа BufferedReader, инициализированное с помощью System.in.
    2. Статический метод writeMessage(String message), который должен выводить
    сообщение message в консоль.
    3. Статический метод String readString(), который должен считывать строку с
    консоли. Если во время чтения произошло исключение, вывести пользователю
    сообщение «Произошла ошибка при попытке ввода текста. Попробуйте еще раз.» И
    повторить ввод. Метод не должен пробрасывать исключения IOException наружу.
    Другие исключения не должны обрабатываться.
    4. Статический метод int readInt(). Он должен возвращать введенное число и
    использовать метод readString(). Внутри метода обработать исключение
    NumberFormatException. Если оно произошло вывести сообщение «Произошла ошибка
    при попытке ввода числа. Попробуйте еще раз.» И повторить ввод числа.

    В этой задаче и далее, если не указано дополнительно другого, то все поля класса должны
    быть приватными, а методы публичными.
    ==================================
    Чат (1)
    Сегодня мы напишем чат. Набор программ с помощью которого можно будет
    обмениваться текстовыми сообщения. Набор будет состоять из одного сервера и
    нескольких клиентов, по одному для каждого участника чата.

    Начнем с сервера. Нам понадобятся такие классы:
    1) Server – основной класс сервера.
    2) MessageType – enum, который отвечает за тип сообщений пересылаемых между
    клиентом и сервером.
    3) Message – класс, отвечающий за пересылаемые сообщения.
    4) Connection – класс соединения между клиентом и сервером.
    5) ConsoleHelper – вспомогательный класс, для чтения или записи в консоль.
 */

//основной класс сервера
public class Server {
    private static Map<String, Connection> connectionMap = new java.util.concurrent.ConcurrentHashMap<>();

    public static void main(String[] args) throws IOException {
        System.out.println("Enter Server port:");
        int serverPort = ConsoleHelper.readInt();

        try (ServerSocket serverSocket = new ServerSocket(serverPort)) {

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
        }



    }

    public static void sendBroadcastMessage(Message message){
            try {
                for (Map.Entry<String, Connection> map : connectionMap.entrySet()) {
                    map.getValue().send(message);
                }
            } catch (IOException e) {
                ConsoleHelper.writeMessage("There is an error while sending messages");
            }
    }

    private static class Handler extends Thread{
        private Socket socket;

        @Override
        public void run() {
            super.run();

            ConsoleHelper.writeMessage("Установленно соединение с адресом " + socket.getRemoteSocketAddress());
            String userName = null;
            try (Connection connection = new Connection(socket)) {
                ConsoleHelper.writeMessage("Подключение к порту: " + connection.getRemoteSocketAddress());
                userName = serverHandshake(connection);
                sendBroadcastMessage(new Message(MessageType.USER_ADDED, userName));
                sendListOfUsers(connection, userName);
                serverMainLoop(connection, userName);

            }catch (IOException e) {
                ConsoleHelper.writeMessage("Ошибка при обмене данными с удаленным адресом");
            } catch (ClassNotFoundException e) {
                ConsoleHelper.writeMessage("Ошибка при обмене данными с удаленным адресом");
                e.printStackTrace();
            }

            if (userName !=null) {
                //После того как все исключения обработаны, удаляем запись из connectionMap
                connectionMap.remove(userName);
                //и отправлялем сообщение остальным пользователям
                sendBroadcastMessage(new Message(MessageType.USER_REMOVED, userName));
            }
            ConsoleHelper.writeMessage("Соединение с удаленным адресом закрыто");



        }

        public Handler(Socket socket) {
            this.socket = socket;
        }

        private String serverHandshake(Connection connection) throws IOException, ClassNotFoundException{
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
        }

        private void sendListOfUsers(Connection connection, String userName){
            for (Map.Entry<String, Connection> pair : connectionMap.entrySet()){
                // Команду с именем равным userName отправлять не нужно, пользователь и так имеет информацию о себе
                if (pair.getKey().equals(userName))
                    break;

                try {
                    connection.send(new Message(MessageType.USER_ADDED, pair.getKey()));
                } catch (IOException e) {
                    ConsoleHelper.writeMessage("There is an error while sending messages");
                }
            }

        }

        private void serverMainLoop(Connection connection, String userName) throws IOException, ClassNotFoundException{
            while (true) {

                Message message = connection.receive();
                // Если принятое сообщение – это текст (тип TEXT)
                if (message.getType() == MessageType.TEXT) {

                    String s = userName + ": " + message.getData();

                    Message formattedMessage = new Message(MessageType.TEXT, s);
                    sendBroadcastMessage(formattedMessage);
                } else {
                    ConsoleHelper.writeMessage("Error");
                }
            }
        }
    }
}
