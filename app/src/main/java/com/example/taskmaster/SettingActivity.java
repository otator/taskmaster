package com.example.taskmaster;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SettingActivity extends AppCompatActivity {
    private Button saveButton;
    private EditText usernameEditText;
    private String username = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        saveButton = findViewById(R.id.saveBtn);
        usernameEditText = findViewById(R.id.usernameEditText);
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!TextUtils.isEmpty(usernameEditText.getText())) {
                    username = usernameEditText.getText().toString();
                    editor.putString("username", username + "'s tasks");
                    editor.apply();
                    Toast.makeText(getApplicationContext(), "User " + username + " saved!", Toast.LENGTH_LONG).show();
                    finish();
                }

            }
        });
    }
}