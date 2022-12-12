package org.haok;

import javafx.geometry.Point2D;

public enum Dir {
    UP(new Point2D(0, -1)),
    DOWN(new Point2D(0, 1)),
    LEFT(new Point2D(-1, 0)),
    RIGHT(new Point2D(1, 0));
    private final Point2D vector;

    Dir(Point2D vector) {
        this.vector = vector;
    }

    public Point2D getVector() {
        return vector;
    }
}
