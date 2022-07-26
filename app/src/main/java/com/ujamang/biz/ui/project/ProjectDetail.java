package com.ujamang.biz.ui.project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.google.android.material.appbar.MaterialToolbar;
import com.ujamang.biz.R;

import java.util.ArrayList;

public class ProjectDetail extends AppCompatActivity {

    //appbar
    private MaterialToolbar mToolbar;

    //recyclerview
    private ArrayList<ProjectTaskItem> mArrayList;
    private ProjectTaskAdapter mAdapter;
    private int count = -1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_detail);

        //appbar
        mToolbar = findViewById(R.id.project_detail_toolbar);
        setSupportActionBar(mToolbar);
        //appbar 뒤로가기버튼
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        //recyclerview
        RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.project_detail_recyclerview);
        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        //code_detail_button = (ImageButton) findViewById(R.id.code_next_detail_button);

        mArrayList = new ArrayList<>();
        mAdapter = new ProjectTaskAdapter(mArrayList);
        mRecyclerView.setAdapter(mAdapter);

        //임시로 더미데이터 쌓아두기
        for (int i=1; i<11; i++){
            ProjectTaskItem data = new ProjectTaskItem("업무이력 OOO " + i, "송현영 ", "2022-07-19");
            mArrayList.add(data);
            mAdapter.notifyDataSetChanged();
            //구분선
            DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(mRecyclerView.getContext(), mLinearLayoutManager.getOrientation());
            mRecyclerView.addItemDecoration(dividerItemDecoration);
        }


        //임시 상세보기2 버튼
        Button button = (Button) findViewById(R.id.temp_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProjectDetail.this, ProjectDetailSub.class);
                startActivity(intent);

            }
        });
    }

    //appbar 메뉴 뒤로가기 이벤트 추가
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}