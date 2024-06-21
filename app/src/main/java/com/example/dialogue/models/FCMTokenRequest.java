package com.example.dialogue.models;

// FCMTokenRequest.java
import com.google.gson.annotations.SerializedName;

public class FCMTokenRequest {

    @SerializedName("token")
    private String token;

    public FCMTokenRequest(String token) {
        this.token = token;
    }
}
