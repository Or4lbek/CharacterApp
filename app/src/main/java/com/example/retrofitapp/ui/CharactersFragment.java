package com.example.retrofitapp.ui;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.retrofitapp.Adaptery;
import com.example.retrofitapp.Character;
import com.example.retrofitapp.CharacterDetailActivity;
import com.example.retrofitapp.MainActivity;
import com.example.retrofitapp.R;
import com.example.retrofitapp.RetrofitClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class CharactersFragment extends Fragment  implements Adaptery.OnItemNoteListener {

    ProgressBar progressBar;
    RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager;
    Adaptery charactersAdapter;
    List<Character> charactersList= new ArrayList<>();

    public static CharactersFragment newInstance() {
        CharactersFragment fragment = new CharactersFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.recyclerView);
        progressBar = view.findViewById(R.id.progressBar);
        linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        charactersAdapter = new Adaptery(charactersList, getContext(), this);
        recyclerView.setAdapter(charactersAdapter);
        fetchCharacters();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_characters, container, false);
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
                Toast.makeText(getContext(),"Error: "+t.getMessage(),Toast.LENGTH_LONG).show();
            }
        });
    }


    @Override
    public void onNoteClick(int position) {
        Character character = charactersList.get(position);
        Intent intent = new Intent(getContext(), CharacterDetailActivity.class);
        intent.putExtra("id",character.getChar_id());
        startActivity(intent);
    }
}