package com.example.writingchallenge;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class onBoarding extends AppCompatActivity {

    private Button buttonNext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_on_boarding);

        buttonNext = findViewById(R.id.button2);

        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Perform the action to navigate to the next page here

                // Example: Start a new activity
                Intent intent = new Intent(onBoarding.this, signUp.class);
                startActivity(intent);
            }
        });


    }
}