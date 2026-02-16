package com.angelozero.ui;

import com.angelozero.core.GameState;

import java.util.List;

/**
 * DTO com o estado da cena para renderização (sem dependência de lógica do jogo).
 */
public record GameScene(
        Drawable background,
        Drawable bird,
        List<Drawable> pipes,
        int score,
        int record,
        GameState gameState) {
}
