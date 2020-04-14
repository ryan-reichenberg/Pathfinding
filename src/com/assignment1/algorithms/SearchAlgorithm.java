package com.assignment1.algorithms;

import com.assignment1.Direction;
import com.assignment1.Location;
import com.assignment1.Map;
import com.assignment1.datastructures.Node;

import java.util.*;

public abstract class SearchAlgorithm {
    private List<Node<Location>> visited;

    public SearchAlgorithm() {
        this.visited = new ArrayList<>();
    }

    public abstract SearchResult search(Map map);

    public SearchResult search(Collection collection, Map map) {
        collection.add(map.getStartNode());
        visited.add(map.getStartNode());
        while (!collection.isEmpty()) {
            Object element = collection instanceof Stack ? ((Stack) collection).pop() : ((Queue) collection).poll();
            Node<Location> current = (Node<Location>) element;
            if (current.getValue().equals(map.getEndNodes().get(0).getValue())) {
                System.out.println("We found goal");
                System.out.println(current.getPath());
                System.out.println(visited.size());
                return null;
            }
            for (Direction direction : Direction.values()) {
                Location location = new Location(current.getValue().getX() + direction.getX(), current.getValue().getY() + direction.getY());
                if (map.isInMap(location)) {
                    if (!map.isInWall(location)) {
                        Node<Location> node = new Node<>(location, current, direction);
                        if (!contains(node)) {
                            collection.add(node);
                            visited.add(node);
                        }
                    }
                }
            }
        }
        System.out.println("Couldn't find solution");
        return null;

    }

    private boolean contains(Node<Location> node) {
        return visited.stream().anyMatch(visitedNode -> visitedNode.getValue().equals(node.getValue()));
    }
}
