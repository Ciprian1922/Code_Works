package com.example.athrow;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.athrow.AnimationView;
import com.example.athrow.R;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private AnimationView animationView;
    private Button resetButton;
    private int ballRadius = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        animationView = findViewById(R.id.animationView);
        resetButton = findViewById(R.id.resetButton);

        // Set OnClickListener for the reset button
        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Generate random throwing positions
                Random random = new Random();
                int minX = ballRadius;  // Minimum x position
                int maxX = animationView.getWidth() - 2 * ballRadius; // Maximum x position
                int minY = ballRadius;  // Minimum y position
                int maxY = animationView.getHeight() - 2 * ballRadius; // Maximum y position

                // Randomly generate x and y positions for the first ball
                int randomX1 = random.nextInt(maxX - minX) + minX;
                int randomY1 = random.nextInt(maxY - minY) + minY;

                // Randomly generate x and y positions for the second ball
                int randomX2 = random.nextInt(maxX - minX) + minX;
                int randomY2 = random.nextInt(maxY - minY) + minY;

                // Set the new positions for the balls
                animationView.setBallPositions(randomX1, randomY1, randomX2, randomY2);

                // Generate random initial velocities for the balls
                float minVelocity = 5.0f;
                float maxVelocity = 20.0f;
                float randomAngle1 = (float) Math.toRadians(random.nextInt(360)); // Random angle in radians
                float randomAngle2 = (float) Math.toRadians(random.nextInt(360)); // Random angle in radians
                float randomSpeed1 = minVelocity + random.nextFloat() * (maxVelocity - minVelocity);
                float randomSpeed2 = minVelocity + random.nextFloat() * (maxVelocity - minVelocity);

                // Calculate initial velocities
                float vx1 = (float) (randomSpeed1 * Math.cos(randomAngle1));
                float vy1 = (float) (randomSpeed1 * Math.sin(randomAngle1));
                float vx2 = (float) (randomSpeed2 * Math.cos(randomAngle2));
                float vy2 = (float) (randomSpeed2 * Math.sin(randomAngle2));

                // Set the new velocities for the balls
                animationView.setBallVelocities(vx1, vy1, vx2, vy2);

                // Restart the animation
                //animationView.resetAnimation();
            }
        });

        // Start the animation
        animationView.startAnimation();
    }


}
