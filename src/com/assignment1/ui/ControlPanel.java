package com.assignment1.ui;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;

public class ControlPanel extends JPanel {
    private  JComboBox<String> selector;

    public ControlPanel(int width, int height){
        setBorder(new LineBorder(Color.gray));
        setLayout(null);

        Label selectionLabel = new Label("Algorithm:");
        selectionLabel.setBounds(7, 10, width - 20, 25);
        add(selectionLabel);

        selector = new JComboBox<>();
        selector.addItem("Depth First Search");
        selector.addItem("Breath First Search");
        selector.addItem("Greedy Best First Search");
        selector.addItem("A*");
        selector.addItem("Iterative Deepening A*");
        selector.addItem("Iterative Deepening Depth First Search");
        selector.setBounds(10, 35, width - 20, 30);
        selector.addActionListener((ActionEvent e) -> {

        });
        add(selector);

        JButton reset = new JButton("Reset");
        reset.setBounds(10, height - 40, 80, 30);
        reset.addActionListener((ActionEvent ae) -> {
            // Reset UI and controls
        });
        add(reset);

        JButton start = new JButton("Start");
        start.setBounds(110, height - 40, 80, 30);
        start.addActionListener((ActionEvent ae) -> {
            // Start solving and drawing
        });
        add(start);
    }


}
