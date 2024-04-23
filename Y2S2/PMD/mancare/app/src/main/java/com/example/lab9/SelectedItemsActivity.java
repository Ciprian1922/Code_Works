package com.example.lab9;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lab9.R;
import com.example.lab9.SelectedItemsAdapter;

import java.util.ArrayList;

public class SelectedItemsActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private SelectedItemsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selected_items);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        ArrayList<String> selectedItems = getIntent().getStringArrayListExtra("selectedItems");
        ArrayList<Integer> itemImages = new ArrayList<>();

        // Add images for selected items
        for (String selectedItem : selectedItems) {
            switch (selectedItem) {
                case "Pizza":
                    itemImages.add(R.drawable.pizza);
                    break;
                case "Burger":
                    itemImages.add(R.drawable.burger);
                    break;
                case "Pancakes":
                    itemImages.add(R.drawable.pancakes);
                    break;
                case "Salad":
                    itemImages.add(R.drawable.salad);
                    break;
                case "Lava Cake":
                    itemImages.add(R.drawable.lavacake);
                    break;
                case "Water":
                    itemImages.add(R.drawable.water);
                    break;
                case "Cola":
                    itemImages.add(R.drawable.cola);
                    break;
                case "Cola Zero":
                    itemImages.add(R.drawable.colazero);
                    break;
                case "Pepsi Twist":
                    itemImages.add(R.drawable.pepsitwist);
                    break;
                case "Fanta":
                    itemImages.add(R.drawable.fanta);
                    break;
            }
        }

        adapter = new SelectedItemsAdapter(this, selectedItems, itemImages);
        recyclerView.setAdapter(adapter);
    }
}
