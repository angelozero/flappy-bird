package com.angelozero.core;

import com.angelozero.domain.Positionable;

public interface CollisionDetector {

    boolean checkCollision(Positionable a, Positionable b);

    boolean isOutOfBounds(Positionable component, int screenHeight);
}
