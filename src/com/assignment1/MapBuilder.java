package com.assignment1;

import com.assignment1.datastructures.Node;

import java.util.ArrayList;
import java.util.List;

public class MapBuilder {

    private int width = 0;
    private int height = 0;
    private List<Node<Wall>> walls;
    private Node<Location> startNode;
    private List<Node<Location>> endNodes;
    private boolean ui = false;

    public void setUI(boolean ui) {
        this.ui = ui;
    }

    public MapBuilder() {
        this.walls = new ArrayList<>();
        this.endNodes = new ArrayList<>();
    }

    public MapBuilder addWall(Node<Wall> node){
        this.walls.add(node);
        return this;
    }

    public MapBuilder setWidth(int width){
        this.width = width;
        return this;
    }

    public MapBuilder setHeight(int height){
        this.height = height;
        return this;
    }
    public MapBuilder addStartNode(Node<Location> node){
        // Only set one start node
        if(this.startNode == null) {
            this.startNode = node;
        }
        return this;
    }

    public MapBuilder addGoalNode(Node<Location> goal){
        this.endNodes.add(goal);
        return this;
    }

    public Map build() {
        return new Map(this.width, this.height, this.walls, this.startNode, this.endNodes, this.ui);
    }
}
