package com.example.dialogue;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.dialogue.network.ApiService;
import com.example.dialogue.network.RetrofitClient;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class Hrlogin extends AppCompatActivity {


    private EditText email3;
    private EditText password3;
    private Button login;
    private FirebaseAuth auth;

    private Button reg;

    private Button testh;

    Retrofit retrofit = RetrofitClient.getClient("https://webhook-kxvp.onrender.com/");
    ApiService newshr = retrofit.create(ApiService.class);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_hrlogin);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        email3 = findViewById(R.id.hremail2);
        password3 = findViewById(R.id.hrpswrd);
        login = findViewById(R.id.login);
        reg = findViewById(R.id.nur);
        auth = FirebaseAuth.getInstance();
        testh = findViewById(R.id.testhr);



        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String txtemail = email3.getText().toString();
                String txtpwd = password3.getText().toString();

                if (TextUtils.isEmpty(txtemail) || TextUtils.isEmpty(txtpwd))
                    Toast.makeText(Hrlogin.this, "Empty credientials ", Toast.LENGTH_SHORT).show();
                else if (txtpwd.length() < 6)
                    Toast.makeText(Hrlogin.this, "wrong password", Toast.LENGTH_SHORT).show();
                else {
                 //  loginuser(txtemail, txtpwd);
                    LoginHr(txtemail, txtpwd);
                }
            }
        });

        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Hrlogin.this,Hrregdetails.class));
            }
        });

        testh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Hrlogin.this,Hrdash.class));
            }
        });

    }

    private void loginuser(String mail, String pwd) {
        auth.signInWithEmailAndPassword(mail, pwd).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                Toast.makeText(Hrlogin.this, "success login", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(Hrlogin.this,Hrdash.class));
            }
        });
    }

    public void LoginHr(String mail, String pswrd) {
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("email", mail);
            jsonObject.put("pswrd", pswrd);
            String requestBody = jsonObject.toString();

            Call<Void> call = newshr.hrLogin(requestBody);
            call.enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    if (response.isSuccessful()) {
                        Log.d(TAG, "login req sent to server successfully");
                        Toast.makeText(Hrlogin.this, "LOGIN SUCCESSFUL", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(Hrlogin.this,Hrdash.class));

                    } else {
                        Log.e(TAG, "Failed to send token to server. Error: " + response.message());
                        Toast.makeText(Hrlogin.this, "INVALID CREDINTIALS", Toast.LENGTH_SHORT).show();

                    }
                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {
                    Log.e(TAG, "Failed to send token to server. Error: " + t.getMessage(), t);
                    Toast.makeText(Hrlogin.this, "Network error. Failed to lohin", Toast.LENGTH_SHORT).show();
                }
            });
        } catch (JSONException e) {
            Log.e(TAG, "Error creating JSON object", e);
            Toast.makeText(Hrlogin.this, "Error sending req to server", Toast.LENGTH_SHORT).show();
        }
    }


    }
