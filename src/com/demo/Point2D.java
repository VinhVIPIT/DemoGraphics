package com.demo;

/**
 * Create by VinhIT
 * On 27/03/2021
 */

public class Point2D {

    private int x, y;

    private int color;


    public Point2D(int x, int y) {
        this.x = x;
        this.y = y;
        this.color = 0xffffff;
    }

    public Point2D(int x, int y, int color) {
        this.x = x;
        this.y = y;
        this.color = color;
    }

    public int getX() {
        return x;
    }


    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getComputerX() {
        return x + DrawCanvas.rowSize / 2;
    }

    public int getComputerY() {
        return DrawCanvas.colSize / 2 - y;
    }

    public Point2D getComputerCoordinate() {
        return new Point2D(x + DrawCanvas.rowSize / 2, DrawCanvas.colSize / 2 - y);
    }

    public static Point2D fromComputerCoordinate(int xm, int ym) {
        return new Point2D(-DrawCanvas.rowSize / 2 + xm, DrawCanvas.colSize / 2 - ym);
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }


    public boolean isSamePoint(Point2D point) {
        return x == point.x && y == point.y && color == point.color;
    }
}
