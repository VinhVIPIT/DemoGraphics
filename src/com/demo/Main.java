package com.demo;

import javax.swing.*;
import java.awt.*;

public class Main {

    public static void main(String[] args) {
        JFrame f = new JFrame("Demo Graphics");

        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        f.setLayout(new FlowLayout());
        f.add(new MyCanvas());

        f.add(new JButton("Click"));

        f.setSize(700, 700);
        f.setVisible(true);
    }
}
