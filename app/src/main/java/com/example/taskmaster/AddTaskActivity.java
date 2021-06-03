package com.example.taskmaster;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.room.Room;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.FileUtils;
import android.provider.OpenableColumns;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.amplifyframework.AmplifyException;
import com.amplifyframework.auth.cognito.AWSCognitoAuthPlugin;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.storage.s3.AWSS3StoragePlugin;
import com.google.gson.Gson;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class AddTaskActivity extends AppCompatActivity implements View.OnClickListener {
    private Button addTaskButton;
    private EditText taskTitle;
    private EditText taskDescription;
    private EditText taskState;
    private AppDatabase appDatabase;
    private TextView tasksCounterTextView;
    private TaskDao taskDao;
    private Button uploadFileButton;
    private String fileName="";

//    private Set<Task> tasks= new HashSet<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        addTaskButton = findViewById(R.id.addTaskBtn);
        taskTitle = findViewById(R.id.taskTitleEditText);
        taskDescription = findViewById(R.id.taskDescriptionEditText);
        taskState = findViewById(R.id.taskStateEditText);
        tasksCounterTextView = findViewById(R.id.numberOfTasksTextView);
        uploadFileButton = findViewById(R.id.uploadBtn);
        appDatabase = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "task_master").allowMainThreadQueries().build();
        taskDao = appDatabase.taskDao();
        tasksCounterTextView.setText(taskDao.getTasksNumber()+"");
        addTaskButton.setOnClickListener(this);

        // Add this line, to include the Auth plugin.
//        try {
//            Amplify.addPlugin(new AWSCognitoAuthPlugin());
//            Amplify.addPlugin(new AWSS3StoragePlugin());
//            Amplify.configure(getApplicationContext());
//        } catch (AmplifyException e) {
//            e.printStackTrace();
//            Toast.makeText(this, "error in plugins", Toast.LENGTH_LONG).show();
//        }

        uploadFileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("*/*");
                startActivityForResult(intent, 540);
            }
        });


    } //end onCreate()

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.addTaskBtn){
            if(TextUtils.isEmpty(taskTitle.getText()))
                taskTitle.setError("Task Title Can't Be Empty");
            if(TextUtils.isEmpty(taskDescription.getText()))
                taskDescription.setError("Task Description Can't Be Empty");
            if(TextUtils.isEmpty(taskState.getText()))
                taskState.setText(R.string.default_state);

            taskDao.addTask(new Task(taskTitle.getText().toString(), taskDescription.getText().toString(), taskState.getText().toString(), fileName));

            Toast.makeText(this, "Task Added", Toast.LENGTH_LONG).show();
            finish();

        }
    } //end onClick()


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 540){
            fileName = getFileName(data.getData());
            Toast.makeText(this, fileName, Toast.LENGTH_LONG).show();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                uploadFile(this, data.getData(), fileName);
                Toast.makeText(this, "File Uploaded Successfully", Toast.LENGTH_LONG).show();
            }else{
                Toast.makeText(this, "Version does not support uploading files...", Toast.LENGTH_LONG).show();
            }
        }

    }
    // get file name ..
    public String getFileName(Uri uri){
        Cursor result = getContentResolver().query(uri, null, null, null, null);
        int index = result.getColumnIndex(OpenableColumns.DISPLAY_NAME);
        result.moveToFirst();
        return result.getString(index);
    }

    @RequiresApi(api = Build.VERSION_CODES.Q)
    protected void uploadFile(Context context, Uri uri, String fileName){
        File file = new File(context.getFilesDir(), fileName);
        File file2 = copy(uri, file);
//        Amplify.Storage.uploadFile(
//                fileName,
//                file2,
//                result -> Log.i("uploadFile", "Successfully Uploaded: "+ result.getKey()),
//                error -> Log.e("uploadFile", "Storage Failure: "+error)
//        );
    }

    private File copy(Uri source, File destination){
        try{
            InputStream inputStream = getContentResolver().openInputStream(source);
            OutputStream outputStream = new FileOutputStream(destination);
            byte[] buffer = new byte[1024];
            int len;
            while((len = inputStream.read(buffer)) > 0){
                outputStream.write(buffer, 0, len);
            }
            outputStream.close();
            inputStream.close();
            return destination;
        }catch (IOException e){
            e.printStackTrace();
        }
        return new File(source.toString());
    }


} //end class