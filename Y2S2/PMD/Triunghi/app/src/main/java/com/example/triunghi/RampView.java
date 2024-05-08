package com.example.triunghi;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

public class RampView extends View {
    private Ball ball;
    private float endRampX, endRampY;

    public RampView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        // Initialize the ball
        ball = new Ball(0, 0, 20, Color.RED); // Adjust the radius and color as needed

        // Calculate the end point of the ramp
        endRampX = getWidth() * 0.4f; // End at 40% of the width
        endRampY = getHeight() * 0.85f; // End at 85% of the height

        // Start the animation
        startBallAnimation();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // Draw the ramp
        drawRamp(canvas);

        // Draw the ball
        ball.draw(canvas);
    }

    private void drawRamp(Canvas canvas) {
        // Define the dimensions of the ramp
        float startX = 0;
        float startY = getHeight(); // Start from the bottom

        // Draw the ramp
        android.graphics.Path rampPath = new android.graphics.Path();
        rampPath.moveTo(startX, startY);
        rampPath.lineTo(startX, endRampY - 100); // Draw a straight line up
        rampPath.quadTo(endRampX / 2, getHeight(), endRampX, endRampY); // Quadratic curve to the end point
        rampPath.lineTo(endRampX, getHeight()); // Draw a straight line to the bottom

        // Draw the ramp
        Paint paint = new Paint();
        paint.setColor(Color.BLUE); // Change color for the ramp
        canvas.drawPath(rampPath, paint);
    }

    private void startBallAnimation() {
        // Define the falling animation
        ValueAnimator animator = ValueAnimator.ofFloat(0, endRampX);
        animator.setDuration(2000); // Adjust duration as needed
        animator.setInterpolator(new LinearInterpolator());
        animator.addUpdateListener(animation -> {
            float value = (float) animation.getAnimatedValue();
            float ballX = value;
            float ballY = calculateBallY(value);
            ball.setPosition(ballX, ballY);
            invalidate();
        });

        // Start the animation
        animator.start();
    }

    private float calculateBallY(float x) {
        // Calculate the corresponding y-coordinate based on the position along the ramp
        return getHeight() - (getHeight() - endRampY) * (x / endRampX);
    }
}
