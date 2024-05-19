package com.example.one2manyactivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

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
        Button openTheoryBtn = findViewById(R.id.openTheoryBtn);

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

        openTheoryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openPDF();
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

    private void openPDF() {
        try {
            // Copy PDF file from res/raw to a cache directory
            File cacheFile = new File(getCacheDir(), "theory.pdf");
            if (!cacheFile.exists()) {
                try (InputStream is = getResources().openRawResource(R.raw.theory);
                     FileOutputStream fos = new FileOutputStream(cacheFile)) {
                    byte[] buffer = new byte[1024];
                    int length;
                    while ((length = is.read(buffer)) > 0) {
                        fos.write(buffer, 0, length);
                    }
                }
            }

            // Open the PDF file with an appropriate app
            Uri pdfUri = FileProvider.getUriForFile(this, getPackageName() + ".provider", cacheFile);
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setDataAndType(pdfUri, "application/pdf");
            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_ACTIVITY_NO_HISTORY);
            startActivity(intent);
        } catch (IOException e) {
            e.printStackTrace();
            Log.e("MainActivity", "Error opening PDF", e);
        }
    }
}
