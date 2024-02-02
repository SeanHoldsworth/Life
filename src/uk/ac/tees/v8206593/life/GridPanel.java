package uk.ac.tees.v8206593.life;

import javax.swing.JPanel;
import javax.swing.Timer;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Rectangle2D;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GridPanel extends JPanel {
    private Life life;
    private final int initialPanelWidth = 480;  // pixels
    private final int initialTimerDelay = 300;  // ms
    private int cellsPerSide;
    private Timer tm;

    public GridPanel(int cellsPerSide) {
        Dimension size = new Dimension(initialPanelWidth, initialPanelWidth);
        setPreferredSize(size);
        setMinimumSize(size);
        life = new Life(cellsPerSide);
        this.cellsPerSide = cellsPerSide;

        tm = new Timer(initialTimerDelay, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                life.createNextGen();
                repaint();
            }
        });

        tm.start();
    }

    public void setSpeed(int gps) {    // Generations Per Second
        if (gps == 0)
            tm.stop();
        else  {
            tm.setDelay(1000 / gps);
            if (!tm.isRunning())
                tm.start();
        }
    }

    public void setSize(int size) {
        cellsPerSide = size;
        restart();
    }

    public void restart() {
        life.initialize(cellsPerSide);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);    // Draws background

        Graphics2D g2d = (Graphics2D) g;

        int nCells = cellsPerSide * cellsPerSide;
        int panelWidth = Math.min(getWidth(), getHeight());
        double cellSize = (double) panelWidth / cellsPerSide;
        double cellWidth = cellSize - 2.0;
        double xOffset = 1.0, yOffset = 1.0;
        int col = 0;

        int[] cells = life.getCells();

        g2d.setRenderingHint(
            RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        for (int i = 0; i < nCells; i++) {
            g2d.setColor((cells[i] == 0) ? Color.WHITE : Color.BLACK);

            g2d.fill(new Rectangle2D.Double(
                xOffset, yOffset, cellWidth, cellWidth));

            if (++col == cellsPerSide) {
                col = 0;
                xOffset = 1.0;
                yOffset += cellSize;
            }
            else
                xOffset += cellSize;
        }
    }
}
