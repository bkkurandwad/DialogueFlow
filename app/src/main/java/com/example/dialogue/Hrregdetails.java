package com.example.dialogue;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.dialogue.network.ApiService;
import com.example.dialogue.network.RetrofitClient;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;



public class Hrregdetails extends AppCompatActivity {

    private EditText id1;
    private EditText name1;
    private EditText num1;
    private EditText email1;
    private EditText pswrd1;

    private ApiService apiservice;
    public Hrregdetails() throws IOException {
    }

    Retrofit retrofit = RetrofitClient.getClient("https://webhook-kxvp.onrender.com/");
    ApiService news = retrofit.create(ApiService.class);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_hrregdetails);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        id1 = findViewById(R.id.hret1);
        name1 = findViewById(R.id.hret2);
        num1 = findViewById(R.id.hret3);
        email1 = findViewById(R.id.hret4);
        pswrd1 = findViewById(R.id.hret5);
    }

    public void RegisterHr(String eid, String name, String number, String mail, String pswrd) {
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id", eid);
            jsonObject.put("name", name);
            jsonObject.put("token", "");
            jsonObject.put("num", number);
            jsonObject.put("email", mail);
            jsonObject.put("pswrd", pswrd);
            //RegisterRequest request = new RegisterRequest(eid, name, "", number, mail, pswrd);
            String requestBody = jsonObject.toString();

            Call<Void> call = news.hrRegis(requestBody);
            Toast.makeText(Hrregdetails.this, "Trying to register", Toast.LENGTH_SHORT).show();
            call.enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    if (response.isSuccessful()) {
                        Log.d(TAG, "Register request sent to server successfully");
                        Toast.makeText(Hrregdetails.this, "Registered the hr successfully", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(Hrregdetails.this, Hrlogin.class));
                    } else {
                        Log.e(TAG, "Failed to send req to server. Error: " + response.message());
                        Toast.makeText(Hrregdetails.this, "Bad response from server", Toast.LENGTH_SHORT).show();
                    }

                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {
                    Log.e(TAG, "Failed to connect " + t.getMessage(), t);
                    Toast.makeText(Hrregdetails.this, "Network error. Failed to register", Toast.LENGTH_SHORT).show();
                }
            });
        } catch (JSONException e) {
            Log.e(TAG, "Error creating JSON object", e);
            Toast.makeText(Hrregdetails.this, "Error creating json", Toast.LENGTH_SHORT).show();
        }
    }

    public void reghr(View view) {
        String eid = id1.getText().toString().trim();
        String name = name1.getText().toString().trim();
        String number = num1.getText().toString().trim();
        String email = email1.getText().toString().trim();
        String pswrd = pswrd1.getText().toString().trim();

        // Validate the inputs
        if (eid.isEmpty() || name.isEmpty() || number.isEmpty() || email.isEmpty() || pswrd.isEmpty()) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        // Call the register method
        RegisterHr(eid, name, number, email, pswrd);
    }
}