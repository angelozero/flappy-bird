package com.angelozero.ui;

import com.angelozero.core.GameState;

import java.util.List;
import java.util.Objects;

/**
 * DTO com o estado da cena para renderização (sem dependência de lógica do jogo).
 */
public final class GameScene {
    private final Drawable background;
    private final Drawable bird;
    private final List<Drawable> pipes;
    private final int score;
    private final int record;
    private final GameState gameState;

    // Construtor principal
    public GameScene(Drawable background, Drawable bird, List<Drawable> pipes,
                     int score, int record, GameState gameState) {
        this.background = background;
        this.bird = bird;
        this.pipes = pipes;
        this.score = score;
        this.record = record;
        this.gameState = gameState;
    }

    // Métodos de acesso (estilo record: nomeDoCampo())
    public Drawable background() { return background; }
    public Drawable bird() { return bird; }
    public List<Drawable> pipes() { return pipes; }
    public int score() { return score; }
    public int record() { return record; }
    public GameState gameState() { return gameState; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GameScene gameScene = (GameScene) o;
        return score == gameScene.score &&
                record == gameScene.record &&
                Objects.equals(background, gameScene.background) &&
                Objects.equals(bird, gameScene.bird) &&
                Objects.equals(pipes, gameScene.pipes) &&
                gameState == gameScene.gameState;
    }

    @Override
    public int hashCode() {
        return Objects.hash(background, bird, pipes, score, record, gameState);
    }

    @Override
    public String toString() {
        return "GameScene[" +
                "background=" + background +
                ", bird=" + bird +
                ", pipes=" + pipes +
                ", score=" + score +
                ", record=" + record +
                ", gameState=" + gameState +
                ']';
    }
}