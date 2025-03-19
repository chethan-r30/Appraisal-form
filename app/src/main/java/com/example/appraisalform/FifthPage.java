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

public class FifthPage extends AppCompatActivity {

    // Declare EditText fields
    private EditText row1UserInput, row2UserInput, row3UserInput, row4UserInput, row5UserInput,
            row6UserInput, row7UserInput, row8UserInput, row9UserInput;
    private Button navigateButton;

    // Declare Firebase variables
    FirebaseDatabase db;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fifth_page); // Ensure the XML is named activity_fifth_page.xml

        // Initialize Firebase
        db = FirebaseDatabase.getInstance();
        reference = db.getReference("Users");

        // Initialize EditText fields
        row1UserInput = findViewById(R.id.row1UserInput);
        row2UserInput = findViewById(R.id.row2UserInput);
        row3UserInput = findViewById(R.id.row3UserInput);
        row4UserInput = findViewById(R.id.row4UserInput);
        row5UserInput = findViewById(R.id.row5UserInput);
        row6UserInput = findViewById(R.id.row6UserInput);
        row7UserInput = findViewById(R.id.row7UserInput);
        row8UserInput = findViewById(R.id.row8UserInput);
        row9UserInput = findViewById(R.id.row9UserInput);

        // Get EmployeeCode passed from FourthPage
        String employeeCode = getIntent().getStringExtra("EmployeeCode");

        // Initialize the Button
        navigateButton = findViewById(R.id.buttonNavigateToSixthPage);

        // Set the click listener for the Button
        navigateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Retrieve data from EditTexts
                String researchPublication = row1UserInput.getText().toString().trim();
                String conferenceProceedings = row2UserInput.getText().toString().trim();
                String booksPublished = row3UserInput.getText().toString().trim();
                String organizingConferences = row4UserInput.getText().toString().trim();
                String fundedProjects = row5UserInput.getText().toString().trim();
                String consultancyMoU = row6UserInput.getText().toString().trim();
                String patents = row7UserInput.getText().toString().trim();
                String researchGuidance = row8UserInput.getText().toString().trim();
                String studentInvolvement = row9UserInput.getText().toString().trim();

                // Validate required fields
                if (researchPublication.isEmpty() || conferenceProceedings.isEmpty() || booksPublished.isEmpty() ||
                        organizingConferences.isEmpty() || fundedProjects.isEmpty() || consultancyMoU.isEmpty() ||
                        patents.isEmpty() || researchGuidance.isEmpty() || studentInvolvement.isEmpty()) {
                    Toast.makeText(FifthPage.this, "Please fill all required fields!", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Create a map to hold the data
                HashMap<String, String> researchData = new HashMap<>();
                researchData.put("1_ResearchPublication", researchPublication);
                researchData.put("2_ConferenceProceedings", conferenceProceedings);
                researchData.put("3_BooksPublished", booksPublished);
                researchData.put("4_OrganizingConferences", organizingConferences);
                researchData.put("5_FundedProjects", fundedProjects);
                researchData.put("6_ConsultancyMoU", consultancyMoU);
                researchData.put("7_Patents", patents);
                researchData.put("8_ResearchGuidance", researchGuidance);
                researchData.put("9_StudentInvolvement", studentInvolvement);

                // Save data to Firebase under the EmployeeCode
                reference.child(employeeCode).child("Research Details").setValue(researchData)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(FifthPage.this, "Data saved successfully!", Toast.LENGTH_SHORT).show();

                                    // Navigate to SixthPage
                                    Intent intent = new Intent(FifthPage.this, SixthPage.class);
                                    intent.putExtra("EmployeeCode", employeeCode);
                                    startActivity(intent);
                                } else {
                                    Toast.makeText(FifthPage.this, "Failed to save data: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });
    }
}
