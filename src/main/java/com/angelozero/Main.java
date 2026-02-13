package com.angelozero;

import javax.swing.*;

public class Main {
    static void main() {
        int boardWidth = 360;
        int boardHeight = 640;

        JFrame frame = new JFrame("Flappy Bird");

        frame.setSize(boardWidth, boardHeight);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        FlappyBirdGame flappyBirdGame = new FlappyBirdGame();
        frame.add(flappyBirdGame);
        frame.pack();
        flappyBirdGame.requestFocus();
        frame.setVisible(true);
    }
}
