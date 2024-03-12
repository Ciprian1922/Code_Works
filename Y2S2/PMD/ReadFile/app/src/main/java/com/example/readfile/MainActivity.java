package com.example.readfile;// MainActivity.java
//        int[][] matrix1 = readMatrix("matrix1.txt");
//        int[][] matrix2 = readMatrix("matrix2.txt");
import android.os.Bundle;
import android.os.Environment;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;

public class MainActivity extends AppCompatActivity {

    private TextView resultTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        resultTextView = findViewById(R.id.resultTextView);

        // Initialize example matrices
        int[][] matrix1 = {
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 9}
        };

        int[][] matrix2 = {
                {9, 8, 7},
                {6, 5, 4},
                {3, 2, 1}
        };

        // Compute sum
        int[][] sum = computeSum(matrix1, matrix2);

        // Display result
        displayMatrix(sum);

        // Write result to a text file
        writeMatrixToFile(sum, "result.txt");
    }

    private int[][] computeSum(int[][] matrix1, int[][] matrix2) {
        int rows = matrix1.length;
        int cols = matrix1[0].length;
        int[][] sum = new int[rows][cols];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                sum[i][j] = matrix1[i][j] + matrix2[i][j];
            }
        }

        return sum;
    }

    private void displayMatrix(int[][] matrix) {
        StringBuilder sb = new StringBuilder();
        for (int[] row : matrix) {
            for (int num : row) {
                sb.append(num).append(" ");
            }
            sb.append("\n");
        }
        resultTextView.setText(sb.toString());
    }

    private void writeMatrixToFile(int[][] matrix, String fileName) {
        try {
            // Specify the directory path where you want to save the file
            String directoryPath = "C:\\Users\\popa_\\Desktop\\GitHub_UVT\\Code_Works\\Y2S2\\PMD\\ReadFile\\";

            // Create the directory if it doesn't exist
            File directory = new File(directoryPath);
            if (!directory.exists()) {
                boolean created = directory.mkdirs();
                if (!created) {
                    System.out.println("Failed to create directory: " + directoryPath);
                    return;
                }
                System.out.println("Directory created: " + directoryPath);
            } else {
                System.out.println("Directory already exists: " + directoryPath);
            }

            // Create the file within the specified directory
            File file = new File(directory, fileName);

            // Open a PrintWriter for writing to the file
            PrintWriter writer = new PrintWriter(file);

            // Write matrix data to the file
            for (int[] row : matrix) {
                for (int num : row) {
                    writer.print(num + " ");
                }
                writer.println(); // Move to the next line after each row
            }

            // Close the PrintWriter
            writer.close();

            // Display a message indicating the file path
            System.out.println("Result saved to " + file.getAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
