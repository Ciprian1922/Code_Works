package com.example.fragments;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void selectFirstFactorialFragment(View view) {
        // Create a new instance of FactorialFragment
        FactorialFragment factorialFragment = new FactorialFragment();

        // Get the FragmentManager and start a FragmentTransaction
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        // Replace the existing fragment (if any) with the FactorialFragment
        fragmentTransaction.replace(R.id.fragment_place, factorialFragment);

        // Commit the transaction
        fragmentTransaction.commit();
    }

    public void selectSecondFactorialFragment(View view) {
        // Create a new instance of FactorialFragment
        FactorialFragment factorialFragment = new FactorialFragment();

        // Get the FragmentManager and start a FragmentTransaction
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        // Replace the existing fragment (if any) with the FactorialFragment
        fragmentTransaction.replace(R.id.fragment_place_2, factorialFragment);

        // Commit the transaction
        fragmentTransaction.commit();
    }
}
