package com.example.taskmaster;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.ViewHolder> {
    private List<MyTask> myTasks = new ArrayList<>();
    static Context context;
    private TextView taskDescriptionTextView;
    private String state;
    private String fileName;
    public class ViewHolder extends RecyclerView.ViewHolder{
        private final TextView taskTitleTextView;

        public ViewHolder(View view){
            super(view);
            taskTitleTextView = view.findViewById(R.id.taskTextView);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(view.getContext(), DetailsActivity.class);
                    intent.putExtra("taskTitle", getTextView().getText());
                    intent.putExtra("taskDescription", taskDescriptionTextView.getText());
                    intent.putExtra("taskState", state);
                    intent.putExtra("fileName", fileName);
                    context.startActivity(intent);
                }
            });
        }

        public TextView getTextView(){
            return taskTitleTextView;
        }

    }
    public TaskAdapter(Context context,List<MyTask> myTasks){
        this.myTasks = myTasks;
        TaskAdapter.context = context;
         taskDescriptionTextView = new TextView(context.getApplicationContext());
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.task_view, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TaskAdapter.ViewHolder holder, int position) {
        holder.getTextView().setText(myTasks.get(position).getTitle());
        taskDescriptionTextView.setText(myTasks.get(position).getDescription());
        state = myTasks.get(position).getState();
        fileName = myTasks.get(position).getImageName();
    }

    @Override
    public int getItemCount() {
        return myTasks.size();
    }



}
