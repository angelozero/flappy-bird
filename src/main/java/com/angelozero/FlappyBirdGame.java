package com.angelozero;

import com.angelozero.board.Background;
import com.angelozero.components.Bird;
import com.angelozero.components.Pipe;
import com.angelozero.components.utils.PipeInfo;
import com.angelozero.extra.FlappyBirdFallException;

import java.util.ArrayList;
import java.util.List;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class FlappyBirdGame extends JPanel implements KeyListener {
    private final Background background;
    private final Bird bird;
    private final List<Pipe> pipeList = new ArrayList<>();
    private boolean gameOver = false;
    private Timer placePipesTimer;
    private Timer gameLoopTimer;

    public FlappyBirdGame() {
        this.background = new Background();
        this.bird = new Bird(background, -15, 1);

        timers();

        setPreferredSize(new Dimension(background.getWidth(), background.getHeight()));
        setBackground(Color.blue);
        setFocusable(true);
        addKeyListener(this);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            bird.setVelocity(-15);
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    private void draw(Graphics g) {
        g.drawImage(background.image(), 0, 0, background.getWidth(), background.getHeight(), null);
        g.drawImage(bird.image(), bird.xPos(), bird.yPos(), bird.width(), bird.height(), null);
        pipeList.forEach(pipe -> g.drawImage(pipe.image(), pipe.xPos(), pipe.yPos(), pipe.width(), pipe.height(), null));
    }

    private void timers() {
        placePipesTimer = new Timer(1500, _ -> {
            var topPipe = new Pipe(PipeInfo.TOP, background.getWidth(), Pipe.getRandomPlace(background.getHeight()), -2);
            var pipesSpace = background.getHeight() / 4;
            var bottomPipe = new Pipe(PipeInfo.BOTTOM, background.getWidth(), topPipe.yPos() + topPipe.height() + pipesSpace, -2);

            pipeList.add(topPipe);
            pipeList.add(bottomPipe);
        });

        gameLoopTimer = new Timer(1000 / 60, _ -> {
            try {
                bird.move();
                repaint();

                pipeList.forEach(pipe -> {
                    pipe.move();
                    if (collision(bird, pipe)) {
                        stopTimers();
                    }
                });


            } catch (FlappyBirdFallException ex) {
                stopTimers();
            }
        });

        startTimers();
    }

    private void startTimers() {
        placePipesTimer.start();
        gameLoopTimer.start();
    }

    private void stopTimers() {
        placePipesTimer.stop();
        gameLoopTimer.stop();
    }

    public boolean collision(Bird bird, Pipe pipe) {
        return bird.xPos() < pipe.xPos() + pipe.width() &&  //bird top left corner doesn't reach pipe top right corner
                bird.xPos() + bird.width() > pipe.xPos() &&  //bird top right corner passes pipe top left corner
                bird.yPos() < pipe.yPos() + pipe.height() && //bird top left corner doesn't reach pipe bottom left corner
                bird.yPos() + bird.height() > pipe.yPos();   //bird bottom left corner passes pipe top left corner
    }
}
