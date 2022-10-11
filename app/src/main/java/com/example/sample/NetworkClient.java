package com.example.sample;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetworkClient {
    private static Retrofit retrofit;
    private static final String BASE_URL = "https://stonelia.herokuapp.com/api/v1/file/";


    public static Retrofit getRetrofit() {


        OkHttpClient okHttpClient = new OkHttpClient().newBuilder()
                .build();
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(okHttpClient)
                    .build();
        }
        return retrofit;

    }
}
