package com.example.dialogue;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.os.Bundle;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.dialogue.models.FCMTokenRequest;
import com.example.dialogue.models.TextRequest;
import com.example.dialogue.network.RetrofitClient;
import com.example.dialogue.network.ApiService;
import com.google.firebase.messaging.FirebaseMessaging;

import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.speech.tts.TextToSpeechService;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;

public class ServerActivity extends AppCompatActivity {

    private TextToSpeechService service;
    private MediaPlayer mediaPlayer;
    private TextView textView;
    private ApiService apiService;
    private String tok;

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
        newtoken();

        mediaPlayer = new MediaPlayer();
        // Make network request
      //  makeNetworkRequest();
        

    }

    private void newtoken() {
        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(task -> {
                    if (!task.isSuccessful()) {
                        Log.w("FCM", "Fetching FCM registration token failed", task.getException());
                        return;
                    }

                    // Get new FCM registration token
                    String token = task.getResult();

                    // Log or send the token to your server
                    Log.d("FCM", "Token: " + token);
                    textView.setText("Token: "+ token);
tok = token;

                });

    }

    private void startAudioPlayback() throws IOException {
        // Set data source to the MP3 file
        File file = new File(getFilesDir(), "call.mp3");
        mediaPlayer.setDataSource(this, Uri.fromFile(file));

        // Prepare and start playback
        mediaPlayer.prepare();
        mediaPlayer.start();
    }

    private void stopAudioPlayback() {
        // Stop playing the audio
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
            mediaPlayer.seekTo(0); // Rewind to the beginning
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Release the MediaPlayer resources when the activity is destroyed
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

    private void getCallResponse() {
        Call<ResponseBody> call = apiService.getCallResponse();
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful() && response.body() != null) {
                    try {
                        // Save the MP3 file to internal storage
                        File file = new File(getFilesDir(), "call.mp3");
                        InputStream inputStream = null;
                        FileOutputStream outputStream = null;

                        try {
                            inputStream = response.body().byteStream();
                            outputStream = new FileOutputStream(file);

                            byte[] buffer = new byte[4096];
                            int bytesRead;
                            while ((bytesRead = inputStream.read(buffer)) != -1) {
                                outputStream.write(buffer, 0, bytesRead);
                            }

                            outputStream.flush();

                            Log.d(TAG, "MP3 file saved successfully");

                        } catch (IOException e) {
                            Log.e(TAG, "Error saving MP3 file", e);
                        } finally {
                            if (inputStream != null) {
                                inputStream.close();
                            }
                            if (outputStream != null) {
                                outputStream.close();
                            }
                        }

                    } catch (IOException e) {
                        Log.e(TAG, "Error reading response body", e);
                    }
                } else {
                    Log.e(TAG, "Unsuccessful response: " + response.code());
                    textView.setText("Error: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e(TAG, "Network request failed", t);
                textView.setText("Network request failed: " + t.getMessage());
            }
        });
    }

    public void sendToservernow(String token) {
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("token", token);

            String requestBody = jsonObject.toString();

            Call<Void> call = apiService.sendFCMTokenToServer(requestBody);
            call.enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    if (response.isSuccessful()) {
                        Log.d(TAG, "Token sent to server successfully");
                        Toast.makeText(ServerActivity.this, "Token sent to server successfully", Toast.LENGTH_SHORT).show();
                    } else {
                        Log.e(TAG, "Failed to send token to server. Error: " + response.message());
                        Toast.makeText(ServerActivity.this, "Token not sent", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {
                    Log.e(TAG, "Failed to send token to server. Error: " + t.getMessage(), t);
                    Toast.makeText(ServerActivity.this, "Network error. Failed to send token", Toast.LENGTH_SHORT).show();
                }
            });
        } catch (JSONException e) {
            Log.e(TAG, "Error creating JSON object", e);
            Toast.makeText(ServerActivity.this, "Error sending token to server", Toast.LENGTH_SHORT).show();
        }
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
        //makeNetworkRequest();
        getCallResponse();
    }

    public void PLAYNOW(View view) {
        try {
            startAudioPlayback();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void STOPNOW(View view) {
        stopAudioPlayback();
    }

    public void postserver(View view) {
        if (tok != null && !tok.isEmpty()) {
            sendToservernow(tok);
        } else {
            Toast.makeText(this, "Token is not available. Please try again later.", Toast.LENGTH_SHORT).show();
            Log.e(TAG, "Token is not available.");
        }
    }

}
