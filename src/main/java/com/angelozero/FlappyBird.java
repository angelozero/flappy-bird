package com.angelozero;

import com.angelozero.board.Background;
import com.angelozero.components.Bird;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Objects;

public class FlappyBird extends JPanel implements ActionListener, KeyListener {


    private final Background background;
    private final Bird bird;
    private final Timer gameLoop;
    private final Image topPipe;
    private final Image bottomPipe;

    public FlappyBird() {
        this.background = new Background();
        this.bird = new Bird(background.getHeight(), background.getWidth(), 0, -25, 0, 1);
        this.gameLoop = new Timer(1000 / 60, this);
        gameLoop.start();

        // Flappy Bird - Bottom
        var bottomPipeImage = Objects.requireNonNull(getClass().getResource("/images/bottompipe.png"));
        this.bottomPipe = new ImageIcon(bottomPipeImage).getImage();

        // Flappy Bird - Top
        var topPipeImage = Objects.requireNonNull(getClass().getResource("/images/toppipe.png"));
        this.topPipe = new ImageIcon(topPipeImage).getImage();

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
    public void actionPerformed(ActionEvent e) {
        bird.move();
        repaint();
    }


    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            bird.setVelocityY(-15);
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
    }
}
