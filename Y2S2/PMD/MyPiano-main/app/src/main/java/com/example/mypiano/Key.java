package com.example.mypiano;

import android.graphics.RectF;

public class Key {
    public int sound;
    public RectF rect;
    public boolean isDown;

    public Key(int sound, RectF rect) {
        this.sound = sound;
        this.rect = rect;
    }
}
