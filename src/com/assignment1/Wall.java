package com.assignment1;

import com.assignment1.Location;

public class Wall extends Location {
    private int width, height;
    public Wall(int x, int y, int width, int height) {
        super(x, y);
        this.width = width;
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public boolean isInWall(Location location) {
        return (location.getX() >= getX() && location.getX() <= (getX() + getWidth()) - 1)
                && location.getY() >= getY() && location.getY() <= (getY() + getHeight()) -1;
    }

    @Override
    public String toString() {
        return "Wall{" +
                "x=" + getX() +
                ", y=" + getY() +
                "width=" + width +
                ", height=" + height +
                '}';
    }
}
