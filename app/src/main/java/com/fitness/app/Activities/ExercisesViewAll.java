package com.fitness.app.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.fitness.app.Data.Exercise;
import com.fitness.app.Data.ExercisesAdapter;
import com.fitness.app.R;
import com.fitness.app.Data.RetrofitClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ExercisesViewAll extends AppCompatActivity {
    RecyclerView recyclerView;
    ProgressBar progressBar;
    LinearLayoutManager layoutManager;
    ExercisesAdapter adapter;
    List<Exercise> exerciseList = new ArrayList<>();
    private ExercisesAdapter.RecyclerViewClickListener listener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercises_view_all);

        setOnClickListener();

        recyclerView = findViewById(R.id.recyclerViewAll);
        progressBar = findViewById(R.id.progressBarViewAll);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new ExercisesAdapter(exerciseList, listener);
        recyclerView.setAdapter(adapter);

        fetchExercises();

    }

    private void setOnClickListener() {
        listener = new ExercisesAdapter.RecyclerViewClickListener() {
            @Override
            public void onClick(View v, int position) {
                Intent intent = new Intent(getApplicationContext(), ExerciseActivity.class);
                intent.putExtra("exercise name", exerciseList.get(position).getName());
                intent.putExtra("body part", exerciseList.get(position).getBodyPart());
                intent.putExtra("gif", exerciseList.get(position).getGifUrl());
                intent.putExtra("equipment", exerciseList.get(position).getEquipment());
                intent.putExtra("target", exerciseList.get(position).getTarget());
                intent.putExtra("id", exerciseList.get(position).getId());
                intent.putExtra("back", "all_list");
                startActivity(intent);
            }
        };
    }

    private void fetchExercises() {
        progressBar.setVisibility(View.VISIBLE);
        RetrofitClient.getRetrofitClient().getExercises().enqueue(new Callback<List<Exercise>>() {
            @Override
            public void onResponse(Call<List<Exercise>> call, Response<List<Exercise>> response) {
                if(response.isSuccessful() && response.body() != null){
                    exerciseList.addAll(response.body());
                    adapter.notifyDataSetChanged();
                    progressBar.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<List<Exercise>> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(ExercisesViewAll.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void backToMain(View view){
        Intent intent = new Intent(getApplicationContext(), Login.class);
        startActivity(intent);
    }
}