package com.angelozero.ui;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class ImageLoader {
    private static final Map<String, Image> cache = new HashMap<>();

    public static Image getImage(ImageInfo imageInfo) {
        if (!cache.containsKey(imageInfo.getValue())) {
            var resource = Objects.requireNonNull(ImageLoader.class.getResource(imageInfo.getValue()),
                    "Resource not found: " + imageInfo.getValue());
            cache.put(imageInfo.getValue(), new ImageIcon(resource).getImage());
        }
        return cache.get(imageInfo.getValue());
    }
}
