package com.angelozero.ui;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public enum ImageInfo {
    BIRD("/images/flappybird.png"),
    BACKGROUND("/images/background.png"),
    PIPE_BOTTOM("/images/pipe_bottom.png"),
    PIPE_TOP("/images/pipe_top.png");

    private final String value;

    private static final Map<String, ImageInfo> LOOKUP = new HashMap<>();

    static {
        for (ImageInfo info : ImageInfo.values()) {
            LOOKUP.put(info.value, info);
        }
    }

    ImageInfo(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static ImageInfo fromValue(String value) {
        return LOOKUP.get(value);
    }

    public Image getSprite() {
        return ImageLoader.getImage(LOOKUP.get(this.value));
    }
}
