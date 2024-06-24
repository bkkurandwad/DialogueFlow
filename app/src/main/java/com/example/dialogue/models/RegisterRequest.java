package com.example.dialogue.models;

public class RegisterRequest {




    private String id;
    private String name;
    private String token;
    private String num;
    private String email;
    private String pswrd;

    public RegisterRequest(String id, String name, String token, String num, String email, String pswrd) {
        this.id = id;
        this.name = name;
        this.token = token;
        this.num = num;
        this.email = email;
        this.pswrd = pswrd;
    }

    // Getters and Setters
}

