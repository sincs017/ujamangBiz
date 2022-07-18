package com.ujamang.biz.codemanager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ujamang.biz.R;

import java.util.ArrayList;

public class CodeAdapter extends RecyclerView.Adapter<CodeAdapter.CodeViewHolder> {
    //코드관리 대메뉴 어댑터

    private ArrayList<CodeItem> mList;

    public class CodeViewHolder extends RecyclerView.ViewHolder{
        protected TextView code;

        public CodeViewHolder(@NonNull View view) {
            super(view);
            this.code = (TextView) view.findViewById(R.id.code_name);


        }
    }

    public CodeAdapter(ArrayList<CodeItem> list){
        this.mList = list;
    }


    @NonNull
    @Override
    public CodeAdapter.CodeViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.codemanager_detail_list_item, viewGroup, false);

        CodeAdapter.CodeViewHolder viewHolder = new CodeAdapter.CodeViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CodeAdapter.CodeViewHolder viewholder, int position) {
        viewholder.code.setText(mList.get(position).getCode());

    }

    @Override
    public int getItemCount() {
        return (null != mList ? mList.size() : 0);
    }
}
