package com.assignment1.ui;

import com.assignment1.Location;
import com.assignment1.Map;
import com.assignment1.Wall;
import com.assignment1.datastructures.Node;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class GridPanel extends JPanel {

    private BasicStroke defaultStroke;
    private BasicStroke widerStroke;
    private Map map;

    public GridPanel(ControlPanel control, Map map) {
        this.map = map;
        this.defaultStroke = new BasicStroke();
        this.widerStroke = new BasicStroke(2);


        setBorder(new LineBorder(Color.gray));

        addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent me) {
            }

            @Override
            public void mousePressed(MouseEvent me) {
                int x = me.getX();
                int y = me.getY();


                int tileX = x / 20;
                int tileY = y / 20;

//                Tile t = grid.find(tileX, tileY);
//
//                if (t != null) {
//                    controls.selectTile(t);
//                }
            }

            @Override
            public void mouseReleased(MouseEvent me) {
            }

            @Override
            public void mouseEntered(MouseEvent me) {
            }

            @Override
            public void mouseExited(MouseEvent me) {
            }
        });

    }


    @Override
    protected void paintComponent(Graphics g1) {
        super.paintComponent(g1);

        Graphics2D g = (Graphics2D) g1;
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        // Map generation
        for (int x = 0; x < map.getWidth(); x++) {
            for(int y = 0; y < map.getHeight(); y++) {
                g.setColor(new Color(220, 220, 220));
                g.drawRect(x * 40, y * 40, 40, 40);
            }
        }
        // Paint Start node
        g.setColor(new Color(78, 189, 30));
        g.fillRect(map.getStartNode().getValue().getX() * 40, map.getStartNode().getValue().getY() * 40, 40, 40);

        // Paint walls:
        // NOTE: Not super efficient but done for aesthetics
        for(Node<Wall> wall : map.getWalls()) {
            for(int w = 0; w < wall.getValue().getWidth(); w++){
                for(int h = 0; h < wall.getValue().getHeight(); h++) {
                    g.setColor(Color.GRAY);
                    g.fillRect((wall.getValue().getX() + w) * 40, (wall.getValue().getY() + h) * 40, 40,  40);
                    g.setColor(new Color(220, 220, 220));
                    g.drawRect((wall.getValue().getX() + w) * 40, (wall.getValue().getY() + h) * 40, 40,  40);
                }
            }
        }

        // Paint Goal states
        for(Node<Location> goal : map.getEndNodes()){
            g.setColor(new Color(189, 57, 30));
            g.fillRect(goal.getValue().getX() * 40, goal.getValue().getY() * 40, 40,  40);
        }

    }

    public void updateUI(){
        repaint();
    }


}
