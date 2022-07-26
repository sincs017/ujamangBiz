package com.ujamang.biz.ui.drawer.codemanager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ujamang.biz.R;

import java.util.ArrayList;

public class CodeDetailAdapter extends RecyclerView.Adapter<CodeDetailAdapter.CodeDetailViewHolder> {
    //코드관리 대메뉴 어댑터

    private ArrayList<CodeItem> mList;

    public class CodeDetailViewHolder extends RecyclerView.ViewHolder{
        protected TextView code;

        public CodeDetailViewHolder(@NonNull View view) {
            super(view);
            this.code = (TextView) view.findViewById(R.id.code_detail_name);


        }
    }

    public CodeDetailAdapter(ArrayList<CodeItem> list){
        this.mList = list;
    }


    @NonNull
    @Override
    public CodeDetailViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.codemanager_detail_list_item, viewGroup, false);

        CodeDetailViewHolder viewHolder = new CodeDetailViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CodeDetailViewHolder viewholder, int position) {
        viewholder.code.setText(mList.get(position).getCode());

    }

    @Override
    public int getItemCount() {
        return (null != mList ? mList.size() : 0);
    }
}
