package com.example.taskmaster;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.amplifyframework.core.Amplify;

import java.io.File;

public class DetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        TextView taskDescriptionTextView = findViewById(R.id.detailsDescriptionText);
        TextView taskStateTextView = findViewById(R.id.detailsStateText);
        TextView taskFileTextView = findViewById(R.id.fileNameTextView);
        TextView taskLocationTextView = findViewById(R.id.locationTextView);
        setTitle(getIntent().getExtras().getString("taskTitle"));
        Log.d("@@@@@@@", getIntent().getExtras().getString("taskTitle"));
        Log.d("@@@@@@@", getIntent().getExtras().getString("taskDescription"));
        Log.d("@@@@@@@", getIntent().getExtras().getString("taskState"));
        Log.d("@@@@@@@", getIntent().getExtras().getString("address"));


        taskDescriptionTextView.setText(getIntent().getExtras().getString("taskDescription"));
        taskStateTextView.setText(getIntent().getExtras().getString("taskState"));
        taskLocationTextView.setText(getIntent().getStringExtra("address"));
        downloadFile(getIntent().getStringExtra("fileName"));
        taskFileTextView.setText(getIntent().getStringExtra("fileName"));
    }

    public void downloadFile(String fileName){
        Amplify.Storage.downloadFile(
                fileName,
                new File(getApplicationContext().getFilesDir() + fileName),
                result -> Log.i("MyAmplifyApp", "Successfully downloaded: " + result.getFile().getName()),
                error -> Log.e("MyAmplifyApp",  "Download Failure", error)
        );
        Toast.makeText(this, "File downloaded successfully", Toast.LENGTH_LONG).show();
    }
}