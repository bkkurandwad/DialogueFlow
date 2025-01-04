package com.example.dialogue;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.auth.oauth2.GoogleCredentials;
//import com.google.cloud.firestore.Firestore;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;

import java.io.IOException;


public class Hrregdetails extends AppCompatActivity {

    public Hrregdetails() throws IOException {
    }

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
    }

    // Use the application default credentials
    GoogleCredentials credentials = GoogleCredentials.getApplicationDefault();
  //  FirebaseOptions options = new FirebaseOptions.Builder();
          //  .setProjectId(credentials)
        //    .setProjectId(projectId)
         //   .build();
//FirebaseApp.initializeApp(options);

   // Firestore db = FirestoreClient.getFirestore();
}