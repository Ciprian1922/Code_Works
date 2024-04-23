package com.example.infinity;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.infinity.R;

public class MainActivity extends AppCompatActivity {
    private ImageView square;
    private int squareX, squareY;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        square = findViewById(R.id.square);

        // Initial position of the square
        squareX = 100;
        squareY = 100;

        // Set the initial position of the square
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(200, 200);
        layoutParams.leftMargin = squareX;
        layoutParams.topMargin = squareY;
        square.setLayoutParams(layoutParams);

        // Listen for arrow key presses to move the square
        square.setFocusable(true);
        square.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                int step = 10; // Adjust the step size as needed
                switch (keyCode) {
                    case KeyEvent.KEYCODE_DPAD_UP:
                        squareY -= step;
                        break;
                    case KeyEvent.KEYCODE_DPAD_DOWN:
                        squareY += step;
                        break;
                    case KeyEvent.KEYCODE_DPAD_LEFT:
                        squareX -= step;
                        break;
                    case KeyEvent.KEYCODE_DPAD_RIGHT:
                        squareX += step;
                        break;
                }

                // Update the position of the square
                RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(200, 200);
                layoutParams.leftMargin = squareX;
                layoutParams.topMargin = squareY;
                square.setLayoutParams(layoutParams);

                return true;
            }
        });
    }
}
