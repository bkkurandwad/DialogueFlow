package com.example.dialogue;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.auth.FirebaseAuth;

public class Edash extends AppCompatActivity {

    private Button signout2;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_edash);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        signout2 = findViewById(R.id.sout2);

        signout2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // FirebaseAuth.getInstance().signOut();
                Toast.makeText(Edash.this, "User Signed Out", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(Edash.this, Firstpage.class));
            }
        });



    }

    public void gotochat(View view) {
        startActivity(new Intent(Edash.this, Chatbot.class));
    }

    public void gotowork(View view) { startActivity(new Intent(Edash.this, Work.class));
    }

    public void gotovoicebot(View view) { startActivity(new Intent(Edash.this, VoiceActivity.class));
    }

    public void gotonot(View view) { startActivity(new Intent(Edash.this, NotificationActivity.class));
    }

    public void gotobot(View view) { startActivity(new Intent(Edash.this, DCV_activity.class));
    }

    public void callbot(View view) {
        TextView tw = findViewById(R.id.tw);
        tw.setText("+1 323-814-5434");
        }

}