package com.example.moon;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private MoonSurfaceView moonSurfaceView;
    private EditText distanceEditText;
    private Button okButton;
    private RadioGroup distanceRadioGroup;
    private RadioButton radioButton2;
    private RadioButton radioButton4;
    private RadioButton radioButton5;
    private RadioButton radioButtonRandom;
    private SeekBar distanceSeekBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        moonSurfaceView = findViewById(R.id.surfaceView);
        distanceEditText = findViewById(R.id.distanceEditText);
        okButton = findViewById(R.id.okButton);
        distanceRadioGroup = findViewById(R.id.distanceRadioGroup);
        radioButton2 = findViewById(R.id.radioButton2);
        radioButton4 = findViewById(R.id.radioButton4);
        radioButton5 = findViewById(R.id.radioButton5);
        radioButtonRandom = findViewById(R.id.radioButtonRandom);
        distanceSeekBar = findViewById(R.id.distanceSeekBar);

        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateMoonDistance();
            }
        });

        distanceRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                handleRadioButtons(checkedId);
            }
        });

        distanceSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                // Update moon distance when SeekBar progress changes
                int distance = progress + 1; // Map progress from 0-5 to 1-6
                moonSurfaceView.setMoonDistance(distance * 50);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });
    }

    private void updateMoonDistance() {
        String input = distanceEditText.getText().toString();
        if (!input.isEmpty()) {
            int value = Integer.parseInt(input);
            if (value >= 1 && value <= 6) {
                moonSurfaceView.setMoonDistance(value * 50);
            } else {
                // Show error message or handle invalid input
            }
        } else {
            // Show error message or handle empty input
        }
    }

    private void handleRadioButtons(int checkedId) {
        if (checkedId == R.id.radioButton2) {
            moonSurfaceView.setMoonDistance(2 * 50);
        } else if (checkedId == R.id.radioButton4) {
            moonSurfaceView.setMoonDistance(4 * 50);
        } else if (checkedId == R.id.radioButton5) {
            moonSurfaceView.setMoonDistance(5 * 50);
        } else if (checkedId == R.id.radioButtonRandom) {
            Random random = new Random();
            int randomDistance = random.nextInt(6) + 1; // Generates a random number between 1 and 6
            moonSurfaceView.setMoonDistance(randomDistance * 50);
        }
    }
}
