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

public class SixthPage extends AppCompatActivity {

    // Declare EditText fields
    private EditText row1UserInput, row2UserInput, row3UserInput, row4UserInput, row5UserInput;
    private Button navigateButtontry2;

    // Declare Firebase variables
    FirebaseDatabase db;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sixth_page); // Ensure your XML is named activity_sixth_page.xml

        // Initialize Firebase
        db = FirebaseDatabase.getInstance();
        reference = db.getReference("Users");

        // Initialize EditText fields
        row1UserInput = findViewById(R.id.row1UserInput);
        row2UserInput = findViewById(R.id.row2UserInput);
        row3UserInput = findViewById(R.id.row3UserInput);
        row4UserInput = findViewById(R.id.row4UserInput);
        row5UserInput = findViewById(R.id.row5UserInput);

        // Get EmployeeCode passed from the previous page (e.g., FifthPage)
        String employeeCode = getIntent().getStringExtra("EmployeeCode");

        // Initialize the Button
        navigateButtontry2 = findViewById(R.id.buttonNavigateToSeventhPage);

        // Set the click listener for the Button
        navigateButtontry2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Retrieve data from EditTexts
                String contributionLevel = row1UserInput.getText().toString().trim();
                String organizingTraining = row2UserInput.getText().toString().trim();
                String participationTraining = row3UserInput.getText().toString().trim();
                String internalRevenueGeneration = row4UserInput.getText().toString().trim();
                String departmentResponsibilities = row5UserInput.getText().toString().trim();

                // Validate required fields
                if (contributionLevel.isEmpty() || organizingTraining.isEmpty() || participationTraining.isEmpty() ||
                        internalRevenueGeneration.isEmpty() || departmentResponsibilities.isEmpty()) {
                    Toast.makeText(SixthPage.this, "Please fill all required fields!", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Create a map to hold the data
                HashMap<String, String> departmentData = new HashMap<>();
                departmentData.put("Contribution In Activities", contributionLevel);
                departmentData.put("Organizing Training", organizingTraining);
                departmentData.put("Participation In Training", participationTraining);
                departmentData.put("Internal Revenue Generation", internalRevenueGeneration);
                departmentData.put("Department Responsibilities", departmentResponsibilities);

                // Save data to Firebase under the EmployeeCode
                reference.child(employeeCode).child("Professional Involvement").setValue(departmentData)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(SixthPage.this, "Data saved successfully!", Toast.LENGTH_SHORT).show();

                                    // Navigate to SeventhPage
                                    Intent intent = new Intent(SixthPage.this, SeventhPage.class);
                                    intent.putExtra("EmployeeCode", employeeCode);
                                    startActivity(intent);
                                } else {
                                    Toast.makeText(SixthPage.this, "Failed to save data: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });
    }
}
