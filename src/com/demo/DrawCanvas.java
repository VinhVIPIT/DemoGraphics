package com.demo;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.List;

/**
 * Create by VinhIT
 * On 27/03/2021
 */

public class DrawCanvas extends Canvas {

    public static final int canvasWidth = 1005, canvasHeight = 605;
    public static final int rowSize = canvasWidth / 5, colSize = canvasHeight / 5;
    public static final int pixelSize = 5;

    public static int currentColor = 0xff0000;

    private int[][] board = new int[rowSize][colSize];
    private int[][] tempBoard = new int[rowSize][colSize];

    private DrawMode drawMode;
    private MouseCoordinateChangeListener mouseListener;


    public void setDrawMode(DrawMode drawMode) {
        this.drawMode = drawMode;
        switch (drawMode) {
            case LINE -> {
                geometry = new Line(this);
            }
            case RECTANGLE -> {
                geometry = new Rectangle(this);
            }
        }
    }

    Geometry geometry;


    public DrawCanvas(MouseCoordinateChangeListener mouseListener) {
        this.mouseListener = mouseListener;

        setPreferredSize(new Dimension(canvasWidth, canvasHeight));
        setBackground(new Color(0xFFFFFF));

        this.addMouseListener(new MyMouseAdapter());
        this.addMouseMotionListener(new MyMouseMotionAdapter());

        for (int i = 0; i < rowSize; i++) {
            for (int j = 0; j < colSize; j++) {
                tempBoard[i][j] = board[i][j] = 0xffffff;
            }
        }

        setDrawMode(DrawMode.LINE);

    }

    public void clearDraw(List<Point2D> point2DS) {
        for (Point2D p : point2DS) {
            if (p.getColor() != board[p.getComputerX()][p.getComputerY()]) {
                p.setColor(board[p.getComputerX()][p.getComputerY()]);
                tempBoard[p.getComputerX()][p.getComputerY()] = board[p.getComputerX()][p.getComputerY()];
                putPixel(p);
            }
        }
    }

    public void applyDraw(List<Point2D> point2DList) {
        for (Point2D p : point2DList) {
            if (p.getColor() != board[p.getComputerX()][p.getComputerY()]) {
                tempBoard[p.getComputerX()][p.getComputerY()] = p.getColor();
                putPixel(p);
            }
        }
    }

    public void merge() {
        for (int i = 0; i < rowSize; i++) {
            for (int j = 0; j < colSize; j++) {
                if (board[i][j] != tempBoard[i][j]) {
                    board[i][j] = tempBoard[i][j];
                }
            }
        }
    }

    private void drawAxis() {
        Graphics g = getGraphics();
        g.setColor(new Color(0xCE503F));
        g.fillRect(rowSize / 2 * 5 + 1, 0, 4, canvasHeight);
        g.fillRect(0, colSize / 2 * 5 + 1, canvasWidth, 5);
    }

    private void drawGrid() {
        Graphics g = getGraphics();
        g.setColor(new Color(0xFFD9C7C7));
        for (int i = 0; i <= rowSize; i++) {
            g.drawLine(i * 5, 0, i * 5, canvasHeight);
        }
        for (int i = 0; i <= colSize; i++) {
            g.drawLine(0, i * 5, canvasWidth, i * 5);
        }
        g.dispose();
    }

    public void paint(Graphics g) {
        super.paint(g);

//        drawAxis();
        drawGrid();


    }


    private void putPixel(Point2D point) {
        Graphics g = DrawCanvas.this.getGraphics();
        g.setColor(new Color(point.getColor()));
        g.fillRect(point.getComputerX() * pixelSize + 1, point.getComputerY() * pixelSize + 1, pixelSize - 1, pixelSize - 1);
        g.dispose();
    }

    void put8Pixel(int x, int y, int color) {
//        putPixel(x, y, color);
//        putPixel(y, x, color);
//        putPixel(y, -x, color);
//        putPixel(x, -y, color);
//        putPixel(-x, -y, color);
//        putPixel(-y, -x, color);
//        putPixel(-y, x, color);
//        putPixel(-x, y, color);

    } // Put8Pixel

    void CircleMidPoint(int R) {
        int x, y;

        x = 0;
        y = R;
        put8Pixel(x, y, 0xff00ff);
        double p = 1.25 - R; // 5/4-R
        while (x < y) {
            if (p < 0) p += 2 * x + 3;
            else {
                p += 2 * (x - y) + 5;
                y--;
            }
            x++;
            put8Pixel(x, y, 0xff00ff);
        }

    }


    public void clearScreen() {
        Graphics g = getGraphics();
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, canvasWidth, canvasHeight);

        for (int i = 0; i < rowSize; i++) {
            for (int j = 0; j < colSize; j++) {
                tempBoard[i][j] = board[i][j] = 0xffffff;
            }
        }

        drawGrid();
    }

    public class MyMouseAdapter extends MouseAdapter {
        @Override
        public void mouseClicked(MouseEvent e) {
            super.mouseClicked(e);
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            super.mouseReleased(e);

            if (drawMode == DrawMode.PEN) {

            } else {
                merge();
                geometry.setStartPoint(null);
                geometry.setEndPoint(null);
            }

        }
    }

    public class MyMouseMotionAdapter extends MouseMotionAdapter {
        @Override
        public void mouseDragged(MouseEvent e) {
            super.mouseDragged(e);


            if (e.getX() >= canvasWidth || e.getY() >= canvasHeight || e.getX() <= 0 || e.getY() <= 0) return;

            Point2D point = Point2D.fromComputerCoordinate(e.getX() / DrawCanvas.pixelSize, e.getY() / DrawCanvas.pixelSize);
            point.setColor(0xff0000);

            if (drawMode == DrawMode.PEN) {
                board[point.getComputerX()][point.getComputerY()] = point.getColor();
                putPixel(point);
            } else {
                if (geometry.getStartPoint() == null) {
                    geometry.setStartPoint(point);
                } else {
                    geometry.setEndPoint(point);
                    geometry.setupDraw();
                }
            }

        }

        @Override
        public void mouseMoved(MouseEvent e) {
            super.mouseMoved(e);
            Point2D point = Point2D.fromComputerCoordinate(e.getX() / DrawCanvas.pixelSize, e.getY() / DrawCanvas.pixelSize);
            mouseListener.mouseCoordinate(point.getX(), point.getY());
        }
    }


}
