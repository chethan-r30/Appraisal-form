package com.example.appraisalform;



import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Find the button by its ID
        Button navigateButton = findViewById(R.id.buttonNavigateToFirstPage);

        // Set an OnClickListener for the button
        navigateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an Intent to navigate to the FirstPage activity
                Intent intent = new Intent(MainActivity.this, FirstPage.class);
                startActivity(intent); // Start the FirstPage activity
            }
        });
    }
}
