package com.angelozero.domain;

import com.angelozero.ui.Drawable;

import java.awt.*;

public class Pipe implements Movable, Drawable {

    private static final int WIDTH = 64;
    private static final int HEIGHT = 512;

    private final Image sprite;
    private int posX;
    private int posY;
    private final int velocity;
    private boolean isPassed;

    public Pipe(Image sprite, int posX, int posY, int velocity) {
        this.sprite = sprite;
        this.posX = posX;
        this.posY = posY;
        this.velocity = velocity;
        this.isPassed = false;
    }

    @Override
    public int xPos() {
        return posX;
    }

    @Override
    public int yPos() {
        return posY;
    }

    @Override
    public int width() {
        return WIDTH;
    }

    @Override
    public int height() {
        return HEIGHT;
    }

    @Override
    public void move() {
        this.posX += this.velocity;
    }

    @Override
    public void draw(Graphics2D g) {
        g.drawImage(sprite, xPos(), yPos(), width(), height(), null);
    }

    public boolean isPassed() {
        return isPassed;
    }

    public void setPassed(boolean passed) {
        isPassed = passed;
    }

    public static int getRandomPlace(int position) {
        return (int) (0 - (double) position / 4 - Math.random() * position / 2);
    }
}
