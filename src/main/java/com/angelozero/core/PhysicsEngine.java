package com.angelozero.core;

import com.angelozero.domain.Positionable;

/**
 * Implementação padrão de detecção de colisão e limites da tela.
 */
public class PhysicsEngine implements CollisionDetector {

    @Override
    public boolean checkCollision(Positionable a, Positionable b) {
        return a.xPos() < b.xPos() + b.width() &&
                a.xPos() + a.width() > b.xPos() &&
                a.yPos() < b.yPos() + b.height() &&
                a.yPos() + a.height() > b.yPos();
    }

    @Override
    public boolean isOutOfBounds(Positionable component, int screenHeight) {
        return component.yPos() > screenHeight || (component.yPos() + component.height()) < 0;
    }
}
