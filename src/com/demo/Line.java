package com.demo;

/**
 * Create by VinhIT
 * On 25/03/2021
 */

public class Line extends Geometry {


    public Line(DrawCanvas canvas, Point2D startPoint2D, Point2D endPoint2D) {
        super(canvas, startPoint2D, endPoint2D);
    }

    public Line(DrawCanvas canvas) {
        super(canvas);
    }


    @Override
    public void setupDraw() {
        if(startPoint != null && endPoint != null) {
            // Đổ listDraw cho listClear
            swapList();

            // Xác định những điểm thuộc đường thẳng MỚI cần vẽ
            // Những điểm cần vẽ sẽ được thêm vào listDraw
            drawLine();

            // Xóa những điểm thuộc listClear
            clearOldPoints();

            // Vẽ những điểm thuộc listDraw
            drawNewPoints();
        }
    }

    public void drawLine(){
        lineBresenham(startPoint.getX(), startPoint.getY(), endPoint.getX(), endPoint.getY());
    }

    private void lineBresenham(int x1, int y1, int x2, int y2) {
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

        Point2D pt = new Point2D(x, y, DrawCanvas.currentColor);
        listDraw.add(pt);

        int cnt = 1;

        if (Dx >= Dy) {
            p = 2 * Dy - Dx;

            while (x != x2) {
                if (p < 0) p += 2 * Dy;
                else {
                    p += 2 * (Dy - Dx);
                    y += yUnit;
                }
                x += xUnit;

//                if(cnt%4 < 3){
                    pt = new Point2D(x, y, DrawCanvas.currentColor);
                    listDraw.add(pt);
//                }
                cnt++;

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

//                if(cnt%4 < 3){
                    pt = new Point2D(x, y, DrawCanvas.currentColor);
                    listDraw.add(pt);
//                }
                cnt++;

            }
        }

    }

}
