package com.company.client.model;

import java.net.URL;

public class HeroImages {
   // public static final String PIRATE_PATH = ".//src//com//company//image//pirate.png";
  //  public static final String KNIGHT_PATH = ".//src//com//company//image//knight.png";

    public static final URL PIRATE_PATH = HeroImages.class.getClassLoader().getResource("image/pirate.png");
    public static final URL KNIGHT_PATH = HeroImages.class.getClassLoader().getResource("image/knight.png");



    public static final URL OK_PATH = HeroImages.class.getClassLoader().getResource("image/a/ok.png");
    public static final URL CANCEL_PATH = HeroImages.class.getClassLoader().getResource("image/a/cancel.png");

    // public static final String ARCHER_PATH = "image/archer.jpg";



  //public static final String PIRATE_PATH = "d:/pirate.png";
 //public static final String KNIGHT_PATH = "d:/knight.png";
    // public static final String ARCHER_PATH = "image/archer.jpg";

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