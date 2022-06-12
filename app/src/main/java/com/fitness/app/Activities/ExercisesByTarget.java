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
import com.fitness.app.Data.RetrofitClient;
import com.fitness.app.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ExercisesByTarget extends AppCompatActivity {

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

            if (extras.getString("number").equals("all"))
                number = byTargetList.size();
            else
                number = Integer.parseInt(extras.getString("number"));
        }
        setOnClickListener();

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        if (!extras.getString("number").equals("all"))
            adapter = new ExercisesAdapter(byTargetLimited, listener);
        else
            adapter = new ExercisesAdapter(byTargetList, listener);

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
                Toast.makeText(ExercisesByTarget.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setOnClickListener() {
        listener = new ExercisesAdapter.RecyclerViewClickListener() {
            @Override
            public void onClick(View v, int position) {
                Intent intent = new Intent(getApplicationContext(), ExerciseActivity.class);
                intent.putExtra("exercise name", byTargetLimited.get(position).getName());
                intent.putExtra("body part", byTargetLimited.get(position).getBodyPart());
                intent.putExtra("gif", byTargetLimited.get(position).getGifUrl());
                intent.putExtra("equipment", byTargetLimited.get(position).getEquipment());
                intent.putExtra("target", byTargetLimited.get(position).getTarget());
                intent.putExtra("id", byTargetLimited.get(position).getId());
                intent.putExtra("back", "random_list");
                startActivity(intent);
            }
        };
    }
    public void backToMain(View view){
        Intent intent = new Intent(getApplicationContext(), Register.class);
        startActivity(intent);
    }

}