package com.example.dialogue;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.content.Intent;
import android.os.Bundle;
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

import com.example.dialogue.models.RegisterRequest;
import com.example.dialogue.network.ApiService;
import com.example.dialogue.network.RetrofitClient;

import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class Eregdetails extends AppCompatActivity {

    private EditText nameEditText;
    private EditText eidEditText;
    private EditText phnnoEditText;
    private ApiService apiService;

    private EditText addrEditText;
    private Button submitButton;  // Added button variable
  //  private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_eregdetails);

        Intent intent = getIntent();
        String email = intent.getStringExtra("email");
        String pswrd = intent.getStringExtra("pwd");

        // Find all the EditText views
        eidEditText = findViewById(R.id.eet1);
        nameEditText = findViewById(R.id.eet2);  // Update these IDs with your actual EditTexts if necessary
        phnnoEditText = findViewById(R.id.eet4);
        submitButton = findViewById(R.id.sube);

        Retrofit retrofit = RetrofitClient.getClient("https://webhook-kxvp.onrender.com/");
        apiService = retrofit.create(ApiService.class);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String eid = eidEditText.getText().toString();
                String name = nameEditText.getText().toString();
                String num = phnnoEditText.getText().toString();
                RegisterUser(eid,name,num,email,pswrd);
            }
        });

    }

    public void RegisterUser(String eid, String name, String number, String mail, String pswrd) {
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id", eid);
            jsonObject.put("name", name);
            jsonObject.put("token", "");
            jsonObject.put("num", number);
            jsonObject.put("email", mail);
            jsonObject.put("pswrd", pswrd);
            //RegisterRequest request = new RegisterRequest(eid, name, "", number, mail, pswrd);
            String requestBody = jsonObject.toString();

            Call<Void> call = apiService.empRegis(requestBody);
            Toast.makeText(Eregdetails.this, "Trying to server successfully", Toast.LENGTH_SHORT).show();
            call.enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    if (response.isSuccessful()) {
                        Log.d(TAG, "Register request sent to server successfully");
                        Toast.makeText(Eregdetails.this, "Token sent to server successfully", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(Eregdetails.this, Elogin.class));
                    } else {
                        Log.e(TAG, "Failed to send req to server. Error: " + response.message());
                        Toast.makeText(Eregdetails.this, "Token not sent", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {
                    Log.e(TAG, "Failed to register to server. Error: " + t.getMessage(), t);
                    Toast.makeText(Eregdetails.this, "Network error. Failed to lohin", Toast.LENGTH_SHORT).show();
                }
            });
        } catch (JSONException e) {
            Log.e(TAG, "Error creating JSON object", e);
            Toast.makeText(Eregdetails.this, "Error sending req to server", Toast.LENGTH_SHORT).show();
        }
    }



}
