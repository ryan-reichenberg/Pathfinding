package com.assignment1.ui;

import com.assignment1.FileHandler;
import com.assignment1.Map;
import com.assignment1.algorithms.SearchType;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;

public class ControlPanel extends JPanel {
    private  JComboBox<String> selector;
    private String alg;
    private boolean reset = false;
    private int selection;

    public ControlPanel(int width, int height, Map map){
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
            if(selection != selector.getSelectedIndex()) {
                selection = selector.getSelectedIndex();
            }
        });
        add(selector);

        JButton reset = new JButton("Reset");
        reset.setBounds(10, height - 40, 80, 30);
        reset.addActionListener((ActionEvent ae) -> {
            if(!isReset())
                setReset(true);
        });
        add(reset);

        JButton start = new JButton("Start");
        start.setBounds(110, height - 40, 80, 30);
        start.addActionListener((ActionEvent ae) -> {
            if(getAlg() == null){
                setReset(true);
                switch(selection) {
                    case 0:
                        setAlg(SearchType.DFS.name());
                        break;
                    case 1:
                        setAlg(SearchType.BFS.name());
                        break;
                    case 2:
                        setAlg(SearchType.GBFS.name());
                        break;
                    case 3:
                        setAlg(SearchType.A_STAR.name());
                        break;
                    case 4:
                        setAlg(SearchType.IDA_STAR.name());
                        break;
                    case 5:
                        setAlg(SearchType.IDDFS.name());
                        break;

                }
            }
        });
        add(start);
    }

    public String getAlg() {
        return alg;
    }

    public void setAlg(String alg) {
        this.alg = alg;
    }

    public boolean isReset() {
        return reset;
    }

    public void setReset(boolean reset) {
        this.reset = reset;
    }
}
