package com.angelozero.domain;

import java.awt.*;

public class Background {

    private Image sprite;
    private final int width;
    private final int height;
    private static final int BOARD_WIDTH = 360;
    private static final int BOARD_HEIGHT = 640;

    public Background(Image sprite) {
        this.sprite = sprite;
        this.width = BOARD_WIDTH;
        this.height = BOARD_HEIGHT;
    }

    public Background(Image sprite, int width, int height) {
        this.sprite = sprite;
        this.width = width;
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Image getSprite() {
        return sprite;
    }
}
