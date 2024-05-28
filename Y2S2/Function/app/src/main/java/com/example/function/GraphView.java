package com.example.function;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

public class GraphView extends View {
    private float a = 1;
    private float b = 0;
    private Paint paint;
    private Paint textPaint;

    public GraphView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        paint = new Paint();
        paint.setColor(Color.BLUE);
        paint.setStrokeWidth(5);

        textPaint = new Paint();
        textPaint.setColor(Color.RED);
        textPaint.setTextSize(30);
    }

    public void setParameters(float a, float b) {
        this.a = a;
        this.b = b;
        invalidate(); // Redraw the view
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        float width = getWidth();
        float height = getHeight();
        float centerX = width / 2;
        float centerY = height / 2;

        // Draw axes
        paint.setColor(Color.BLACK);
        canvas.drawLine(0, centerY, width, centerY, paint);
        canvas.drawLine(centerX, 0, centerX, height, paint);

        // Draw the function y = ax + b
        paint.setColor(Color.BLUE);
        float startX = 0;
        float startY = centerY - (a * (startX - centerX)) - b;
        float endX = width;
        float endY = centerY - (a * (endX - centerX)) - b;

        canvas.drawLine(startX, startY, endX, endY, paint);

        // Draw x and y values below the graph
        drawValues(canvas, centerX, centerY);
    }

    private void drawValues(Canvas canvas, float centerX, float centerY) {
        float exampleX = 10; // Example x value for demonstration (can be any value)
        float calculatedY = a * exampleX + b;

        // Convert to canvas coordinates
        float canvasX = centerX + exampleX;
        float canvasY = centerY - calculatedY;

        // Adjust position to draw text at the bottom of the canvas
        float textX = 20; // Fixed position near the bottom left
        float textY = getHeight() - 50; // Place near the bottom

        // Draw x and y values
        canvas.drawText("x = " + exampleX, textX, textY - 30, textPaint);
        canvas.drawText("y = " + calculatedY, textX, textY, textPaint);
    }
}
