package com.satkeev.github.taskmaster;

import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.generated.model.Task;

import java.io.File;
import java.util.ArrayList;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> {

    public ArrayList<Task> tasks;
    public OnInteractingWithTaskListener listener;


    public TaskAdapter(ArrayList<Task> tasks, OnInteractingWithTaskListener listener) {
        this.tasks = tasks;
        this.listener = listener;
    }
//TODO: create View holder
    public static class TaskViewHolder extends RecyclerView.ViewHolder{
        public Task task;
        public View view;

        public TaskViewHolder(@NonNull View itemView){
          super(itemView);
          this.view = view;
        }
}

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
                listener.taskListener(viewHolder.task);
            }
        });
         return viewHolder;
    }

    public static interface OnInteractingWithTaskListener {
        public void taskListener(Task task);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {
        holder.task = tasks.get(position);

        TextView taskNameTextView = holder.itemView.findViewById(R.id.task_name_title_fragment);
        TextView taskDetailsTextView = holder.itemView.findViewById(R.id.fragment_details);
        TextView taskStateTextView = holder.itemView.findViewById(R.id.fragment_state);
        ImageView taskKeyImageView = holder.itemView.findViewById(R.id.image_fragment);
        TextView taskName1TextView = holder.itemView.findViewById(R.id.team_name_fragment);

        taskNameTextView.setText(holder.task.title);
        taskDetailsTextView.setText(holder.task.body);
        taskStateTextView.setText(holder.task.state);
        taskName1TextView.setText(holder.task.apartOf.getName());
        taskKeyImageView.setImageBitmap(BitmapFactory.decodeFile(MainActivity.imageFile.getPath()));
    }

    @Override
    public int getItemCount() {
        return tasks.size();
    }

        }


