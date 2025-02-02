package com.demo.shape;

import com.demo.DrawCanvas;
import com.demo.LineMode;
import com.demo.models.Point2D;
import com.demo.models.Vector2D;

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
        if (startPoint != null && endPoint != null) {
            // Đổ listDraw cho listClear
            swapList();

            // Xác định những điểm thuộc đường thẳng MỚI cần vẽ
            // Những điểm cần vẽ sẽ được thêm vào listDraw
            drawLine();

            // Xóa những điểm thuộc listClear
            clearOldPoints();

            // Vẽ những điểm thuộc listDraw
            drawNewPoints();

            showPointsCoordinate();
        }
    }

    /*
     * Hiển thị tọa độ 2 điểm start và end
     */
    @Override
    public void showPointsCoordinate() {
//        canvas.drawPointsCoordinate(startPoint);
//        canvas.drawPointsCoordinate(endPoint);
//        Graphics g = canvas.getGraphics();
//        g.setColor(Color.BLACK);
//
//        if (startPoint != null)
//            g.drawString(String.format("(%d, %d)", startPoint.getX(), startPoint.getY()), startPoint.getComputerX() * 5 + 5, startPoint.getComputerY() * 5 - 5);
//        if (endPoint != null)
//            g.drawString(String.format("(%d, %d)", endPoint.getX(), endPoint.getY()), endPoint.getComputerX() * 5 + 5, endPoint.getComputerY() * 5 - 5);

//        g.dispose();
    }

    public void drawLine() {
        lineBresenham(startPoint.getX(), startPoint.getY(), endPoint.getX(), endPoint.getY());

        if (DrawCanvas.lineMode == LineMode.ARROW) {
            Point2D pA = startPoint.translate(-startPoint.getX(), -startPoint.getY());
            Point2D pB = endPoint.translate(-startPoint.getX(), -startPoint.getY());

            Vector2D vAB = new Vector2D(pA, pB);
            double angle = vAB.angleRadian(Vector2D.oX);

            Point2D pC = pB.rotate(pB.getY() < pA.getY() ? angle : -angle);

            Point2D pU = new Point2D(pC.getX() - 4, pC.getY() + 4);
            Point2D pV = new Point2D(pC.getX() - 4, pC.getY() - 4);

            pU = pU.rotate(pB.getY() < pA.getY() ? -angle : angle);
            pV = pV.rotate(pB.getY() < pA.getY() ? -angle : angle);

            pU = pU.translate(startPoint.getX(), startPoint.getY());
            pV = pV.translate(startPoint.getX(), startPoint.getY());

            lineBresenham(endPoint.getX(), endPoint.getY(), pU.getX(), pU.getY());
            lineBresenham(endPoint.getX(), endPoint.getY(), pV.getX(), pV.getY());
        }
    }

    private boolean isShowPoint(int index) {
        switch (DrawCanvas.lineMode) {  // DrawCanvas.lineMode là chế độ vẽ
            case DEFAULT -> {           // Nét liền
                return true;
            }
            case DOT -> {               // Nét chấm
                return (index % 2) == 0;
            }
            case DASH -> {              // Nét gạch
                return (index % 4) < 3;
            }
            case DASH_DOT -> {          // Nét gạch chấm
                return (index % 6) < 3 || (index % 6) == 4;
            }
            case DASH_DOT_DOT -> {      // Nét gạch 2 chấm
                return (index % 12 < 4) || (index % 12) == 6 || (index % 12) == 9;
            }
        }
        return true;
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
        // listDraw là List chứa danh sách những điểm sẽ vẽ lên màn hình
        listDraw.add(pt);

        // biến này đếm vị trí của điểm vẽ để xét xem nó có được hiển thị cho phù hợp với nét vẽ hay không
        int cnt = 1;

        if (Dx >= Dy) {
            // Trường hợp này ta chạy vòng lặp while theo biến x

            p = 2 * Dy - Dx;

            while (x != x2) {
                if (p < 0) p += 2 * Dy;
                else {
                    p += 2 * (Dy - Dx);
                    y += yUnit;
                }
                x += xUnit;

                // Kiểm tra xem điểm đó được hiển thị hay không, nếu được thì thêm vào list
                if (isShowPoint(cnt)) {
                    pt = new Point2D(x, y, DrawCanvas.currentColor);
                    listDraw.add(pt);
                }
                // Tăng giá trị biến đếm lên
                cnt++;

            }
        } else {
            // Trường hợp này ta chạy vòng lặp while theo biến y

            p = 2 * Dx - Dy;

            while (y != y2) {
                if (p < 0) p += 2 * Dx;
                else {
                    p += 2 * (Dx - Dy);
                    x += xUnit;
                }
                y += yUnit;

                if (isShowPoint(cnt)) {
                    pt = new Point2D(x, y, DrawCanvas.currentColor);
                    listDraw.add(pt);
                }
                cnt++;

            }
        }

    }


}
