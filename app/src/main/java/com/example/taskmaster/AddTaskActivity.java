package com.example.taskmaster;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.room.Room;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

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
    private Intent intent;
    private File fileFromIntent;
    private String address;

//    private Set<MyTask> myTasks= new HashSet<>();

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
        address = MainActivity.currentLocation;
        uploadFileButton = findViewById(R.id.uploadBtn);
        appDatabase = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "task_master").allowMainThreadQueries().build();
        taskDao = appDatabase.taskDao();
        tasksCounterTextView.setText(taskDao.getTasksNumber()+"");
        addTaskButton.setOnClickListener(this);
        intent = getIntent();
        if(intent.getType() != null){
//            Toast.makeText(this, intent.getData().toString(), Toast.LENGTH_LONG).show();
//            if(intent.getType().contains("image/")){
//                fileName = getFileName(intent.getData());
//                // TODO: call the upload file here, when it is fixed
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
//                    uploadFile(this, intent.getData(), fileName);
//                }
//            }
        }




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
                taskTitle.setError("MyTask Title Can't Be Empty");
            if(TextUtils.isEmpty(taskDescription.getText()))
                taskDescription.setError("MyTask Description Can't Be Empty");
            if(TextUtils.isEmpty(taskState.getText()))
                taskState.setText(R.string.default_state);

            taskDao.addTask(new MyTask(taskTitle.getText().toString(), taskDescription.getText().toString(), taskState.getText().toString(), fileName, address));

            Toast.makeText(this, "Task Added Successfully", Toast.LENGTH_LONG).show();
            finish();

        }
    } //end onClick()


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Toast.makeText(this, data.getData().toString(), Toast.LENGTH_LONG).show();
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