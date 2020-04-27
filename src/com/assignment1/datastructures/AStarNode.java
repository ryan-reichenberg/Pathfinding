package com.assignment1.datastructures;



public class AStarNode<T> extends Node<T> implements Comparable<AStarNode<T>>{
    private double g;
    private double h;

    public AStarNode(Node<T> node) {
        super(node.getValue(), node.getParent(), node.getDirection());
    }


    public double getF() {
        return g + h;
    }


    public double getG() {
        return g;
    }

    public void setG(double g) {
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
                ", f=" +  getF() +
                ", s parent=" + super.getParent() +
                ", dir=" + super.getDirection() +
                '}';
    }

    @Override
    public int compareTo(AStarNode<T> o) {
        return (int)(this.getF() - o.getF());
    }
}
