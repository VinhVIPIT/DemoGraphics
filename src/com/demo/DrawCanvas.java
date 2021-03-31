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

    public static final int pixelSize = 5;  // Kích thước 1 đơn vị

    public static LineMode lineMode;

    public static int currentColor = 0xff0000;  // Màu vẽ đang chọn hiện tại

    private int[][] board = new int[rowSize][colSize];      // Bảng màu canvas chính
    private int[][] tempBoard = new int[rowSize][colSize];  // Bảng màu phụ cho việc preview hình, sau khi `merge()` thì board và tempBoard sẽ hợp lại thành 1

    private DrawMode drawMode; // Chế độ hình vẽ

    private MouseCoordinateChangeListener mouseListener; // Sự kiện cập nhập tọa độ con chuột

    private Geometry geometry;  // Hình vẽ

    private boolean isShowAxis = true;
    private boolean isShowGrid = true;


    //------------------------------------------------------------------------//


    public DrawCanvas(MouseCoordinateChangeListener mouseListener) {
        this.mouseListener = mouseListener;

        setPreferredSize(new Dimension(canvasWidth, canvasHeight));

        // mặc định nền màu trắng
        setBackground(new Color(0xFFFFFF));

        this.addMouseListener(new MyMouseAdapter());
        this.addMouseMotionListener(new MyMouseMotionAdapter());

        for (int i = 0; i < rowSize; i++) {
            for (int j = 0; j < colSize; j++) {
                tempBoard[i][j] = board[i][j] = 0xffffff;
            }
        }

        // Mode mặc định là vẽ PEN
        setDrawMode(DrawMode.LINE);

        lineMode = LineMode.DEFAULT;

        Cursor c = new Cursor(Cursor.DEFAULT_CURSOR);
        setCursor(c);

    }

    public void setShowAxis(boolean showAxis) {
        isShowAxis = showAxis;
        if (isShowAxis) drawAxis();
        else clearAxis();
    }

    public void setShowGrid(boolean showGrid) {
        isShowGrid = showGrid;
        if (isShowGrid) drawGrid();
        else clearGrid();
    }

    public boolean isShowAxis() {
        return isShowAxis;
    }

    public boolean isShowGrid() {
        return isShowGrid;
    }

    /*
                Cài đặt hình muốn vẽ
                 */
    public void setDrawMode(DrawMode drawMode) {
        this.drawMode = drawMode;
        switch (drawMode) {
            case PEN -> {
                geometry = new Pen(this);
            }
            case LINE -> {
                geometry = new Line(this);
            }
            case RECTANGLE -> {
                geometry = new Rectangle(this);
            }
            case CIRCLE -> {
                geometry = new Circle(this);
            }
        }
    }


    /*
     * Xóa những điểm đã cũ mà không thuộc hình vẽ preview
     * Bằng cách lấy màu nền vẽ đè lên
     */
    public void clearDraw(List<Point2D> point2DS) {
        for (Point2D p : point2DS) {
            if (p.getComputerX() < 0 || p.getComputerY() < 0 || p.getComputerX() >= rowSize || p.getComputerY() >= colSize)
                continue;

            if (p.getColor() != board[p.getComputerX()][p.getComputerY()]) {
                p.setColor(board[p.getComputerX()][p.getComputerY()]);
                tempBoard[p.getComputerX()][p.getComputerY()] = board[p.getComputerX()][p.getComputerY()];

                if ((p.getX() == 0 || p.getY() == 0) && p.getColor() == 0xffffff) {
                    if (isShowAxis) p.setColor(0x3FBDCE);
                }

                putPixel(p);
            }
        }
    }

    /*
     * Vẽ bản preview của hình
     */
    public void applyDraw(List<Point2D> point2DList) {
//        System.out.println("aaa: "+point2DList.size());

        for (Point2D p : point2DList) {
            if (p.getComputerX() < 0 || p.getComputerY() < 0 || p.getComputerX() >= rowSize || p.getComputerY() >= colSize)
                continue;

            if (p.getColor() != board[p.getComputerX()][p.getComputerY()]) {
                tempBoard[p.getComputerX()][p.getComputerY()] = p.getColor();
                putPixel(p);
            }
        }
    }

    /*
     * Hợp nhất nét vẽ preview của hình lên canvas
     */
    public void merge() {
        for (int i = 0; i < rowSize; i++) {
            for (int j = 0; j < colSize; j++) {
                if (board[i][j] != tempBoard[i][j]) {
                    board[i][j] = tempBoard[i][j];
                }
            }
        }
    }

    /*
     * Vẽ trục tọa độ
     */
    private void drawAxis() {

        Point2D p;

        int j = colSize / 2;
        int i = 0;
        for (; i < rowSize; i++) {
            if (board[i][j] == 0xffffff) {
                p = Point2D.fromComputerCoordinate(i, j);
                p.setColor(0x3FBDCE);
                putPixel(p);
            }
        }

        i = rowSize / 2;
        j = 0;
        for (; j < colSize; j++) {
            if (board[i][j] == 0xffffff) {
                p = Point2D.fromComputerCoordinate(i, j);
                p.setColor(0x3FBDCE);
                putPixel(p);
            }
        }

    }

    /*
     * Xóa 2 trục tọa độ
     */
    private void clearAxis() {
        Point2D p;

        int j = colSize / 2;
        int i = 0;
        for (; i < rowSize; i++) {
            p = Point2D.fromComputerCoordinate(i, j);
            p.setColor(board[i][j]);
            putPixel(p);
        }

        i = rowSize / 2;
        j = 0;
        for (; j < colSize; j++) {
            p = Point2D.fromComputerCoordinate(i, j);
            p.setColor(board[i][j]);
            putPixel(p);
        }
    }

    /*
     * Vẽ lưới hệ tọa độ
     */
    private void drawGrid() {

        Graphics g = getGraphics();
        g.setColor(new Color(0xFFD9C7C7));

        for (int i = 0; i <= rowSize; i++) {
            g.drawLine(i * pixelSize, 0, i * pixelSize, canvasHeight);
        }
        for (int i = 0; i <= colSize; i++) {
            g.drawLine(0, i * pixelSize, canvasWidth, i * pixelSize);
        }
        g.dispose();
    }

    /*
     * Xóa lưới tọa độ
     */
    private void clearGrid() {
        Point2D p;

        for (int i = 0; i < rowSize; i++) {
            for (int j = 0; j < colSize; j++) {
                p = Point2D.fromComputerCoordinate(i, j);
                p.setColor(board[i][j]);

                if (i == rowSize / 2 || j == colSize / 2) {
                    if (board[i][j] == 0xffffff) p.setColor(0x3FBDCE);
                }
                putPixel(p);
            }
        }

        if (isShowAxis) drawAxis();
    }

    /*
     * Paint Super Pha-ke
     */
    @Override
    public void paint(Graphics g) {
        super.paint(g);

        drawAxis();
        drawGrid(); // Vẽ ô lưới

    }


    private void putPixel(Point2D point) {
        Graphics g = DrawCanvas.this.getGraphics();
        g.setColor(new Color(point.getColor()));

        if (isShowGrid)
            g.fillRect(point.getComputerX() * pixelSize + 1, point.getComputerY() * pixelSize + 1, pixelSize - 1, pixelSize - 1);
        else
            g.fillRect(point.getComputerX() * pixelSize, point.getComputerY() * pixelSize, pixelSize, pixelSize);

        g.dispose();
    }


    /*
     * Xóa toàn bộ màn hình, mặc định màn hình sẽ quay về màu trắng
     */
    public void clearScreen() {
        geometry.clearAll();

        Graphics g = getGraphics();
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, canvasWidth, canvasHeight);    // vẽ như này cho nhanh

        for (int i = 0; i < rowSize; i++) {
            for (int j = 0; j < colSize; j++) {
                tempBoard[i][j] = board[i][j] = 0xffffff;
            }
        }

        if(isShowAxis) drawAxis();

        if(isShowGrid) drawGrid(); // Xóa xong thì vẽ lại lưới tọa độ
    }

    // Lớp cài đặt sự kiện nhấn chuột
    public class MyMouseAdapter extends MouseAdapter {

        @Override
        public void mouseClicked(MouseEvent e) {
            // TODO: Them ve diem
            super.mouseClicked(e);

            // Lấy tọa độ con trỏ chuột trên màn hình, chuyển sang tọa độ Descartes
            Point2D point = Point2D.fromComputerCoordinate(e.getX() / DrawCanvas.pixelSize, e.getY() / DrawCanvas.pixelSize);
            // Set màu cho điểm vẽ là màu đang chọn
//            point.setColor(DrawCanvas.currentColor);
//            geometry.setStartPoint(point);
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            // Hàm này được gọi khi chuột được nhả ra

            super.mouseReleased(e);

            // Hợp nhất hình vừa vẽ lên canvas
            merge();

            // Reset các thuộc tính của Hình vẽ
            geometry.clearAll();

        }
    }


    // Lớp cài đặt sự kiện di chuột
    public class MyMouseMotionAdapter extends MouseMotionAdapter {


        @Override
        public void mouseDragged(MouseEvent e) {
            // Nhấn giữ chuột và kéo

            super.mouseDragged(e);
            if (e.getX() >= canvasWidth || e.getY() >= canvasHeight || e.getX() <= 0 || e.getY() <= 0) return;

            // Lấy tọa độ con trỏ chuột trên màn hình, chuyển sang tọa độ Descartes
            Point2D point = Point2D.fromComputerCoordinate(e.getX() / DrawCanvas.pixelSize, e.getY() / DrawCanvas.pixelSize);
            // Set màu cho điểm vẽ là màu đang chọn
            point.setColor(DrawCanvas.currentColor);

            if (geometry.getStartPoint() == null) {
                geometry.setStartPoint(point);
            } else {
                geometry.setEndPoint(point);
                geometry.setupDraw(); // Xem trước nét vẽ
            }

        }


        @Override
        public void mouseMoved(MouseEvent e) {
            // Chuột di chuyển bình thưởng, KHÔNG nhấn giữ

            super.mouseMoved(e);

            // Sử dụng kĩ thuật Callback, cập nhật tọa độ hiển thị lên JLable ở góc dưới màn hình
            Point2D point = Point2D.fromComputerCoordinate(e.getX() / DrawCanvas.pixelSize, e.getY() / DrawCanvas.pixelSize);
            mouseListener.mouseCoordinate(point.getX(), point.getY());
        }

    }


}
