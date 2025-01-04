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
import com.google.firebase.messaging.FirebaseMessaging;

import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


public class Elogin extends AppCompatActivity {

    private EditText email2;
    public String txtemail;
    private ApiService apiService;
    private EditText password2;
    FirebaseAuth auth;

    private Button test1;
    public String app_token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_elogin);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Retrofit retrofit = RetrofitClient.getClient("https://webhook-kxvp.onrender.com/");
        apiService = retrofit.create(ApiService.class);


        email2 = findViewById(R.id.empemail);
        password2 = findViewById(R.id.epswrd);
        Button login = findViewById(R.id.esignin);
        Button reg = findViewById(R.id.nur);
        auth = FirebaseAuth.getInstance();
        test1 = findViewById(R.id.gem);

        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(task -> {
                    if (!task.isSuccessful()) {
                        Log.w("FCM", "Fetching FCM registration token failed", task.getException());
                        return;
                    }
                    // Get new FCM registration token
                    app_token = task.getResult();
                    // Log or send the token to your server
                    Log.d("FCM", "Token: " + app_token);
                });


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                txtemail = email2.getText().toString();
                String txtpwd = password2.getText().toString();

                if (TextUtils.isEmpty(txtemail) || TextUtils.isEmpty(txtpwd))
                    Toast.makeText(Elogin.this, "Empty credientials ", Toast.LENGTH_SHORT).show();
                else if (txtpwd.length() < 3)
                    Toast.makeText(Elogin.this, "wrong password", Toast.LENGTH_SHORT).show();
                else {
                    if(app_token == null){
                        Toast.makeText(Elogin.this, "toast is empty", Toast.LENGTH_SHORT).show();
                    }

                    Loginuser(txtemail, txtpwd);

                }
            }
        });

        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Elogin.this, Eregdetails.class));
            }
        });


        test1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Elogin.this, Edash.class));
            }
        });

    }

    private void loginuser(String mail, String pwd) {
        auth.signInWithEmailAndPassword(mail, pwd).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                Toast.makeText(Elogin.this, "success login", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(Elogin.this, Edash.class));
            }
        });
    }

    public void Loginuser(String mail, String pswrd) {
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("email", mail);
            jsonObject.put("pswrd", pswrd);
            String requestBody = jsonObject.toString();

            Call<Void> call = apiService.empLogin(requestBody);
            call.enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    if (response.isSuccessful()) {
                        Log.d(TAG, "login req sent to server successfully");
                        Tokenuser(txtemail, app_token);
                        Toast.makeText(Elogin.this, "LOGIN SUCCESSFUL", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(Elogin.this,Edash.class));

                    } else {
                        Log.e(TAG, "Failed to send token to server. Error: " + response.message());
                        Toast.makeText(Elogin.this, "INVALID CREDINTIALS", Toast.LENGTH_SHORT).show();

                    }
                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {
                    Log.e(TAG, "Failed to send token to server. Error: " + t.getMessage(), t);
                    Toast.makeText(Elogin.this, "Network error. Failed to lohin", Toast.LENGTH_SHORT).show();
                }
            });
        } catch (JSONException e) {
            Log.e(TAG, "Error creating JSON object", e);
            Toast.makeText(Elogin.this, "Error sending req to server", Toast.LENGTH_SHORT).show();
        }
    }

    public void Tokenuser(String mail, String token) {
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("email", mail);
            jsonObject.put("token", token);
            String requestBody = jsonObject.toString();

            Call<Void> call = apiService.empToken(requestBody);
            call.enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    if (response.isSuccessful()) {
                        Log.d(TAG, "Token sent to DB successfully");
                        Toast.makeText(Elogin.this, "Token sent to server successfully", Toast.LENGTH_SHORT).show();
                    } else {
                        Log.e(TAG, "Failed to send token to server. Error: " + response.message());
                        Toast.makeText(Elogin.this, "Token not sent", Toast.LENGTH_SHORT).show();
                    }

                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {
                    Log.e(TAG, "Failed to send token to server. Error: " + t.getMessage(), t);
                    Toast.makeText(Elogin.this, "Network error. Failed to Token Server", Toast.LENGTH_SHORT).show();
                }
            });
        } catch (JSONException e) {
            Log.e(TAG, "Error creating JSON object", e);
            Toast.makeText(Elogin.this, "Error making JSON object for server", Toast.LENGTH_SHORT).show();
        }
    }

    public void create_token() {
       // final String[] generated_token = {null};


        //return generated_token[0];
    }
}
