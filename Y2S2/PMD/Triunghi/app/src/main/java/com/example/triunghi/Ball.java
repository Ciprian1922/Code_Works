package com.example.triunghi;

import android.graphics.Canvas;
import android.graphics.Paint;

public class Ball {
    private float x, y;
    private float radius;
    private Paint paint;

    public Ball(float x, float y, float radius, int color) {
        this.x = x;
        this.y = y;
        this.radius = radius;

        paint = new Paint();
        paint.setColor(color);
        paint.setAntiAlias(true);
    }

    public void draw(Canvas canvas) {
        canvas.drawCircle(x, y, radius, paint);
    }

    public void setPosition(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getRadius() {
        return radius;
    }
}
