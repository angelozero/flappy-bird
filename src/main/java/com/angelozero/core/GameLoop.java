package com.angelozero.core;

import javax.swing.*;
import java.awt.event.ActionListener;

public class GameLoop {

    private final Timer timer;

    public GameLoop(int delay, ActionListener actionListener) {
        this.timer = new Timer(delay, actionListener);
    }

    public void start() {
        timer.start();
    }

    public void stop() {
        timer.stop();
    }
}
