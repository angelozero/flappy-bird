package com.angelozero.components;

import java.awt.*;

public interface GameComponent {
    int xPos();

    int yPos();

    int width();

    int height();

    Image image();

    void move();
}
