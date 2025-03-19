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

import java.util.LinkedHashMap;

public class FirstPage extends AppCompatActivity {

    FirebaseDatabase db;
    DatabaseReference reference;

    // Declare EditTexts for the form fields
    private EditText employeeCode, employeeName, employeeDesignation, collegeInstitute,
            joiningDate, assessmentFrom, assessmentTo, campus, department;

    // Declare the Button to navigate to SecondPage
    private Button navigateButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_page);  // Load the activity_first_page.xml layout

        // Initialize the views by linking them to the corresponding views in the XML layout
        employeeCode = findViewById(R.id.employeeCode);
        employeeName = findViewById(R.id.employeeName);
        employeeDesignation = findViewById(R.id.employeeDesignation);
        collegeInstitute = findViewById(R.id.collegeInstitute);
        campus = findViewById(R.id.campus);
        department = findViewById(R.id.department);
        joiningDate = findViewById(R.id.joiningDate);
        assessmentFrom = findViewById(R.id.assessmentFrom);
        assessmentTo = findViewById(R.id.assessmentTo);

        // Initialize the Button
        navigateButton = findViewById(R.id.buttonNavigateToSecondPage);

        // Set the click listener for the Button
        navigateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Retrieve field values
                String empCode = employeeCode.getText().toString().trim();
                String name = employeeName.getText().toString().trim();
                String designation = employeeDesignation.getText().toString().trim();
                String college = collegeInstitute.getText().toString().trim();
                String campusValue = campus.getText().toString().trim();
                String departmentValue = department.getText().toString().trim();
                String joiningDateStr = joiningDate.getText().toString().trim();
                String assessmentFromStr = assessmentFrom.getText().toString().trim();
                String assessmentToStr = assessmentTo.getText().toString().trim();

                if (!empCode.isEmpty() && !name.isEmpty()) {
                    // Use LinkedHashMap with numbered keys to define field order
                    LinkedHashMap<String, String> user = new LinkedHashMap<>();
                    user.put("01_EmpCode", empCode);
                    user.put("02_Name", name);
                    user.put("03_Designation", designation);
                    user.put("04_College", college);
                    user.put("05_Campus", campusValue);
                    user.put("06_Department", departmentValue);
                    user.put("07_JoiningDate", joiningDateStr);
                    user.put("08_AssessmentFrom", assessmentFromStr);
                    user.put("09_AssessmentTo", assessmentToStr);

                    db = FirebaseDatabase.getInstance();
                    reference = db.getReference("Users");

                    // Save the user to Firebase
                    reference.child(empCode).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(FirstPage.this, "Data saved successfully!", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(FirstPage.this, SecondPage.class);
                                intent.putExtra("EmployeeCode", empCode);
                                startActivity(intent);
                            } else {
                                Toast.makeText(FirstPage.this, "Failed to save data: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                } else {
                    Toast.makeText(FirstPage.this, "Please fill all required fields!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
