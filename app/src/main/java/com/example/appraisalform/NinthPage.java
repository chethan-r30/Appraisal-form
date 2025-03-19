package com.example.appraisalform;

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

public class NinthPage extends AppCompatActivity {

    // Declare EditText fields for ratings and remarks
    private EditText edtProfessorOutstanding, edtProfessorCompetent, edtProfessorGood, edtProfessorSatisfactory, edtProfessorPoor;
    private EditText edtAssocProfessorOutstanding, edtAssocProfessorCompetent, edtAssocProfessorGood, edtAssocProfessorSatisfactory, edtAssocProfessorPoor;
    private EditText edtAsstProfessorOutstanding, edtAsstProfessorCompetent, edtAsstProfessorGood, edtAsstProfessorSatisfactory, edtAsstProfessorPoor;
    private EditText edtRemarksHoD, edtRemarksExternalAuditor1, edtRemarksExternalAuditor2;
    private Button saveButton;

    // Declare Firebase variables
    FirebaseDatabase db;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ninth_page); // Ensure your XML is named activity_ninth_page.xml

        // Initialize Firebase
        db = FirebaseDatabase.getInstance();
        reference = db.getReference("Users");

        // Initialize EditText fields for ratings
        edtProfessorOutstanding = findViewById(R.id.edtProfessorOutstanding);
        edtProfessorCompetent = findViewById(R.id.edtProfessorCompetent);
        edtProfessorGood = findViewById(R.id.edtProfessorGood);
        edtProfessorSatisfactory = findViewById(R.id.edtProfessorSatisfactory);
        edtProfessorPoor = findViewById(R.id.edtProfessorPoor);

        edtAssocProfessorOutstanding = findViewById(R.id.edtAssocProfessorOutstanding);
        edtAssocProfessorCompetent = findViewById(R.id.edtAssocProfessorCompetent);
        edtAssocProfessorGood = findViewById(R.id.edtAssocProfessorGood);
        edtAssocProfessorSatisfactory = findViewById(R.id.edtAssocProfessorSatisfactory);
        edtAssocProfessorPoor = findViewById(R.id.edtAssocProfessorPoor);

        edtAsstProfessorOutstanding = findViewById(R.id.edtAsstProfessorOutstanding);
        edtAsstProfessorCompetent = findViewById(R.id.edtAsstProfessorCompetent);
        edtAsstProfessorGood = findViewById(R.id.edtAsstProfessorGood);
        edtAsstProfessorSatisfactory = findViewById(R.id.edtAsstProfessorSatisfactory);
        edtAsstProfessorPoor = findViewById(R.id.edtAsstProfessorPoor);

        // Initialize EditText fields for remarks
        edtRemarksHoD = findViewById(R.id.edtRemarksHoD);
        edtRemarksExternalAuditor1 = findViewById(R.id.edtRemarksExternalAuditor1);
        edtRemarksExternalAuditor2 = findViewById(R.id.edtRemarksExternalAuditor2);

        // Get EmployeeCode passed from the previous page (e.g., EightPage)
        String employeeCode = getIntent().getStringExtra("EmployeeCode");

        // Initialize the Button
        saveButton = findViewById(R.id.buttonNavigateToNinthPage);

        // Set the click listener for the Button
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Retrieve data from EditTexts
                String professorOutstanding = edtProfessorOutstanding.getText().toString().trim();
                String professorCompetent = edtProfessorCompetent.getText().toString().trim();
                String professorGood = edtProfessorGood.getText().toString().trim();
                String professorSatisfactory = edtProfessorSatisfactory.getText().toString().trim();
                String professorPoor = edtProfessorPoor.getText().toString().trim();

                String assocProfessorOutstanding = edtAssocProfessorOutstanding.getText().toString().trim();
                String assocProfessorCompetent = edtAssocProfessorCompetent.getText().toString().trim();
                String assocProfessorGood = edtAssocProfessorGood.getText().toString().trim();
                String assocProfessorSatisfactory = edtAssocProfessorSatisfactory.getText().toString().trim();
                String assocProfessorPoor = edtAssocProfessorPoor.getText().toString().trim();

                String asstProfessorOutstanding = edtAsstProfessorOutstanding.getText().toString().trim();
                String asstProfessorCompetent = edtAsstProfessorCompetent.getText().toString().trim();
                String asstProfessorGood = edtAsstProfessorGood.getText().toString().trim();
                String asstProfessorSatisfactory = edtAsstProfessorSatisfactory.getText().toString().trim();
                String asstProfessorPoor = edtAsstProfessorPoor.getText().toString().trim();

                String remarksHoD = edtRemarksHoD.getText().toString().trim();
                String remarksExternalAuditor1 = edtRemarksExternalAuditor1.getText().toString().trim();
                String remarksExternalAuditor2 = edtRemarksExternalAuditor2.getText().toString().trim();

                // Validate required fields
                if (professorOutstanding.isEmpty() || professorCompetent.isEmpty() || professorGood.isEmpty() || professorSatisfactory.isEmpty() || professorPoor.isEmpty() ||
                        assocProfessorOutstanding.isEmpty() || assocProfessorCompetent.isEmpty() || assocProfessorGood.isEmpty() || assocProfessorSatisfactory.isEmpty() || assocProfessorPoor.isEmpty() ||
                        asstProfessorOutstanding.isEmpty() || asstProfessorCompetent.isEmpty() || asstProfessorGood.isEmpty() || asstProfessorSatisfactory.isEmpty() || asstProfessorPoor.isEmpty() ||
                        remarksHoD.isEmpty() || remarksExternalAuditor1.isEmpty() || remarksExternalAuditor2.isEmpty()) {
                    Toast.makeText(NinthPage.this, "Please fill all the fields!", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Create a map to hold the data
                HashMap<String, String> ratingData = new HashMap<>();
                ratingData.put("Professor Outstanding", professorOutstanding);
                ratingData.put("Professor Competent", professorCompetent);
                ratingData.put("Professor Good", professorGood);
                ratingData.put("Professor Satisfactory", professorSatisfactory);
                ratingData.put("Professor Poor", professorPoor);

                ratingData.put("Assoc Professor Outstanding", assocProfessorOutstanding);
                ratingData.put("Assoc Professor Competent", assocProfessorCompetent);
                ratingData.put("Assoc Professor Good", assocProfessorGood);
                ratingData.put("Assoc Professor Satisfactory", assocProfessorSatisfactory);
                ratingData.put("Assoc Professor Poor", assocProfessorPoor);

                ratingData.put("Asst Professor Outstanding", asstProfessorOutstanding);
                ratingData.put("Asst Professor Competent", asstProfessorCompetent);
                ratingData.put("Asst Professor Good", asstProfessorGood);
                ratingData.put("Asst Professor Satisfactory", asstProfessorSatisfactory);
                ratingData.put("Asst Professor Poor", asstProfessorPoor);

                ratingData.put("Remarks HoD", remarksHoD);
                ratingData.put("Remarks External Auditor 1", remarksExternalAuditor1);
                ratingData.put("Remarks External Auditor 2", remarksExternalAuditor2);

                // Save data to Firebase under the EmployeeCode
                reference.child(employeeCode).child("Faculty Ratings and Remarks").setValue(ratingData)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(NinthPage.this, "Data saved successfully!", Toast.LENGTH_SHORT).show();

                                    // No need to navigate anywhere since this is the last page
                                } else {
                                    Toast.makeText(NinthPage.this, "Failed to save data: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });
    }
}
