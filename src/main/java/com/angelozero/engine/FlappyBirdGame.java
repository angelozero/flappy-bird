package com.angelozero.engine;

import com.angelozero.core.CollisionDetector;
import com.angelozero.core.GameConstants;
import com.angelozero.core.GameState;
import com.angelozero.core.GameTimer;
import com.angelozero.core.ScoreService;
import com.angelozero.domain.Background;
import com.angelozero.domain.Bird;
import com.angelozero.domain.Pipe;
import com.angelozero.ui.GameRenderer;
import com.angelozero.ui.GameScene;
import com.angelozero.ui.ImageInfo;
import com.angelozero.ui.Drawable;

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
    private final GameTimer gameTimer;
    private final GameTimer pipesTimer;
    private final ScoreService scoreService;
    private final CollisionDetector collisionDetector;

    public FlappyBirdGame(GameRenderer gameRenderer, ScoreService scoreService, CollisionDetector collisionDetector) {
        this.pipeList = new ArrayList<>();
        this.gameRenderer = gameRenderer;
        this.scoreService = scoreService;
        this.collisionDetector = collisionDetector;
        this.gameState = GameState.NEW_GAME;
        this.background = new Background(ImageInfo.BACKGROUND.getSprite());
        this.bird = new Bird(background, ImageInfo.BIRD.getSprite(),
                GameConstants.BIRD_FLAP_VELOCITY, GameConstants.BIRD_GRAVITY);
        this.gameTimer = new GameTimer(GameConstants.TICK_MS, _ -> updateGame());
        this.pipesTimer = new GameTimer(GameConstants.PIPE_SPAWN_INTERVAL_MS, _ -> placePipes());

        setPreferredSize(new Dimension(background.getWidth(), background.getHeight()));
        setFocusable(true);
        addKeyListener(this);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        GameScene scene = buildScene();
        gameRenderer.render(g, scene);
    }

    private GameScene buildScene() {
        List<Drawable> pipes = pipeList.stream()
                .map(p -> (Drawable) p)
                .toList();
        return new GameScene(
                background,
                bird,
                pipes,
                scoreService.getCurrentScoreAsInt(),
                scoreService.getRecord(),
                gameState
        );
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() != KeyEvent.VK_SPACE) {
            return;
        }
        if (gameState == GameState.GAME_OVER) {
            resetGame();
            return;
        }
        if (gameState == GameState.NEW_GAME) {
            gameState = GameState.PLAYING;
            startTimers();
        }
        bird.setVelocity(GameConstants.BIRD_FLAP_VELOCITY);
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    private void placePipes() {
        int pipeVelocity = GameConstants.PIPE_VELOCITY;
        var topPipe = new Pipe(ImageInfo.PIPE_TOP.getSprite(), background.getWidth(),
                Pipe.getRandomPlace(background.getHeight()), pipeVelocity);
        int pipesSpace = background.getHeight() / 4;
        var bottomPipe = new Pipe(ImageInfo.PIPE_BOTTOM.getSprite(), background.getWidth(),
                topPipe.yPos() + topPipe.height() + pipesSpace, pipeVelocity);

        pipeList.add(topPipe);
        pipeList.add(bottomPipe);
    }

    private void updateGame() {
        if (gameState != GameState.PLAYING) {
            return;
        }
        bird.move();

        if (collisionDetector.isOutOfBounds(bird, background.getHeight())) {
            endGame();
            return;
        }

        for (Pipe pipe : pipeList) {
            pipe.move();
            if (collisionDetector.checkCollision(bird, pipe)) {
                endGame();
                return;
            }
            if (!pipe.isPassed() && bird.xPos() > pipe.xPos() + pipe.width()) {
                pipe.setPassed(true);
                scoreService.increment();
            }
        }

        repaint();
    }

    private void startTimers() {
        gameTimer.start();
        pipesTimer.start();
    }

    private void stopTimers() {
        gameTimer.stop();
        pipesTimer.stop();
    }

    private void resetGame() {
        bird = new Bird(background, ImageInfo.BIRD.getSprite(),
                GameConstants.BIRD_FLAP_VELOCITY, GameConstants.BIRD_GRAVITY);
        pipeList.clear();
        scoreService.reset();
        gameState = GameState.NEW_GAME;
        repaint();
    }

    private void endGame() {
        scoreService.updateRecordIfBetter(scoreService.getCurrentScoreAsInt());
        gameState = GameState.GAME_OVER;
        stopTimers();
        repaint();
    }
}
