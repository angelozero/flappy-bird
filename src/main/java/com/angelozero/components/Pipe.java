package com.angelozero.components;

import com.angelozero.components.utils.PipeInfo;

import java.util.List;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;
import java.util.Random;

public class Pipe implements GameComponent {

    private static final int WIDTH = 64;
    private static final int HEIGHT = 512;
    private final PipeInfo pipeInfo;
    private int posX;
    private int posY;
    private int velocity;
    private int gravity;
    private boolean isPassed;

    public Pipe(PipeInfo pipeInfo, int posX, int posY, int velocity, int gravity) {
        this.pipeInfo = pipeInfo;
        this.posX = posX;
        this.posY = posY;
        this.velocity = velocity;
        this.gravity = gravity;
        this.isPassed = false;
    }

    public void setVelocity(int velocity) {
        this.velocity = velocity;
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
    public Image image() {
        var birdImage = Objects.requireNonNull(getClass().getResource(pipeInfo.getValue()));
        return new ImageIcon(birdImage).getImage();
    }

    @Override
    public void move() {
        this.posX += this.velocity;
    }

    public static int getRandomPlace(int position) {
        return (int) (0 - (double) position / 4 - Math.random() * position / 2);
    }
}
