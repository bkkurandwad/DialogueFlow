package com.example.dialogue.network;
import com.example.dialogue.models.TextRequest;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiService {
    @GET("/app")
    Call<ResponseBody> getHelloWorld();
    @POST("/synthesis") // Replace with your endpoint
    Call<Void> synthesizeText(@Body TextRequest textRequest);

   // @POST("/posttoken")
    //Call<Void> sendFCMTokenToServer(@Body String requestBody);

    @GET("/call")
    Call<ResponseBody> getCallResponse();

    }



