package com.example.dialogue;

import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.File;
import java.io.IOException;

public class CallAActivity extends AppCompatActivity {

    private MediaPlayer mediaPlayer;
    private TextView timerTextView;
    private Handler handler;
    private int secondsPassed;
    private Runnable updateTimer;

    private AudioManager audioManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //getActionBar().setTitle("Call");
        audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_call_aactivity);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        timerTextView = findViewById(R.id.timerTextView);
        handler = new Handler();
        secondsPassed = 0;

        updateTimer = new Runnable() {
            @Override
            public void run() {
                int minutes = secondsPassed / 60;
                int seconds = secondsPassed % 60;
                timerTextView.setText(String.format("%02d:%02d", minutes, seconds));
                secondsPassed++;
                handler.postDelayed(this, 1000);
            }
        };

        mediaPlayer = new MediaPlayer();
        try {
            Toast.makeText(this, "Starting message",Toast.LENGTH_SHORT).show();
            AlarmReceiver.stopRingtone();
            handler.post(updateTimer);
            startAudioPlayback();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }





    public void cut_call(View view) {
        Toast.makeText(this, "exiting the call", Toast.LENGTH_SHORT).show();
        stopAudioPlayback();
        System.exit(0);
        startActivity(new Intent(CallAActivity.this,Edash.class));
    }

    private void startAudioPlayback() throws IOException {
        stopAudioPlayback();
        audioManager.requestAudioFocus(focusRequest -> {}, AudioManager.STREAM_VOICE_CALL, AudioManager.AUDIOFOCUS_GAIN);
        audioManager.setMode(AudioManager.MODE_IN_COMMUNICATION);
        audioManager.setSpeakerphoneOn(false);
        // Set data source to the MP3 file
        File file = new File(getFilesDir(), "hrcall.mp3");
        mediaPlayer.setDataSource(this, Uri.fromFile(file));

        // Set OnCompletionListener to navigate to Edash activity when the audio finishes playing
        mediaPlayer.setOnCompletionListener(mp -> {
            Toast.makeText(CallAActivity.this, "Call ended", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(CallAActivity.this, Edash.class));
            finish(); // Close the current activity
        });

        // Prepare and start playback
        mediaPlayer.prepare();
        mediaPlayer.start();
        Toast.makeText(this, "Call started", Toast.LENGTH_SHORT).show();
    }

    private void stopAudioPlayback() {
        // Stop playing the audio
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
            mediaPlayer.seekTo(0); // Rewind to the beginning
        }
    }
    protected void onDestroy() {
        super.onDestroy();
        // Release the MediaPlayer resources when the activity is destroyed
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
        handler.removeCallbacks(updateTimer);
        audioManager.setMode(AudioManager.MODE_NORMAL);
    }
}



