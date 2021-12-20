package com.example.retrofitapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MenuItem;


import com.example.retrofitapp.ui.CharactersFragment;
import com.example.retrofitapp.ui.SettingsFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements  NavigationBarView.OnItemSelectedListener {

//    private TextView TextViewH;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //TextViewH = findViewById(R.id.TextViewH);
//        recyclerView = findViewById(R.id.recyclerView);
//        progressBar = findViewById(R.id.progressBar);
//        linearLayoutManager = new LinearLayoutManager(this);
//        recyclerView.setLayoutManager(linearLayoutManager);
//        charactersAdapter = new Adaptery(charactersList, getApplicationContext(), this);
//        recyclerView.setAdapter(charactersAdapter);

//        fetchCharacters();

        loadFragment(new CharactersFragment());

        //getting bottom navigation view and attaching the listener
        BottomNavigationView navigation = findViewById(R.id.btnNavView);
        navigation.setOnItemSelectedListener(this);

    }


    private boolean loadFragment(Fragment fragment) {
        //switching fragment
        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.placeholder, fragment)
                    .commit();
        }
        return false;
    }




    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment fragment = null;

        switch (item.getItemId()) {
            case R.id.characters:
                fragment = new CharactersFragment();
                break;

            case R.id.settings:
                fragment = new SettingsFragment();
                break;

            case R.id.profile:
//                fragment = new NotificationsFragment();
                break;
        }

        return loadFragment(fragment);
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}