package com.company.Hero;

public abstract class FieldItem {
    private boolean isSelected = false;


    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public  boolean turnSelect(){
        isSelected = !isSelected;
        return isSelected;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public abstract String getContentType();
}
