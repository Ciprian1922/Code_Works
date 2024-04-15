package com.example.one2manyactivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        // Get the result
        int correctAnswers = getIntent().getIntExtra("correctAnswersCount", 0); // Update key here
        int totalQuestions = 5; // Assuming there are 5 questions

        // Calculate percentage of correct answers
        int percentage = (int) (((double) correctAnswers / totalQuestions) * 100);

        // Display the result
        TextView resultTextView = findViewById(R.id.resultTextView);
        resultTextView.setText(getString(R.string.result_message, correctAnswers, totalQuestions, percentage));

        // Button to return to home
        Button homeButton = findViewById(R.id.homeButton);
        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); // Close ResultActivity and return to MainActivity
            }
        });
    }

}
