package com.angelozero.ui;

import com.angelozero.core.GameConstants;

import java.awt.*;

/**
 * Renderiza a cena do jogo a partir de um DTO (sem efeitos colaterais de lógica).
 */
public class GameRenderer {

    public void render(Graphics g, GameScene scene) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        scene.background().draw(g2d);
        scene.bird().draw(g2d);
        scene.pipes().forEach(p -> p.draw(g2d));

        renderUI(g, scene);
    }

    private void renderUI(Graphics g, GameScene scene) {
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, GameConstants.SCORE_FONT_SIZE));

        switch (scene.gameState()) {
            case NEW_GAME:
            case PLAYING:
                g.drawString(scene.score() + "", GameConstants.SCORE_POS_X, GameConstants.SCORE_POS_Y);
                break;

            case GAME_OVER:
                g.drawString("Game Over: " + scene.score(), GameConstants.SCORE_POS_X, GameConstants.SCORE_POS_Y);
                break;

            default:
                // É sempre boa prática ter um default no Java antigo
                break;
        }

        if (scene.record() > 0) {
            g.drawString("Record: " + scene.record(), GameConstants.SCORE_POS_X, GameConstants.BOARD_HEIGHT - 10);
        }
    }
}
