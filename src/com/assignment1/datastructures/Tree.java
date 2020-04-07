package com.assignment1.datastructures;

import java.util.ArrayList;
import java.util.List;

public class Tree<T> {
    private T value;
    private List<Tree<T>> children;

    private Tree(T value) {
        this.value = value;
        this.children = new ArrayList<>();
    }


    public T getValue() {
        return value;
    }

    public List<Tree<T>> getChildren() {
        return children;
    }
}
