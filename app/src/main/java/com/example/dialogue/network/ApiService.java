package com.example.dialogue.network;
import com.example.dialogue.models.RegisterRequest;
import com.example.dialogue.models.TextRequest;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface ApiService {
    @GET("/app")
    Call<ResponseBody> getHelloWorld();
    @POST("/synthesis") // Replace with your endpoint
    Call<Void> synthesizeText(@Body TextRequest textRequest);

    @Headers("Content-Type: application/json")
    @POST("/posttoken")
    Call<Void> sendFCMTokenToServer(@Body String body);

    @Headers("Content-Type: application/json")
    @POST("api/emp/log")
    Call<Void> empLogin(@Body String body);


    @Headers("Content-Type: application/json")
    @POST("api/emp/reg")
    Call<Void> empRegis(@Body String requestBody);

    @Headers("Content-Type: application/json")
    @POST("api/emp/tok")
    Call<Void> empToken(@Body String body);

    @Headers("Content-Type: application/json")
    @POST("api/hr/log")
    Call<Void> hrLogin(@Body String body);

    @Headers("Content-Type: application/json")
    @POST("api/hr/reg")
    Call<Void> hrRegis(@Body String body);

    @POST("/api/work/reg")
    Call<Void> sendWorkDetailsToServer(@Body String requestBody);

    @Headers("Content-Type: application/json")
    @POST("api/work/announce")
    Call<Void> Announce(@Body String body);

    @GET("/call")
    Call<ResponseBody> getCallResponse();

    }



