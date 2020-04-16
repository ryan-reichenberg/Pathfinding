package com.assignment1.datastructures;

import com.assignment1.Direction;

public class AStarNode<T> extends Node<T> implements Comparable<AStarNode<T>> {
    private int f,g,h;

    public int getF() {
        return f;
    }

    public void setF(int f) {
        this.f = f;
    }

    public int getG() {
        return g;
    }

    public void setG(int g) {
        this.g = g;
    }

    public int getH() {
        return h;
    }

    public void setH(int h) {
        this.h = h;
    }

    public AStarNode(Node<T> node) {
        super(node.getValue(), node.getParent(), node.getDirection());
    }
    public AStarNode(T value, Node<T> parent, Direction direction) {
        super(value, parent, direction);
    }
    public AStarNode(T value){
        super(value);
    }


    @Override
    public int compareTo(AStarNode<T> o) {
        return 0;
    }
}
