package com.demo;

import java.util.ArrayList;
import java.util.List;

/**
 * Create by VinhIT
 * On 25/03/2021
 */

public class Line {

    private DrawCanvas canvas;
    private List<Point2D> listPoint2DDraw = new ArrayList<>();
    private Point2D startPoint2D, endPoint2D;


    public Line(Point2D startPoint2D, Point2D endPoint2D, DrawCanvas canvas) {
        this.startPoint2D = startPoint2D;
        this.endPoint2D = endPoint2D;
        this.canvas = canvas;
    }

    public void draw() {
        if (startPoint2D != null && endPoint2D != null) {
            lineBresenham(startPoint2D.getX(), startPoint2D.getY(), endPoint2D.getX(), endPoint2D.getY());
            canvas.applyDraw(listPoint2DDraw);
        }
    }

    public void lineBresenham(int x1, int y1, int x2, int y2) {
        int x, y, Dx, Dy, p;
        Dx = Math.abs(x2 - x1);
        Dy = Math.abs(y2 - y1);
        x = x1;
        y = y1;

        int xUnit = 1, yUnit = 1;

        if (x2 - x1 < 0)
            xUnit = -xUnit;
        if (y2 - y1 < 0)
            yUnit = -yUnit;

        Point2D pt = new Point2D(x, y, 0xff0000);
        listPoint2DDraw.add(pt);

        if (Dx >= Dy) {
            p = 2 * Dy - Dx;

            while (x != x2) {
                if (p < 0) p += 2 * Dy;
                else {
                    p += 2 * (Dy - Dx);
                    y += yUnit;
                }
                x += xUnit;

                pt = new Point2D(x, y, 0xff0000);
                listPoint2DDraw.add(pt);
            }
        } else {
            p = 2 * Dx - Dy;

            while (y != y2) {
                if (p < 0) p += 2 * Dx;
                else {
                    p += 2 * (Dx - Dy);
                    x += xUnit;
                }
                y += yUnit;

                pt = new Point2D(x, y, 0xff0000);
                listPoint2DDraw.add(pt);
            }
        }

    }

    public void setStartPoint(Point2D startPoint2D) {
        this.startPoint2D = startPoint2D;
    }

    public void setEndPoint(Point2D endPoint2D) {
        if(endPoint2D != null){
            canvas.clearDraw(listPoint2DDraw);
            listPoint2DDraw.clear();
            this.endPoint2D = endPoint2D;
            draw();
        }
    }

    public Point2D getStartPoint() {
        return startPoint2D;
    }

    public Point2D getEndPoint() {
        return endPoint2D;
    }

}
