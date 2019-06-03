package com.multiPlayer.client.swing.model;

public class HexagonItem {

    private int index;
    private Hexagon hexagon;
    private boolean isSelected = false;

    public HexagonItem(Hexagon hexagon, int cellIndex) {
        this.hexagon = hexagon;
        this.index = cellIndex;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public int getIndex() {
        return index;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setHexagon(Hexagon hexagon) {
        this.hexagon = hexagon;
    }

    public Hexagon getHexagon() {
        return hexagon;
    }

}
