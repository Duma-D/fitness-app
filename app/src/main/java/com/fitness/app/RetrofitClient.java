package com.fitness.app;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    private static final String BASE_URL="https://exercisedb.p.rapidapi.com/";
    private static Retrofit retrofit = null;

    public static ExerciseDbApi getRetrofitClient(){
        if(retrofit == null){

            OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
            httpClient.addInterceptor(new Interceptor() {
                @Override
                public okhttp3.Response intercept(Chain chain) throws IOException {
                    Request original = chain.request();
                    Request request = original.newBuilder()
                            .addHeader("X-RapidAPI-Host", "exercisedb.p.rapidapi.com")
                            .addHeader("X-RapidAPI-Key", "2413872a06mshfa3df114057cf23p19a747jsnee3403131448")
                            .method(original.method(), original.body())
                            .build();
                    return chain.proceed(request);
                }
            });

            OkHttpClient client = httpClient.build();

            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(client)
                    .build();
        }

        return retrofit.create(ExerciseDbApi.class);
    }

}
