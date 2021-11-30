package com.example.retrofitapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ProgressBar;
import android.widget.Toast;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements Adaptery.OnItemNoteListener {

//    private TextView TextViewH;
    ProgressBar progressBar;
    RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager;
    Adaptery charactersAdapter;
    List<Character> charactersList= new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //TextViewH = findViewById(R.id.TextViewH);
        recyclerView = findViewById(R.id.recyclerView);
        progressBar = findViewById(R.id.progressBar);
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        charactersAdapter = new Adaptery(charactersList, getApplicationContext(), this);
        recyclerView.setAdapter(charactersAdapter);

        fetchCharacters();

    }

    private void fetchCharacters() {
        progressBar.setVisibility(View.VISIBLE);
        RetrofitClient.getRetrofitClient().getCharacters().enqueue(new Callback<List<Character>>() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onResponse(@NonNull Call<List<Character>> call, Response<List<Character>> response) {
                if(response.isSuccessful() && response.body() != null){
                    charactersList.addAll(response.body());
                    charactersAdapter.notifyDataSetChanged();
                    progressBar.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<List<Character>> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(MainActivity.this,"Error: "+t.getMessage(),Toast.LENGTH_LONG).show();
            }
        });
    }


    @Override
    public void onNoteClick(int position) {
        Character character = charactersList.get(position);
        Intent intent = new Intent(getApplicationContext(), CharacterDetailActivity.class);
        intent.putExtra("id",character.getChar_id());
        startActivity(intent);
    }
}