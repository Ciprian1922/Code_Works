package com.example.moon;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class MoonSurfaceView extends SurfaceView implements SurfaceHolder.Callback, Runnable {

    private SurfaceHolder surfaceHolder;
    private Thread thread;
    private boolean isRunning = false;

    private float earthX, earthY;
    private float moonX, moonY;
    private float angle = 0;
    private float radius = 200;

    public MoonSurfaceView(Context context) {
        super(context);
        surfaceHolder = getHolder();
        surfaceHolder.addCallback(this);
    }

    public MoonSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        surfaceHolder = getHolder();
        surfaceHolder.addCallback(this);
    }

    public MoonSurfaceView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        surfaceHolder = getHolder();
        surfaceHolder.addCallback(this);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        earthX = getWidth() / 2f;
        earthY = getHeight() / 2f;
        moonX = earthX + radius;
        moonY = earthY;
        startAnimation();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        stopAnimation();
    }

    private void startAnimation() {
        isRunning = true;
        thread = new Thread(this);
        thread.start();
    }

    private void stopAnimation() {
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
            update();
            draw();
            try {
                Thread.sleep(16); // ~60 fps
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void update() {
        angle += 1; // Increase angle for rotation
        float radians = (float) Math.toRadians(angle);
        moonX = earthX + radius * (float) Math.cos(radians);
        moonY = earthY + radius * (float) Math.sin(radians);
    }

    private void draw() {
        Canvas canvas = surfaceHolder.lockCanvas();
        if (canvas != null) {
            try {
                // Draw background
                canvas.drawColor(Color.GRAY);

                // Draw Earth
                Paint paint = new Paint();
                paint.setColor(Color.BLUE);
                canvas.drawCircle(earthX, earthY, 70, paint);

                // Draw Moon
                paint.setColor(Color.WHITE);
                canvas.drawCircle(moonX, moonY, 20, paint);
            } finally {
                surfaceHolder.unlockCanvasAndPost(canvas);
            }
        }
    }

    public void setMoonDistance(int distance) {
        // Adjust the radius based on the provided distance
        radius = distance;
    }

}
