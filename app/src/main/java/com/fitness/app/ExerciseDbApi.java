package com.fitness.app;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ExerciseDbApi {
    @GET("exercises")
    Call<List<Exercise>> getExercises();

}
