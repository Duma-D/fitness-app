package com.fitness.app.Data;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ExerciseDbApi {
    @GET("exercises")
    Call<List<Exercise>> getExercises();

    @GET("exercises/targetList")
    Call<List<String>> getExerciseTargets();

    @GET("exercises/target/{target}")
    Call<List<Exercise>> getListOnTarget(@Path("target") String target);

}
