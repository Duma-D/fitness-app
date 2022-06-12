package com.fitness.app.Data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class MyDatabaseHelper extends SQLiteOpenHelper {

    private Context context;
    private static final String DATABASE_NAME = "FavoriteExercises.db";
    public static final int DATABSE_VERSION  = 1;
    private static final String TABLE_NAME = "my_exercises";
    private static final String COLUMN_BODYPART = "body_part";
    private static final String COLUMN_EQUIPMENT = "_equipment";
    private static final String COLUMN_GIF_URL = "gif_url";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_NAME = "_name";
    private static final String COLUMN_TARGET = "_target";

    public MyDatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABSE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_NAME
                        + " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                        + COLUMN_NAME + " TEXT, "
                        + COLUMN_BODYPART + " TEXT, "
                        + COLUMN_EQUIPMENT + " TEXT, "
                        + COLUMN_TARGET + " TEXT, "
                        + COLUMN_GIF_URL + " TEXT);";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public void addExercise(String id, String name, String bodyPart, String equipment, String target, String gifUrl){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues cv = new ContentValues();

        cv.put(COLUMN_ID, id);
        cv.put(COLUMN_NAME, name);
        cv.put(COLUMN_BODYPART, bodyPart);
        cv.put(COLUMN_EQUIPMENT, equipment);
        cv.put(COLUMN_TARGET, target);
        cv.put(COLUMN_GIF_URL, gifUrl);

        long result = db.insert(TABLE_NAME, null, cv);

        if(result == -1){
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
        }
        else
        {
            Toast.makeText(context, "Added Successfully!", Toast.LENGTH_SHORT).show();
        }

    }

    public Cursor readAllData(){
        String query = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;

        if(db != null){
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }

    public boolean itExists(String id){
        Cursor cursor = null;
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT " + COLUMN_ID + " FROM " + TABLE_NAME + " WHERE " + COLUMN_ID + "= '" + id + "'";

        cursor = db.rawQuery(query, null);

        boolean exists = (cursor.getCount() > 0);
        cursor.close();

        return exists;
    }

    public void deleteOneRow(String id){
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete(TABLE_NAME, "_id=?", new String[]{id});

        if(result == -1){
            Toast.makeText(context, "Failed to delete.", Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(context, "Successfully Deleted.", Toast.LENGTH_SHORT).show();
        }
    }

}
