package com.demo;

import java.util.ArrayList;
import java.util.List;

/**
 * Create by VinhIT
 * On 28/03/2021
 */

public abstract class Geometry {

    protected DrawCanvas canvas;
    protected List<Point2D> listDraw = new ArrayList<>(), listClear = new ArrayList<>();
    protected Point2D startPoint, endPoint;

    public Geometry(DrawCanvas canvas, Point2D startPoint, Point2D endPoint) {
        this.canvas = canvas;
        this.startPoint = startPoint;
        this.endPoint = endPoint;
    }

    public Geometry(DrawCanvas canvas) {
        this(canvas, null, null);
    }

    public abstract void setupDraw();

    protected final void clearOldPoints(){
        List<Point2D> list = new ArrayList<>();

        for(Point2D pc:listClear){
            boolean del = true;
            for(Point2D pd: listDraw){
                if(pc.isSamePoint(pd)) {
                    del = false; break;
                }
            }
            if(del) list.add(pc);
        }
        canvas.clearDraw(list);
    }

    protected final void drawNewPoints(){
        canvas.applyDraw(listDraw);
    }

    protected void swapList(){
        listClear.clear();
        listClear.addAll(listDraw);
        listDraw.clear();
    }

    public void setStartPoint(Point2D startPoint) {
        this.startPoint = startPoint;
    }

    public void setEndPoint(Point2D endPoint) {
        this.endPoint = endPoint;
    }

    public Point2D getStartPoint() {
        return startPoint;
    }

    public Point2D getEndPoint() {
        return endPoint;
    }
}
