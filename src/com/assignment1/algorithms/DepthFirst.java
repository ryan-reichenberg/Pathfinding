package com.assignment1.algorithms;

import com.assignment1.Direction;
import com.assignment1.Location;
import com.assignment1.Map;
import com.assignment1.datastructures.Node;

import java.util.*;

public class DepthFirst extends SearchAlgorithm {

    @Override
    public SearchResult search(Map map) {
        Stack stack = new Stack();
        super.search(stack,map);
        return null;
    }

}
