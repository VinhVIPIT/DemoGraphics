package com.demo;

/**
 * Create by VinhIT
 * On 29/03/2021
 */

public class Pen extends Geometry{

    private Line line;

    public Pen(DrawCanvas canvas) {
        super(canvas);
        line = new Line(canvas);
    }

    @Override
    public void setupDraw() {
        // TODO: Thuật toán vẽ PEN củ loèn, rảnh fix sau

        if(listDraw.size() >= 1){
            if(endPoint != null && !endPoint.isSamePoint(listDraw.get(listDraw.size()-1))){
                listDraw.add(endPoint);

                line.setStartPoint(listDraw.get(listDraw.size()-2));
                line.setEndPoint(listDraw.get(listDraw.size()-1));
                line.drawLine();
                line.drawNewPoints();
            }
        }
    }

    @Override
    public void setStartPoint(Point2D startPoint) {
        super.setStartPoint(startPoint);
        if(startPoint != null){
            listDraw.add(startPoint);
            drawNewPoints();
        }

    }

    @Override
    public void setEndPoint(Point2D endPoint) {
        this.endPoint = endPoint;
    }

    @Override
    protected void clearAll() {
        super.clearAll();
        line.clearAll();
    }
}
