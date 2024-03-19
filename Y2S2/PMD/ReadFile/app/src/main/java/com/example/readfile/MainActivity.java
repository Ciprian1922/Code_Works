package com.example.readfile;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

public class MainActivity extends AppCompatActivity {

    private TextView resultTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        resultTextView = findViewById(R.id.resultTextView);

        // Create two matrices and save them to files
        int[][] matrix1 = {
                {1, 2, 10},
                {4, 5, 6},
                {7, 8, 9}
        };
        int[][] matrix2 = {
                {9, 8, 7},
                {6, 5, 4},
                {3, 2, 1}
        };
        writeMatrixToFile(matrix1, "matrix1.txt");
        writeMatrixToFile(matrix2, "matrix2.txt");

        // Read matrices from files
        int[][] matrix1FromFile = readMatrixFromFile("matrix1.txt");
        int[][] matrix2FromFile = readMatrixFromFile("matrix2.txt");

        if (matrix1FromFile != null && matrix2FromFile != null) {
            // Compute sum of matrices
            int[][] sum = computeSum(matrix1FromFile, matrix2FromFile);

            // Display result on screen
            displayMatrix(sum);

            // Save result to a file
            writeMatrixToFile(sum, "result.txt");

            // Display a success message
            Toast.makeText(this, "Result saved to result.txt", Toast.LENGTH_SHORT).show();
        } else {
            // Display an error message if reading matrices from files failed
            Toast.makeText(this, "Error reading matrices from files", Toast.LENGTH_SHORT).show();
        }
    }

    private int[][] readMatrixFromFile(String fileName) {
        try {
            File file = new File(getExternalFilesDir(null) + "/Matrices", fileName);
            BufferedReader br = new BufferedReader(new FileReader(file));
            int[][] matrix = new int[3][3];
            String line;
            int row = 0;
            while ((line = br.readLine()) != null && row < 3) {
                String[] values = line.split(" ");
                for (int col = 0; col < 3 && col < values.length; col++) {
                    matrix[row][col] = Integer.parseInt(values[col]);
                }
                row++;
            }
            br.close();
            return matrix;
        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
            return null;
        }
    }

    private int[][] computeSum(int[][] matrix1, int[][] matrix2) {
        int[][] sum = new int[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                sum[i][j] = matrix1[i][j] + matrix2[i][j];
            }
        }
        return sum;
    }

    private void displayMatrix(int[][] matrix) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                sb.append(matrix[i][j]).append(" ");
            }
            sb.append("\n");
        }
        resultTextView.setText(sb.toString());
    }

    private void writeMatrixToFile(int[][] matrix, String fileName) {
        try {
            File directory = new File(getExternalFilesDir(null), "Matrices");
            if (!directory.exists()) {
                directory.mkdirs();
            }
            File file = new File(directory, fileName);
            PrintWriter writer = new PrintWriter(new FileOutputStream(file));
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    writer.print(matrix[i][j] + " ");
                }
                writer.println();
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
