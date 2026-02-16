package com.angelozero.ui;

import com.angelozero.core.GameState;
import com.angelozero.core.ScoreManager;
import com.angelozero.domain.Background;
import com.angelozero.domain.Bird;
import com.angelozero.domain.Pipe;

import java.util.List;

import java.awt.*;

public class GameRenderer {
    public void render(Graphics g, Background bg, Bird bird, List<Pipe> pipes, ScoreManager score, GameState gameState) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g.drawImage(bg.getSprite(), 0, 0, bg.getWidth(), bg.getHeight(), null);
        g.drawImage(bird.getSprite(), bird.xPos(), bird.yPos(), bird.width(), bird.height(), null);
        pipes.forEach(p -> g.drawImage(p.getSprite(), p.xPos(), p.yPos(), p.width(), p.height(), null));
        renderUI(g, score, gameState, bg.getHeight());
    }

    private void renderUI(Graphics g, ScoreManager score, GameState gameState, int screenHeight) {
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 32));

        switch (gameState) {
            case GameState.NEW_GAME ->
                g.drawString(score.getFormattedScore(), 10, 35);

            case GameState.GAME_OVER -> {
                score.setRecord(score.getCurrentScoreAsInt());
                g.drawString("Game Over: " + score.getCurrentScoreAsInt(), 10, 35);
            }

        }

        if (score.getRecord() > 0) {
            g.drawString("Record: " + score.getRecord(), 10, screenHeight - 10);
        }
    }
}
