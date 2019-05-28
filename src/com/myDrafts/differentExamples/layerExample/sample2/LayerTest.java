package com.myDrafts.differentExamples.layerExample.sample2;


import javax.swing.*;
import javax.swing.plaf.LayerUI;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


public class LayerTest {

    private JFrame frame;
    private FirstPanel firstPanel;
    private SecondPanel secondPanel;
    private int delta = 26;

    private BlueRectangleMouseListener blueRectangleMouseListener = new BlueRectangleMouseListener();
    private GreenRectangleMouseListener greenRectangleMouseListener = new GreenRectangleMouseListener();

    public static void main(String[] args) {
        new LayerTest().buildGUI();
    }

    private void buildGUI() {
        EventQueue.invokeLater(() ->
        {
            frame = new JFrame("W");
            frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            frame.setSize(400, 400);
            frame.setResizable(false);


            frame.addMouseListener(blueRectangleMouseListener);


            firstPanel = new FirstPanel();
            secondPanel = new SecondPanel();


            JLayer<JPanel> jlayer = new JLayer<JPanel>(firstPanel, secondPanel);
            frame.add(jlayer);


            frame.setVisible(true);

        });
    }

    class BlueRectangleMouseListener extends MouseAdapter {
        @Override
        public void mouseClicked(MouseEvent e) {
            super.mouseClicked(e);

            Point p = e.getPoint();
            p.y-=delta;

            if (firstPanel.rectangle.contains(p)) {

                firstPanel.isFirstPanelClicked = true;
                frame.removeMouseListener(blueRectangleMouseListener);
                frame.addMouseListener(greenRectangleMouseListener);
                frame.repaint();
            }
            System.out.println(this + " " + firstPanel.isFirstPanelClicked + e.getPoint());
        }
    }

    class GreenRectangleMouseListener extends MouseAdapter {
        @Override
        public void mouseClicked(MouseEvent e) {
            super.mouseClicked(e);

            Point p = e.getPoint();
            p.y-=delta;

            if (secondPanel.rectangle.contains(p)) {
                /*secondPanel.isSecondPanelClicked = true;*/
                firstPanel.isFirstPanelClicked = false;
                frame.removeMouseListener(greenRectangleMouseListener);
                frame.addMouseListener(blueRectangleMouseListener);
                frame.repaint();
            }
            System.out.println(this + " " + secondPanel.isSecondPanelClicked + e.getPoint());

        }
    }


    class FirstPanel extends JPanel {

        public Rectangle rectangle = new Rectangle(0, 0, 100, 100);
        public boolean isFirstPanelClicked = false;

        @Override
        public void paint(Graphics g) {
            super.paint(g);


            Graphics2D g2 = (Graphics2D) g.create();

            g2.setColor(Color.blue);
            //  g2.draw(rectangle);
            g2.fill(rectangle);
            g2.dispose();

        }

        @Override
        public String toString() {
            return "box";
        }

    }

    //class WaitLayerUI extends LayerUI<JPanel> implements ActionListener {
    class SecondPanel extends LayerUI<JPanel> {
        public Rectangle rectangle = new Rectangle(0, 200, 100, 100);
        public boolean isSecondPanelClicked = false;
        private int mFadeCount;
        private int mFadeLimit = 15;


        @Override
        public void paint(Graphics g, JComponent c) {
            super.paint(g, c);
            int w = c.getWidth();
            int h = c.getHeight();


            Graphics2D g2 = (Graphics2D) g.create();

            if (firstPanel.isFirstPanelClicked) {
                float fade = (float) mFadeCount / (float) mFadeLimit;
                // Gray it out.
                Composite urComposite = g2.getComposite();
                g2.setComposite(AlphaComposite.getInstance(
                        AlphaComposite.SRC_OVER, .5f * /*fade*/1f));
                g2.fillRect(0, 0, w, h);
                g2.setComposite(urComposite);


                g2.setColor(Color.green);
                g2.fill(rectangle);

            }


        }

    }
}