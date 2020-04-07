package com.assignment1;

import java.util.ArrayList;
import java.util.List;

public class Map {
    private int width = 0;
    private int height = 0;
    private List<Node> walls;
    private Node startNode;
    private List<Node> endNodes;

    public Map(int width, int height, List<Node> walls, Node startNode, List<Node> endNodes) {
        this.width = width;
        this.height = height;
        this.walls = walls;
        this.startNode = startNode;
        this.endNodes = endNodes;
    }

    public void addWall(Node node){
        this.walls.add(node);
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public List<Node> getWalls() {
        return walls;
    }

    public Node getStartNode() {
        return startNode;
    }

    public List<Node> getEndNodes() {
        return endNodes;
    }
}
