package com.angelozero.components.utils;

public enum PipeInfo {
    BOTTOM("/images/pipe_bottom.png"),
    TOP("/images/pipe_top.png");

    private final String value;

    PipeInfo(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

}
