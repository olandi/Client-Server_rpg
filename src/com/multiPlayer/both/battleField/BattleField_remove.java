/*
package com.multiPlayer.both.battleField;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

*/
/**
 * Класс формирует игровое поле. Содержит доступные и недоступные ячейки для перемещения.
 *//*

public class BattleField {
    private int row;
    private int col;
    private boolean[][] battleField;

    public BattleField(int row, int col) {
        this.row = row;
        this.col = col;
        battleField = new boolean[row][col];

        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                battleField[i][j] = true;
            }
        }
    }

    */
/*

        public Set<Integer> getMovementArray(int index, int range) {

            ;
        }

        public Set<Integer> getAllNeighbours(int index) {
            Set<Integer> set = new HashSet<>();

            set.add(getAndMoveToEast(index));
            set.add(getAndMoveToWest(index));
            set.add(getAndMoveToNordEast(index));
            set.add(getAndMoveToNordWest(index));
            set.add(getAndMoveToSouthEast(index));
            set.add(getAndMoveToSouthWest(index));
            return set;
        }

    *//*

    public void setBattleFieldTileAvailable(int x, int y, boolean value) {
        battleField[y][x] = value;
    }

    private boolean isBattleFieldTileAvailable(int x, int y) {
        return battleField[y][x];
    }


    private int getAndMoveToEast(int fromIndex) {
        return isValidIndex(fromIndex + 1) ? fromIndex + 1 : -1;
    }

    private int getAndMoveToWest(int fromIndex) {
        return isValidIndex(fromIndex - 1) ? fromIndex - 1 : -1;
    }

    private int getAndMoveToNordEast(int fromIndex) {
        return isValidIndex(fromIndex - evenRowLength) ? fromIndex - evenRowLength : -1;
    }

    private int getAndMoveToNordWest(int fromIndex) {
        return isValidIndex(fromIndex - oddRowLength) ? fromIndex - oddRowLength : -1;
    }

    private int getAndMoveToSouthEast(int fromIndex) {
        return isValidIndex(fromIndex + oddRowLength) ? fromIndex + oddRowLength : -1;
    }

    private int getAndMoveToSouthWest(int fromIndex) {
        return isValidIndex(fromIndex + evenRowLength) ? fromIndex + evenRowLength : -1;
    }


public Set<Integer> getMovementArray(int index, int range) {

    Set<Integer> first = new HashSet<>();
    Set<Integer> second = new HashSet<>();

    first.add(index);

    for (int i = 0; i < range; i++){
        first.forEach(item -> second.addAll(getAllNeighbours(item)));

        first.addAll(second);
    }

    first.remove(index);
    return first;
}


    public Set<Integer> getAllNeighbours(int index) {
        Set<Integer> set = new HashSet<>();

        set.add(getAndMoveToEast(index));
        set.add(getAndMoveToWest(index));
        set.add(getAndMoveToNordEast(index));
        set.add(getAndMoveToNordWest(index));
        set.add(getAndMoveToSouthEast(index));
        set.add(getAndMoveToSouthWest(index));
        return set;
    }


    public int getIndexByCoords(int i, int j){

        return i * col + j * row;
}

    private boolean isValidCoords(int x, int y) {
        return x >= 0 && y < col && y <= 0 && y < row && battleField[y][x];
        //index >= 0 && index < battleField.length && battleField[index];
    }

    private boolean isValidIndex(int index) {
    int i = index / col;
    int j = index % col;
        return j >= 0 && j < col && i >= 0 && i < row && battleField[i][j];
    }

    private boolean getCoordsByIndex(int index){

    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < battleField.length; i++) {
            if (i % 2 != 0) sb.append("\t");
            for (int j = 0; j < battleField[i].length; j++) {
                if (battleField[i][j])
                sb.append("(").append(i).append(":").append(j).append(")").append("\t");
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        BattleField a = new BattleField(6, 8);
*/
/*
        a.setBattleFieldTileAvailable(0, false);
        a.setBattleFieldTileAvailable(30, false);
        a.setBattleFieldTileAvailable(7, false);
        *//*

        a.setBattleFieldTileAvailable(4, 3,false);

        for (boolean[] row : a.battleField){
            for (boolean element : row){
                System.out.print(element+ " ");}
            System.out.println();
        }



        System.out.println(a);
        System.out.println(
                a.battleField.length

        );


    }

}
*/
