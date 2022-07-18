package com.ujamang.biz;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.view.KeyEvent;
import android.view.View;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class Login extends AppCompatActivity {

    /*private TextInputEditText companyId;
    private TextInputEditText userId;
    private TextInputEditText password;*/
    private MaterialButton loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        /*companyId = (TextInputEditText) findViewById(R.id.company_id_TIL);
        userId = (TextInputEditText) findViewById(R.id.user_id_TIL);
        password = (TextInputEditText) findViewById(R.id.password_TIL);*/

        final TextInputLayout companyIdInput = findViewById(R.id.company_id_TIL);
        final TextInputEditText companyIdEditText = findViewById(R.id.company_id_TIE);
        final TextInputLayout userIdInput = findViewById(R.id.user_id_TIL);
        final TextInputEditText userIdEditText = findViewById(R.id.user_id_TIE);
        final TextInputLayout passwordInput = findViewById(R.id.password_TIL);
        final TextInputEditText passwordEditText = findViewById(R.id.password_TIE);


        loginButton = (MaterialButton) findViewById(R.id.login_check_button);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //3개 항목의 데이터 가지고 메인 액티비티로 넘어가는 코드가 작성되어야 함.

                //password 오류 검출 코드 : material design tutorial MDC-101 참고
                if (!isPasswordValid(passwordEditText.getText())) {
                    passwordInput.setError(getString(R.string.login_password_error));
                } else {
                    passwordInput.setError(null); //Clear the error : 에러 없다는 뜻인가?
                    Intent intent = new Intent(Login.this, MainActivity.class);
                    startActivity(intent);
                }



            }
        });

        // Clear the error once more than 8 characters are typed.
        // 코드는 에러가 발견되지 않았지만, 8글자 아래로 타이핑하라고 제한을 또 줄거다.
        passwordEditText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (isPasswordValid(passwordEditText.getText())){
                    passwordInput.setError(null); //Clear the error
                }
                return false;
            }
        });
    }

    private boolean isPasswordValid(@Nullable Editable text) {
        return text != null && text.length() >= 8;
    }


}