package com.example.function;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private EditText inputA;
    private EditText inputB;
    private Button plotButton;
    private TextView resultText;
    private GraphView graphView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        inputA = findViewById(R.id.input_a);
        inputB = findViewById(R.id.input_b);
        plotButton = findViewById(R.id.plot_button);
        resultText = findViewById(R.id.result_text);
        graphView = findViewById(R.id.graph_view);

        plotButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                plotFunction();
            }
        });
    }

    private void plotFunction() {
        String aStr = inputA.getText().toString();
        String bStr = inputB.getText().toString();

        if (!aStr.isEmpty() && !bStr.isEmpty()) {
            float a = Float.parseFloat(aStr);
            float b = Float.parseFloat(bStr);

            graphView.setParameters(a, b);

            String result = "Function: y = " + a + "x + " + b;
            result += "\nSlope: " + a;
            resultText.setText(result);
        }
    }
}
