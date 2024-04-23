package com.example.restaurant;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.appcompat.app.AppCompatActivity;

public class FirstActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Find radio groups for food and drinks
        RadioGroup foodRadioGroup = findViewById(R.id.food_radio_group);
        RadioGroup drinkRadioGroup = findViewById(R.id.drink_radio_group);

        // Button to navigate to second activity
        Button menuButton = findViewById(R.id.menu_button);
        menuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the selected food and drink
                String selectedFood = getSelectedRadioButtonText(foodRadioGroup);
                String selectedDrink = getSelectedRadioButtonText(drinkRadioGroup);

                // Start SecondActivity with selected food and drink
                Intent intent = new Intent(FirstActivity.this, SecondActivity.class);
                intent.putExtra("food", selectedFood);
                intent.putExtra("drink", selectedDrink);
                startActivity(intent);
            }
        });
    }

    // Method to get the text of the selected radio button in a radio group
    private String getSelectedRadioButtonText(RadioGroup radioGroup) {
        int radioButtonId = radioGroup.getCheckedRadioButtonId();
        if (radioButtonId != -1) {
            RadioButton radioButton = findViewById(radioButtonId);
            return radioButton.getText().toString();
        }
        return null; // No radio button selected
    }
}
