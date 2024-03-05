package com.example.my_first_app;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.my_first_app.R;

public class MainActivity extends AppCompatActivity {

    EditText firstValue;
    EditText secondApprox;
    TextView computeResult;
    Button btnCompute;
    double approx, sum;
    String value;
    double prev_term;
    double curr_term;
    int n, i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firstValue = findViewById(R.id.txtValue);
        secondApprox = findViewById(R.id.txtApprox);
        computeResult = findViewById(R.id.txtResult);
        btnCompute = findViewById(R.id.btnCompute);

        btnCompute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                value = firstValue.getText().toString();
                approx = Double.parseDouble(secondApprox.getText().toString());

                if(value.equals("pi")) {
                    double prev_term = 1;
                    double curr_term = -1.0/3;
                    sum = prev_term + curr_term;
                    i=2;
                    int sign=-1;
                    while (Math.abs(prev_term) - Math.abs(curr_term) > approx) {
                        prev_term = curr_term;
                        i++;
                        sign *= (-1);
                        curr_term = sign * 1.0 / (2*i-1);
                        sum += curr_term;
                    }
                    sum = 4.0 * sum;
                    computeResult.setText(Double.toString(sum));
                } else {
                    computeResult.setText("Incorrect string for Value");
                }
            }
        });
    }
}
