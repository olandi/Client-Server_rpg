package com.multiPlayer.client.swing.model;

import java.net.URL;
import java.util.HashMap;

public class HeroImages {


    public static final URL PIRATE_PATH = HeroImages.class.getClassLoader().getResource("image/pirate.png");
    public static final URL KNIGHT_PATH = HeroImages.class.getClassLoader().getResource("image/knight.png");

    public static final URL PIRATE_HEAD_PATH = HeroImages.class.getClassLoader().getResource("image/pirateHead.png");
    public static final URL KNIGHT_HEAD_PATH = HeroImages.class.getClassLoader().getResource("image/knightHead.png");

    public static final URL OK_PATH = HeroImages.class.getClassLoader().getResource("image/a/ok.png");
    public static final URL CANCEL_PATH = HeroImages.class.getClassLoader().getResource("image/a/cancel.png");

    public final static HashMap<String, URL> DATA_BASE = new HashMap<String, URL>() {
        {
            put("PIRATE", PIRATE_PATH);
            put("KNIGHT", KNIGHT_PATH);

            put("PIRATE_HEAD", PIRATE_HEAD_PATH);
            put("KNIGHT_HEAD", KNIGHT_HEAD_PATH);

            put("BUTTON_OK",OK_PATH);
            put("BUTTON_CANCEL",CANCEL_PATH);
        }


    };
}
/*
Пример 1. используем конструкцию getClass().getResource("/images/logo.png").
Поскольку имя начинается с символа '/' – оно считается абсолютным. Поиск ресурса происходит следующим образом:

К пути из classpath c:\work\myproject\classes приписывается имя ресурса /images/logo.png,
в результате чего ищется файл c:\work\myproject\classes\images\logo.png. Если файл найден – поиск прекращается.
Иначе: В jar-файле c:\lib\lib.jar ищется файл /images/logo.png, причем поиск ведется от корня jar-файла.

Пример 2. Мы используем конструкцию getClass().getResource("res/data.txt").
Поскольку имя не начинается с символа '/' – оно считается относительным. Поиск ресурса происходит следующим образом:

К пути из classpath c:\work\myproject\classes приписывается текущий пакет класса, где находится код, – /ru/skipy/test, –
и далее имя ресурса res/data.txt, в результате чего ищется файл c:\work\myproject\classes\ru\skipy\test\res\data.txt.
Если файл найден – поиск прекращается. Иначе: В jar-файле c:\lib\lib.jar ищется файл /ru/skipy/test/res/data.txt
(имя пакета текущего класса плюс имя ресурса), причем поиск ведется от корня jar-файла.

В вашем случае нужно писать getResourceAsStream("db.properties") без указания символа '/', чтобы файл нашел в папке
project\src\main\resources\ но вы можете положить db.properties в project\db.properties и уже указать как в вашем примере
 */