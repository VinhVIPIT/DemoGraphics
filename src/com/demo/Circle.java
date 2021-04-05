package com.demo;

/**
 * Create by VinhIT
 * On 29/03/2021
 */

public class Circle extends Geometry {

    public Circle(DrawCanvas canvas, Point2D startPoint, Point2D endPoint) {
        super(canvas, startPoint, endPoint);
    }

    public Circle(DrawCanvas canvas) {
        super(canvas);
    }

    @Override
    public void setupDraw() {
        if (startPoint != null && endPoint != null) {
            int r = (int) Math.sqrt((startPoint.getX() - endPoint.getX()) * (startPoint.getX() - endPoint.getX()) +
                    (startPoint.getY() - endPoint.getY()) * (startPoint.getY() - endPoint.getY()));

            swapList();

            circleMidPoint(r);

            clearOldPoints();
            drawNewPoints();
        }
    }

    @Override
    public void showPointsCoordinate() {

    }

    void put8Pixel(int x, int y, int color) {
        int xc = startPoint.getX();
        int yc = startPoint.getY();

        listDraw.add(new Point2D(x + xc, y + yc, color));
        listDraw.add(new Point2D(y + xc, x + yc, color));
        listDraw.add(new Point2D(y + xc, -x + yc, color));
        listDraw.add(new Point2D(x + xc, -y + yc, color));
        listDraw.add(new Point2D(-x + xc, -y + yc, color));
        listDraw.add(new Point2D(-y + xc, -x + yc, color));
        listDraw.add(new Point2D(-y + xc, x + yc, color));
        listDraw.add(new Point2D(-x + xc, y + yc, color));
    }

    void circleMidPoint(int R) {
        int x, y;

        x = 0;
        y = R;

        put8Pixel(x, y, DrawCanvas.currentColor);
        double p = 1.25 - R; // 5/4-R
        while (x < y) {
            if (p < 0) p += 2 * x + 3;
            else {
                p += 2 * (x - y) + 5;
                y--;
            }
            x++;
            put8Pixel(x, y, DrawCanvas.currentColor);
        }

    }
}
