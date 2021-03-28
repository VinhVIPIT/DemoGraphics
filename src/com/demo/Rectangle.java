package com.demo;

/**
 * Create by VinhIT
 * On 28/03/2021
 */

public class Rectangle extends Geometry {

    public Rectangle(DrawCanvas canvas, Point2D startPoint2D, Point2D endPoint2D) {
        super(canvas, startPoint2D, endPoint2D);
    }

    public Rectangle(DrawCanvas canvas) {
        super(canvas);
    }

    @Override
    public void setupDraw() {
        if (startPoint != null && endPoint != null) {
            swapList();

            drawRectangle(startPoint.getX(), startPoint.getY(), endPoint.getX(), endPoint.getY());

            clearOldPoints();
            drawNewPoints();

        }
    }

    private void drawRectangle(int x1, int y1, int x2, int y2) {
        int xUnit = 1, yUnit = 1;
        if (x2 < x1) xUnit = -xUnit;
        if (y2 < y1) yUnit = -yUnit;

        int x = x1, y = y1;
        Point2D pt;

        pt = new Point2D(x2, y2, 0xff0000);
        listDraw.add(pt);

        while (x != x2) {
            pt = new Point2D(x, y1, 0xff0000);
            listDraw.add(pt);
            pt = new Point2D(x, y2, 0xff0000);
            listDraw.add(pt);
            x += xUnit;
        }
        while (y != y2) {
            pt = new Point2D(x1, y, 0xff0000);
            listDraw.add(pt);
            pt = new Point2D(x2, y, 0xff0000);
            listDraw.add(pt);
            y += yUnit;
        }
    }

    @Override
    public void setEndPoint(Point2D endPoint) {
        this.endPoint = endPoint;
        setupDraw();
    }

}
