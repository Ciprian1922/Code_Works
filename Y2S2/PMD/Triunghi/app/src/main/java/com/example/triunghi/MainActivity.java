package com.example.triunghi;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    static final float RAMP_HEIGHT_ABSOLUTE = 2200; // Example value, adjust as needed

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new MyView(this));
    }

    public class MyView extends View {
        Paint paint = new Paint();
        Ball ball;
        boolean onRamp = false;

        public MyView(MainActivity context) {
            super(context);
            setBackgroundColor(Color.WHITE); // Add a background color to MyView
            paint.setColor(Color.BLACK);
            paint.setStrokeWidth(3);
            paint.setStyle(Paint.Style.FILL);
            initializeBall(); // Initialize the ball
        }

        private void initializeBall() {
            // Initialize the ball
            float ballRadius = 20; // Radius of the ball
            int ballColor = Color.RED; // Color of the ball
            float ballStartX = getWidth() / 2 + 40; // Start at the center horizontally
            float ballStartY = 100; // Start at the top vertically
            ball = new Ball(ballStartX, ballStartY, ballRadius, ballColor, RAMP_HEIGHT_ABSOLUTE);
        }

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);

            // Draw the ramp
            drawRamp(canvas);

            // Update and draw the ball
            if (ball != null) {
                if (!onRamp) {
                    ball.update();
                } else {
                    // Sliding on the ramp
                    ball.setVelocityY(10); // Adjust the sliding speed as needed
                    ball.setX(calculateSlidingPositionX(ball.getX(), ball.getY()));
                    ball.setY(calculateSlidingPositionY(ball.getX(), ball.getY()));
                }
                ball.draw(canvas);

                // Check collision with the triangle
                boolean collision = checkTriangleCollision(ball.getX(), ball.getY(), ball.getRadius());
                if (collision) {
                    // Handle collision here, for example:
                    ball.setVelocityY(0); // Stop the ball
                    onRamp = true; // Set flag to indicate that the ball is on the ramp
                }

                invalidate(); // Redraw the view to continue the animation
            }
        }

        private void drawRamp(Canvas canvas) {
            Paint paint = new Paint();
            paint.setColor(Color.BLUE);
            paint.setStyle(Paint.Style.FILL);

            // Define the triangle points
            Path path = new Path();
            path.moveTo(0, getHeight()); // Bottom left corner
            path.lineTo(0, getHeight() / 2f); // Top left center
            path.lineTo(getWidth(), getHeight()); // Bottom right corner
            path.close(); // Close the shape

            canvas.drawPath(path, paint);
        }

        // Method to check collision between ball and triangle
        private boolean checkTriangleCollision(float ballX, float ballY, float radius) {
            // Define the triangle points
            float[] trianglePoints = {0, getHeight(), 0, getHeight() / 2f, getWidth(), getHeight()};

            // Check collision with each line segment of the triangle
            for (int i = 0; i < trianglePoints.length - 2; i += 2) {
                float startX = trianglePoints[i];
                float startY = trianglePoints[i + 1];
                float endX = trianglePoints[i + 2];
                float endY = trianglePoints[i + 3];

                // Calculate distance between ball center and line segment
                float distance = distanceToLineSegment(ballX, ballY, startX, startY, endX, endY);

                // Check if distance is less than the radius of the ball
                if (distance < radius) {
                    return true; // Collision detected
                }
            }
            return false; // No collision detected
        }

        // Helper method to calculate distance between a point and a line segment
        private float distanceToLineSegment(float px, float py, float x1, float y1, float x2, float y2) {
            float dx = x2 - x1;
            float dy = y2 - y1;
            float lenSquared = dx * dx + dy * dy;
            float r = ((px - x1) * dx + (py - y1) * dy) / lenSquared;
            if (r <= 0) {
                return (float) Math.sqrt((px - x1) * (px - x1) + (py - y1) * (py - y1));
            }
            if (r >= 1) {
                return (float) Math.sqrt((px - x2) * (px - x2) + (py - y2) * (py - y2));
            }
            float s = ((y1 - py) * dx - (x1 - px) * dy) / lenSquared;
            return Math.abs(s) * (float) Math.sqrt(lenSquared);
        }

        // Method to calculate new X position after sliding on the triangle
        private float calculateSlidingPositionX(float x, float y) {
            // Assuming the ball maintains its current X position while sliding on the triangle
            return x;
        }

        // Method to calculate new Y position after sliding on the triangle
// Method to calculate new Y position after sliding on the triangle
        private float calculateSlidingPositionY(float x, float y) {
            // Calculate the height of the triangle at the current X position of the ball
            float triangleHeight = getHeight() - (getHeight() / 2f); // Height of the triangle from top to base

            // Calculate the slope of the line joining the top-left and bottom-left corners
            float slope = triangleHeight / (getWidth() - 0);

            // Find the nearest point on the triangle to the ball's current position
            float nearestX = x; // Assuming the ball maintains its current X position while sliding on the triangle
            float nearestY = slope * (x - 0); // Calculate the corresponding Y position on the triangle

            return nearestY;
        }

    }
}
