package com.ujamang.biz.ui.project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.appbar.MaterialToolbar;
import com.ujamang.biz.R;

public class ProjectDetailSub extends AppCompatActivity {

    //appbar
    private MaterialToolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_detail_sub);

        //appbar
        mToolbar = findViewById(R.id.project_detail_sub_toolbar);
        setSupportActionBar(mToolbar);
        //appbar 뒤로가기버튼
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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