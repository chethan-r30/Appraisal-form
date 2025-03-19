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

public class SecondPage extends AppCompatActivity {

    private EditText tlpMaxMarks, tlpSelfEval;
    private EditText pdrcMaxMarks, pdrcSelfEval;
    private EditText totalMaxMarks, totalSelfEval;
    private EditText facultySignature, hodSignature, evaluatorSignature, principalSignature;
    private Button navigateButton;

    private FirebaseDatabase db;
    private DatabaseReference reference;
    private String employeeCode;  // To hold the passed Employee Code

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second_page);

        // Initialize Firebase
        db = FirebaseDatabase.getInstance();
        reference = db.getReference("Users");

        // Get the Employee Code from the Intent
        employeeCode = getIntent().getStringExtra("EmployeeCode");

        // Initialize the EditText fields
        tlpMaxMarks = findViewById(R.id.tlpMaxMarks);
        tlpSelfEval = findViewById(R.id.tlpSelfEval);
        pdrcMaxMarks = findViewById(R.id.pdrcMaxMarks);
        pdrcSelfEval = findViewById(R.id.pdrcSelfEval);
        totalMaxMarks = findViewById(R.id.totalMaxMarks);
        totalSelfEval = findViewById(R.id.totalSelfEval);

        facultySignature = findViewById(R.id.facultySignature);
        hodSignature = findViewById(R.id.hodSignature);
        evaluatorSignature = findViewById(R.id.evaluatorSignature);
        principalSignature = findViewById(R.id.principalSignature);

        navigateButton = findViewById(R.id.buttonNavigateToThirdPage);

        // Set the click listener for the Button
        navigateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveDataToFirebase();
            }
        });
    }

    private void saveDataToFirebase() {
        // Retrieve data from the fields
        String totalMax = totalMaxMarks.getText().toString().trim();
        String totalSelf = totalSelfEval.getText().toString().trim();
        String facultyName = facultySignature.getText().toString().trim();
        String hodName = hodSignature.getText().toString().trim();
        String evaluatorName = evaluatorSignature.getText().toString().trim();
        String principalName = principalSignature.getText().toString().trim();

        if (employeeCode == null || employeeCode.isEmpty()) {
            Toast.makeText(this, "Employee code is missing!", Toast.LENGTH_SHORT).show();
            return;
        }

        // Create a map to hold the data
        HashMap<String, String> appraisalData = new HashMap<>();
        appraisalData.put("10_TotalMaxMarks", totalMax);
        appraisalData.put("11_TotalSelfEval", totalSelf);
        appraisalData.put("12_FacultyName", facultyName);
        appraisalData.put("13_HODName", hodName);
        appraisalData.put("14_EvaluatorName", evaluatorName);
        appraisalData.put("15_PrincipalName", principalName);

        // Save the data to Firebase under the corresponding Employee Code
        reference.child(employeeCode).child("Faculty Performance Measuring Index").setValue(appraisalData)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(SecondPage.this, "Data saved successfully!", Toast.LENGTH_SHORT).show();
                            // Navigate to ThirdPage
                            Intent intent = new Intent(SecondPage.this, ThirdPage.class);
                            intent.putExtra("EmployeeCode", employeeCode);
                            startActivity(intent);
                        } else {
                            Toast.makeText(SecondPage.this, "Failed to save data: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
