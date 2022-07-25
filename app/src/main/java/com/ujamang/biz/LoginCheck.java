package com.ujamang.biz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.textfield.TextInputEditText;
import com.ujamang.biz.R;

public class LoginCheck extends AppCompatActivity {

    private TextInputEditText t1;
    private TextInputEditText t2;
    private TextInputEditText t3;
    private TextInputEditText t4;
    /*private TextInputEditText t5;
    private TextInputEditText t6;
    private TextInputEditText t7;*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_check);

        //원래는 회사 아이디, 멤버 아이디, 비밀번호 이렇게 3개를 받아서 체크해보려고 했는데
        //회사 아이디, 멤버 아이디, 멤버 사원번호 3개를 받아야 겠다.

        Intent intent = getIntent();


        t1 = findViewById(R.id.login_company_id);
        t2 = findViewById(R.id.login_user_id);
        t3 = findViewById(R.id.login_password);
        t4 = findViewById(R.id.login_ok);
        /*t5 = findViewById(R.id.login_company_token);
        t6 = findViewById(R.id.login_user_token);
        t7 = findViewById(R.id.login_password_token);*/


        String company = intent.getStringExtra("company");
        String user = intent.getStringExtra("user");
        String password = intent.getStringExtra("password");
        String ok = intent.getStringExtra("ok");
        /*String company_tk = intent.getStringExtra("company_tk");
        String user_tk = intent.getStringExtra("user_tk");
        String userNo_tk = intent.getStringExtra("userNo_tk");*/

        t1.setText(company);
        t2.setText(user);
        t3.setText(password);
        t4.setText(ok);
        /*t5.setText(company_tk);
        t6.setText(user_tk);
        t7.setText(userNo_tk);*/






    }
}