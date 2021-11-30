package com.example.retrofitapp;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.media.Image;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CharacterDetailActivity extends AppCompatActivity {

    private TextView textViewDName;
    private TextView textViewDDateOfBirthday;
    private TextView textViewDNick;
    private TextView textViewDStatus;
    private int id;
    private Character character;
    private ArrayList<String> occ;
    private ListView listViewOccupation;
    private ImageView imageViewD;
    ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_character_detail);

        Intent intent = getIntent();
        id = intent.getIntExtra("id",1);
        textViewDName = findViewById(R.id.textViewDName);
        textViewDDateOfBirthday = findViewById(R.id.textViewDDateOfBirthday);
        textViewDNick = findViewById(R.id.textViewDNick);
        textViewDStatus = findViewById(R.id.textViewDStatus);
        listViewOccupation = findViewById(R.id.listViewOccupation);
        imageViewD = findViewById(R.id.imageViewD);
        progressBar = findViewById(R.id.progressBar2);



        fetchCharacters();

    }

    private void fetchCharacters() {
        progressBar.setVisibility(View.VISIBLE);
        RetrofitClient.getRetrofitClient().getCharacterById(id).enqueue(new Callback<List<Character>>() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onResponse(@NonNull Call<List<Character>> call, @NonNull Response<List<Character>> response) {
                if(response.isSuccessful() && response.body() != null){

                    character = new Character(response.body());
                    textViewDName.setText(character.getName());
                    textViewDDateOfBirthday.setText(character.getBirthday());
                    occ = character.getOccupation();
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(),  android.R.layout.simple_list_item_1, occ);
                    listViewOccupation.setAdapter(adapter);

                    Picasso.with(getApplicationContext())
                            .load(character.getImg())
                            .placeholder(R.drawable.back)
                            .fit()
                            .into(imageViewD);
                    textViewDNick.setText(character.getNickName());
                    textViewDStatus.setText(character.getStatus());
                    progressBar.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<List<Character>> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(CharacterDetailActivity.this,"Error: "+t.getMessage(),Toast.LENGTH_LONG).show();

            }
        });
    }




}