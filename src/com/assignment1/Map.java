package com.assignment1;

import com.assignment1.algorithms.SearchResult;
import com.assignment1.algorithms.SearchType;
import com.assignment1.datastructures.AStarNode;
import com.assignment1.datastructures.BestFirstNode;
import com.assignment1.datastructures.Node;
import com.assignment1.ui.Launcher;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.util.*;
import java.util.List;

public class Map extends JPanel {

    private int mazeWidth = 0;
    private int mazeHeight = 0;
    private List<Node<Wall>> walls;
    private Node<Location> startNode;
    private List<Node<Location>> endNodes;
    public List<Node<Location>> visited;
    private List<Double> nextDepthBound;
    private List<Node<Location>> path;
    private Node<Location> current = null;
    // Used to quickly determine if node with same state is in the open set and to retrieve that states best F value
    private HashMap<Node<Location>, Double> tempFrontier;
    private double depthBound = 0;
    private boolean solution = false;
    private boolean ui;
    private Collection collection;


    public Map(int mazeWidth, int mazeHeight, List<Node<Wall>> walls, Node<Location> startNode, List<Node<Location>> endNodes, boolean ui) {
        this.mazeWidth = mazeWidth;
        this.mazeHeight = mazeHeight;
        this.walls = walls;
        this.startNode = startNode;
        this.endNodes = endNodes;
        this.visited = new ArrayList<>();
        this.nextDepthBound = new ArrayList<>();
        this.tempFrontier = new HashMap<>();
        this.path = new ArrayList<>();
        this.ui = ui;

        setBorder(new LineBorder(Color.gray));
    }


    public int getMazeWidth() {
        return mazeWidth;
    }

    public int getMazeHeight() {
        return mazeHeight;
    }

    public List<Node<Wall>> getWalls() {
        return walls;
    }

    public Node<Location> getStartNode() {
        return startNode;
    }

    public List<Node<Location>> getEndNodes() {
        return endNodes;
    }

