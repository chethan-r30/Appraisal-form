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

public class FourthPage extends AppCompatActivity {

    // Declare EditText fields
    private EditText qualificationImprovementInput, certifiedTrainerInput, certificationReputeInput, awardsRecognitionInput;
    private Button navigateButtontry2;

    // Declare Firebase variables
    FirebaseDatabase db;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fourth_page);

        // Initialize Firebase
        db = FirebaseDatabase.getInstance();
        reference = db.getReference("Users");

        // Initialize EditText fields
        qualificationImprovementInput = findViewById(R.id.row1UserInput);
        certifiedTrainerInput = findViewById(R.id.row2UserInput);
        certificationReputeInput = findViewById(R.id.row3UserInput);
        awardsRecognitionInput = findViewById(R.id.row4UserInput);

        // Get EmployeeCode passed from ThirdPage
        String employeeCode = getIntent().getStringExtra("EmployeeCode");

        // Initialize the Button
        navigateButtontry2 = findViewById(R.id.buttonNavigateToFifthPage);

        // Set the click listener for the Button
        navigateButtontry2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Retrieve data from EditTexts
                String qualificationImprovement = qualificationImprovementInput.getText().toString().trim();
                String certifiedTrainer = certifiedTrainerInput.getText().toString().trim();
                String certificationRepute = certificationReputeInput.getText().toString().trim();
                String awardsRecognition = awardsRecognitionInput.getText().toString().trim();

                // Validate required fields
                if (qualificationImprovement.isEmpty() || certifiedTrainer.isEmpty() || certificationRepute.isEmpty() || awardsRecognition.isEmpty()) {
                    Toast.makeText(FourthPage.this, "Please fill all required fields!", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Create a map to hold the data
                HashMap<String, String> pdrcData = new HashMap<>();
                pdrcData.put("QualificationImprovement", qualificationImprovement);
                pdrcData.put("CertifiedTrainer", certifiedTrainer);
                pdrcData.put("CertificationRepute", certificationRepute);
                pdrcData.put("AwardsRecognition", awardsRecognition);

                // Save data to Firebase under the EmployeeCode
                reference.child(employeeCode).child("PDRCDetails").setValue(pdrcData)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(FourthPage.this, "Data saved successfully!", Toast.LENGTH_SHORT).show();

                                    // Navigate to FifthPage
                                    Intent intent = new Intent(FourthPage.this, FifthPage.class);
                                    intent.putExtra("EmployeeCode", employeeCode);
                                    startActivity(intent);
                                } else {
                                    Toast.makeText(FourthPage.this, "Failed to save data: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });
    }
}
