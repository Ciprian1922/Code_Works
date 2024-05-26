package com.example.one2manyactivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class BinaryTreeActivity extends AppCompatActivity {

    private BinaryTree binaryTree;
    private BinaryTreeView binaryTreeView;
    private EditText valueEditText;

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

        setContentView(R.layout.activity_binary_tree);

        binaryTree = new BinaryTree();
        binaryTreeView = findViewById(R.id.binaryTreeView);
        valueEditText = findViewById(R.id.valueEditText);

        Button addValueBtn = findViewById(R.id.addValueBtn);
        Button undoBtn = findViewById(R.id.undoBtn);
        Button backBtn = findViewById(R.id.backBtn);

        addValueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String valueText = valueEditText.getText().toString();
                if (!valueText.isEmpty()) {
                    int value = Integer.parseInt(valueText);
                    binaryTree.add(value);
                    binaryTreeView.setBinaryTree(binaryTree);
                    valueEditText.setText(""); // Clear the text box
                } else {
                    Toast.makeText(BinaryTreeActivity.this, "Please enter a value", Toast.LENGTH_SHORT).show();
                }
            }
        });

        undoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (binaryTree.removeLastAdded()) {
                    binaryTreeView.setBinaryTree(binaryTree);
                } else {
                    Toast.makeText(BinaryTreeActivity.this, "No values to undo", Toast.LENGTH_SHORT).show();
                }
            }
        });

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BinaryTreeActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
