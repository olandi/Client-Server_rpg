package com.multiPlayer.client.swing.model.util;

import com.multiPlayer.both.battleField.BattleField;
import com.multiPlayer.client.swing.model.Hexagon;
import com.multiPlayer.client.swing.model.HexagonItem;

import java.util.*;

public class BattleFildDrawer {
    /*
            for (int i = 0; i < 48; i++) {

            int x = i % 8;
            int y = i / 8;

            int xx = (y % 2 == 0) ? 40 + 80 * x : 80 + 80 * x;
            int yy = 40 + 70 * y;

            int fieldOffset = 30;


        }
     */

    /***
     * r - hexagon radius
     * d - отступы
     *
     * x, y - крайние левая - верхняя координата, крайнего левого верхнего хексагона.
     */


    public static List<HexagonItem> createGuiBattlefield(int x, int y, BattleField battleField, int r, int d) {
        List<HexagonItem> list = new ArrayList<>();
        int j = 0;
        boolean switcher = true;
        int xx = 0, yy = 0;

        for (int i = 0; i < battleField.getSize(); i++) {

            if (j == (switcher ? battleField.getOddRowLength() : battleField.getEvenRowLength())) {
                // sb.append("\n"); //тут переход на новую строку

               // xx = x + r;
                xx=0;
                yy += r + (r-10);//todo константа отступ по у

                switcher = !switcher;

                if (!switcher) {
                    // sb.append("\t"); //тут сдвиг вперед на пол хекса
                    xx += r;
                }
                j = 0;
            }

            //добавление эл-та
            list.add(new HexagonItem(new Hexagon(x + xx, y + yy, r), i));
            System.out.println("x: " + (int) (x + xx) + " y: " + (int) (y + yy));
            j++;
            xx += r*2;
        }


        return list;
    }
}
