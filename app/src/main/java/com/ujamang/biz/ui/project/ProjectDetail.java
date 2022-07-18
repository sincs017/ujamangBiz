package com.ujamang.biz.ui.project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.google.android.material.appbar.MaterialToolbar;
import com.ujamang.biz.R;

public class ProjectDetail extends AppCompatActivity {

    //appbar
    private MaterialToolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_detail);

        //appbar
        mToolbar = findViewById(R.id.project_detail_toolbar);
        setSupportActionBar(mToolbar);
        //appbar 뒤로가기버튼
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


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