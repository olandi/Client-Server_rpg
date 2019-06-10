package com.multiPlayer.client.swing.model.util;


import com.multiPlayer.both.battleField.BattleField;
import com.multiPlayer.client.swing.model.Hexagon;
import com.multiPlayer.client.swing.model.HexagonItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public class BattleFieldDrawer {
    private final static Logger LOGGER = LoggerFactory.getLogger(BattleFieldDrawer.class);

    /***
     * Создает поле вида:
     *              *   *   *   *   *   *
     *                *   *   *   *   *
     *              *   *   *   *   *   *
     *                *   *   *   *   *
     *              *   *   *   *   *   *
     * r - hexagon radius
     * d - отступы
     *
     * x, y - крайние левая - верхняя координата, крайнего левого верхнего хексагона.
     */
    public static void main(String[] args) {
        createGuiBattlefield(40, 40, new BattleField(9, 6), 40, 5);
    }


    public static List<HexagonItem> createGuiBattlefield(int x, int y, BattleField battleField, int r, int d) {
        List<HexagonItem> list = new ArrayList<>();
        int j = 0;//номер эл-та в ряде
        boolean isOddRow = true;
        int xx = 0, yy = 0;

        LOGGER.trace("Start computing battlefield item coordinates");
        for (int i = 0; i < battleField.getSize(); i++) {

            if (j == (isOddRow ? battleField.getOddRowLength() : battleField.getEvenRowLength())) {
                // sb.append("\n"); //тут переход на новую строку

                if (isOddRow) LOGGER.trace("odd row is finished");
                else LOGGER.trace("even row is finished");

                // xx = x + r;
                xx = 0;
                yy += r + (r - 10);//todo константа отступ по у

                isOddRow = !isOddRow;

                if (!isOddRow) {
                    // sb.append("\t"); //тут сдвиг вперед на пол хекса
                    xx += r;
                }
                j = 0;
            }

            //добавление эл-та
            list.add(new HexagonItem(new Hexagon(x + xx, y + yy, r), i));

            LOGGER.trace("x: {},  y: {}", (x + xx), (y + yy));
            j++;
            xx += r * 2;
        }

        LOGGER.trace("Finish computing battlefield item coordinates");
        return list;
    }
}
