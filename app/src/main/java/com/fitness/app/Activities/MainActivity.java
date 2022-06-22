package com.fitness.app.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.fitness.app.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    TextView textView;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        textView = findViewById(R.id.textView3);

        if(fAuth.getCurrentUser() != null) {
            userID = Objects.requireNonNull(fAuth.getCurrentUser()).getUid();

            DocumentReference documentReference = fStore.collection("users").document(userID);
            documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
                @Override
                public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                    assert value != null;
                    textView.setText("Welcome back, " + value.getString("fName") + "!");
                }
            });
        }
        else
            startActivity(new Intent(getApplicationContext(), Login.class));



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

    public void goToViewTarget(View view){
        Intent intent = new Intent(getApplicationContext(), ViewByTargetActivity.class);
        startActivity(intent);
    }

    public void goToSettings(View view){
        Intent intent = new Intent(getApplicationContext(), SettingsActivity.class);
        startActivity(intent);
    }
}