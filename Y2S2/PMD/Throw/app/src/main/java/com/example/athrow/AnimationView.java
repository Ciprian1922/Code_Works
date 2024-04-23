package com.example.athrow;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

public class AnimationView extends View {
    private int groundCollisionCount1 = 0; // Counter for ball 1 ground collisions
    private int ballRadius = 100; // Counter for ball 1 ground collisions
    private int groundCollisionCount2 = 0; // Counter for ball 2 ground collisions
    private long lastCollisionTime1 = 0; // Time stamp of last collision for ball 1
    private long lastCollisionTime2 = 0; // Time stamp of last collision for ball 2
    private static final int DELAY = 10; // Delay between animation frames in milliseconds
    private static final float REST_THRESHOLD = 0.5f;
    private Paint paint;
    private Paint paint1;
    private float x, y; // Position of the object
    private float x2, y2; // Position of the object
    private float vx, vy; // Velocity components
    private float vx2, vy2; // Velocity components
    private float angle; // Angle of projection
    private float gravity; // Acceleration due to gravity
    private RectF resetButton; // Reset button

    public AnimationView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        paint = new Paint();
        paint.setColor(Color.RED);
        paint.setStyle(Paint.Style.FILL);
        paint1 = new Paint();
        paint1.setColor(Color.BLUE);
        paint1.setStyle(Paint.Style.FILL);

        // Initialize the first ball (moving from left to right)
        x = 160; // Initial x position
        y = getHeight() / 2 + 200; // Initial y position (center of the screen)
        vx = 10; // Initial x velocity (moving to the right)
        vy = 10; // Initial y velocity (moving downward)

        // Initialize the second ball (moving from right to left)
        x2 = 200; // Initial x position (right edge of the screen)
        y2 = getHeight() / 2 + 150; // Initial y position (center of the screen)
        vx2 = 10; // Initial x velocity (moving to the left)
        vy2 = -10; // Initial y velocity (moving downward)

        gravity = 1.0f; // Increase the gravitational acceleration