    public boolean isInMap(Location location) {
        return location.getX() >= 0 && location.getX() <=  getMazeWidth() -1
                &&  location.getY() >= 0 && location.getY() <= getMazeHeight() -1;
    }
    public boolean isInWall(Location location){
        for(Node<Wall> wall: getWalls()){
            if(wall.getValue().isInWall(location)){
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
        return Math.abs(location.getX()  - endNodes.get(0).getValue().getX()) + Math.abs(location.getY() - endNodes.get(0).getValue().getY());
    }

    private Collection determineDataStructureForType(SearchType type) {
        switch(type){
            case DFS:
            case IDDFS:
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

    public void reset(){
        if(collection != null && !collection.isEmpty())
            this.collection.clear();
        if(!visited.isEmpty())
            this.visited.clear();
        if(current != null)
            this.current = null;
        if(!path.isEmpty())
            this.path.clear();
        repaint();
    }

    public SearchResult search(SearchType type) throws InterruptedException {
        this.collection = determineDataStructureForType(type);
        SearchResult result = null;
        if(type == SearchType.IDDFS) {
            for(int depthLevel = 0; depthLevel <= Integer.MAX_VALUE; depthLevel++){
                result = search(collection, type, depthLevel);
                // This check might break if we don't find the solution
                if(result.isSolutionFound()) break;
                visited.clear();
            }
            return result;
        }
        return search(collection, type, 0);
    }

    private SearchResult search(Collection collection, SearchType type, int depthLevel) throws InterruptedException {

        Node<Location> startNode =  addNodeToFrontier(getStartNode(), collection, type);
        if(type == SearchType.IDA_STAR && startNode instanceof AStarNode){
            depthBound = ((AStarNode<Location>) startNode).getF();
        }
        while (!collection.isEmpty()) {
            Object element = collection instanceof Stack ? ((Stack) collection).pop() : ((Queue) collection).poll();
            this.current = (Node<Location>) element;
            // Check for goal
            if (current.getValue().equals(this.getEndNodes().get(0).getValue())) {
                StringBuilder sb = new StringBuilder();
                // Re loop for aesthetics and for directions
                for(Node<Location> location : current.getPath()){
                    if(ui) {
                        Thread.sleep(400);
                        this.path.add(location);
                        repaint();
                    } else {
                        sb.append(location.getDirection().name()).append("; ");
                    }
                }
                if(sb.length() > 0){
                    sb.deleteCharAt(sb.length() - 1);
                }
                return new SearchResult(visited.size(), sb.toString(), depthLevel, true);
            }

            // Add children
            if(type == SearchType.IDDFS && current.getDepth() >= depthLevel) {
                continue;
            }
            addChildrenToFrontier(collection, current, type);
            // IDA Check
            if(type == SearchType.IDA_STAR && collection.size() == 1){
               Collections.sort(nextDepthBound);
               depthBound = nextDepthBound.get(0);
               addNodeToFrontier(getStartNode(), collection, SearchType.IDA_STAR);
               nextDepthBound.clear();
            }
            if(ui) {
                repaint();
                Thread.sleep(500);
            }
        }
        return new SearchResult(0, "", depthLevel, false);

    }

    private void addChildrenToFrontier(Collection collection, Node<Location>  current, SearchType type) {
        for (Direction direction : Direction.values()) {
            Location location = new Location(current.getValue().getX() + direction.getX(), current.getValue().getY() + direction.getY());
            if (this.isInMap(location)) {
                if (!this.isInWall(location)) {
                    Node<Location> node = new Node<>(location, current, direction);
                    Node<Location> newNode = addNodeToFrontier(node, collection, type);
                    if(newNode == null) continue;
                    if (type == SearchType.IDA_STAR && newNode instanceof AStarNode) {
                        if (((AStarNode) newNode).getF() >= depthBound) {
                            // Don't worry about duplicates
                            nextDepthBound.add(((AStarNode) newNode).getF());
                        }
                    }
                }
            }
        }

    }

    private Node<Location> addNodeToFrontier(Node<Location> node, Collection collection, SearchType type){
        if (type == SearchType.A_STAR || type == SearchType.IDA_STAR) {
            AStarNode<Location> aStarNode = new AStarNode<>(node);
            aStarNode.setH(this.calculateHeuristic(aStarNode));
            // If parent is null, we can assume start node, otherwise increment.
            double g = aStarNode.getParent() == null ? 0 : ((AStarNode)aStarNode.getParent()).getG() + 1;
            aStarNode.setG(g);

            // Check if child node is in queue and if the version in queue has a smaller F value
            AStarNode previousVersion = getValueFromCollection(collection, node);
            if(previousVersion != null && previousVersion.getF() < aStarNode.getF()){
                return null;
            }
            if(type == SearchType.A_STAR) {
                checkForVisited(collection, aStarNode);
                tempFrontier.put(aStarNode, aStarNode.getF());
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
            int depth = node.getParent() == null ? 0 : node.getParent().getDepth() + 1;
            node.setDepth(depth);
            checkForVisited(collection,node);
            return node;
        }
    }
    private void checkForVisited(Collection collection, Node<Location> node) {
        if (!contains(node)) {
            collection.add(node);
            visited.add(node);
        }
    }

    // Utility functions/optimisations
    private boolean contains(Node<Location> node) {
        return this.visited.stream().anyMatch(visitedNode -> visitedNode.getValue().equals(node.getValue()));
    }

    // contain APIs on the collection check for explicit matches but we are only interested in the location value.
    private AStarNode<Location> getValueFromCollection(Collection collection, Node<Location> other){
        return ((AStarNode) collection.stream().filter(node -> ((AStarNode) node).getValue().equals(other.getValue())).findFirst().orElse(null));
    }

    public void launch() throws InterruptedException {
        this.ui = true;
        new Launcher(this).launch();

    }
    public SearchResult launch(SearchType type) throws InterruptedException {
        this.ui = false;
        return search(type);

    }

    @Override
    protected void paintComponent(Graphics g1) {
//        System.out.println("Painting");
        super.paintComponent(g1);

        Graphics2D g = (Graphics2D) g1;
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        // Map generation
        for (int x = 0; x < this.getMazeWidth(); x++) {
            for(int y = 0; y < this.getMazeHeight(); y++) {
                g.setColor(new Color(220, 220, 220));
                g.drawRect(x * 40, y * 40, 40, 40);
            }
        }
        // Paint Start node
        g.setColor(new Color(78, 189, 30));
        g.fillRect(this.getStartNode().getValue().getX() * 40, this.getStartNode().getValue().getY() * 40, 40, 40);

        // Paint walls:
        // NOTE: Not super efficient but done for aesthetics
        for(Node<Wall> wall : this.getWalls()) {
            for(int w = 0; w < wall.getValue().getWidth(); w++){
                for(int h = 0; h < wall.getValue().getHeight(); h++) {
                    g.setColor(Color.GRAY);
                    g.fillRect((wall.getValue().getX() + w) * 40, (wall.getValue().getY() + h) * 40, 40,  40);
                    g.setColor(new Color(220, 220, 220));
                    g.drawRect((wall.getValue().getX() + w) * 40, (wall.getValue().getY() + h) * 40, 40,  40);
                }
            }
        }

        // Paint Goal states
        for(Node<Location> goal : this.getEndNodes()){
            g.setColor(new Color(189, 57, 30));
            g.fillRect(goal.getValue().getX() * 40, goal.getValue().getY() * 40, 40,  40);
        }
        // Paint searched nodes
        for(Node<Location> node : this.visited){
            g.setColor(Color.CYAN);
            g.fillRect(node.getValue().getX() * 40, node.getValue().getY() * 40, 40,  40);
            g.setColor(new Color(220, 220, 220));
            g.drawRect(node.getValue().getX() * 40, node.getValue().getY() * 40, 40,  40);
        }

        //Paint current node
        if(this.current != null){
            g.setColor(Color.ORANGE);
            g.fillRect(current.getValue().getX() * 40, current.getValue().getY() * 40, 40,  40);
            g.setColor(new Color(220, 220, 220));
            g.drawRect(current.getValue().getX() * 40, current.getValue().getY() * 40, 40,  40);
        }
        //Paint path
        if(!this.path.isEmpty()){
            for(Node<Location> node : this.path) {
                g.setColor(Color.MAGENTA);
                g.fillRect(node.getValue().getX() * 40, node.getValue().getY() * 40, 40, 40);
                g.setColor(new Color(220, 220, 220));
                g.drawRect(node.getValue().getX() * 40, node.getValue().getY() * 40, 40, 40);
            }
        }

    }

    @Override
    public String toString() {
        return "Map{" +
                "width=" + mazeWidth +
                ", height=" + mazeHeight +
                ", number of walls=" + walls.size() +
                ", walls=" + walls +
                ", startNode=" + startNode +
                ", endNodes=" + endNodes +
                '}';
    }
}


