package com.example.taskmaster;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.TextView;

public class DetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        TextView taskDescriptionTextView = findViewById(R.id.detailsDescriptionText);
        TextView taskStateTextView = findViewById(R.id.detailsStateText);
        setTitle(getIntent().getExtras().getString("taskTitle"));
        taskDescriptionTextView.setText(getIntent().getExtras().getString("taskDescription"));
        taskStateTextView.setText(getIntent().getExtras().getString("taskState"));
    }
}