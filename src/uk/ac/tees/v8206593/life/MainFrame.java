package uk.ac.tees.v8206593.life;

import javax.swing.JFrame;

import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;

import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

class MainFrame extends JFrame {
    private final int cellsPerSide = 40;
    private GridPanel gridPanel;
    private ControlPanel controlPanel;

    public MainFrame(String name) {
        super(name);

        gridPanel = new GridPanel(cellsPerSide);
        controlPanel = new ControlPanel();

        controlPanel.addRestartListener(new RestartListener() {
            @Override
            public void restartButtonPressed() {
                gridPanel.restart();
            };
        });

        controlPanel.addSpeedChangeListener(new SpeedChangeListener() {
            @Override
            public void valueChanged(int gps) {
                gridPanel.setSpeed(gps);
            }
        });

        controlPanel.addSizeChangeListener(new SizeChangeListener() {
            @Override
            public void sizeChanged(int size) {
                gridPanel.setSize(size);
            }
        });

        // Maintain square aspect ratio of gridPanel component.

        gridPanel.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                int w = gridPanel.getWidth();
                int h = gridPanel.getHeight();
                int size =  Math.min(w, h);
                gridPanel.setPreferredSize(new Dimension(size, size));
                revalidate();
            }
        });

        setLayout(new GridBagLayout());
        GridBagConstraints gc = new GridBagConstraints();

        gc.fill = GridBagConstraints.BOTH;
        gc.gridx = 0; gc.gridy = 0;
        gc.weightx = 1; gc.weighty = 8; 
        add(gridPanel, gc);
        
        gc.gridx = 0; gc.gridy = 1;
        gc.weightx = 1; gc.weighty = 1; 
        add(controlPanel, gc);

        pack();

        // Do not let the frame be resized smaller that this minimum size.

        setMinimumSize(getSize());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
