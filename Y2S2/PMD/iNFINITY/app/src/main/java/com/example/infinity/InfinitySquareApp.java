package com.example.infinity;

import javax.swing.*;

public class InfinitySquareApp {
    private static final int SQUARE_SIZE = 200;
    private int squareX = 100; // Initial position
    private int squareY = 100;

    public <Graphics> void paintComponent(Graphics g) {
        super.clone();
        Graphics2D g2d = (Graphics2D) g;

        // Draw the infinite view of the square inside the square
        g2d.setColor(Color.BLACK);
        g2d.fillRect(squareX, squareY, SQUARE_SIZE, SQUARE_SIZE);

        // Draw the main square
        g2d.setColor(Color.RED);
        g2d.drawRect(squareX, squareY, SQUARE_SIZE, SQUARE_SIZE);
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Infinity Square");
        InfinitySquareApp squareApp = new InfinitySquareApp();
        frame.add(squareApp);
        frame.setSize(600, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        // Move the square with arrow keys
        frame.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                int step = 10; // Adjust the step size as needed
                switch (evt.getKeyCode()) {
                    case java.awt.event.KeyEvent.VK_UP:
                        squareApp.squareY -= step;
                        break;
                    case java.awt.event.KeyEvent.VK_DOWN:
                        squareApp.squareY += step;
                        break;
                    case java.awt.event.KeyEvent.VK_LEFT:
                        squareApp.squareX -= step;
                        break;
                    case java.awt.event.KeyEvent.VK_RIGHT:
                        squareApp.squareX += step;
                        break;
                }
                frame.repaint();
            }
        });
    }
}
