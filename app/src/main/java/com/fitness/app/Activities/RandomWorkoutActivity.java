package com.fitness.app.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.fitness.app.R;
import com.fitness.app.Data.RetrofitClient;
import com.fitness.app.Data.TargetAdapter;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RandomWorkoutActivity extends AppCompatActivity {

    EditText mNumber;
    List<String> targetMuscles;
    TargetAdapter adapter;
    ProgressBar progressBar;
    LinearLayoutManager layoutManager;
    RecyclerView recyclerView;
    private TargetAdapter.RecyclerViewClickListener listener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_random_workout);

        targetMuscles = new ArrayList<>();
        mNumber = findViewById(R.id.editTextNumberOfExercises);
        progressBar = findViewById(R.id.progressBarRandomExercises);
        recyclerView = findViewById(R.id.recyclerViewRandom);

        setOnClickListener();

        layoutManager = new LinearLayoutManager(this);

        recyclerView.setLayoutManager(layoutManager);
        adapter = new TargetAdapter(targetMuscles, listener);
        recyclerView.setAdapter(adapter);

        fetchTargets();

        // adauga un buton pt mNumber
        // creeaza un nou activity cu allexercises %targetMuscle%
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
                Toast.makeText(RandomWorkoutActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setOnClickListener() {
        listener = new TargetAdapter.RecyclerViewClickListener() {
            @Override
            public void onClick(View v, int position) {
                String number = "";
                if(!mNumber.getText().toString().equals("") && isNumeric(mNumber.getText().toString())){
                    number = mNumber.getText().toString();
                    Intent intent = new Intent(getApplicationContext(), ExercisesByTarget.class);

                    intent.putExtra("target", targetMuscles.get(position));
                    intent.putExtra("number", number);

                    startActivity(intent);
                }
                else
                {
                    Toast.makeText(RandomWorkoutActivity.this, "You need to chose a number.", Toast.LENGTH_SHORT).show();
                }
            }
        };
    }

    public static boolean isNumeric(String strNum) {
        if (strNum == null) {
            return false;
        }
        try {
            double d = Double.parseDouble(strNum);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

    public void backToMain(View view){
        Intent intent = new Intent(getApplicationContext(), Register.class);
        startActivity(intent);
    }
}