package com.example.one2manyactivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

public class FirstActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);

        // Initialize views
        EditText approximationEditText = findViewById(R.id.approximationEditText);
        Button computeButton = findViewById(R.id.computeButton);
        Button backButton = findViewById(R.id.backButton);

        // Handle compute button click
        computeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get approximation from the input field
                String approximationStr = approximationEditText.getText().toString();
                double approximation = Double.parseDouble(approximationStr);

                // Calculate Pi based on the approximation
                double pi = computePi(approximation);

                // Pass the calculated Pi back to MainActivity
                Intent intent = new Intent();
                intent.putExtra("pi", pi);
                setResult(RESULT_OK, intent);
                finish(); // Close FirstActivity and return to MainActivity
            }
        });

        // Handle back button click
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); // Close FirstActivity and return to MainActivity
            }
        });
    }

    // Method to compute Pi using the series expansion method with the given approximation
    private double computePi(double approximation) {
        double prevTerm = 1;
        double currTerm = -1.0 / 3;
        double sum = prevTerm + currTerm;
        int i = 2;
        int sign = -1;

        while (Math.abs(prevTerm) - Math.abs(currTerm) > approximation) {
            prevTerm = currTerm;
            i++;
            sign *= (-1);
            currTerm = sign * 1.0 / (2 * i - 1);
            sum += currTerm;
        }
        return 4.0 * sum;
    }
}
