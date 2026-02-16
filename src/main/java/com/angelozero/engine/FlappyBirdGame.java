package com.angelozero.engine;

import com.angelozero.core.GameLoop;
import com.angelozero.core.GameState;
import com.angelozero.core.PhysicsEngine;
import com.angelozero.core.ScoreManager;
import com.angelozero.domain.Background;
import com.angelozero.domain.Bird;
import com.angelozero.domain.Pipe;
import com.angelozero.ui.GameRenderer;
import com.angelozero.ui.ImageInfo;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;

public class FlappyBirdGame extends JPanel implements KeyListener {
    private Bird bird;
    private GameState gameState;
    private final Background background;
    private final List<Pipe> pipeList;
    private final GameRenderer gameRenderer;
    private final GameLoop gameLoop;
    private final GameLoop pipesLoop;
    private final ScoreManager scoreManager;

    public FlappyBirdGame() {
        this.pipeList = new ArrayList<>();
        this.gameRenderer = new GameRenderer();
        this.scoreManager = new ScoreManager();
        this.gameState = GameState.NEW_GAME;
        this.background = new Background(ImageInfo.BACKGROUND.getSprite());
        this.bird = new Bird(background, ImageInfo.BIRD.getSprite(), -15, 1);

        gameLoop = new GameLoop(1000 / 60, _ -> updateGame());
        pipesLoop = new GameLoop(1500, _ -> placePipes());
        startTimers();

        setPreferredSize(new Dimension(background.getWidth(), background.getHeight()));
        setFocusable(true);
        addKeyListener(this);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        gameRenderer.render(g, background, bird, pipeList, scoreManager, gameState);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            bird.setVelocity(-15);
            if (gameState == GameState.GAME_OVER) {
                resetGame();
            }
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    private void placePipes() {
        var topPipe = new Pipe(ImageInfo.PIPE_TOP.getSprite(), background.getWidth(), Pipe.getRandomPlace(background.getHeight()), -2);
        var pipesSpace = background.getHeight() / 4;
        var bottomPipe = new Pipe(ImageInfo.PIPE_BOTTOM.getSprite(), background.getWidth(), topPipe.yPos() + topPipe.height() + pipesSpace, -2);

        pipeList.add(topPipe);
        pipeList.add(bottomPipe);
    }

    private void updateGame() {
        bird.move();

        if (PhysicsEngine.isOutOfBounds(bird, background.getHeight())) {
            endGame();

        } else {
            pipeList.forEach(pipe -> {
                pipe.move();

                if (PhysicsEngine.checkCollision(bird, pipe)) {
                    endGame();
                }

                if (!pipe.isPassed() && bird.xPos() > pipe.xPos() + pipe.width()) {
                    pipe.setPassed(true);
                    scoreManager.increment();
                }
            });

            repaint();
        }
    }

    private void startTimers() {
        gameLoop.start();
        pipesLoop.start();
    }

    private void stopTimers() {
        gameLoop.stop();
        pipesLoop.stop();
    }

    private void resetGame() {
        bird = new Bird(background, ImageInfo.BIRD.getSprite(), -15, 1);
        pipeList.clear();
        scoreManager.reset();
        gameLoop.start();
        pipesLoop.start();
        gameState = GameState.NEW_GAME;
    }

    private void endGame() {
        gameState = GameState.GAME_OVER;
        stopTimers();
        repaint();
    }
}
