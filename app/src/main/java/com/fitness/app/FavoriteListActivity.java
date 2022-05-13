package com.fitness.app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class FavoriteListActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    MyDatabaseHelper myDB;
    List<Exercise> favoriteExercises;
    LinearLayoutManager layoutManager;
    ExercisesAdapter adapter;
    private ExercisesAdapter.RecyclerViewClickListener listener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite_list);

        recyclerView = findViewById(R.id.recyclerViewFavorite);
        myDB = new MyDatabaseHelper(FavoriteListActivity.this);
        setOnClickListener();

        favoriteExercises = new ArrayList<>();

        storeDataInList();

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new ExercisesAdapter(favoriteExercises, listener);
        recyclerView.setAdapter(adapter);

    }

    void storeDataInList(){
        Cursor cursor = myDB.readAllData();
        if (cursor.getCount() == 0){
            Toast.makeText(this, "No data.", Toast.LENGTH_SHORT).show();
        }
        else {
            while (cursor.moveToNext()){
                favoriteExercises.add(new Exercise(cursor.getString(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5)));
            }
        }
    }

    private void setOnClickListener() {
        listener = new ExercisesAdapter.RecyclerViewClickListener() {
            @Override
            public void onClick(View v, int position) {
                Intent intent = new Intent(getApplicationContext(), ExerciseActivity.class);
                intent.putExtra("exercise name", favoriteExercises.get(position).getName());
                intent.putExtra("body part", favoriteExercises.get(position).getBodyPart());
                intent.putExtra("gif", favoriteExercises.get(position).getGifUrl());
                intent.putExtra("equipment", favoriteExercises.get(position).getEquipment());
                intent.putExtra("target", favoriteExercises.get(position).getTarget());
                intent.putExtra("id", favoriteExercises.get(position).getId());
                intent.putExtra("back", "fav_list");
                startActivity(intent);
            }
        };
    }

    public void backToMain(View view){
        Intent intent = new Intent(getApplicationContext(), Register.class);
        startActivity(intent);
    }

}