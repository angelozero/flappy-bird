package com.angelozero.board;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class Background {

    private static final String IMAGE = "/images/flappybirdbg.png";
    private final int width;
    private final int height;
    private static final int BOARD_WIDTH = 360;
    private static final int BOARD_HEIGHT = 640;

    public Background() {
        this.width = BOARD_WIDTH;
        this.height = BOARD_HEIGHT;
    }

    public Background(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Image image() {
        var birdImage = Objects.requireNonNull(getClass().getResource(IMAGE));
        return new ImageIcon(birdImage).getImage();
    }
}
