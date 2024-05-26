package com.example.one2manyactivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.VideoView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class MainActivity extends AppCompatActivity {

    private TextView correctAnswersTextView;
    private int correctAnswer = 0; // Ensure correctAnswer is initialized
    private VideoView videoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Make the activity fullscreen
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);

        setContentView(R.layout.activity_main);

        // Initialize views
        correctAnswersTextView = findViewById(R.id.correctAnswersTextView);
        videoView = findViewById(R.id.videoView);

        // Set video background
        String videoPath = "android.resource://" + getPackageName() + "/" + R.raw.video1;
        videoView.setVideoURI(Uri.parse(videoPath));
        videoView.start();
        videoView.setOnCompletionListener(mediaPlayer -> videoView.start());

        // Stretch the VideoView to cover the screen
        videoView.post(new Runnable() {
            @Override
            public void run() {
                // Calculate the appropriate height for the VideoView to cover the entire screen vertically
                int screenWidth = getResources().getDisplayMetrics().widthPixels;
                int screenHeight = getResources().getDisplayMetrics().heightPixels;
                int videoHeight = screenWidth * 1920 / 1080; // Assuming the video has a 1080x1920 resolution
                if (videoHeight < screenHeight) {
                    // If the calculated height is less than the screen height, adjust the height accordingly
                    videoView.getLayoutParams().height = screenHeight;
                } else {
                    // Otherwise, use the calculated height
                    videoView.getLayoutParams().height = videoHeight;
                }
            }
        });

        // Initialize buttons
        Button computeBinaryTreeBtn = findViewById(R.id.computeBinaryTreeBtn);
        Button goToSecondActivityBtn = findViewById(R.id.goToSecondActivityBtn);
        Button openTheoryBtn = findViewById(R.id.openTheoryBtn);

        // Set click listeners
        computeBinaryTreeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, BinaryTreeActivity.class);
                startActivity(intent);
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
            if (requestCode == 2 && data != null) {
                // Receive correctAnswersCount from SecondActivity
                int correctAnswersCount = data.getIntExtra("correctAnswersCount", 0);
                // Update correctAnswer variable
                correctAnswer = correctAnswersCount;
                // Update UI
                Log.d("MainActivity", "Correct Answers received: " + correctAnswersCount);
                correctAnswersTextView.setText("Correct Answers: " + correctAnswersCount);
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!videoView.isPlaying()) {
            videoView.start();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (videoView.isPlaying()) {
            videoView.pause();
        }
    }

    private void openPDF() {
        try {
            // Copy PDF file from res/raw to a cache directory
            File cacheFile = new File(getCacheDir(), "theorys.pdf");
            if (!cacheFile.exists()) {
                try (InputStream is = getResources().openRawResource(R.raw.theorys);
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
