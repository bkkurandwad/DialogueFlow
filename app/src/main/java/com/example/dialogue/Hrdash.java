package com.example.dialogue;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;


import com.google.firebase.auth.FirebaseAuth;

public class Hrdash extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_hrdash);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    public void gotoempd(View view) { startActivity(new Intent(Hrdash.this, Empreg.class));
    }

    public void gotoademp(View view) { startActivity(new Intent(Hrdash.this, Empreg.class));
    }

    public void gotoremp(View view) { startActivity(new Intent(Hrdash.this, Empreg.class));
    }

    public void gotowork(View view) { startActivity(new Intent(Hrdash.this, Workreg.class));
    }

    public void gotoadwork(View view) { startActivity(new Intent(Hrdash.this, WorkInsActivity.class));
    }

    public void gotoupwork(View view) { startActivity(new Intent(Hrdash.this, Workreg.class));
    }

    public void gotorework(View view) { startActivity(new Intent(Hrdash.this, Workreg.class));
    }

    public void signout(View view) {
        FirebaseAuth.getInstance().signOut();
        Toast.makeText(Hrdash.this, "User Signed Out", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(Hrdash.this, Firstpage.class));
    }


    public void gotobot(View view) {
        TextView tw = findViewById(R.id.tw);
        tw.setText("+1 323-814-5434");
    }

    public void gotochat(View view) {
        startActivity(new Intent(Hrdash.this, Chatbot.class));
    }

    public void gocht(View view) { startActivity(new Intent(Hrdash.this, DCV_activity.class));
    }
}