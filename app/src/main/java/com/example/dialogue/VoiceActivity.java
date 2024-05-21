package com.example.dialogue;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.media.MediaPlayer;

import java.io.IOException;

public class VoiceActivity extends AppCompatActivity {

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_voice);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }


   /* MediaPlayer mediaPlayer = MediaPlayer.create(this, R.raw.song);
    public void startvb(View view) throws IOException {

                mediaPlayer.prepare();

                mediaPlayer.start();

            }
*/

  /*  public void stop(View view) {
        mediaPlayer.stop();

        // Release the MediaPlayer object resources
        mediaPlayer.release();

        // Create a new MediaPlayer object for future use
        mediaPlayer = new MediaPlayer();
    }*/
}


