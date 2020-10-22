package com.satkeev.github.taskmaster;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> {

    public ArrayList<Task> tasks;
    public OnInteractWithTaskListener listener;


    public TaskAdapter(ArrayList<Task> tasks, OnInteractWithTaskListener listener) {
        this.tasks = tasks;
        this.listener = listener;
    }
//TODO: create View holder

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_task, parent, false);

        final TaskViewHolder viewHolder = new TaskViewHolder(view);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println(viewHolder.task.title);
                listener.tasksToDoListener(viewHolder.task);//TODO: create listener
            }
        });
         return viewHolder;
    }

    public static interface OnInteractWithTaskListener {
        public void tasksToDoListener(Task task);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {
        holder.task = tasks.get(position);

        TextView taskNameTextView = holder.itemView.findViewById(R.id.task_name_title_fragment);
        TextView taskDetailsTextView = holder.itemView.findViewById(R.id.fragment_details);
        TextView taskStateTextView = holder.itemView.findViewById(R.id.fragment_state);

        taskNameTextView.setText(holder.task.title);
        taskDetailsTextView.setText(holder.task.body);
        taskStateTextView.setText(holder.task.state);
    }

    @Override
    public int getItemCount() {
        return tasks.size();
    }

    // TODO: 1 create our viewHolder class
//     view holder deals with passing the data from java to the fragment (List Item)
    public static class TaskViewHolder extends RecyclerView.ViewHolder {
        public Task task;
        // store view, so I can change it from any external method
        public View itemView;

        public TaskViewHolder(@NonNull View itemView) {
            super(itemView);
            this.itemView = itemView;
        }
    }
}
