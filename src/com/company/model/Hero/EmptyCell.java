package com.company.model.Hero;

public class EmptyCell extends FieldItem {
    //public static final EmptyCell EMPTY_CELL = new EmptyCell();
    private int index;

    public EmptyCell(int index) {
        this.index = index;
    }

    public int getIndex() {
        return index;
    }

    @Override
    public String getContentType() {
        return "EmptyCell";
    }
}