        // Define reset button
        resetButton = new RectF(getWidth() * 0.8f, getHeight() * 0.8f, getWidth() * 0.9f, getHeight() * 0.9f);
    }

    public void startAnimation() {
        post(new Runnable() {
            @Override
            public void run() {
                updatePosition();
                invalidate();
                postDelayed(this, DELAY);
            }
        });
    }

    private void updatePosition() {
        // Update velocity components for the first ball
        vx = vx; // x velocity remains constant
        vy += gravity; // Increase y velocity due to gravity

        // Update position for the first ball
        x += vx;
        y += vy;

        // Log the position of the first ball
        Log.d("Ball1", "x: " + x + ", y: " + y);

        // Check for collision with screen boundaries for the first ball
        if (x <= ballRadius || x >= getWidth() - ballRadius) {
            // Reverse x velocity to simulate bounce off the left or right edge
            vx = -vx;
        }
        if (y >= getHeight() - ballRadius) {
            // Reverse y velocity to simulate bounce off the bottom edge
            vy = -vy;
            // Increment ground collision count and check if it's reached four
            if (System.currentTimeMillis() - lastCollisionTime1 < 250) {
                groundCollisionCount1++;
                if (groundCollisionCount1 >= 4) {
                    y = getHeight() - ballRadius; // Set the ball on the ground
                }
            } else {
                groundCollisionCount1 = 1;
            }
            lastCollisionTime1 = System.currentTimeMillis();
        }

        // Check for collision with the ceiling for the first ball
        if (y <= ballRadius) {
            // Reverse y velocity to simulate bounce off the ceiling
            vy = -vy;
        }

        // Update velocity components for the second ball
        vx2 = vx2; // x velocity remains constant
        vy2 += gravity; // Increase y velocity due to gravity

        // Update position for the second ball
        x2 += vx2;
        y2 += vy2;

        // Log the position of the second ball
        Log.d("Ball2", "x2: " + x2 + ", y2: " + y2);

        // Check for collision with screen boundaries for the second ball
        if (x2 <= ballRadius || x2 >= getWidth() - ballRadius) {
            // Reverse x velocity to simulate bounce off the left or right edge
            vx2 = -vx2;
        }
        if (y2 >= getHeight() - ballRadius) {
            // Reverse y velocity to simulate bounce off the bottom edge
            vy2 = -vy2;
            // Increment ground collision count and check if it's reached four
            if (System.currentTimeMillis() - lastCollisionTime2 < 250) {
                groundCollisionCount2++;
                if (groundCollisionCount2 >= 4) {
                    y2 = getHeight() - ballRadius; // Set the ball on the ground
                }
            } else {
                groundCollisionCount2 = 1;
            }
            lastCollisionTime2 = System.currentTimeMillis();
        }

        // Check for collision with the ceiling for the second ball
        if (y2 <= ballRadius) {
            // Reverse y velocity to simulate bounce off the ceiling
            vy2 = -vy2;
        }

        // Check for collision between the balls
        float dx = x - x2;
        float dy = y - y2;
        float distanceSquared = dx * dx + dy * dy;
        float radiusSquared = (2 * ballRadius) * (2 * ballRadius); // Sum of radii squared, with the increased radius
        if (distanceSquared <= radiusSquared) {
            // Balls are colliding

            // Calculate unit normal vector
            float length = (float) Math.sqrt(distanceSquared);
            float nx = dx / length;
            float ny = dy / length;

            // Calculate relative velocity
            float rvx = vx - vx2;
            float rvy = vy - vy2;

            // Calculate relative velocity in terms of the normal direction
            float dotProduct = rvx * nx + rvy * ny;

            // If dotProduct is positive, balls are moving away from each other
            if (dotProduct > 0) {
                return;
            }

            // Calculate impulse
            float impulse = dotProduct / (1 + 1);

            // Apply impulse to first ball
            vx -= impulse * 1 * nx;
            vy -= impulse * 1 * ny;

            // Apply impulse to second ball
            vx2 += impulse * 1 * nx;
            vy2 += impulse * 1 * ny;
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        float ballRadius = 100; // New radius for the balls

        // Draw the first ball (red)
        canvas.drawCircle(x, y, ballRadius, paint);

        // Draw the second ball (blue)
        canvas.drawCircle(x2, y2, ballRadius, paint1);

        // Draw reset button
        Paint buttonPaint = new Paint();
        buttonPaint.setColor(Color.GREEN); // Example color
        canvas.drawRect(resetButton, buttonPaint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float xTouch = event.getX();
        float yTouch = event.getY();

        // Check if the touch event occurred within the reset button
        if (resetButton.contains(xTouch, yTouch)) {
            reset(); // Call the reset method
            return true; // Event handled
        }

        return super.onTouchEvent(event);
    }

    private void reset() {
        // Reset positions of the balls to their initial positions
        x = 160;
        y = getHeight() / 2 + 200;
        vx = 10;
        vy = 10;

        x2 = 200;
        y2 = getHeight() / 2 + 150;
        vx2 = 10;
        vy2 = -10;
    }

    public void resetAnimation() {
        // Reset positions of the balls to their initial positions
        x = 160;
        y = getHeight() / 2 + 200;
        x2 = 200;
        y2 = getHeight() / 2 + 150;

        // Reset velocities if needed
        vx = 10;
        vy = 10;
        vx2 = 10;
        vy2 = -10;

        // Reset any other variables if needed

        // Request a redraw to update the view
        invalidate();
    }

    // Method to set the positions of the balls
    public void setBallPositions(int randomX1, int randomY1, int randomX2, int randomY2) {
        // Update the positions of the first ball
        x = randomX1;
        y = randomY1;

        // Update the positions of the second ball
        x2 = randomX2;
        y2 = randomY2;

        // Request a redraw to reflect the new positions
        invalidate();
    }

    public void setBallVelocities(float vx1, float vy1, float vx2, float vy2) {
        // Set the initial velocities of the first ball
        vx = vx1;
        vy = vy1;

        // Set the initial velocities of the second ball
        vx2 = vx2;
        vy2 = vy2;
    }

}
