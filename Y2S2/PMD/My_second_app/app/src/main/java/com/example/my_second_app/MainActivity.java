package com.example.my_second_app;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class MainActivity extends Activity {
    EditText textmsg;
    TextView resultTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textmsg = findViewById(R.id.editText1);
        resultTextView = findViewById(R.id.resultTextView);
    }

    // Method to solve linear equations system using Cramer's Rule
    private void solveEquationsUsingCramer() {
        try {
            // Read the matrix from the input text
            String matrixText = textmsg.getText().toString();
            String[] lines = matrixText.split("\n");

            // Assuming the first line contains the dimension of the matrix
            int n = Integer.parseInt(lines[0].trim());
            double[][] coefficients = new double[n][n];
            double[] constants = new double[n];

            // Parsing the matrix coefficients and constants
            for (int i = 1; i <= n; i++) {
                String[] row = lines[i].trim().split(" ");
                for (int j = 0; j < n; j++) {
                    coefficients[i - 1][j] = Double.parseDouble(row[j]);
                }
                constants[i - 1] = Double.parseDouble(row[n]);
            }

            // Solve the equations using Cramer's Rule
            double[] solutions = cramerRule(coefficients, constants);

            // Display the solutions
            StringBuilder result = new StringBuilder();
            for (int i = 0; i < n; i++) {
                result.append("x").append(i + 1).append(" = ").append(solutions[i]).append("\n");
            }
            resultTextView.setText(result.toString());
        } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
            // Handle any parsing errors or array index out of bounds exceptions
            Toast.makeText(this, "Invalid input format", Toast.LENGTH_SHORT).show();
        }
    }

    // Cramer's Rule implementation
    private double[] cramerRule(double[][] coefficients, double[] constants) {
        int n = constants.length;
        double[] solutions = new double[n];

        double determinant = determinant(coefficients);

        // Solve for each variable using determinants
        for (int i = 0; i < n; i++) {
            double[][] modifiedMatrix = replaceColumn(coefficients, i, constants);
            solutions[i] = determinant(modifiedMatrix) / determinant;
        }

        return solutions;
    }

    // Method to calculate the determinant of a square matrix
    private double determinant(double[][] matrix) {
        // Implementation of determinant calculation (omitted for brevity)
        // You can implement this method using standard algorithms like recursion
        return 0.0; // Placeholder, replace with your implementation
    }

    // Method to replace a column in a matrix with a vector
    private double[][] replaceColumn(double[][] matrix, int columnIndex, double[] vector) {
        // Implementation of replacing a column in a matrix (omitted for brevity)
        // You need to replace the specified column with the given vector
        return new double[1][1]; // Placeholder, replace with your implementation
    }

    // Method to handle button click event
    public void solveEquations(View view) {
        solveEquationsUsingCramer();
    }

    // Method to read text from file
    public void readFromFile(View v) {
        try {
            FileInputStream fileIn = openFileInput("system.txt");
            InputStreamReader InputRead = new InputStreamReader(fileIn);
            char[] inputBuffer = new char[100];
            StringBuilder s = new StringBuilder();
            int charRead;
            while ((charRead = InputRead.read(inputBuffer)) > 0) {
                s.append(inputBuffer, 0, charRead);
            }
            InputRead.close();
            textmsg.setText(s.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Method to write text to file
    public void writeToFile(View v) {
        try {
            FileOutputStream fileout = openFileOutput("system.txt", MODE_PRIVATE);
            OutputStreamWriter outputWriter = new OutputStreamWriter(fileout);
            outputWriter.write(textmsg.getText().toString());
            outputWriter.close();
            Toast.makeText(getApplicationContext(), "Text saved successfully!", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), "Error saving text", Toast.LENGTH_SHORT).show();
        }
    }
}
