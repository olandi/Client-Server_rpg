package com.singlePlayer.company.client.SwingView;

import javax.swing.*;
import java.awt.event.MouseListener;
import java.util.Arrays;

public class MainPanel extends JPanel {

    private boolean isHittingPanelVisible = false;
    MouseListener BattleFieldMouseListener;
    MouseListener HittingPanelMouseListener;

    public void closeHittingPanel() {
        isHittingPanelVisible = false;
    }

    public void openHittingPanel() {
        isHittingPanelVisible = true;
    }

    public boolean isVisibleBattleFrame() {
        return isHittingPanelVisible;
    }

    public void removeCurrentMouseListener(MouseListener mouseListener) {
        removeMouseListener(mouseListener);
    }

    public void removeCurrentMouseListener() {
        removeMouseListener(getMouseListeners()[0]);
    }

    public void removeAllMouseListeners() {
        Arrays.stream(getMouseListeners()).forEach(this::removeMouseListener);
    }

}
/*
* for (Iterator<Integer> iterator = list.iterator(); iterator.hasNext(); )
    if (iterator.next() > 10)
        iterator.remove();*/