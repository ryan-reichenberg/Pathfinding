package com.assignment1.datastructures;

import com.assignment1.Direction;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class Node<T>{
    private T value;
    private Node<T> parent;
    private Direction direction;
    private List<String> path = new ArrayList<>();
    private int numSteps = 0;

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

    public int getNumSteps() {
        return numSteps;
    }

    public void incrementStepNumber() {
        this.numSteps += 1;
    }

    public T getValue() {
        return value;
    }


    public Node<T> getParent() {
        return parent;
    }

    public void setParent(Node<T> parent) {
        this.parent = parent;
    }

    public List<String> getPath(){
        path.add(this.getDirection().name());
        while(parent != null){
            if(parent.getDirection() != null) {
                path.add(parent.getDirection().name());
                incrementStepNumber();
            }
            parent = parent.getParent();
        }
        System.out.println(path.size());
        Collections.reverse(path);
        return path;

    }
    public BestFirstNode<T> toBestFirstNode(){
        return new BestFirstNode<>(this);
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
