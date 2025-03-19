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

public class ThirdPage extends AppCompatActivity {

    // Declare EditText fields
    private EditText lecturesTakenInput, tutorialsPracticalInput, extraLecturesInput,
            semesterDutiesInput, internalExamsInput, innovativeTeachingInput,
            attendanceAbove85Input, studentFeedbackInput, resultsInput;

    // Declare Firebase variables
    FirebaseDatabase db;
    DatabaseReference reference;

    // Declare the Button
    private Button navigateButtontry1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third_page);

        // Initialize Firebase
        db = FirebaseDatabase.getInstance();
        reference = db.getReference("Users");

        // Initialize EditText fields
        lecturesTakenInput = findViewById(R.id.row1UserInput);
        tutorialsPracticalInput = findViewById(R.id.row2UserInput);
        extraLecturesInput = findViewById(R.id.row3UserInput);
        semesterDutiesInput = findViewById(R.id.row4UserInput);
        internalExamsInput = findViewById(R.id.row5UserInput);
        innovativeTeachingInput = findViewById(R.id.row6UserInput);
        attendanceAbove85Input = findViewById(R.id.row10UserInput);
        studentFeedbackInput = findViewById(R.id.row11UserInput);
        resultsInput = findViewById(R.id.row12UserInput);

        // Get EmployeeCode passed from SecondPage
        String employeeCode = getIntent().getStringExtra("EmployeeCode");

        // Initialize the Button
        navigateButtontry1 = findViewById(R.id.buttonNavigateToFourthPage);

        // Set the click listener for the Button
        navigateButtontry1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Retrieve data from EditTexts
                String lecturesTaken = lecturesTakenInput.getText().toString().trim();
                String tutorialsPractical = tutorialsPracticalInput.getText().toString().trim();
                String extraLectures = extraLecturesInput.getText().toString().trim();
                String semesterDuties = semesterDutiesInput.getText().toString().trim();
                String internalExams = internalExamsInput.getText().toString().trim();
                String innovativeTeaching = innovativeTeachingInput.getText().toString().trim();
                String attendanceAbove85 = attendanceAbove85Input.getText().toString().trim();
                String studentFeedback = studentFeedbackInput.getText().toString().trim();
                String results = resultsInput.getText().toString().trim();

                // Validate required fields
                if (lecturesTaken.isEmpty() || tutorialsPractical.isEmpty() || extraLectures.isEmpty() ||
                        semesterDuties.isEmpty() || internalExams.isEmpty()) {
                    Toast.makeText(ThirdPage.this, "Please fill all required fields!", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Create a map to hold the data
                HashMap<String, String> appraisalData = new HashMap<>();
                appraisalData.put("1_LecturesTaken", lecturesTaken);
                appraisalData.put("2_TutorialsPractical", tutorialsPractical);
                appraisalData.put("3_ExtraLectures", extraLectures);
                appraisalData.put("4_SemesterDuties", semesterDuties);
                appraisalData.put("5_InternalExams", internalExams);
                appraisalData.put("6_InnovativeTeaching", innovativeTeaching);
                appraisalData.put("7_AttendanceAbove85", attendanceAbove85);
                appraisalData.put("8_StudentFeedback", studentFeedback);
                appraisalData.put("9_Results", results);

                // Save data to Firebase under the EmployeeCode
                reference.child(employeeCode).child("Teaching Learning Activities").setValue(appraisalData)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(ThirdPage.this, "Data saved successfully!", Toast.LENGTH_SHORT).show();

                                    // Navigate to FourthPage
                                    Intent intent = new Intent(ThirdPage.this, FourthPage.class);
                                    intent.putExtra("EmployeeCode", employeeCode);
                                    startActivity(intent);
                                } else {
                                    Toast.makeText(ThirdPage.this, "Failed to save data: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });
    }
}
