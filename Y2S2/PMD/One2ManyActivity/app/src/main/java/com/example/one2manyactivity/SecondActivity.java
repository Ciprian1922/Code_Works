package com.example.one2manyactivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class SecondActivity extends AppCompatActivity {

    private String[] questions;
    private String[][] answers;
    private RadioButton[] optionButtons;
    private RadioGroup radioGroup;
    private Button submitButton;
    private int currentQuestionIndex = 0;
    private int correctAnswers = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        Log.d("SecondActivity", "onCreate() called"); // Add this log message

        // Initialize views
        TextView questionTextView = findViewById(R.id.questionTextView);
        radioGroup = findViewById(R.id.radioGroup);
        submitButton = findViewById(R.id.submitButton);

        // Initialize questions and answers
        questions = getResources().getStringArray(R.array.questions);
        answers = new String[][] {
                getResources().getStringArray(R.array.answers1),
                getResources().getStringArray(R.array.answers2),
                getResources().getStringArray(R.array.answers3),
                getResources().getStringArray(R.array.answers4),
                getResources().getStringArray(R.array.answers5)
        };
        optionButtons = new RadioButton[4];
        optionButtons[0] = findViewById(R.id.option1Button);
        optionButtons[1] = findViewById(R.id.option2Button);
        optionButtons[2] = findViewById(R.id.option3Button);
        optionButtons[3] = findViewById(R.id.option4Button);

        // Set up initial question
        displayQuestion(currentQuestionIndex);

        // Handle submit button click
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("SecondActivity", "Submit button clicked"); // Add this log message
                checkAnswer();
                if (currentQuestionIndex < questions.length - 1) {
                    currentQuestionIndex++;
                    displayQuestion(currentQuestionIndex);
                } else {
                    submitAnswers(); // This should be called when all questions are answered
                }
            }
        });
    }

    private void displayQuestion(int questionIndex) {
        Log.d("SecondActivity", "Displaying question: " + questionIndex); // Add this log message
        // Display question text
        TextView questionTextView = findViewById(R.id.questionTextView);
        questionTextView.setText(questions[questionIndex]);

        // Display answer options
        for (int i = 0; i < 4; i++) {
            optionButtons[i].setText(answers[questionIndex][i]);
        }
    }

    private void checkAnswer() {
        // Check the selected answer
        int selectedOptionId = radioGroup.getCheckedRadioButtonId();
        RadioButton selectedButton = findViewById(selectedOptionId);
        int selectedOptionIndex = radioGroup.indexOfChild(selectedButton);

        // Get the correct answer index
        int correctAnswerIndex = getCorrectAnswerIndex(currentQuestionIndex);

        // Check if the selected answer is correct
        if (selectedOptionIndex == correctAnswerIndex) {
            correctAnswers++;
        }
        Log.d("SecondActivity", "Correct answers so far: " + correctAnswers); // Add this log message
    }

    private int getCorrectAnswerIndex(int questionIndex) {
        // Retrieve the index of the correct answer from the string array
        String[] answerArray = answers[questionIndex];
        String correctAnswer = answerArray[0]; // Assuming the correct answer is always at index 0
        for (int i = 0; i < answerArray.length; i++) {
            if (answerArray[i].startsWith("A)")) {
                return i;
            }
        }
        return -1; // Default to -1 if correct answer is not found
    }


    private void submitAnswers() {
        // Calculate the number of correct answers
        int correctAnswers = calculateCorrectAnswers();

        // Log the correctAnswersCount for debugging
        Log.d("SecondActivity", "Correct Answers Count: " + correctAnswers);

        // Create an intent to return the result to MainActivity
        Intent resultIntent = new Intent();
        resultIntent.putExtra("correctAnswersCount", correctAnswers);
        setResult(RESULT_OK, resultIntent);

        // Finish SecondActivity
        finish();
    }


    private int calculateCorrectAnswers() {
        int correctAnswers = 0;

        // Loop through the questions and check if the selected answer is correct
        for (int i = 0; i < questions.length; i++) {
            int correctOptionIndex = getCorrectOptionIndex(i);

            // Get the ID of the selected radio button inside the loop
            int selectedRadioButtonId = radioGroup.getCheckedRadioButtonId();
            RadioButton selectedRadioButton = findViewById(selectedRadioButtonId);

            if (selectedRadioButton != null) {
                int selectedOptionIndex = radioGroup.indexOfChild(selectedRadioButton);
                if (selectedOptionIndex == correctOptionIndex) {
                    correctAnswers++;
                }
            }
        }

        return correctAnswers;
    }




    private int getCorrectOptionIndex(int questionIndex) {
        // Retrieve the index of the correct answer from the string array
        String[] answerArray = answers[questionIndex];
        for (int i = 0; i < answerArray.length; i++) {
            if (answerArray[i].startsWith("A)")) {
                return i;
            }
        }
        return -1; // Default to -1 if correct answer is not found
    }
}
