package com.example.a4objects;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.FrameLayout;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private Handler handler;
    private MovingObjectView[] movingObjects;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        handler = new Handler();
        movingObjects = new MovingObjectView[4];
        createMovingObjects();
    }

    private void createMovingObjects() {
        int screenWidth = getWidth();
        int screenHeight = getHeight();

        movingObjects[0] = new MovingObjectView(this, 0, 0, 5, 5); // Top-left corner
        movingObjects[1] = new MovingObjectView(this, screenWidth, 0, -5, 5); // Top-right corner
        movingObjects[2] = new MovingObjectView(this, 0, screenHeight, 5, -5); // Bottom-left corner
        movingObjects[3] = new MovingObjectView(this, screenWidth, screenHeight, -5, -5); // Bottom-right corner

        FrameLayout container = findViewById(R.id.container);
        for (MovingObjectView object : movingObjects) {
            container.addView(object);
            animateObject(object);
        }
    }

    private int getWidth() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics.widthPixels;
    }

    private int getHeight() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics.heightPixels;
    }

    private void animateObject(final MovingObjectView object) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                object.updatePosition(getWidth(), getHeight()); // Call updatePosition here
                if (object.isVisible()) {
                    handler.postDelayed(this, 16); // ~60 fps
                } else {
                    ((FrameLayout) findViewById(R.id.container)).removeView(object);
                }
            }
        };
        handler.postDelayed(runnable, 16); // Start animation
    }

    private class MovingObjectView extends View {

        private float posX, posY;
        private float speedX, speedY;
        private boolean visible = true;

        public MovingObjectView(MainActivity context, float startX, float startY, float speedX, float speedY) {
            super(context);
            this.posX = startX;
            this.posY = startY;
            this.speedX = speedX;
            this.speedY = speedY;
        }

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);
            if (visible) {
                Paint paint = new Paint();
                paint.setColor(Color.RED); // Set the color of the moving object
                paint.setStyle(Paint.Style.FILL);
                canvas.drawCircle(posX, posY, 50, paint); // Draw a circle representing the moving object
            }
        }

        public void updatePosition(int screenWidth, int screenHeight) {
            posX += speedX;
            posY += speedY;

            // Check collision with walls
            if (posX <= 0 || posX >= screenWidth) {
                speedX = -speedX; // Reverse horizontal direction
            }
            if (posY <= 0 || posY >= screenHeight) {
                speedY = -speedY; // Reverse vertical direction
            }

            // Check collision with other objects
            for (MovingObjectView object : movingObjects) {
                if (object != this && object.isVisible()) {
                    float dx = posX - object.posX;
                    float dy = posY - object.posY;
                    float distanceSquared = dx * dx + dy * dy;
                    float radiusSquared = (50 + 50) * (50 + 50); // Radius of both objects is 50

                    if (distanceSquared < radiusSquared) {
                        // Collision detected, reverse direction for both objects
                        float tempSpeedX = speedX;
                        float tempSpeedY = speedY;
                        speedX = object.speedX;
                        speedY = object.speedY;
                        object.speedX = tempSpeedX;
                        object.speedY = tempSpeedY;
                        break; // No need to check further collisions
                    }
                }
            }

            invalidate();
        }

        public boolean isVisible() {
            return visible;
        }
    }
}
