package com.example.one2manyactivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private TextView piTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button goToFirstActivityBtn = findViewById(R.id.goToFirstActivityBtn);
        Button goToSecondActivityBtn = findViewById(R.id.goToSecondActivityBtn);
        piTextView = findViewById(R.id.piTextView);

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
                startActivity(intent);
            }
        });
    }

    // Method to handle the result returned from FirstActivity
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK && data != null) {
            double piValue = data.getDoubleExtra("pi", 0.0);
            piTextView.setText("Value of Pi: " + piValue);
        }
    }
}
