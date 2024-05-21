package com.example.mypiano;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private PianoView pianoView;
    private Button buttonRecord, buttonStop, buttonPlay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        pianoView = findViewById(R.id.pianoView);
        buttonRecord = findViewById(R.id.buttonRecord);
        buttonStop = findViewById(R.id.buttonStop);
        buttonPlay = findViewById(R.id.buttonPlay);

        buttonRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pianoView.startRecording();
            }
        });

        buttonStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pianoView.stopRecording();
            }
        });

        buttonPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pianoView.playRecordedSounds();
            }
        });
    }
}
