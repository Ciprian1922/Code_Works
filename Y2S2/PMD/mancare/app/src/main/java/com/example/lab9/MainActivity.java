package com.example.lab9;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RadioGroup;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.mancare.R;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

    private RadioGroup radioGroup;
    private ListView listView;
    private Button menuButton;

    private String[] foodItems = {"Pizza", "Burger", "Pancakes", "Salad", "Lava Cake"};
    private String[] drinkItems = {"Water", "Cola", "Cola Zero", "Pepsi Twist", "Fanta"};
    private Set<Integer> selectedFoodItems = new HashSet<>();
    private Set<Integer> selectedDrinkItems = new HashSet<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        radioGroup = findViewById(R.id.radioGroup);
        listView = findViewById(R.id.listView);
        menuButton = findViewById(R.id.menuButton);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_multiple_choice);
        listView.setAdapter(adapter);

        radioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == R.id.foodRadioButton) {
                adapter.clear();
                adapter.addAll(foodItems);
                setCheckedItems(selectedFoodItems);
            } else if (checkedId == R.id.drinkRadioButton) {
                adapter.clear();
                adapter.addAll(drinkItems);
                setCheckedItems(selectedDrinkItems);
            }
        });

        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        listView.setOnItemClickListener((parent, view, position, id) -> {
            if (radioGroup.getCheckedRadioButtonId() == R.id.foodRadioButton) {
                toggleItemSelection(selectedFoodItems, position);
            } else if (radioGroup.getCheckedRadioButtonId() == R.id.drinkRadioButton) {
                toggleItemSelection(selectedDrinkItems, position);
            }
        });

        menuButton.setOnClickListener(v -> {
            // Create a list of selected food and drink items
            ArrayList<String> selectedItemsList = new ArrayList<>();
            selectedItemsList.addAll(getSelectedItems(foodItems, selectedFoodItems));
            selectedItemsList.addAll(getSelectedItems(drinkItems, selectedDrinkItems));

            // Start SelectedItemsActivity and pass the selected items
            Intent intent = new Intent(MainActivity.this, SelectedItemsActivity.class);
            intent.putStringArrayListExtra("selectedItems", selectedItemsList);
            startActivity(intent);
        });
    }

    private void toggleItemSelection(Set<Integer> selectedItems, int position) {
        if (selectedItems.contains(position)) {
            selectedItems.remove(position);
        } else {
            selectedItems.add(position);
        }
        listView.setItemChecked(position, selectedItems.contains(position));
    }

    private void setCheckedItems(Set<Integer> selectedItems) {
        listView.clearChoices();
        for (int position : selectedItems) {
            listView.setItemChecked(position, true);
        }
    }

    private ArrayList<String> getSelectedItems(String[] items, Set<Integer> selectedItems) {
        ArrayList<String> selectedItemsList = new ArrayList<>();
        for (int position : selectedItems) {
            selectedItemsList.add(items[position]);
        }
        return selectedItemsList;
    }
}
