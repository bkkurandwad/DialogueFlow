package com.example.dialogue;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Eregister extends AppCompatActivity {

    private EditText email;
    private EditText password;
    private Button reg;
    private FirebaseAuth auth;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_eregister);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        email = findViewById(R.id.editTextTextEmailAddress);
        password = findViewById(R.id.editTextTextPassword);
        reg = findViewById(R.id.registerButton);
        auth = FirebaseAuth.getInstance();

        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String txtemail = email.getText().toString();
                String txtpwd = password.getText().toString();

                if (TextUtils.isEmpty(txtemail) || TextUtils.isEmpty(txtpwd))
                    Toast.makeText(Eregister.this, "Empty credientials ", Toast.LENGTH_SHORT).show();
                else if (txtpwd.length() < 6)
                    Toast.makeText(Eregister.this, "wrong password", Toast.LENGTH_SHORT).show();
                else {
                    registeruser(txtemail, txtpwd);
                }
            }
        });

    }

    private void registeruser(String mail, String pwd) {
        auth.createUserWithEmailAndPassword(mail, pwd).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                Toast.makeText(Eregister.this, "Registration successful", Toast.LENGTH_SHORT).show();
                //RegperActivity.send(mail);
                startActivity(new Intent(Eregister.this, Elogin.class));
            }
        });
    }

    }
