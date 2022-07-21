package com.ujamang.biz.ui.project;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ujamang.biz.R;

import java.util.ArrayList;

public class ProjectTaskAdapter extends RecyclerView.Adapter<ProjectTaskAdapter.ProjectTaskViewHolder> {

    private ArrayList<ProjectTaskItem> mList;

    public class ProjectTaskViewHolder extends RecyclerView.ViewHolder {
        protected TextView project_task_name;
        protected TextView project_task_writer;
        protected TextView project_task_write_date;

        public ProjectTaskViewHolder(@NonNull View view){
            super(view);
            this.project_task_name = (TextView) view.findViewById(R.id.project_task_name);
            this.project_task_writer = (TextView) view.findViewById(R.id.project_task_writer);
            this.project_task_write_date = (TextView) view.findViewById(R.id.project_task_write_date);
        }
    }

    public ProjectTaskAdapter(ArrayList<ProjectTaskItem> mList) {
        this.mList = mList;
    }

    @NonNull
    @Override
    public ProjectTaskViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.project_detail_list_item, viewGroup, false );

        ProjectTaskAdapter.ProjectTaskViewHolder viewHolder = new ProjectTaskAdapter.ProjectTaskViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ProjectTaskViewHolder viewholder, int position) {
        viewholder.project_task_name.setText(mList.get(position).getProject_task_name());
        viewholder.project_task_writer.setText(mList.get(position).getProject_task_writer());
        viewholder.project_task_write_date.setText(mList.get(position).getProject_task_write_date());
    }

    @Override
    public int getItemCount() {
        return (null != mList ? mList.size() : 0);
    }


}
