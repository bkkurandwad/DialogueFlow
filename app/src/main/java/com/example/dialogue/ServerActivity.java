package com.example.dialogue;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.os.Bundle;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.dialogue.models.TextRequest;
import com.example.dialogue.network.RetrofitClient;
import com.example.dialogue.network.ApiService;

import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.speech.tts.TextToSpeechService;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.io.IOException;

public class ServerActivity extends AppCompatActivity {

    private TextToSpeechService service;

    private TextView textView;
    private ApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_server);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Initialize TextView
        textView = findViewById(R.id.serst);

        // Initialize Retrofit using RetrofitClient
        Retrofit retrofit = RetrofitClient.getClient("https://webhook-kxvp.onrender.com/");

        // Create ApiService instance
        apiService = retrofit.create(ApiService.class);

        // Make network request
        makeNetworkRequest();
    }

    private void sendTextAndReceiveAudio(String text) {
        TextRequest textRequest = new TextRequest(text);

        Call<Void> call = apiService.synthesizeText(textRequest);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    // Handle successful response (audio received)
                    // You can play the audio or save it locally
                } else {
                    // Handle unsuccessful response
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                // Handle failure (network error, etc.)
            }
        });
    }

    private void makeNetworkRequest() {
        Call<ResponseBody> call = apiService.getHelloWorld();
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful() && response.body() != null) {
                    String helloWorldResponse = null;
                    try {
                        helloWorldResponse = response.body().string();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    // Update TextView with the received response
                    textView.setText(helloWorldResponse);
                } else {
                    // Handle unsuccessful response
                    Log.e(TAG, "Unsuccessful response: " + response.code());
                    textView.setText("Error: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                // Handle failure
                Log.e(TAG, "Network request failed: " + t.getMessage(), t);
                textView.setText("Network request failed: " + t.getMessage());
            }
        });
    }



    public void callserver(View view) {
        makeNetworkRequest();
    }
}
