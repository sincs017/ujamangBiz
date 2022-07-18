package com.ujamang.biz.ui.project;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.ujamang.biz.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class ProjectFragment extends Fragment {

    private ArrayList<ProjectItem> mArrayList;
    private ProjectAdapter mAdapter;
    private int count = -1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_project, container, false);
        // Inflate the layout for this fragment

        RecyclerView mRecyclerView = (RecyclerView) view.findViewById(R.id.project_recyclerview);
        LinearLayoutManager mLinearlayoutManager = new LinearLayoutManager(this.getContext());
        mRecyclerView.setLayoutManager(mLinearlayoutManager);

        mArrayList = new ArrayList<>();
        mAdapter = new ProjectAdapter(mArrayList);
        mRecyclerView.setAdapter(mAdapter);

        //구분선
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(mRecyclerView.getContext(), mLinearlayoutManager.getOrientation());
        mRecyclerView.addItemDecoration(dividerItemDecoration);

        Button buttonInsert = (Button) view.findViewById(R.id.button_main_insert);
        buttonInsert.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onClick(View v) {
                count ++;

                ProjectItem data = new ProjectItem("카테고리" + count, "프로젝트 제목" + count);

                //mArrayList.add(0, dict); //RecyclerView의 첫 줄에 삽입
                mArrayList.add(data);      //RecyclerView의 마지막 줄에 삽입

                mAdapter.notifyDataSetChanged();
            }
        });

        //floating action button
        FloatingActionButton fab = view.findViewById(R.id.project_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Snackbar.make(view, "Here's a Snackbar", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*/

                Intent intent = new Intent(getActivity(), ProjectCreate.class);
                intent.addFlags(intent.FLAG_ACTIVITY_NEW_TASK);
                getActivity().startActivity(intent);
                //requireActivity().overridePendingTransition(0,0);
            }
        });


        return view;
    }
}