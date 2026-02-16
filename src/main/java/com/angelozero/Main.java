package com.angelozero;

import com.angelozero.core.CollisionDetector;
import com.angelozero.core.GameConstants;
import com.angelozero.core.PhysicsEngine;
import com.angelozero.core.ScoreManager;
import com.angelozero.engine.FlappyBirdGame;
import com.angelozero.ui.GameRenderer;

import javax.swing.*;

public class Main {

    public static void main(String[] args) {
        GameRenderer gameRenderer = new GameRenderer();
        ScoreManager scoreManager = new ScoreManager();
        CollisionDetector collisionDetector = new PhysicsEngine();

        FlappyBirdGame flappyBirdGame = new FlappyBirdGame(gameRenderer, scoreManager, collisionDetector);

        JFrame frame = new JFrame("Flappy Bird");
        frame.setSize(GameConstants.BOARD_WIDTH, GameConstants.BOARD_HEIGHT);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(flappyBirdGame);
        frame.pack();
        flappyBirdGame.requestFocus();
        frame.setVisible(true);
    }
}
