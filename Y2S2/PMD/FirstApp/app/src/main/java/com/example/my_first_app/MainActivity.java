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
                    // Compute approximation of pi
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
                } else if (value.equals("e")) {
                    // Compute Euler's number (e) using the approximation
                    double epsilon = approx; // User-provided approximation
                    double e = 1; // Initialize e
                    double factorial = 1; // Initialize factorial
                    double term = 1; // Initialize term
                    int n = 1; // Initialize n
                    do {
                        factorial *= n; // Update factorial
                        term = 1.0 / factorial; // Update term
                        e += term; // Update e
                        n++; // Increment n
                    } while (term > epsilon); // Continue until the term is smaller than the approximation
                    computeResult.setText(Double.toString(e));
                } else if (value.equals("golden_ratio")) {
                    // Compute the Golden Ratio (phi) with approximation
                    double epsilon = approx; // Approximation threshold
                    double phi = 1 + epsilon; // Initialize phi with a value slightly larger than 1
                    double previousPhi;
                    do {
                        previousPhi = phi;
                        phi = 1 + 1 / phi; // Recursively compute phi
                    } while (Math.abs(phi - previousPhi) > epsilon); // Stop when the difference is smaller than the approximation threshold
                    computeResult.setText(Double.toString(phi));
                }else {
                    computeResult.setText("Incorrect string for Value");
                }
            }
        });

    }
}
