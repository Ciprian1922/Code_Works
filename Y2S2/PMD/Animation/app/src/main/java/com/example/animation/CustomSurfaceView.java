package com.example.animation;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class CustomSurfaceView implements SurfaceHolder.Callback, Runnable {

    private SurfaceView surfaceView;
    private SurfaceHolder surfaceHolder;
    private Thread thread;
    private boolean isRunning = false;

    private float objectX, objectY;
    private float scaleFactor = 1.0f;

    public CustomSurfaceView(Context context, SurfaceView surfaceView) {
        this.surfaceView = surfaceView;
        surfaceHolder = surfaceView.getHolder();
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        objectX = surfaceView.getWidth() / 2f;
        objectY = surfaceView.getHeight() / 2f;
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
        paint.setColor(Color.BLUE);

        // Example: Drawing a rectangle representing the object
        float objectWidth = 100 * scaleFactor;
        float objectHeight = 100 * scaleFactor;

        // Adjust object position to simulate coming closer
        float distanceFromViewer = 500; // Initial distance from viewer
        float distanceStep = 2000; // Increase this value to make the animation faster

        distanceFromViewer -= distanceStep;

        float left = (canvas.getWidth() - objectWidth) / 2;
        float top = (canvas.getHeight() - objectHeight) / 2;
        float right = left + objectWidth;
        float bottom = top + objectHeight;
        canvas.drawRect(left, top, right, bottom, paint);

        // Update object scale
        scaleFactor += 0.1; // Simulate object zooming in
    }


}
