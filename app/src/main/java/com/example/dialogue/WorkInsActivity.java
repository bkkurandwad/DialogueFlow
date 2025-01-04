package com.example.dialogue;

import android.annotation.SuppressLint;
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

import com.example.dialogue.network.ApiService;
import com.example.dialogue.network.RetrofitClient;

import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
public class WorkInsActivity extends AppCompatActivity {
    private static final String TAG = "AddWorkActivity";
    private ApiService apiService;

    private EditText workId;
    private EditText workTitle;
    private EditText workDescription;
    private EditText assignedTo;
    private EditText assignedBy;
    private EditText startTime;
    private EditText endTime;
    private EditText dueDate;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_work_ins);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        Retrofit retrofit = RetrofitClient.getClient("https://webhook-kxvp.onrender.com/");
        apiService = retrofit.create(ApiService.class);

        workId = findViewById(R.id.work_id);
        workTitle = findViewById(R.id.work_title);
        workDescription = findViewById(R.id.work_description);
        assignedTo = findViewById(R.id.assigned_to);
        assignedBy = findViewById(R.id.assigned_by);
        startTime = findViewById(R.id.start_time);
        endTime = findViewById(R.id.end_time);
        dueDate = findViewById(R.id.due_date);
        Button submitButton = findViewById(R.id.submit_button);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendWorkDetailsToServer();
            }
        });
    }


    private void sendWorkDetailsToServer() {
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("work_id", workId.getText().toString());
            jsonObject.put("work_title", workTitle.getText().toString());
            jsonObject.put("work_description", workDescription.getText().toString());
            jsonObject.put("assigned_to", assignedTo.getText().toString());
            jsonObject.put("assigned_by", assignedBy.getText().toString());
            jsonObject.put("start_time", startTime.getText().toString());
            jsonObject.put("end_time", endTime.getText().toString());
            jsonObject.put("due_date", dueDate.getText().toString());
            String requestBody = jsonObject.toString();

            Call<Void> call = apiService.sendWorkDetailsToServer(requestBody);
            call.enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    if (response.isSuccessful()) {
                        Log.d(TAG, "Work details sent to server successfully");
                        Toast.makeText(WorkInsActivity.this, "Work details sent to server successfully", Toast.LENGTH_SHORT).show();
                    } else {
                        Log.e(TAG, "Failed to send work details to server. Error: " + response.message());
                        Toast.makeText(WorkInsActivity.this, "Work details not sent", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {
                    Log.e(TAG, "Failed to send work details to server. Error: " + t.getMessage(), t);
                    Toast.makeText(WorkInsActivity.this, "Network error. Failed to send work details", Toast.LENGTH_SHORT).show();
                }
            });
        } catch (JSONException e) {
            Log.e(TAG, "Error creating JSON object", e);
            Toast.makeText(WorkInsActivity.this, "Error sending work details to server", Toast.LENGTH_SHORT).show();
        }
    }
}

