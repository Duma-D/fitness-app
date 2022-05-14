package com.fitness.app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    TextView textView;
    FirebaseAuth fAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fAuth = FirebaseAuth.getInstance();
        textView = findViewById(R.id.textView3);
        textView.setText("Welcome back, " + fAuth.getCurrentUser().getEmail());
    }

    public void logout(View view){
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(getApplicationContext(), Login.class));
        finish();
    }

    public void goToExercises(View view){
        Intent intent = new Intent(getApplicationContext(), ExercisesViewAll.class);
        startActivity(intent);
    }

    public void goToFavExercises(View view){
        Intent intent = new Intent(getApplicationContext(), FavoriteListActivity.class);
        startActivity(intent);
    }

    public void goToRandom(View view){
        Intent intent = new Intent(getApplicationContext(), RandomWorkoutActivity.class);
        startActivity(intent);
    }
}