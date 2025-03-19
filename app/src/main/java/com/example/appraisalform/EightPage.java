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

public class EightPage extends AppCompatActivity {

    // Declare EditText fields
    private EditText row1UserInput, row2UserInput, row3UserInput, row4UserInput, row5UserInput, row6UserInput, row7UserInput, row8UserInput;
    private Button navigateButtontry2;

    // Declare Firebase variables
    FirebaseDatabase db;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eight_page); // Ensure your XML is named activity_eight_page.xml

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

        // Get EmployeeCode passed from the previous page (e.g., SeventhPage)
        String employeeCode = getIntent().getStringExtra("EmployeeCode");

        // Initialize the Button
        navigateButtontry2 = findViewById(R.id.buttonNavigateToNinthPage);

        // Set the click listener for the Button
        navigateButtontry2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Retrieve data from EditTexts
                String invitedAsSpeaker = row1UserInput.getText().toString().trim();
                String liveIndustrialProjects = row2UserInput.getText().toString().trim();
                String otherSpecifies = row3UserInput.getText().toString().trim();
                String subjectExpert = row4UserInput.getText().toString().trim();
                String judgeConference = row5UserInput.getText().toString().trim();
                String reviewerJournal = row6UserInput.getText().toString().trim();
                String editorialBoard = row7UserInput.getText().toString().trim();
                String resourcePerson = row8UserInput.getText().toString().trim();

                // Validate required fields
                if (invitedAsSpeaker.isEmpty() || liveIndustrialProjects.isEmpty() || otherSpecifies.isEmpty() || subjectExpert.isEmpty() ||
                        judgeConference.isEmpty() || reviewerJournal.isEmpty() || editorialBoard.isEmpty() || resourcePerson.isEmpty()) {
                    Toast.makeText(EightPage.this, "Please fill all the fields!", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Create a map to hold the data
                HashMap<String, String> interactionData = new HashMap<>();
                interactionData.put("Invited As Speaker", invitedAsSpeaker);
                interactionData.put("Live Industrial Projects", liveIndustrialProjects);
                interactionData.put("Other Specifies", otherSpecifies);
                interactionData.put("Subject Expert", subjectExpert);
                interactionData.put("Judge Conference", judgeConference);
                interactionData.put("Reviewer Journal", reviewerJournal);
                interactionData.put("Editorial Board", editorialBoard);
                interactionData.put("Resource Person", resourcePerson);

                // Save data to Firebase under the EmployeeCode
                reference.child(employeeCode).child("External Interface Details").setValue(interactionData)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(EightPage.this, "Data saved successfully!", Toast.LENGTH_SHORT).show();

                                    // Navigate to NinthPage
                                    Intent intent = new Intent(EightPage.this, NinthPage.class);
                                    intent.putExtra("EmployeeCode", employeeCode); // Pass EmployeeCode
                                    startActivity(intent);
                                } else {
                                    Toast.makeText(EightPage.this, "Failed to save data: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });
    }
}
