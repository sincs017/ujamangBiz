package com.ujamang.biz.ui.project;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ujamang.biz.R;

import java.util.ArrayList;

public class ProjectAdapter extends RecyclerView.Adapter<ProjectAdapter.ProjectViewHolder> {

    private ArrayList<ProjectItem> mList;

    public class ProjectViewHolder extends RecyclerView.ViewHolder{
        protected TextView project_category;
        protected TextView project_name;

        public ProjectViewHolder(@NonNull View view){
            super(view);
            this.project_category = (TextView) view.findViewById(R.id.project_category);
            this.project_name = (TextView) view.findViewById(R.id.project_name);
        }
    }

    public ProjectAdapter(ArrayList<ProjectItem> mList) {
        this.mList = mList;
    }

    @NonNull
    @Override
    public ProjectViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.project_list_item, viewGroup, false );

        ProjectViewHolder viewHolder = new ProjectViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ProjectViewHolder viewholder, int position) {
        viewholder.project_category.setText(mList.get(position).getProject_category());
        viewholder.project_name.setText(mList.get(position).getProject_name());
    }

    @Override
    public int getItemCount() {
        return (null != mList ? mList.size() : 0);
    }


}
