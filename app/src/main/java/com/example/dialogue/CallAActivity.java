package com.example.dialogue;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //getActionBar().setTitle("Call");
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_call_aactivity);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        mediaPlayer = new MediaPlayer();
        try {
            Toast.makeText(this, "Starting message",Toast.LENGTH_SHORT).show();
            startAudioPlayback();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }





    public void cut_call(View view) {
        Toast.makeText(this, "exiting the call", Toast.LENGTH_SHORT).show();
        stopAudioPlayback();
        System.exit(0);
    }

    private void startAudioPlayback() throws IOException {
        stopAudioPlayback();
        // Set data source to the MP3 file
        File file = new File(getFilesDir(), "call2.mp3");
        mediaPlayer.setDataSource(this, Uri.fromFile(file));

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
    }
}



