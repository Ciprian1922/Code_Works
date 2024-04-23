package com.example.ballanimation;

public class CustomPaint {
    private int color;
    private float strokeWidth;
    // Add any additional properties and methods as needed

    public CustomPaint() {
        // Default constructor
    }

    public CustomPaint(int color, float strokeWidth) {
        this.color = color;
        this.strokeWidth = strokeWidth;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public float getStrokeWidth() {
        return strokeWidth;
    }

    public void setStrokeWidth(float strokeWidth) {
        this.strokeWidth = strokeWidth;
    }
}
