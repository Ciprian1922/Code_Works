package com.example.one2manyactivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private TextView piTextView;
    private TextView correctAnswersTextView;
    private int correctAnswer = 0; // Ensure correctAnswer is initialized

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize views
        piTextView = findViewById(R.id.piTextView);
        correctAnswersTextView = findViewById(R.id.correctAnswersTextView);

        Button goToFirstActivityBtn = findViewById(R.id.goToFirstActivityBtn);
        Button goToSecondActivityBtn = findViewById(R.id.goToSecondActivityBtn);

        goToFirstActivityBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, FirstActivity.class);
                startActivityForResult(intent, 1); // Start FirstActivity and wait for result
            }
        });

        goToSecondActivityBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SecondActivity.class);
                startActivityForResult(intent, 2); // Start SecondActivity and wait for result
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == 1) {
                // Handle result from FirstActivity if needed
            } else if (requestCode == 2 && data != null) {
                // Receive correctAnswersCount from SecondActivity
                int correctAnswersCount = data.getIntExtra("correctAnswersCount", 0);
                // Update correctAnswer variable
                correctAnswer = correctAnswersCount;
                // Update UI
                Log.d("MainActivity", "Correct Answers received: " + correctAnswersCount); // Add this log message
                correctAnswersTextView.setText("Correct Answers: " + correctAnswersCount);
            }
        }
    }

}

