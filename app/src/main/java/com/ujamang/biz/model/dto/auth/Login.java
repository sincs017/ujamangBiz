package com.ujamang.biz.model.dto.auth;

import androidx.annotation.NonNull;
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
import com.google.gson.Gson;
import com.ujamang.biz.MainActivity;
import com.ujamang.biz.R;
import com.ujamang.biz.library.extenstion.DateTimeExtensions;
import com.ujamang.biz.library.extenstion.StringExtenstions;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class Login extends AppCompatActivity {

    private TextInputLayout companyIdLayout;
    private TextInputLayout userIdlayout;
    private TextInputLayout passwordLayout;

    private TextInputEditText companyIdEditText;
    private TextInputEditText userIdEditText;
    private TextInputEditText passwordEditText;

    private MaterialButton loginButton;     //아이디와 비밀번호가 잘 일치하는지? 유효성 검사를 해야하는 버튼입니다.
    private LoginGetSet.Response apiResponse;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        /*companyId = (TextInputEditText) findViewById(R.id.company_id_TIL);
        userId = (TextInputEditText) findViewById(R.id.user_id_TIL);
        password = (TextInputEditText) findViewById(R.id.password_TIL);*/

        companyIdLayout = findViewById(R.id.company_id_TIL);
        userIdlayout = findViewById(R.id.user_id_TIL);
        passwordLayout = findViewById(R.id.password_TIL);

        companyIdEditText = findViewById(R.id.company_id_TIE);
        userIdEditText = findViewById(R.id.user_id_TIE);
        passwordEditText = findViewById(R.id.password_TIE);

        loginButton = (MaterialButton) findViewById(R.id.login_check_button);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String CompanyId = companyIdEditText.getText().toString();
                String UserId = userIdEditText.getText().toString();
                String Password = passwordEditText.getText().toString();

                StringExtenstions.isNullOrEmpty(CompanyId);
                StringExtenstions.isNullOrEmpty(UserId);
                StringExtenstions.isNullOrEmpty(Password);

                new Thread(() -> {
                    Gson gson = new Gson();
                    OkHttpClient okHttpClient = new OkHttpClient();

                    // API URL
                    String apiUrl = "https://api.ujamang.biz/api/v1/auth";

                    //변수 받아서 넣으면
                    // Request Body
                    LoginGetSet.Request dto = LoginGetSet.Request.builder()
                            .memberCompany(CompanyId)
                            .memberId(UserId)
                            .password(Password)
                            .machineType("Mobile")
                            .latitude("")
                            .longitude("")
                            .country("")
                            .locality("")
                            .sublocalityLevel1("")
                            .sublocalityLevel2("")
                            .sublocalityLevel3("")
                            .sublocalityLevel4("")
                            .sublocalityLevel5("")
                            .premise("")
                            .machineIp("127.0.0.1")
                            .externalIp("127.0.0.1")
                            .mobileUniqueId("19e5bd0a04852c5a")
                            .mobileBrand("google")
                            .mobileDeviceId("goldfish_x86")
                            .mobileSystemName("Android")
                            .mobileSystemVersion("11")
                            .mobileReadableVersion("1.0.1")
                            .mobileDeviceName("android test")
                            .mobileTabletYesNo("N")
                            .mobileDeviceToken("")
                            .build();

                    //String class의 dto를 json으로 변환시켜서 requestBody 에 집어넣는 코드, 파일형식은 json, utf-8로 설정
                    RequestBody requestBody = RequestBody
                            .create(gson.toJson(dto), MediaType.parse("application/json; charset=utf-8"));

                    //post(requestBody)를 함
                    Request.Builder builder = new Request.Builder()
                            .url(apiUrl)
                            .post(requestBody);
                    builder.addHeader("Content-type", "application/json");
                    //builder.addHeader("Authorization", "Bearer $token");
                    Request request = builder.build();

                    // Async Response
                    okHttpClient.newCall(request).enqueue(new Callback() {
                        @Override
                        public void onFailure(@NotNull Call call, @NotNull IOException e) {
                            System.out.println("API Response Failure: " + e.toString());
                        }

                        @Override
                        public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                            if (response.isSuccessful()) {
                                ResponseBody responseBody = response.body();
                                apiResponse = gson.fromJson(response.body().string(), LoginGetSet.Response.class);

                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Intent intent = new Intent(Login.this, MainActivity.class);
                                        startActivity(intent);
                                        /*et_ok.setText(apiResponse.ok ? "성공" : "실패");
                                        et_memberCompany.setText(apiResponse.memberCompany.toString());
                                        et_memberId.setText(apiResponse.memberId.toString());
                                        et_memberNo.setText(apiResponse.memberNo.toString());*/
                                    }
                                });
                            } else {
                                System.out.println("HTTP Status Code: " + response.code());
                                System.out.println("HTTP Status Message: " + response.message());
                            }
                        }
                    });

                }).start();


                //3개 항목의 데이터 가지고 메인 액티비티로 넘어가는 코드가 작성되어야 함.

                /*//password 오류 검출 코드 : material design tutorial MDC-101 참고
                if (!isPasswordValid(passwordEditText.getText())) {
                    passwordLayout.setError(getString(R.string.login_password_error));
                } else {
                    passwordLayout.setError(null); //Clear the error : 에러 없다는 뜻인가?
                    Intent intent = new Intent(Login.this, MainActivity.class);
                    startActivity(intent);
                }*/



            }
        });


        /*--------------------------*/
        //값을 받은 것을 넣어서 검사해야함.
        //총 3개 텍스트를 받을거니까 검사해라.
        //로그인 버튼 눌렀을 때? 가장 먼저 검사하는 코드가 되면 될 것 같다.
        StringExtenstions.isNullOrEmpty("");
        //DateTimeExtensions.threeWeekDateSeparate(7, "yyyyMMdd");







        /*// Clear the error once more than 8 characters are typed.
        // 코드는 에러가 발견되지 않았지만, 8글자 아래로 타이핑하라고 제한을 또 줄거다.
        passwordEditText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (isPasswordValid(passwordEditText.getText())){
                    passwordLayout.setError(null); //Clear the error
                }
                return false;
            }
        });*/


        /*new Thread(() -> {
            //로그인 okhttp3 rest api 호출
            OkHttpClient client = new OkHttpClient();


        }*/


    }

    /*//tutorial에서 적은 8글자 비번 검사 함수임.
    private boolean isPasswordValid(@Nullable Editable text) {
        return text != null && text.length() >= 8;
    }*/

}