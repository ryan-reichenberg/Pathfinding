package com.assignment1;

import com.assignment1.algorithms.AStar;
import com.assignment1.algorithms.SearchType;
import com.assignment1.datastructures.AStarNode;

public class Main {

    public static void main(String[] args) {
        Map map = FileHandler.readFile("map.txt");
//        map.search(SearchType.A_STAR);
        AStar aStar = new AStar();
        AStarNode<Location> node = aStar.search(map);
        if(node != null)
            System.out.println(node.getPath());
    }
}
