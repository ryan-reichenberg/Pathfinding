package com.assignment1.algorithms;

public class SearchResult {
    private int visitedNodes;
    private String path;
    private int depth;
    private boolean solutionFound;
    private int iterations;
    private int frontierSize;

    public SearchResult(int vistedNodes, String path, int depth, boolean solutionFound, int iterations, int frontierSize) {
        this.visitedNodes = vistedNodes;
        this.path = path;
        this.depth = depth;
        this.solutionFound = solutionFound;
        this.iterations = iterations;
        this.frontierSize = frontierSize;
    }

    public int getVistedNodes() {
        return visitedNodes;
    }

    public String getPath() {
        return path;
    }

    public int getDepth() {
        return depth;
    }

    public boolean isSolutionFound() {
        return solutionFound;
    }

    public int getIterations() {
        return iterations;
    }

    public int getFrontierSize() {
        return frontierSize;
    }
}
