package com.example.taskmaster;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.ViewHolder> {
    private List<MyTask> myTasks;
    Context context;
    public TextView taskDescription;
    public TextView state;
    public TextView fileName;
    public TextView address;


    public class ViewHolder extends RecyclerView.ViewHolder{
        private final TextView taskTitleTextView;

        public ViewHolder(View view){
            super(view);
            taskTitleTextView = view.findViewById(R.id.taskTextView);


            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(view.getContext(), DetailsActivity.class);
                    intent.putExtra("taskTitle", getTitleTextView().getText());
                    intent.putExtra("taskDescription", getTaskDescription().getText());
                    intent.putExtra("taskState", getState().getText());
                    intent.putExtra("fileName", getFileName().getText());
                    intent.putExtra("address", getAddress().getText());
                    context.startActivity(intent);
                }
            });
        }

        public TextView getTitleTextView(){
            return taskTitleTextView;
        }
        public TextView getTaskDescription() {
            return taskDescription;
        }

        public TextView getState() {
            return state;
        }

        public TextView getFileName() {
            return fileName;
        }
        public TextView getAddress() {
            return address;
        }

    }
    public TaskAdapter(Context outContext,List<MyTask> myTasks){
        this.myTasks = myTasks;
        context = outContext;
//         taskDescriptionTextView = new TextView(context.getApplicationContext());
        taskDescription = new TextView(context.getApplicationContext());
        state = new TextView(context.getApplicationContext());
        fileName = new TextView(context.getApplicationContext());
        address = new TextView(context.getApplicationContext());
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.task_view, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TaskAdapter.ViewHolder holder, int position) {
        holder.getTitleTextView().setText(myTasks.get(position).getTitle());
        state.setText(myTasks.get(position).getState());
        taskDescription.setText(myTasks.get(position).getDescription());
        fileName.setText(myTasks.get(position).getImageName());
        address.setText(myTasks.get(position).getAddress());
    }

    @Override
    public int getItemCount() {
        return myTasks.size();
    }



}
