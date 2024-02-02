package uk.ac.tees.v8206593.life;

import javax.swing.SwingUtilities;

public class LifeApp {
    public static void main(String args[]) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new MainFrame("Conway's Game of Life").setVisible(true);
            }
        });
    }
}
