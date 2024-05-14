package com.example.triunghi;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

public class RampView extends View {

    public RampView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // Draw the blue triangle
        drawTriangle(canvas);
    }

    private void drawTriangle(Canvas canvas) {
        Paint paint = new Paint();
        paint.setColor(Color.BLUE);
        paint.setStyle(Paint.Style.FILL);

        // Define the triangle points
        Path path = new Path();
        path.moveTo(0, getHeight()); // Bottom left corner
        path.lineTo(getWidth(), getHeight()); // Bottom right corner
        path.lineTo(getWidth() / 2, 0); // Top center
        path.close(); // Close the shape

        canvas.drawPath(path, paint);
    }
}
