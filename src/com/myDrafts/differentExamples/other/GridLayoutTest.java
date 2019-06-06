package com.myDrafts.differentExamples.other;

import java.awt.*;
import javax.swing.*;

public class GridLayoutTest extends JFrame
{
    public GridLayoutTest()
    {
        super("GridLayoutTest");
        setSize(320, 320);
        setLocation(100, 100);
        setDefaultCloseOperation( EXIT_ON_CLOSE );
        // Вспомогательная панель
        JPanel grid = new JPanel();
        /*
         * Первые два параметра конструктора GridLayout определяют количество
         * строк и столбцов в таблице. Вторые 2 параметра - расстояние между
         * ячейками по горизонтали и вертикали
         */
        GridLayout layout = new GridLayout(2, 0, 5, 12);
        grid.setLayout(layout);
        // Создаем 8 кнопок



        for (int i = 0; i < 8; i++) {
            grid.add(new JButton("Кнопка " + i));
        }
        // Размещаем нашу панель в панели содержимого 
        getContentPane().add(grid);
        // Устанавливаем оптимальный размер окна
        pack();
        // Открываем окно
        setVisible(true);
    }
    public static void main(String[] args) {
        new GridLayoutTest();
    }
}