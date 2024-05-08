package com.example.dialogue;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

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

    public void gotoadwork(View view) { startActivity(new Intent(Hrdash.this, Workreg.class));
    }

    public void gotoupwork(View view) { startActivity(new Intent(Hrdash.this, Workreg.class));
    }

    public void gotorework(View view) { startActivity(new Intent(Hrdash.this, Workreg.class));
    }
}