package com.example.dialogue;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

//import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Eregdetails extends AppCompatActivity {

    private EditText nameEditText;
    private EditText eidEditText;
    private EditText phnnoEditText;
    private EditText addrEditText;
    private Button saveButton;  // Added button variable
  //  private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_eregdetails);

        // Initialize Firebase Firestore
      //  db = FirebaseFirestore.getInstance();

        // Find all the EditText views
        nameEditText = findViewById(R.id.eet2);  // Update these IDs with your actual EditTexts if necessary
        eidEditText = findViewById(R.id.eet1);
        phnnoEditText = findViewById(R.id.eet4);
        addrEditText = findViewById(R.id.eet5);

        // Find the save button
        saveButton = findViewById(R.id.sube);

        // Set click listener for the save button
        saveButton.setOnClickListener(v -> saveEmployeeData());
    }

    private void saveEmployeeData() {
        String name = nameEditText.getText().toString();
        String eid = eidEditText.getText().toString();
        String phnno = phnnoEditText.getText().toString();
        String addr = addrEditText.getText().toString();

        // Create a Map to store employee data
        Map<String, Object> employee = new HashMap<>();
        employee.put("name", name);
        employee.put("eid", eid);
        employee.put("phnno", phnno);
        employee.put("addr", addr);

        // Add employee data to Cloud Firestore

    }
}
