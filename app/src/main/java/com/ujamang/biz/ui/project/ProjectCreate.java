package com.ujamang.biz.ui.project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;

import com.google.android.material.appbar.MaterialToolbar;
import com.ujamang.biz.R;
import com.google.android.material.textfield.TextInputLayout;

public class ProjectCreate extends AppCompatActivity {

    //appbar
    private MaterialToolbar mToolbar;

    //dropdown 추가하는 코드들임
    TextInputLayout TIL_project_category, TIL_project_progress;
    AutoCompleteTextView TIL_project_category_item, TIL_project_progress_item;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_create);

        //appbar
        mToolbar = findViewById(R.id.project_create_toolbar);
        setSupportActionBar(mToolbar);
        //appbar 뒤로가기버튼
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //프로젝트 분류
        TIL_project_category = findViewById(R.id.TIL_project_category);
        TIL_project_category_item = findViewById(R.id.TIL_project_category_item);

        //진행상황
        TIL_project_progress = findViewById(R.id.TIL_project_progress);
        TIL_project_progress_item = findViewById(R.id.TIL_project_progress_item);

        String[] categoryItems = {"item1", "item2", "item3", "item4", "item5"};     //실제로는 프로젝트 분류 코드관리(카테고리 내용이 DB로 연결되어야함)
        ArrayAdapter<String> categoryItemAdapter = new ArrayAdapter<>(ProjectCreate.this, R.layout.project_create_dropdownmenu_list_item, categoryItems);
        TIL_project_category_item.setAdapter(categoryItemAdapter);

        TIL_project_category_item.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {

            }
        });

        String[] progressItems = {"item1", "item2", "item3", "item4", "item5"};     //실제로는 프로젝트 분류 코드관리(카테고리 내용이 DB로 연결되어야함)
        ArrayAdapter<String> progressItemAdapter = new ArrayAdapter<>(ProjectCreate.this, R.layout.project_create_dropdownmenu_list_item, progressItems);
        TIL_project_progress_item.setAdapter(progressItemAdapter);

        TIL_project_progress_item.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {

            }
        });


        //임시 상세보기1 버튼
        Button button = (Button) findViewById(R.id.temp_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProjectCreate.this, ProjectDetail.class);
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