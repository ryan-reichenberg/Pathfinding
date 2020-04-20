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
    private List<Integer> nextDepthBound;
    private int maxDepth = Integer.MAX_VALUE;
    private int depthBound = 0;
    private int depth = 0;


    public Map(int width, int height, List<Node<Location>> walls, Node<Location> startNode, List<Node<Location>> endNodes) {
        this.width = width;
        this.height = height;
        this.walls = walls;
        this.startNode = startNode;
        this.endNodes = endNodes;
        this.visited = new ArrayList<>();
        this.nextDepthBound = new ArrayList<>();
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
        for(Node<Location> wall: getWalls()){
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
        return Math.abs(location.getX()  - endNodes.get(0).getValue().getX()) + Math.abs(location.getX() - endNodes.get(0).getValue().getY());
    }
    public int calculateHeuristic1(Node<Location> node) {
        Location location = node.getValue();
        return Math.abs(this.getEndNodes().get(0).getValue().getX() - location.getX()) + Math.abs(this.getEndNodes().get(0).getValue().getY() - location.getX());
    }

    private Collection determineDataStructureForType(SearchType type) {
        switch(type){
            case DFS:
//            case IDDFS:
                return new  Stack();
            case BFS:
                return new ArrayDeque();
            case  A_STAR:
            case IDA_STAR:
            case GBFS:
                return new PriorityQueue();
            default:
                throw new RuntimeException("Unknown search algorithm");
        }
    }

    public SearchResult search(SearchType type){
        Collection collection = determineDataStructureForType(type);
        return search(collection,  type);
    }

    private SearchResult search(Collection collection, SearchType type) {

        Node<Location> startNode =  addNodeToFrontier(getStartNode(), collection, type);
        if(type == SearchType.IDA_STAR && startNode instanceof AStarNode){
            depthBound = ((AStarNode<Location>) startNode).getF();
        }
        while (!collection.isEmpty()) {
            //System.out.println(collection);
            Object element = collection instanceof Stack ? ((Stack) collection).pop() : ((Queue) collection).poll();
            Node<Location> current = (Node<Location>) element;
            System.out.println("Current: "+current.getValue());
            if(collection instanceof Queue){
                System.out.println("Queue");
            }
            // Check for goal
            if (current.getValue().equals(this.getEndNodes().get(0).getValue())) {
//                Collections.sort(collection);
                System.out.println("We found goal");
                System.out.println(current);
                System.out.println(current.getPath());
                System.out.println(visited.size());
                visited.forEach(visitedNode -> System.out.println(visitedNode));
//                if(type == SearchType.IDDFS)
//                    System.out.println("Found at depth: "+depth);
                return null;
            }
//            System.out.println("H: "+((AStarNode)current).getH());
//            System.out.println("G: "+((AStarNode)current).getG());
//            System.out.println("F: "+((AStarNode)current).getF());
//            visited.forEach(visitedNode -> System.out.println(visitedNode));
            // Add children
            addChildrenToFrontier(collection, current, type);
            collection.forEach(queue -> System.out.println("Head: "+((Node)queue).getValue()));
            //visited.forEach(visitedNode -> System.out.println("visited: "+visitedNode));
            // IDA Check
            if(type == SearchType.IDA_STAR && collection.size() == 1){
               Collections.sort(nextDepthBound);
               depthBound = nextDepthBound.get(0);
               addNodeToFrontier(getStartNode(), collection, SearchType.IDA_STAR);
               nextDepthBound.clear();
            }
        }
        System.out.println("Couldn't find solution");
        return null;

    }

    private void addChildrenToFrontier(Collection collection, Node<Location>  current, SearchType type) {
        for (Direction direction : Direction.values()) {
            Location location = new Location(current.getValue().getX() + direction.getX(), current.getValue().getY() + direction.getY());
            if (this.isInMap(location)) {
                if (!this.isInWall(location)) {
                    Node<Location> node = new Node<>(location, current, direction);
                    Node<Location> newNode = addNodeToFrontier(node, collection, type);
                    if(type == SearchType.IDA_STAR && newNode instanceof AStarNode) {
                        if (((AStarNode) newNode).getF() >= depthBound) {
                            // Don't worry about duplicates
                            nextDepthBound.add(((AStarNode) newNode).getF());
                        }
                    }
                }else {
                    System.out.println("In wall: "+ location);
                }
            }else {
                System.out.println("Not in map: "+ location);
            }
        }

    }

    private Node<Location> addNodeToFrontier(Node<Location> node, Collection collection, SearchType type){
        if (type == SearchType.A_STAR || type == SearchType.IDA_STAR) {
            AStarNode<Location> aStarNode = new AStarNode<>(node);
            aStarNode.setH(this.calculateHeuristic1(aStarNode));
            // If parent is null, we can assume start node,otherwise increment.
            int g = aStarNode.getParent() == null ? 0 : ((AStarNode)aStarNode.getParent()).getG() + 1;
            aStarNode.setG(g);
            if(type == SearchType.A_STAR) {
                checkForVisited(collection, aStarNode);
            } else {
                collection.add(aStarNode);
            }
            return aStarNode;
        } else if (type == SearchType.GBFS) {
            BestFirstNode<Location> gbfsNode = new BestFirstNode<>(node);
            gbfsNode.setH(this.calculateHeuristic(gbfsNode));
            checkForVisited(collection, gbfsNode);
            return gbfsNode;
        }else {
            checkForVisited(collection,node);
            return node;
        }
    }
    private void checkForVisited(Collection collection, Node<Location> node) {
        if (!contains(node)) {
            collection.add(node);
            visited.add(node);
        } else {
            System.out.println("Already visited: "+ node.getValue());
        }
    }

    private boolean contains(Node<Location> node) {
        return this.visited.stream().anyMatch(visitedNode -> visitedNode.getValue().equals(node.getValue()));
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


