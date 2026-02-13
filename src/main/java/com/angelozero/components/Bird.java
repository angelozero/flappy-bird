package com.angelozero.components;

import com.angelozero.board.Background;
import com.angelozero.extra.FlappyBirdFallException;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class Bird implements GameComponent {

    private static final String IMAGE = "/images/flappybird.png";
    private static final int WIDTH = 34;
    private static final int HEIGHT = 24;
    private int posX;
    private int posY;
    private int velocity;
    private int gravity;
    private int backgroundHeight;

    public Bird(int posX, int posY, int velocity, int gravity, int backgroundHeight) {
        this.posX = posX;
        this.posY = posY;
        this.velocity = velocity;
        this.gravity = gravity;
        this.backgroundHeight = backgroundHeight;
    }

    public Bird(Background background, int velocity, int gravity) {
        this.posX = background.getHeight();
        this.posY = background.getWidth();
        this.velocity = velocity;
        this.gravity = gravity;
        this.backgroundHeight = background.getHeight();
    }

    public void setVelocity(int velocity) {
        this.velocity = velocity;
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
        this.velocity += this.gravity;
        this.posY += velocity;
        this.posY = Math.max(posY, 0);

        if (yPos() > backgroundHeight) {
            throw new FlappyBirdFallException("Flappy Bird Fall!");
        }
    }
}
