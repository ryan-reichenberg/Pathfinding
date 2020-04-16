package com.assignment1.datastructures;

import com.assignment1.Direction;

import java.util.Comparator;

public class BestFirstNode<T> extends Node<T> implements Comparable<BestFirstNode<T>> {
    private int h;

    public BestFirstNode(Node<T> node) {
        super(node.getValue(), node.getParent(), node.getDirection());
    }
    public BestFirstNode(T value, Node<T> parent, Direction direction) {
        super(value, parent, direction);
    }

    public BestFirstNode(T value) {
        super(value);
    }

    public int getH() {
        return h;
    }

    public void setH(int h) {
        this.h = h;
    }

    @Override
    public int compareTo(BestFirstNode<T> o) {
        return this.getH() - o.getH();
    }
}
