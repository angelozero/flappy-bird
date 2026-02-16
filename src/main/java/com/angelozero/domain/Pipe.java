package com.angelozero.domain;

import java.awt.*;

public class Pipe implements GameComponent {

    private static final int WIDTH = 64;
    private static final int HEIGHT = 512;
    private final Image sprite;
    private int posX;
    private int posY;
    private int velocity;
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

    public Image getSprite() {
        return sprite;
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
