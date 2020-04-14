package com.assignment1;

import com.assignment1.algorithms.BreadthFirst;
import com.assignment1.algorithms.DepthFirst;

public class Main {

    public static void main(String[] args) {
        Map map = FileHandler.readFile("map.txt");
        DepthFirst dfs = new DepthFirst();
        dfs.search(map);
        BreadthFirst bfs  = new BreadthFirst();
        bfs.search(map);
    }
}
