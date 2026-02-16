package com.angelozero.core;

import javax.swing.*;
import java.awt.event.ActionListener;

/**
 * Timer que dispara callbacks em intervalo fixo (ex.: game tick, spawn de obstáculos).
 */
public class GameTimer {

    private final Timer timer;

    public GameTimer(int delayMs, ActionListener actionListener) {
        this.timer = new Timer(delayMs, actionListener);
    }

    public void start() {
        timer.start();
    }

    public void stop() {
        timer.stop();
    }
}
