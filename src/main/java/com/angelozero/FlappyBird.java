package com.angelozero;

import com.angelozero.board.Background;
import com.angelozero.components.Bird;
import com.angelozero.components.Pipe;
import com.angelozero.components.utils.PipeInfo;

import java.util.ArrayList;
import java.util.List;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class FlappyBird extends JPanel implements KeyListener {
    private final Background background;
    private final Bird bird;
    private final List<Pipe> topPipeList = new ArrayList<>();

    public FlappyBird() {
        this.background = new Background();
        this.bird = new Bird(background.getHeight(), background.getWidth(), -25, 1);

        startTimers();

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
        topPipeList.forEach(pipe -> g.drawImage(pipe.image(), pipe.xPos(), pipe.yPos(), pipe.width(), pipe.height(), null));
    }

    private void startTimers() {
        var placePipesTimer = new Timer(1500, _ -> {
            topPipeList.add(new Pipe(PipeInfo.TOP, background.getWidth(), Pipe.getRandomPlace(background.getHeight()), -2, 0));
        });

        var gameLoopTimer = new Timer(1000 / 60, _ -> {
            bird.move();
            repaint();
            topPipeList.forEach(Pipe::move);
        });

        placePipesTimer.start();
        gameLoopTimer.start();
    }
}
