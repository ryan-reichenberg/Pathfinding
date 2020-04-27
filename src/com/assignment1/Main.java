package com.assignment1;

import com.assignment1.algorithms.SearchType;
import com.assignment1.ui.ControlPanel;
import com.assignment1.ui.GridPanel;

import javax.swing.*;
import java.awt.*;

public class Main {

    private static JPanel container;
    private static GridPanel canvas;
    private static ControlPanel controls;
    private static JFrame frame;

    public static void main(String[] args) throws InterruptedException {
        int w = 11 *  40;
        int h = 5* 40;
        int controlsW = 200;
        int margin = 10;

        Map map = FileHandler.readFile("map.txt");


        frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);

        frame.setPreferredSize(new Dimension(w + controlsW + 15 + (margin * 3), h + 40 + (margin * 2)));

        container = new JPanel();
        container.setLayout(null);

        controls = new ControlPanel(controlsW, 120);
        controls.setBounds(w + (margin * 2), margin, controlsW, h);

        map.setBounds(margin, margin, w, h);

        container.add(controls);
        container.add(map);

        frame.setContentPane(container);
        frame.pack();
        frame.setVisible(true);

        // This should happen on start clicked
        Thread.sleep(500);
        map.search(SearchType.IDA_STAR);

    }
}
