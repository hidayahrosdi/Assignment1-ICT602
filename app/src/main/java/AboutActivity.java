package com.example.individualassignment1;

import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class AboutActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        // Make GitHub link clickable
        TextView aboutText = findViewById(R.id.aboutText);
        aboutText.setMovementMethod(LinkMovementMethod.getInstance());

        // Return to MainActivity
        Button homeButton = findViewById(R.id.homeButton);
        homeButton.setOnClickListener(v -> finish()); // just finishes current activity
    }
}



