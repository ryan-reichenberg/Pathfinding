package com.assignment1.datastructures;


import com.assignment1.Location;

import java.util.Comparator;
import java.util.Objects;

public class AStarNode<T> extends Node<T> implements Comparable<AStarNode<T>>{
    private int g,h = 0;
    private int f;

    public void setF(int f) {
        this.f = f;
    }

    public AStarNode(Node<T> node) {
        super(node.getValue(), node.getParent(), node.getDirection());
    }

    public int getH() {
        return h;
    }

    public int getF() {
        return g + h;
    }


    public int getG() {
        return g;
    }

    public void setG(int g) {
        this.g = g;
    }

    public void setH(int h) {
        this.h = h;
    }

    @Override
    public String toString() {
        return "AStarNode{" +
                "value=" + getValue() +
                ", g=" + g +
                ", h=" + h +
                ", f=" +  getF()+
                ", s parent=" + super.getParent() +
                ", dir=" + super.getDirection() +
                '}';
    }


    @Override
    public boolean equals(Object o) {
        if(this == o) return true;
        if (!(o instanceof AStarNode)) return false;
        AStarNode<?> aStarNode = (AStarNode<?>) o;
        return this.getValue().equals(aStarNode.getValue());
    }

    @Override
    public int compareTo(AStarNode<T> o) {
        return this.getF() - o.getF();
    }
}
