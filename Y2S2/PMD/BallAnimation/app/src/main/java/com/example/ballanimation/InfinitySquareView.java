package com.example.ballanimation;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class InfinitySquareView extends View {
    private Paint squarePaint;
    private int squareSize = 200; // Initial size of the square
    private int squareX, squareY; // Position of the square's top-left corner
    private int viewWidth, viewHeight; // Width and height of the view

    public InfinitySquareView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        squarePaint = new Paint();
        squarePaint.setColor(Color.BLUE);
        squarePaint.setStyle(Paint.Style.FILL);

        // Initialize the square's position to the center of the screen
        squareX = (getWidth() - squareSize) / 2;
        squareY = (getHeight() - squareSize) / 2;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        viewWidth = w;
        viewHeight = h;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // Draw the square
        canvas.drawRect(squareX, squareY, squareX + squareSize, squareY + squareSize, squarePaint);

        // Draw the preview of the square in an infinite pattern
        drawInfinityPattern(canvas);
    }

    private void drawInfinityPattern(Canvas canvas) {
        int offsetX = (squareX + squareSize) % viewWidth;
        int offsetY = (squareY + squareSize) % viewHeight;

        // Draw the preview squares horizontally and vertically
        for (int x = -squareSize - offsetX; x < viewWidth; x += squareSize) {
            for (int y = -squareSize - offsetY; y < viewHeight; y += squareSize) {
                canvas.drawRect(x, y, x + squareSize, y + squareSize, squarePaint);
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getActionMasked();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_POINTER_DOWN: // Handle multi-touch
                // Update the position of the square to the touch position
                squareX = (int) event.getX() - squareSize / 2;
                squareY = (int) event.getY() - squareSize / 2;
                invalidate();
                return true;
            default:
                return super.onTouchEvent(event);
        }
    }
}
