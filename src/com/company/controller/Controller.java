package com.company.controller;

import com.company.client.SwingView.CombatLogPanel;
import com.company.client.SwingView.HittingPanel;
import com.company.client.SwingView.BattleFieldPanel;
import com.company.client.SwingView.TimerPanel;

import javax.swing.*;
import java.awt.event.MouseListener;

public class Controller {

    private static CombatLogPanel combatLogPanel;
    private static BattleFieldPanel battleFieldPanel;
    private static HittingPanel hittingPanel;
    private static TimerPanel timerPanel;
    private static JPanel mainGamePanel;

    public static JPanel getMainGamePanel() {
        return mainGamePanel;
    }

    public static void setMainGamePanel(JPanel mainGamePanel) {
        Controller.mainGamePanel = mainGamePanel;
    }

    public static CombatLogPanel getCombatLogPanel() {
        return combatLogPanel;
    }

    public static void setCombatLogPanel(CombatLogPanel combatLogPanel) {
        Controller.combatLogPanel = combatLogPanel;
    }

    public static BattleFieldPanel getBattleFieldPanel() {
        return battleFieldPanel;
    }

    public static void setBattleFieldPanel(BattleFieldPanel battleFieldPanel) {
        Controller.battleFieldPanel = battleFieldPanel;
    }

    public static HittingPanel getHittingPanel() {
        return hittingPanel;
    }

    public static void setHittingPanel(HittingPanel hittingPanel) {
        Controller.hittingPanel = hittingPanel;
    }

    public static TimerPanel getTimerPanel() {
        return timerPanel;
    }

    public static void setTimerPanel(TimerPanel timerPanel) {
        Controller.timerPanel = timerPanel;
    }
}
