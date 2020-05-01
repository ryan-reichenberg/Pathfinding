package com.assignment1.ui;

import com.assignment1.Map;
import com.assignment1.algorithms.SearchType;

import javax.swing.*;
import java.awt.*;

public class Launcher {
    private JPanel container;
    private ControlPanel controls;
    private JFrame frame;
    private Map map;

    public Launcher(Map map) {
        this.map = map;
        int w = map.getMazeWidth() *  40;
        int h = map.getMazeHeight()* 40;
        int controlsW = 200;
        int margin = 10;

        frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);

        frame.setPreferredSize(new Dimension(w + controlsW + 15 + (margin * 3), h + 40 + (margin * 2)));

        container = new JPanel();
        container.setLayout(null);

        controls = new ControlPanel(controlsW, 120, map);
        controls.setBounds(w + (margin * 2), margin, controlsW, h);

        map.setBounds(margin, margin, w, h);

        container.add(controls);
        container.add(map);

        frame.setContentPane(container);
    }

    public void launch() throws InterruptedException {
        frame.pack();
        frame.setVisible(true);
        // Game loop
        while(true){
            if(controls.isReset()){
                map.reset();
                controls.setReset(false);
            }
            if(controls.getAlg() != null){
                SearchType type = SearchType.valueOf(controls.getAlg());
                if(type != null){
                    map.search(SearchType.valueOf(controls.getAlg()));
                    controls.setAlg(null);
                }
            }
            map.repaint();
            Thread.sleep(200);

        }

    }
}
