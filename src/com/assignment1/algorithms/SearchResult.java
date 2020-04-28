package com.assignment1.algorithms;

public class SearchResult {
    private int vistedNodes;
    private String path;
    private int depth;
    private boolean solutionFound;

    public SearchResult(int vistedNodes, String path, int depth, boolean solutionFound) {
        this.vistedNodes = vistedNodes;
        this.path = path;
        this.depth = depth;
        this.solutionFound = solutionFound;
    }

    public int getVistedNodes() {
        return vistedNodes;
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
}
