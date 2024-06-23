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
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Elogin extends AppCompatActivity {

    private EditText email2;
    private EditText password2;
    FirebaseAuth auth;
    private ApiService apiService;
    private Button test1;

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



        email2 = findViewById(R.id.empemail);
        password2 = findViewById(R.id.epswrd);
        Button login = findViewById(R.id.esignin);
        Button reg = findViewById(R.id.nur);
        auth = FirebaseAuth.getInstance();
        test1 = findViewById(R.id.gem);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String txtemail = email2.getText().toString();
                String txtpwd = password2.getText().toString();

                if (TextUtils.isEmpty(txtemail) || TextUtils.isEmpty(txtpwd))
                    Toast.makeText(Elogin.this, "Empty credientials ", Toast.LENGTH_SHORT).show();
                else if (txtpwd.length() < 6)
                    Toast.makeText(Elogin.this, "wrong password", Toast.LENGTH_SHORT).show();
                else {
                    loginuser(txtemail, txtpwd);
                }
            }
        });

        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Elogin.this,Eregister.class));
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
                startActivity(new Intent(Elogin.this,Edash.class));
            }
        });
    }

    public void Loginuser(String mail, String pswrd) {
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("mail", mail);
            jsonObject.put("pswrd", pswrd);
            String requestBody = jsonObject.toString();

            Call<Void> call = apiService.empLogin(requestBody);
            call.enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    if (response.isSuccessful()) {
                        Log.d(TAG, "login req sent to server successfully");
                        Toast.makeText(Elogin.this, "Token sent to server successfully", Toast.LENGTH_SHORT).show();
                    } else {
                        Log.e(TAG, "Failed to send token to server. Error: " + response.message());
                        Toast.makeText(Elogin.this, "Token not sent", Toast.LENGTH_SHORT).show();
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

}
