package com.angelozero.components;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class Bird implements GameComponent {

    private static final String IMAGE = "/images/flappybird.png";
    private static final int WIDTH = 34;
    private static final int HEIGHT = 24;
    private int posX;
    private int posY;
    private int velocityX;

    private int velocityY;
    private int gravityX;
    private int gravityY;

    public Bird(int posX, int posY, int velocityX, int velocityY, int gravityX, int gravityY) {
        this.posX = posX;
        this.posY = posY;
        this.velocityX = velocityX;
        this.velocityY = velocityY;
        this.gravityX = gravityX;
        this.gravityY = gravityY;
    }

    public void setVelocityY(int velocityY) {
        this.velocityY = velocityY;
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
    public Image image() {
        var birdImage = Objects.requireNonNull(getClass().getResource(IMAGE));
        return new ImageIcon(birdImage).getImage();
    }

    @Override
    public void move() {
        this.velocityY += this.gravityY;
        this.posY += velocityY;
        this.posY = Math.max(posY, 0);
    }
}
