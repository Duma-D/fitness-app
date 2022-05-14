package com.fitness.app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RandomByTarget extends AppCompatActivity {

    RecyclerView recyclerView;
    List<Exercise> byTargetList;
    LinearLayoutManager layoutManager;
    ExercisesAdapter adapter;
    private ExercisesAdapter.RecyclerViewClickListener listener;
    ProgressBar progressBar;
    String target;
    List<Exercise> byTargetLimited;
    int number;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_random_by_target);

        progressBar = findViewById(R.id.progressBarByTargetRan);

        recyclerView = findViewById(R.id.recyclerViewByTarget);
        byTargetList = new ArrayList<>();
        byTargetLimited = new ArrayList<>();

        target = "";

        Bundle extras = getIntent().getExtras();
        if (extras != null){
            target = extras.getString("target").trim();
            number = Integer.parseInt(extras.getString("number"));
        }

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new ExercisesAdapter(byTargetLimited, listener);
        recyclerView.setAdapter(adapter);

        fetchExercises();
    }

    private void fetchExercises() {
        progressBar.setVisibility(View.VISIBLE);
        RetrofitClient.getRetrofitClient().getListOnTarget(target).enqueue(new Callback<List<Exercise>>() {
            @Override
            public void onResponse(Call<List<Exercise>> call, Response<List<Exercise>> response) {
                if(response.isSuccessful() && response.body() != null){
                    byTargetList.addAll(response.body());
                    adapter.notifyDataSetChanged();
                    progressBar.setVisibility(View.GONE);
                }
                for (int i = 0; i < number; i ++){
                    int position = (int)(Math.random() * byTargetList.size());
                    byTargetLimited.add(byTargetList.get(position));
                }
            }

            @Override
            public void onFailure(Call<List<Exercise>> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(RandomByTarget.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}