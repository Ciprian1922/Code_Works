package com.example.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class FactorialFragment extends Fragment {

    EditText numberInput;
    TextView resultText;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_factorial, container, false);

        numberInput = view.findViewById(R.id.number_input);
        Button computeButton = view.findViewById(R.id.compute_button);
        resultText = view.findViewById(R.id.result_text);

        computeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                computeFactorial();
            }
        });

        return view;
    }

    private void computeFactorial() {
        String input = numberInput.getText().toString();
        if (!input.isEmpty()) {
            int number = Integer.parseInt(input);
            long factorial = calculateFactorial(number);
            resultText.setText(String.valueOf(factorial));
        }
    }

    private long calculateFactorial(int n) {
        if (n == 0)
            return 1;
        else
            return n * calculateFactorial(n - 1);
    }
}
