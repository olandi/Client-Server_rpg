package com.multiPlayer.client;

import javax.swing.*;

public class MainLayoutController {

    private LoginGui loginGui = new LoginGui(this);
    private ClientGuiView clientGuiView = new ClientGuiView(this);
    private MainLayoutView mainLayoutView = new MainLayoutView(this);

    private MainLayoutModel mainLayoutModel = new MainLayoutModel();

    public MainLayoutModel getMainLayoutModel() {
        return mainLayoutModel;
    }

    public MainLayoutView getMainLayoutView() {
        return mainLayoutView;
    }

    public void setMainLayoutView(MainLayoutView mainLayoutView) {
        this.mainLayoutView = mainLayoutView;
    }

    public LoginGui getLoginGui() {
        return loginGui;
    }


    public ClientGuiView getClientGuiView() {
        return clientGuiView;
    }


    public void updatePlayerLabels() {
        clientGuiView.updatePlayerLabel(mainLayoutModel.getPlayer());
        clientGuiView.updateServerLabel(mainLayoutModel.getConnection().getSocket().getRemoteSocketAddress().toString());
    }


    public static void main(String[] args) {

        try {
            //UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
            UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
        } catch (UnsupportedLookAndFeelException ex) {
            ex.printStackTrace();
        } catch (IllegalAccessException ex) {
            ex.printStackTrace();
        } catch (InstantiationException ex) {
            ex.printStackTrace();
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        /* Turn off metal's use of bold fonts */
        UIManager.put("swing.boldMetal", Boolean.FALSE);

        //Schedule a job for the event dispatch thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(() ->{
            MainLayoutController controller = new MainLayoutController();

            controller.getMainLayoutView().createAndShowGUI();
        });
    }
}
