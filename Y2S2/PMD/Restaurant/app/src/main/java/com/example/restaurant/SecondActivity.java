package com.example.restaurant;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class SecondActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        // Get the selected food and drink from the intent extras
        String selectedFood = getIntent().getStringExtra("food");
        String selectedDrink = getIntent().getStringExtra("drink");

        // Find views
        ImageView foodImageView = findViewById(R.id.food_image);
        ImageView drinkImageView = findViewById(R.id.drink_image); // Added
        TextView foodTextView = findViewById(R.id.food_text);
        TextView drinkTextView = findViewById(R.id.drink_text);

        // Display selected food and drink
        if (selectedFood != null && !selectedFood.isEmpty()) {
            // Set image for selected food
            int foodImageResource = getFoodImageResource(selectedFood);
            foodImageView.setImageResource(foodImageResource);
            foodTextView.setText("Selected Food: " + selectedFood);
        }

        if (selectedDrink != null && !selectedDrink.isEmpty()) {
            // Set image for selected drink
            int drinkImageResource = getDrinkImageResource(selectedDrink);
            drinkImageView.setImageResource(drinkImageResource); // Corrected
            drinkTextView.setText("Selected Drink: " + selectedDrink);
        }
    }

    // Method to get the resource ID of the food image based on the selected food
    private int getFoodImageResource(String selectedFood) {
        switch (selectedFood) {
            case "Burger":
                return R.drawable.default_burger_image;
            case "Kebab":
                return R.drawable.default_kebab_image;
            case "Salad":
                return R.drawable.default_salad_image;
            default:
                // Default image if food not recognized
                return R.drawable.default_burger_image;
        }
    }

    // Method to get the resource ID of the drink image based on the selected drink
    private int getDrinkImageResource(String selectedDrink) {
        switch (selectedDrink) {
            case "Cola":
                return R.drawable.default_cola_image;
            case "Orange Juice":
                return R.drawable.default_orange_image;
            case "Water":
                return R.drawable.default_water_image;
            default:
                // Default image if drink not recognized
                return R.drawable.default_cola_image;
        }
    }
}
