package com.example.animation;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class MainActivity extends AppCompatActivity {

    private CustomSurfaceView customSurfaceView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SurfaceView surfaceView = findViewById(R.id.surfaceView);
        customSurfaceView = new CustomSurfaceView(this, surfaceView);
        surfaceView.getHolder().addCallback(customSurfaceView);
    }
}
