package com.ujamang.biz.ui.drawer.codemanager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import com.ujamang.biz.R;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;

public class CodeDetailActivity extends AppCompatActivity {
    //코드관리 소메뉴

    //appbar
    private MaterialToolbar mToolbar;

    //textInput
    TextInputLayout textInputLayout;
    TextInputEditText textInputEditText;

    private ArrayList<CodeItem> mArrayList;
    private CodeDetailAdapter mAdapter;
    private int count = -1;

    //dialog 해보려고
    private LinearLayout linearLayout;
    private String codeDetail_create_data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_code_detail_recycler_view);

        //appbar
        mToolbar = findViewById(R.id.codeManager_toolbar);
        setSupportActionBar(mToolbar);
        //appbar 뒤로가기버튼
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        //textInput
        textInputLayout = (TextInputLayout) findViewById(R.id.code_create_TIL);
        textInputEditText = (TextInputEditText) findViewById(R.id.code_create_TIE);

        //recyclerview
        RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.code_detail_recyclerView);
        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);

        mArrayList = new ArrayList<>();
        mAdapter = new CodeDetailAdapter(mArrayList);
        mRecyclerView.setAdapter(mAdapter);

        //임시로 더미데이터 쌓아두기
        for (int i=1; i<17; i++){
            CodeItem data = new CodeItem("코드 " + i);
            mArrayList.add(data);
            mAdapter.notifyDataSetChanged();
        }

        //dialog 해보려고
        linearLayout = (LinearLayout) View.inflate(CodeDetailActivity.this, R.layout.code_detail_dialog_create, null);
        //codeDetail_create_data = textInputEditText.getText().toString();
        //String new_code_name = textInputEditText.getText().toString();


        //액티비티에서 액티비티로 데이터 전달
        //전달할 데이터를 받을 Intent //text 키 값으로 데이터를 받는다. String을 받아야 하므로 getStringExtra()를 사용한다.
        /*Intent intent = getIntent();
        String text = intent.getStringExtra("text");*/
        //startActivityFor
        //registerForActivityResult()
        /*CodeItem data = new CodeItem(codeDetail_create_data); // + " " + count
        mArrayList.add(data);
        mAdapter.notifyDataSetChanged();*/
        //액티비티에서 액티비티로 데이터 전달

        //데이터 받을 때
        Intent intent = getIntent();
        String action = intent.getAction();
        String type = intent.getType();

        if (Intent.ACTION_SEND.equals(action) && type != null){
            if ("text/plain".equals(type)) {
                handleSendText(intent); //Handle text being sent
            } else {
                //Handle other intents, such as being started from the home screen
            }
        }


        //수정, 삭제

    }

    /*private void textWatcher(){
        //공백일 때 오류를 나타내면서 추가 버튼이 눌리지 않도록 구현하는 것이 목표
    }*/

    //appbar 메뉴 디자인 추가
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.codemanager_detail_toolbar_menu, menu);
        return true;
    }

    //appBar의 추가 버튼 눌렀을 때 이벤트
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                return true;
            case R.id.codemanager_detail_create_icon:
                //일단 등록 페이지로 이동은 해야하니까..
                Intent intent = new Intent(CodeDetailActivity.this, CodeDetailCreate.class);
                startActivity(intent);

                //dialog 코드
                /*new AlertDialog.Builder(CodeDetailRecyclerView.this).setView(linearLayout)
                        .setPositiveButton("추가", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if (Objects.requireNonNull(textInputEditText.getText()).toString().length() == 0){

                                    //(textInputEditText.getText().toString().length() == 0)


                                    //다음에는 dismiss 말고 팝업 메세지를 주는거로 바꾸자. 지금은 몰라서 일단 해둠.
                                    dialog.dismiss();
                                } else {
                                    //String new_code_name = textInputEditText.getText().toString();
                                    codeDetail_create_data = textInputEditText.getText().toString();

                                    //textWatcher();
                                    //count ++;
                                    CodeItem data = new CodeItem(codeDetail_create_data); // + " " + count
                                    mArrayList.add(data);

                                    mAdapter.notifyDataSetChanged();
                                }

                            }
                        })
                        .setNegativeButton("취소", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .show();*/
                //dialog 코드


                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /* //액티비티간 데이터 이동 코드 작성중
    private void moveCreateActivity(){
        Intent intent = new Intent(CodeDetailRecyclerView.this, CodeDetailCreate.class);
        //registerForActivityResult();
    }*/

    /*대메뉴에서 골라서 넘어온 상세페이지의 Title에 사용될 textView 내용도 가져와서 CodeDetailRecyclerView
    * 의 layout xml 파일에 materialToolbar 안에 있는 textView의 text를 핸들링 해줄 수 있으면 된다.*/

    //액티비티 간 데이터 이동 코드 작성중 2
    // 받는데 도움이 되는 함수?
    void handleSendText(Intent intent) {
        String sharedText = intent.getStringExtra(Intent.EXTRA_TEXT);
        if (sharedText != null) {
            // Update UI to reflect text being shared

            //codeDetail_create_data = textInputEditText.getText().toString();

            //textWatcher();
            //count ++;
            CodeItem data = new CodeItem(sharedText); // + " " + count
            mArrayList.add(data);

            mAdapter.notifyDataSetChanged();
        }
    }
}

















