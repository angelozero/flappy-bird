package com.angelozero.domain;

import com.angelozero.ui.Drawable;

import java.awt.*;

/**
 * Pássaro controlado pelo jogador. Coordenadas internas em escala (x/8, y/2) para movimento suave.
 */
public class Bird implements Movable, Drawable {

    private static final int WIDTH = 34;
    private static final int HEIGHT = 24;
    private static final int X_SCALE = 8;
    private static final int Y_SCALE = 2;

    private final Image sprite;
    private int posX;
    private int posY;
    private int velocity;
    private final int gravity;

    public Bird(Background background, Image sprite, int velocity, int gravity) {
        this.sprite = sprite;
        this.gravity = gravity;
        this.velocity = velocity;
        // Posição inicial: centro horizontal, ~1/3 da altura (em unidades internas)
        this.posX = (background.getWidth() / 2 - WIDTH / 2) * X_SCALE;
        this.posY = (background.getHeight() / 3) * Y_SCALE;
    }

    @Override
    public int xPos() {
        return posX / X_SCALE;
    }

    @Override
    public int yPos() {
        return posY / Y_SCALE;
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

    @Override
    public void draw(Graphics2D g) {
        g.drawImage(sprite, xPos(), yPos(), width(), height(), null);
    }

    public void setVelocity(int velocity) {
        this.velocity = velocity;
    }
}
