package com.assignment1;

import com.assignment1.algorithms.SearchResult;
import com.assignment1.algorithms.SearchType;
import com.assignment1.datastructures.AStarNode;
import com.assignment1.datastructures.BestFirstNode;
import com.assignment1.datastructures.Node;

import java.util.*;

public class Map {
    private int width = 0;
    private int height = 0;
    private List<Node<Location>> walls;
    private Node<Location> startNode;
    private List<Node<Location>> endNodes;
    private List<Node<Location>> visited;

    public Map(int width, int height, List<Node<Location>> walls, Node<Location> startNode, List<Node<Location>> endNodes) {
        this.width = width;
        this.height = height;
        this.walls = walls;
        this.startNode = startNode;
        this.endNodes = endNodes;
        this.visited = new ArrayList<>();
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public List<Node<Location>> getWalls() {
        return walls;
    }

    public Node<Location> getStartNode() {
        return startNode;
    }

    public List<Node<Location>> getEndNodes() {
        return endNodes;
    }

    public boolean isInMap(Location location) {
        return location.getX() >= 0 && location.getX() <=  getWidth() -1
                &&  location.getY() >= 0 && location.getY() <= getHeight() -1;
    }
    public boolean isInWall(Location location){
        for(Node<Location> wall: walls){
            if(((Wall)wall.getValue()).isInWall(location)){
                return true;
            }
        }
        return false;
    }

    /**
     * Manhattan Distance Heuristic
     * @param node
     * @return
     */
    public int calculateHeuristic(Node<Location> node) {
        Location location = node.getValue();
        return Math.abs(location.getX() - endNodes.get(0).getValue().getX()) + Math.abs(location.getY() - endNodes.get(0).getValue().getY());
    }

    public SearchResult search(SearchType type){
        Collection collection = determineDataStructureForType(type);
        return search(collection,  type);
    }

    private Collection determineDataStructureForType(SearchType type) {
        switch(type){
            case DFS:
                return new  Stack();
            case BFS:
                return new ArrayDeque();
            case  A_STAR:
            case GBFS:
                return new PriorityQueue();
            default:
                throw new RuntimeException("Unknown search algorithm");
        }
    }

    private SearchResult search(Collection collection, SearchType type) {
        Node<Location> startNode = getStartNode();
        if (type == SearchType.A_STAR) {
            AStarNode<Location> node = new AStarNode<>(startNode);
            node.setH(this.calculateHeuristic(startNode));
            startNode = node;
        } else if (type == SearchType.GBFS){
            BestFirstNode<Location> node = new BestFirstNode<>(startNode);
            node.setH(this.calculateHeuristic(startNode));
            startNode = node;
        }
        collection.add(startNode);
        visited.add(startNode);
        while (!collection.isEmpty()) {
            Object element = collection instanceof Stack ? ((Stack) collection).pop() : ((Queue) collection).poll();
            Node<Location> current = (Node<Location>) element;
            if (current.getValue().equals(this.getEndNodes().get(0).getValue())) {
                System.out.println("We found goal");
                System.out.println(current.getPath());
                System.out.println(visited.size());
                return null;
            }
            for (Direction direction : Direction.values()) {
                Location location = new Location(current.getValue().getX() + direction.getX(), current.getValue().getY() + direction.getY());
                if (this.isInMap(location)) {
                    if (!this.isInWall(location)) {
                            Node<Location> node = new Node<>(location, current, direction);
                            if (type == SearchType.A_STAR) {
                                ((AStarNode<Location>) node).setH(this.calculateHeuristic(startNode));
                            } else if (type == SearchType.GBFS){
                                BestFirstNode<Location> gbfsNode = new BestFirstNode<>(startNode);
                                gbfsNode.setH(this.calculateHeuristic(startNode));
                                node =  gbfsNode;
                            }
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

    @Override
    public String toString() {
        return "Map{" +
                "width=" + width +
                ", height=" + height +
                ", number of walls=" + walls.size() +
                ", walls=" + walls +
                ", startNode=" + startNode +
                ", endNodes=" + endNodes +
                '}';
    }
}


