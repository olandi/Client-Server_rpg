package com.multiPlayer.both.battleField;

import java.io.Serializable;
import java.util.*;

/**
 * Класс формирует игровое поле. Содержит доступные и недоступные ячейки для перемещения.
 */
public class BattleField implements Serializable {
    private int oddRowLength;
    private int evenRowLength;
    private int col;
    private boolean[] battleField;
    private int size;

    public BattleField(int oddRowLength, int col) {
        this.oddRowLength = oddRowLength;
        this.col = col;
        this.evenRowLength = oddRowLength - 1;
        int battleFieldTilesQuantity =
                (col % 2 == 0) ?
                        (oddRowLength + evenRowLength) * col / 2 :
                        (oddRowLength + evenRowLength) * (col + 1) / 2 - evenRowLength;

        battleField = new boolean[battleFieldTilesQuantity];
        Arrays.fill(battleField, Boolean.TRUE);

        size = battleField.length;
    }

    public int getOddRowLength() {
        return oddRowLength;
    }

    public int getEvenRowLength() {
        return evenRowLength;
    }

    public int getCol() {
        return col;
    }

    public int getSize() {
        return size;
    }

    public boolean[] getBattleFieldItems() {
        return battleField;
    }

    public Set<Integer> getMovementArray(int index, int range) {

        Set<Integer> first = new HashSet<>();
        Set<Integer> second = new HashSet<>();

        first.add(index);

        for (int i = 0; i < range; i++) {
            first.forEach(item -> second.addAll(getAllNeighbours(item)));

            first.addAll(second);
        }

        first.remove(index);
        first.remove(-1);
        return first;
    }

    private Set<Integer> getAllNeighbours(int index) {
        Set<Integer> set = new HashSet<>();

        set.add(getAndMoveToEast(index));
        set.add(getAndMoveToWest(index));
        set.add(getAndMoveToNordEast(index));
        set.add(getAndMoveToNordWest(index));
        set.add(getAndMoveToSouthEast(index));
        set.add(getAndMoveToSouthWest(index));
        return set;
    }


    public void setBattleFieldTileAvailable(int index, boolean value) {
        battleField[index] = value;
    }

    public boolean isBattleFieldTileAvailable(int index) {
        return battleField[index];
    }


    private int getAndMoveToEast(int fromIndex) {

        if (isValidIndex(fromIndex + 1) && getRowNumber(fromIndex + 1) == getRowNumber(fromIndex))
            return fromIndex + 1;
        else
            return -1;
    }

    private int getAndMoveToWest(int fromIndex) {
        if (isValidIndex(fromIndex - 1) && getRowNumber(fromIndex - 1) == getRowNumber(fromIndex))
            return fromIndex - 1;
        else
            return -1;
    }

    private int getAndMoveToNordEast(int fromIndex) {

        if (isValidIndex(fromIndex - evenRowLength) && getRowNumber(fromIndex) - getRowNumber(fromIndex - evenRowLength) == 1)
            return fromIndex - evenRowLength;
        else
            return -1;

    }

    private int getAndMoveToNordWest(int fromIndex) {

        if (isValidIndex(fromIndex - oddRowLength) && getRowNumber(fromIndex) - getRowNumber(fromIndex - oddRowLength) == 1)
            return fromIndex - oddRowLength;
        else
            return -1;
    }

    private int getAndMoveToSouthEast(int fromIndex) {
        if (isValidIndex(fromIndex + oddRowLength) && getRowNumber(fromIndex) - getRowNumber(fromIndex + oddRowLength) == -1)
            return fromIndex + oddRowLength;
        else
            return -1;

        // return isValidIndex(fromIndex + oddRowLength) ? fromIndex + oddRowLength : -1;
    }

    private int getAndMoveToSouthWest(int fromIndex) {

        if (isValidIndex(fromIndex + evenRowLength) && getRowNumber(fromIndex) - getRowNumber(fromIndex + evenRowLength) == -1)
            return fromIndex + evenRowLength;
        else
            return -1;

        // return isValidIndex(fromIndex + evenRowLength) ? fromIndex + evenRowLength : -1;
    }


    private boolean isValidIndex(int index) {
        return index >= 0 && index < battleField.length && battleField[index];
    }

    @Override
    public String toString() {

        StringBuilder sb = new StringBuilder();

        int j = 0;
        boolean switcher = true;

        for (int i = 0; i < battleField.length; i++) {

            if (j == (switcher ? oddRowLength : evenRowLength)) {
                sb.append("\n");

                switcher = !switcher;

                if (!switcher) {
                    sb.append("\t");
                }
                j = 0;
            }

            sb.append(
                    battleField[i] ? i + "" : ""
            ).append("\t").append("\t");
            j++;

        }
        return sb.toString();
    }

    private int getRowNumber(int index) {
        int i = 0;
        int sum = -1;
        while (sum < battleField.length) {
            sum += (oddRowLength);
            if (index <= sum) return i;
            i++;

            sum += (evenRowLength);
            if (index <= sum) return i;
            i++;

        }
        return 0;
    }


    public int mirrorDirection(int from, int to) {
        if (to == getAndMoveToEast(from)) return getAndMoveToEast(to);
        else if (to == getAndMoveToWest(from)) return getAndMoveToWest(to);
        else if (to == getAndMoveToNordEast(from)) return getAndMoveToNordEast(to);
        else if (to == getAndMoveToNordWest(from)) return getAndMoveToNordWest(to);
        else if (to == getAndMoveToSouthEast(from)) return getAndMoveToSouthEast(to);
        else if (to == getAndMoveToSouthWest(from)) return getAndMoveToSouthWest(to);
        else return -1;
    }

    public static void main(String[] args) {
        BattleField a = new BattleField(8, 5);

        a.setBattleFieldTileAvailable(0, false);
        a.setBattleFieldTileAvailable(30, false);
        a.setBattleFieldTileAvailable(7, false);
        a.setBattleFieldTileAvailable(37, false);

        System.out.println(a);

        System.out.println(
                "\n\n length of array: " + a.battleField.length + "\n\n"

        );

        System.out.println(
                a.getMovementArray(23, 1)

        );

       /* int ind = 0;

        for (int i = 0; i < a.battleField.length; i++)
            System.out.println(i + " " + a.getRowNumber(i));


*/
    }

}
