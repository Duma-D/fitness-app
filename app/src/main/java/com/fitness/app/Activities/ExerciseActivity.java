package com.fitness.app.Activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.fitness.app.Data.ExercisesAdapter;
import com.fitness.app.Data.MyDatabaseHelper;
import com.fitness.app.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import pl.droidsonroids.gif.GifImageView;

public class ExerciseActivity extends AppCompatActivity {

    TextView nameTxt, bodyPartTxt, equipmentTxt, targetTxt;
    GifImageView gifImageView;
    private ExercisesAdapter.RecyclerViewClickListener listener;
    FloatingActionButton buttonAdd, buttonRemove;
    String backBtn = "";
    private String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise);

        nameTxt = findViewById(R.id.textViewExNameS);
        bodyPartTxt = findViewById(R.id.textViewBodyPartS);
        gifImageView = findViewById(R.id.gifImageViewS);
        equipmentTxt = findViewById(R.id.textViewEquipmentS);
        targetTxt = findViewById(R.id.textViewTargetS);
        buttonAdd = findViewById(R.id.buttonAddToFavorite);
        buttonRemove = findViewById(R.id.buttonRemoveFromFavorite);

        MyDatabaseHelper myDB = new MyDatabaseHelper(ExerciseActivity.this);

        String name = "Name not set";
        String bodyP = "";
        String gifUrl = "";
        String equipment = "";
        String target = "";
        id = "";

        Bundle extras = getIntent().getExtras();
        if (extras != null){
            name = extras.getString("exercise name");
            bodyP = extras.getString("body part");
            gifUrl = extras.getString("gif");
            equipment = extras.getString("equipment");
            target = extras.getString("target");
            id = extras.getString("id");
            backBtn = extras.getString("back");
        }

        if(myDB.itExists(id)){
            isInFavorite();
        }

        nameTxt.setText(name.toUpperCase());
        bodyPartTxt.setText("Body part: " + bodyP);
        equipmentTxt.setText("Equipment needed: " + equipment);
        targetTxt.setText("Target muscles: " + target);

        Glide
                .with(this)
                .load(gifUrl)
                .placeholder(R.drawable.img_3)
                .into(gifImageView);

        String finalId = id;
        String finalName = name;
        String finalBodyP = bodyP;
        String finalEquipment = equipment;
        String finalTarget = target;
        String finalGifUrl = gifUrl;
        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myDB.addExercise(finalId.trim(), finalName.trim(), finalBodyP.trim(), finalEquipment.trim(),
                        finalTarget.trim(), finalGifUrl.trim());
                isInFavorite();
            }
        });

        buttonRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirmDialog();
            }
        });

    }

    public void backToExercises(View view) {
        if (backBtn.equals("all_list")) {
            Intent intent = new Intent(getApplicationContext(), ExercisesViewAll.class);
            startActivity(intent);
        }
        else if(backBtn.equals("fav_list")){
            Intent intent = new Intent(getApplicationContext(), FavoriteListActivity.class);
            startActivity(intent);
        }
        else if(backBtn.equals("random_list")){
            Intent intent = new Intent(getApplicationContext(), RandomWorkoutActivity.class);
            startActivity(intent);
        }
    }
    public void isInFavorite(){
        buttonAdd.setVisibility(View.INVISIBLE);
        buttonRemove.setVisibility(View.VISIBLE);
    }

    void confirmDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Remove from list");
        builder.setMessage("Are you sure you want to remove this exercise from your favorite list?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                MyDatabaseHelper myDB = new MyDatabaseHelper(ExerciseActivity.this);
                myDB.deleteOneRow(id);
                buttonAdd.setVisibility(View.VISIBLE);
                buttonRemove.setVisibility(View.INVISIBLE);
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.create().show();
    }

}
