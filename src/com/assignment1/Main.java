package com.assignment1;

import com.assignment1.algorithms.SearchType;

public class Main {

    public static void main(String[] args) {
        Map map = FileHandler.readFile("map.txt");
        map.search(SearchType.GBFS);
    }
}
