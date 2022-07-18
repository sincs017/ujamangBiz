package com.ujamang.biz.codemanager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.appbar.MaterialToolbar;
import com.ujamang.biz.R;

import java.util.ArrayList;

public class CodeRecyclerView extends AppCompatActivity {
    //코드관리 대메뉴

    //appbar
    private MaterialToolbar mToolbar;

    //recyclerview
    private ArrayList<CodeItem> mArrayList;
    private CodeAdapter mAdapter;
    private int count = -1;
    private ImageButton code_detail_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_code_recycler_view);

        //appbar
        mToolbar = findViewById(R.id.codeManager_toolbar);
        setSupportActionBar(mToolbar);
        //appbar 뒤로가기버튼
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



        //recyclerview
        RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.code_recyclerView);
        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        code_detail_button = (ImageButton) findViewById(R.id.code_next_detail_button);

        mArrayList = new ArrayList<>();
        mAdapter = new CodeAdapter(mArrayList);
        mRecyclerView.setAdapter(mAdapter);

        /*code_detail_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //어떤 코드 아이템을 누르는지에 따라서 어느 코드 상세페이지로 이동할지 key값을 기준으로 작성해야한다.
                //그게 여기에 작성하면 되는 것 같다.
            }
        });*/
    }

    //appbar 메뉴 디자인 추가
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.codemanager_detail_toolbar_menu, menu);
        return true;
    }

    //appbar 메뉴 뒤로가기 이벤트 추가
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                return true;
            case R.id.codemanager_detail_create_icon:
                //일단 등록 페이지로 이동은 해야하니까..
                Intent intent = new Intent(CodeRecyclerView.this, CodeDetailRecyclerView.class);
                startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}