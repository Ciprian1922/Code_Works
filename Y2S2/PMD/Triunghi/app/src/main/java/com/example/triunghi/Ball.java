package com.example.triunghi;

import android.graphics.Canvas;
import android.graphics.Paint;

public class Ball {
    private float x, y;
    private float radius;
    private Paint paint;
    private float velocityY = 0; // Initial vertical velocity
    private float gravity = 0.5f; // Gravity acceleration
    private float restitution = 0.8f; // Coefficient of restitution (bounciness)
    private boolean isFalling = true;
    private float rampHeight;
    private float[] rampCurve;

    public Ball(float x, float y, float radius, int color, float rampHeight) {
        this.x = x;
        this.y = y;
        this.radius = radius;
        this.rampHeight = rampHeight;

        paint = new Paint();
        paint.setColor(color);
        paint.setAntiAlias(true);
    }

    public void draw(Canvas canvas) {
        canvas.drawCircle(x, y, radius, paint);
    }

    public void update() {
        this.rampCurve = this.rampCurve;
        if (isFalling) {
            // Apply gravity to the vertical velocity
            velocityY += gravity;

            // Update the vertical position based on velocity
            y += velocityY;

            // Check for collision with the ramp curve
            if (this.rampCurve != null && this.rampCurve.length >= 6) {
                for (int i = 0; i < this.rampCurve.length - 5; i += 4) {
                    float startX = this.rampCurve[i];
                    float startY = this.rampCurve[i + 1];
                    float controlX = this.rampCurve[i + 2];
                    float controlY = this.rampCurve[i + 3];
                    float endX = this.rampCurve[i + 4];
                    float endY = this.rampCurve[i + 5];

                    if (checkCollision(startX, startY, controlX, controlY, endX, endY)) {
                        // Adjust the position to prevent the ball from going inside the ramp
                        float[] nearestPoint = findNearestPoint(startX, startY, controlX, controlY, endX, endY);
                        y = nearestPoint[1] - radius;

                        // Reverse the velocity with some loss due to restitution (bounciness)
                        velocityY = -velocityY * restitution;

                        // If the velocity is small, stop the ball
                        if (Math.abs(velocityY) < 1.0f) {
                            velocityY = 0;
                            isFalling = false;
                            break;
                        }
                    }
                }
            }

            // Check for collision with the ground (ramp)
            if (y + radius >= rampHeight) {
                // Adjust the position to prevent the ball from going below the ground
                y = rampHeight - radius;

                // Reverse the velocity with some loss due to restitution (bounciness)
                velocityY = -velocityY * restitution;

                // If the velocity is small, stop the ball
                if (Math.abs(velocityY) < 1.0f) {
                    velocityY = 0;
                    isFalling = false;
                }
            }
        }
    }

    private boolean checkCollision(float startX, float startY, float controlX, float controlY, float endX, float endY) {
        // Implement collision detection algorithm here, such as checking distance between ball center and curve
        // For simplicity, I'll leave this part for you to implement
        return false; // Placeholder, replace with actual collision detection
    }

    private float[] findNearestPoint(float startX, float startY, float controlX, float controlY, float endX, float endY) {
        // Implement calculation to find the nearest point on the curve to the ball's current position
        // For simplicity, I'll leave this part for you to implement
        return new float[]{x, y}; // Placeholder, replace with actual calculation
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

    public float getVelocityY() {
        return velocityY;
    }


    public void setVelocityY(float velocityY) {
        this.velocityY = velocityY;
    }

    public void setX(float v) {
        this.x = v;
    }

    public void setY(float v) {
        this.y = v;
    }
}

