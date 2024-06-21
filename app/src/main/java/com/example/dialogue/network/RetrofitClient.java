package com.example.dialogue.network;

import com.example.dialogue.helpers.LenientGsonConverterFactory;
import com.example.dialogue.models.StringConverterFactory;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;

import java.util.concurrent.TimeUnit;

public class RetrofitClient {
    private static Retrofit retrofit = null;

    public static Retrofit getClient(String baseUrl) {
        if (retrofit == null) {
            // Configure the OkHttpClient with timeout settings
            OkHttpClient okHttpClient = new OkHttpClient.Builder()
                    .connectTimeout(30, TimeUnit.SECONDS) // Set connection timeout
                    .readTimeout(30, TimeUnit.SECONDS) // Set read timeout
                    .writeTimeout(30, TimeUnit.SECONDS) // Set write timeout
                    .build();

            // Build the Retrofit instance with the custom OkHttpClient
            retrofit = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .client(okHttpClient)
                    .addConverterFactory(StringConverterFactory.create())
                    .addConverterFactory(LenientGsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
