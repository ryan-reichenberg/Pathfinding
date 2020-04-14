package com.assignment1.algorithms;

import com.assignment1.Location;
import com.assignment1.Map;
import com.assignment1.datastructures.Node;

import java.util.*;

public class BreadthFirst extends SearchAlgorithm {

    @Override
    public SearchResult search(Map map) {
        Queue queue = new ArrayDeque();
        super.search(queue, map);
        return null;
    }
}
