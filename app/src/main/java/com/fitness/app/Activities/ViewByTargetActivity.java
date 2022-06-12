package com.fitness.app.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.fitness.app.Data.RetrofitClient;
import com.fitness.app.Data.TargetAdapter;
import com.fitness.app.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ViewByTargetActivity extends AppCompatActivity {

    List<String> targetMuscles;
    TargetAdapter adapter;
    ProgressBar progressBar;
    LinearLayoutManager layoutManager;
    RecyclerView recyclerView;
    private TargetAdapter.RecyclerViewClickListener listener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_by_target);

        targetMuscles = new ArrayList<>();
        progressBar = findViewById(R.id.progressBarViewByTarget);
        recyclerView = findViewById(R.id.recycleViewAllTargets);

        setOnClickListener();

        layoutManager = new LinearLayoutManager(this);

        recyclerView.setLayoutManager(layoutManager);
        adapter = new TargetAdapter(targetMuscles, listener);
        recyclerView.setAdapter(adapter);

        fetchTargets();
    }

    private void fetchTargets() {
        progressBar.setVisibility(View.VISIBLE);
        RetrofitClient.getRetrofitClient().getExerciseTargets().enqueue(new Callback<List<String>>() {
            @Override
            public void onResponse(Call<List<String>> call, Response<List<String>> response) {
                if(response.isSuccessful() && response.body() != null){
                    targetMuscles.addAll(response.body());
                    adapter.notifyDataSetChanged();
                    progressBar.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<List<String>> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(ViewByTargetActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setOnClickListener() {
        listener = new TargetAdapter.RecyclerViewClickListener() {
            @Override
            public void onClick(View v, int position) {
                String number = "all";
                    Intent intent = new Intent(getApplicationContext(), ExercisesByTarget.class);

                    intent.putExtra("target", targetMuscles.get(position));
                    intent.putExtra("number", number);

                    startActivity(intent);
                }
        };
    }

    public void backToMain(View view){
        Intent intent = new Intent(getApplicationContext(), Register.class);
        startActivity(intent);
    }
}