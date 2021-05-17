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
        TextView taskTitleTextView = findViewById(R.id.detailsTitleText);
        TextView taskDescriptionTextView = findViewById(R.id.detailsDescriptionText);

        taskTitleTextView.setText(getIntent().getExtras().getString("taskTitle"));
        taskDescriptionTextView.setText(R.string.lorem);


    }
}