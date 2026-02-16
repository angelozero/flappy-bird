package com.angelozero.core;

import com.angelozero.domain.GameComponent;

public class PhysicsEngine {
    public static boolean checkCollision(GameComponent a, GameComponent b) {
        return a.xPos() < b.xPos() + b.width() &&
                a.xPos() + a.width() > b.xPos() &&
                a.yPos() < b.yPos() + b.height() &&
                a.yPos() + a.height() > b.yPos();
    }

    public static boolean isOutOfBounds(GameComponent component, int screenHeight) {
        return component.yPos() > screenHeight || (component.yPos() + component.height()) < 0;
    }
}
