package com.example.animation;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class CustomSurfaceView implements SurfaceHolder.Callback, Runnable {

    private SurfaceView surfaceView;
    private SurfaceHolder surfaceHolder;
    private Thread thread;
    private boolean isRunning = false;

    private float pyramidY;
    private float cubeY;
    private float scaleFactor = 1.0f;

    public CustomSurfaceView(Context context, SurfaceView surfaceView) {
        this.surfaceView = surfaceView;
        surfaceHolder = surfaceView.getHolder();
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        pyramidY = surfaceView.getHeight() / 2f;
        cubeY = surfaceView.getHeight() / 2f;
        startDrawing();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        stopDrawing();
    }

    private void startDrawing() {
        isRunning = true;
        thread = new Thread(this);
        thread.start();
    }

    private void stopDrawing() {
        isRunning = false;
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        while (isRunning) {
            long startTime = System.currentTimeMillis();

            Canvas canvas = surfaceHolder.lockCanvas();
            if (canvas != null) {
                draw(canvas);
                surfaceHolder.unlockCanvasAndPost(canvas);
            }

            long timeElapsed = System.currentTimeMillis() - startTime;
            long sleepTime = 1000 / 7 - timeElapsed;
            if (sleepTime > 0) {
                try {
                    Thread.sleep(sleepTime);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void draw(Canvas canvas) {
        canvas.drawColor(Color.WHITE);
        Paint paint = new Paint();

        // Draw pyramid
        paint.setColor(Color.RED);
        Path pyramidPath = new Path();
        float pyramidBase = 200 * scaleFactor;
        float pyramidHeight = 200 * scaleFactor;
        float halfBase = pyramidBase / 2;
        pyramidPath.moveTo(canvas.getWidth() / 2 - halfBase, pyramidY + pyramidHeight / 2);
        pyramidPath.lineTo(canvas.getWidth() / 2, pyramidY - pyramidHeight / 2);
        pyramidPath.lineTo(canvas.getWidth() / 2 + halfBase, pyramidY + pyramidHeight / 2);
        pyramidPath.lineTo(canvas.getWidth() / 2 - halfBase, pyramidY + pyramidHeight / 2); // Closing the triangle
        canvas.drawPath(pyramidPath, paint);

        // Update pyramid position and scale (move towards the viewer with zoom effect)
        pyramidY -= 2; // Adjust speed
        scaleFactor += 0.2f; // Adjust zoom speed

        // Draw cube
        paint.setColor(Color.BLUE);
        float cubeSize = 200;
        canvas.drawRect(canvas.getWidth() / 2 - cubeSize / 2, cubeY - cubeSize / 2,
                canvas.getWidth() / 2 + cubeSize / 2, cubeY + cubeSize / 2, paint);

        // Update cube position (come after the pyramid)
        cubeY -= 2; // Adjust speed
    }
}
