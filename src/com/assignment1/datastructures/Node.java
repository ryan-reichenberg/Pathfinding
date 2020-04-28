package com.assignment1.datastructures;

import com.assignment1.Direction;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class Node<T>{
    private T value;
    private Node<T> parent;
    private Direction direction;
    private int depth;
    private List<Node<T>> path = new ArrayList<>();

    public Direction getDirection() {
        return direction;
    }


    public Node(T value, Node<T> parent, Direction direction) {
        this.value = value;
        this.parent = parent;
        this.direction = direction;
    }
    public Node(T value) {
      this(value, null, null);
    }



    public T getValue() {
        return value;
    }


    public Node<T> getParent() {
        return parent;
    }

    public int getDepth() {
        return depth;
    }

    public void setDepth(int depth) {
        this.depth = depth;
    }

    public List<Node<T>> getPath(){
        path.add(this);
        while(parent != null){
            if(parent.getDirection() != null) {
                path.add(parent);
            }
            parent = parent.getParent();
        }
        Collections.reverse(path);
        return path;
    }


    @Override
    public String toString() {
        return "Node{" +
                "value=" + value +
                ", parent=" + parent +
                ", direction=" + direction +
                '}';
    }
}
