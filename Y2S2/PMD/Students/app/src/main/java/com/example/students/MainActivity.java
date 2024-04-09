package com.example.students;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    private SQLiteDatabase mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DBHelper dbHelper = new DBHelper(this);
        mDatabase = dbHelper.getWritableDatabase();

        Button btnAdd = findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAddDialog();
            }
        });

        Button btnQuery = findViewById(R.id.btnQuery);
        btnQuery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showQueryDialog();
            }
        });
    }

    private void showAddDialog() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.add_dialog, null);
        dialogBuilder.setView(dialogView);

        final EditText editName = dialogView.findViewById(R.id.editName);
        final EditText editBirthday = dialogView.findViewById(R.id.editBirthday);
        final EditText editAddress = dialogView.findViewById(R.id.editAddress);

        dialogBuilder.setTitle("Add Friend");
        dialogBuilder.setPositiveButton("Add", (dialog, whichButton) -> {
            String name = editName.getText().toString().trim();
            String birthday = editBirthday.getText().toString().trim();
            String address = editAddress.getText().toString().trim();
            if (!name.isEmpty() && !birthday.isEmpty() && !address.isEmpty()) {
                addFriend(name, birthday, address);
            } else {
                Toast.makeText(MainActivity.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            }
        });
        dialogBuilder.setNegativeButton("Cancel", (dialog, whichButton) -> {
            // Cancelled, do nothing
        });
        AlertDialog b = dialogBuilder.create();
        b.show();
    }

    private void showQueryDialog() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.query_dialog, null);
        dialogBuilder.setView(dialogView);

        final EditText editAge = dialogView.findViewById(R.id.editAge);

        dialogBuilder.setTitle("Query Friends");
        dialogBuilder.setPositiveButton("Query", (dialog, whichButton) -> {
            String ageString = editAge.getText().toString().trim();
            if (!ageString.isEmpty()) {
                int age = Integer.parseInt(ageString);
                queryFriendsByAge(age);
            } else {
                queryAllFriends();
            }
        });
        dialogBuilder.setNegativeButton("Cancel", (dialog, whichButton) -> {
            // Cancelled, do nothing
        });
        AlertDialog b = dialogBuilder.create();
        b.show();
    }

    private void addFriend(String name, String birthday, String address) {
        ContentValues cv = new ContentValues();
        cv.put(com.example.lab7.DBContract.Friends.COLUMN_NAME, name);
        cv.put(com.example.lab7.DBContract.Friends.COLUMN_BIRTHDAY, birthday);
        cv.put(com.example.lab7.DBContract.Friends.COLUMN_ADDRESS, address);

        long result = mDatabase.insert(com.example.lab7.DBContract.Friends.TABLE_NAME, null, cv);
        if (result == -1) {
            Toast.makeText(this, "Failed to add friend", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Friend added successfully", Toast.LENGTH_SHORT).show();
        }
    }

    private void queryAllFriends() {
        Cursor cursor = mDatabase.query(
                com.example.lab7.DBContract.Friends.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                null
        );

        displayFriends(cursor);
    }

    private void queryFriendsByAge(int age) {
        if (age == 0) {
            // If age is 0, it means the user wants to view all friends
            queryAllFriends();
        } else {
            // Get the current date
            Calendar currentDate = Calendar.getInstance();
            int currentYear = currentDate.get(Calendar.YEAR);

            // Calculate the minimum birth year based on the provided age
            int minBirthYear = currentYear - age;

            // Calculate the maximum birth year (one year prior) based on the provided age
            int maxBirthYear = minBirthYear - 1;

            // Format the min and max birth years into strings
            String minBirthYearString = String.valueOf(minBirthYear);
            String maxBirthYearString = String.valueOf(maxBirthYear);

            // Query the database for friends born within the specified age range
            Cursor cursor = mDatabase.query(
                    com.example.lab7.DBContract.Friends.TABLE_NAME,
                    null,
                    "substr(" + com.example.lab7.DBContract.Friends.COLUMN_BIRTHDAY + ", 7) BETWEEN ? AND ?", // Extracting year part of the birthday
                    new String[]{maxBirthYearString, minBirthYearString},
                    null,
                    null,
                    null
            );

            displayFriends(cursor);
        }
    }

    private void showModifyDialog(String name, String birthday, String address) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.modify_dialog, null);
        dialogBuilder.setView(dialogView);

        final EditText editName = dialogView.findViewById(R.id.editName);
        final EditText editBirthday = dialogView.findViewById(R.id.editBirthday);
        final EditText editAddress = dialogView.findViewById(R.id.editAddress);

        // Populate the EditText fields with the selected friend's information
        editName.setText(name);
        editBirthday.setText(birthday);
        editAddress.setText(address);

        dialogBuilder.setTitle("Modify Friend");
        dialogBuilder.setPositiveButton("Save", (dialog, whichButton) -> {
            // Get the modified information from EditText fields
            String modifiedName = editName.getText().toString().trim();
            String modifiedBirthday = editBirthday.getText().toString().trim();
            String modifiedAddress = editAddress.getText().toString().trim();

            // Update the friend entry in the database
            updateFriend(name, modifiedName, modifiedBirthday, modifiedAddress);
        });
        dialogBuilder.setNegativeButton("Cancel", (dialog, whichButton) -> {
            // Cancelled, do nothing
        });
        AlertDialog b = dialogBuilder.create();
        b.show();
    }


    private void deleteFriend(String name) {
        // Delete the friend entry from the database
        mDatabase.delete(com.example.lab7.DBContract.Friends.TABLE_NAME,
                com.example.lab7.DBContract.Friends.COLUMN_NAME + " = ?",
                new String[]{name});

        // Notify the user
        Toast.makeText(this, "Friend deleted successfully", Toast.LENGTH_SHORT).show();
    }

    private void updateFriend(String originalName, String modifiedName, String modifiedBirthday, String modifiedAddress) {
        ContentValues cv = new ContentValues();
        cv.put(com.example.lab7.DBContract.Friends.COLUMN_NAME, modifiedName);
        cv.put(com.example.lab7.DBContract.Friends.COLUMN_BIRTHDAY, modifiedBirthday);
        cv.put(com.example.lab7.DBContract.Friends.COLUMN_ADDRESS, modifiedAddress);

        // Update the friend entry in the database
        mDatabase.update(com.example.lab7.DBContract.Friends.TABLE_NAME, cv,
                com.example.lab7.DBContract.Friends.COLUMN_NAME + " = ?",
                new String[]{originalName});

        // Notify the user
        Toast.makeText(this, "Friend modified successfully", Toast.LENGTH_SHORT).show();
    }



    private void displayFriends(Cursor cursor) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Friends");

        View dialogView = LayoutInflater.from(this).inflate(R.layout.friends_list_dialog, null);
        LinearLayout friendListLayout = dialogView.findViewById(R.id.friendListLayout);

        while (cursor.moveToNext()) {
            @SuppressLint("Range") String name = cursor.getString(cursor.getColumnIndex(com.example.lab7.DBContract.Friends.COLUMN_NAME));
            @SuppressLint("Range") String birthday = cursor.getString(cursor.getColumnIndex(com.example.lab7.DBContract.Friends.COLUMN_BIRTHDAY));
            @SuppressLint("Range") String address = cursor.getString(cursor.getColumnIndex(com.example.lab7.DBContract.Friends.COLUMN_ADDRESS));

            // Create a new TextView to display friend information
            TextView friendTextView = new TextView(this);
            friendTextView.setText(String.format("Name: %s\nBirthday: %s\nAddress: %s", name, birthday, address));
            friendTextView.setPadding(0, 0, 0, 20); // Add some padding between entries

            // Create a button for modifying the friend entry
            Button modifyButton = new Button(this);
            modifyButton.setText("Modify");
            modifyButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Handle modify button click
                    showModifyDialog(name, birthday, address);
                }
            });

            // Create a button for deleting the friend entry
            Button deleteButton = new Button(this);
            deleteButton.setText("Delete");
            deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Handle delete button click
                    deleteFriend(name);
                }
            });

            // Add friend information TextView and buttons to the layout
            friendListLayout.addView(friendTextView);
            friendListLayout.addView(modifyButton);
            friendListLayout.addView(deleteButton);
        }

        builder.setView(dialogView);
        builder.setPositiveButton("OK", null);
        builder.show();

        cursor.close();
    }

}