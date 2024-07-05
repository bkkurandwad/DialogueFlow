package com.example.dialogue;

//import static com.example.dialogue.AlarmReceiver.mediaPlayer;

import android.content.Intent;
import android.media.MediaActionSound;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.telecom.Call;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class CallRActivity extends AppCompatActivity {

    private static MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_call_ractivity);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
       // mediaPlayer2 = new MediaPlayer();
    }



    public void r_call(View view) {

        startActivity(new Intent(CallRActivity.this, CallAActivity.class));
        stopRingtone();
    }

    public void c_call(View view) {
        //stopAudioPlayback();
       // stopRingtone();
        AlarmReceiver.stopRingtone();
        startActivity(new Intent(CallRActivity.this, Edash.class));
    }
//    private void stopAudioPlayback() {
//        // Stop playing the audio
//        if (mediaPlayer2 != null && mediaPlayer2.isPlaying()) {
//            mediaPlayer2.pause();
//            mediaPlayer2.seekTo(0); // Rewind to the beginning
//        }
//    }

    private void stopRingtone() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }
}
