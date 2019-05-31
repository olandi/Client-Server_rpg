package com.singlePlayer.company.model.Hero;

public abstract class FieldItem {
    protected boolean isSelected = false;


    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public abstract String getContentType();
}
