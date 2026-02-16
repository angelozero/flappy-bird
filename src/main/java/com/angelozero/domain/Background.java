package com.angelozero.domain;

import com.angelozero.core.GameConstants;
import com.angelozero.ui.Drawable;

import java.awt.*;

public class Background implements Drawable {

    private final Image sprite;
    private final int width;
    private final int height;

    public Background(Image sprite) {
        this.sprite = sprite;
        this.width = GameConstants.BOARD_WIDTH;
        this.height = GameConstants.BOARD_HEIGHT;
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

    @Override
    public void draw(Graphics2D g) {
        g.drawImage(sprite, 0, 0, width, height, null);
    }
}
