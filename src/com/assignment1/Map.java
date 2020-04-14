package com.assignment1;

import com.assignment1.datastructures.Node;

import java.util.ArrayList;
import java.util.List;

public class Map {
    private int width = 0;
    private int height = 0;
    private List<Node<Location>> walls;
    private Node<Location> startNode;
    private List<Node<Location>> endNodes;

    public Map(int width, int height, List<Node<Location>> walls, Node<Location> startNode, List<Node<Location>> endNodes) {
        this.width = width;
        this.height = height;
        this.walls = walls;
        this.startNode = startNode;
        this.endNodes = endNodes;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public List<Node<Location>> getWalls() {
        return walls;
    }

    public Node<Location> getStartNode() {
        return startNode;
    }

    public List<Node<Location>> getEndNodes() {
        return endNodes;
    }

    public boolean isInMap(Location location) {
        return location.getX() >= 0 && location.getX() <=  getWidth() -1
                &&  location.getY() >= 0 && location.getY() <= getHeight() -1;
    }
    public boolean isInWall(Location location){
        for(Node<Location> wall: walls){
            if(((Wall)wall.getValue()).isInWall(location)){
                return true;
            }
        }
        return false;
    }
    @Override
    public String toString() {
        return "Map{" +
                "width=" + width +
                ", height=" + height +
                ", number of walls=" + walls.size() +
                ", walls=" + walls +
                ", startNode=" + startNode +
                ", endNodes=" + endNodes +
                '}';
    }
}
// Tree building attempt


