package com.example.triunghi;

import androidx.appcompat.app.AppCompatActivity;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new MyView(this));
    }

    public class MyView extends View {
        Paint paint = new Paint();

        public MyView(MainActivity context) {
            super(context);
            setBackgroundColor(Color.WHITE); // Add a background color to MyView
            paint.setColor(Color.BLACK);
            paint.setStrokeWidth(3);
            paint.setStyle(Paint.Style.FILL);
        }


        @Override
        protected void onDraw(Canvas canvas) {
//            super.onDraw(canvas);
//            int width = getWidth();
//            int height = getHeight();
//
//            // Define triangle coordinates
//            float x1 = 0;
//            float y1 = height;
//            float x2 = 0;
//            float y2 = height/2+700;
//            float x3 = width/2;
//            float y3 = height;
//
//            // Draw triangle
//            canvas.drawPath(createTriangle(x1, y1, x2, y2, x3, y3), paint);
//
//
//
//
//
//
//            // Define coordinates for the mirrored triangle
//            float x4 = width;
//            float y4 = height;
//            float x5 = width;
//            float y5 = height / 2 + 700;
//            float x6 = width / 2;
//            float y6 = height;
//
//            // Draw the mirrored triangle
//            canvas.drawPath(createTriangle(x4, y4, x5, y5, x6, y6), paint);
//
//            // Define curved hypothesis path for the mirrored triangle
//            android.graphics.Path curvedPath = new android.graphics.Path();
//            curvedPath.moveTo(x6, y6);
//            float controlX1 = x6 + (x5 - x6) / 4; // Adjust control point's x-coordinate
//            float controlY1 = y6 - 100; // Adjust control point's y-coordinate
//            float controlX2 = x6 + (x5 - x6) * 3 / 4; // Adjust control point's x-coordinate
//            float controlY2 = y6 - 100; // Adjust control point's y-coordinate
//            float endX = x5-400;
//            float endY = y5+100;
//            curvedPath.cubicTo(controlX1, controlY1, controlX2, controlY2, endX, endY); // Add curve to the path
//
//            // Draw curved hypothesis for the mirrored triangle
//            paint.setColor(Color.BLUE); // Change color for the curved hypothesis
//            canvas.drawPath(curvedPath, paint);
            super.onDraw(canvas);
            int width = getWidth();
            int height = getHeight();

            // Define the dimensions of the ramp
            float startX = 0;
            float startY = height; // Start from the bottom
            float endX = width * 0.4f; // End at 40% of the width
            float endY = height * 0.85f; // End at 85% of the height
            float controlX = width * 0.2f; // Control point at 20% of the width

            // Draw the ramp
            android.graphics.Path rampPath = new android.graphics.Path();
            rampPath.moveTo(startX, startY);
            rampPath.lineTo(startX, startY - height * 0.3f); // Draw a straight line up
            rampPath.quadTo(controlX, height, endX, endY); // Quadratic curve to the end point
            rampPath.lineTo(endX, startY); // Draw a straight line to the bottom

            // Draw the ramp
            paint.setColor(Color.BLUE); // Change color for the ramp
            canvas.drawPath(rampPath, paint);
        }






        // Method to create a triangle path
        private android.graphics.Path createTriangle(float x1, float y1, float x2, float y2, float x3, float y3) {
            android.graphics.Path path = new android.graphics.Path();
            path.moveTo(x1, y1);
            path.lineTo(x2, y2);
            path.lineTo(x3, y3);
            path.lineTo(x1, y1);
            return path;
        }
    }
}
