package com.angelozero.domain;

import java.awt.*;

public class Bird implements GameComponent {

    private static final int WIDTH = 34;
    private static final int HEIGHT = 24;
    private final Image sprite;
    private int posX;
    private int posY;
    private int velocity;
    private int gravity;

    public Bird(Background background, Image sprite, int velocity, int gravity) {
        this.sprite = sprite;
        this.posX = background.getHeight();
        this.posY = background.getWidth();
        this.velocity = velocity;
        this.gravity = gravity;

    }

    @Override
    public int xPos() {
        return posX / 8;
    }

    @Override
    public int yPos() {
        return posY / 2;
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
        this.velocity += this.gravity;
        this.posY += velocity;
        this.posY = Math.max(posY, 0);
    }

    public Image getSprite() {
        return sprite;
    }

    public void setVelocity(int velocity) {
        this.velocity = velocity;
    }
}
