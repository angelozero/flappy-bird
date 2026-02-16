package com.angelozero.core;

/**
 * Constantes do jogo (dimensões, FPS, velocidades, pontuação).
 */
public final class GameConstants {

    public static final int BOARD_WIDTH = 360;
    public static final int BOARD_HEIGHT = 640;

    public static final int TARGET_FPS = 60;
    public static final int TICK_MS = 1000 / TARGET_FPS;

    public static final int PIPE_SPAWN_INTERVAL_MS = 1500;

    public static final int BIRD_FLAP_VELOCITY = -15;
    public static final int BIRD_GRAVITY = 1;

    public static final int PIPE_VELOCITY = -2;

    /**
     * Pontos por par de canos (cada par = 0.5 + 0.5 = 1 ponto).
     */
    public static final double SCORE_PER_PIPE = 0.5;

    public static final int SCORE_FONT_SIZE = 32;
    public static final int SCORE_POS_X = 10;
    public static final int SCORE_POS_Y = 35;

    private GameConstants() {
    }
}
