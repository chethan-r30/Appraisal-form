package com.example.appraisalform;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class SeventhPage extends AppCompatActivity {

    // Declare EditText fields
    private EditText row1UserInput;
    private Button navigateButtontry2;

    // Declare Firebase variables
    FirebaseDatabase db;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seventh_page); // Ensure your XML is named activity_seventh_page.xml

        // Initialize Firebase
        db = FirebaseDatabase.getInstance();
        reference = db.getReference("Users");

        // Initialize EditText fields
        row1UserInput = findViewById(R.id.row1UserInput);

        // Get EmployeeCode passed from the previous page (e.g., SixthPage)
        String employeeCode = getIntent().getStringExtra("EmployeeCode");

        // Initialize the Button
        navigateButtontry2 = findViewById(R.id.buttonNavigateToEightPage);

        // Set the click listener for the Button
        navigateButtontry2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Retrieve data from EditTexts
                String institutionalResponsibilities = row1UserInput.getText().toString().trim();

                // Validate required field
                if (institutionalResponsibilities.isEmpty()) {
                    Toast.makeText(SeventhPage.this, "Please fill the field!", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Create a map to hold the data
                HashMap<String, String> institutionalData = new HashMap<>();
                institutionalData.put("Institutional Responsibilities", institutionalResponsibilities);

                // Save data to Firebase under the EmployeeCode
                reference.child(employeeCode).child("Responsibility Assigned").setValue(institutionalData)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(SeventhPage.this, "Data saved successfully!", Toast.LENGTH_SHORT).show();

                                    // Navigate to EightPage
                                    Intent intent = new Intent(SeventhPage.this, EightPage.class);
                                    intent.putExtra("EmployeeCode", employeeCode); // Pass EmployeeCode
                                    startActivity(intent);
                                } else {
                                    Toast.makeText(SeventhPage.this, "Failed to save data: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });
    }
}
