package com.ujamang.biz;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;
import com.ujamang.biz.library.extenstion.StringExtenstions;
import com.ujamang.biz.model.dto.auth.Login;

import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class LoginActivity extends AppCompatActivity {

    private TextInputLayout companyIdLayout;
    private TextInputLayout userIdLayout;
    private TextInputLayout passwordLayout;

    private TextInputEditText companyIdEditText;
    private TextInputEditText userIdEditText;
    private TextInputEditText passwordEditText;

    private MaterialButton loginButton;     //로그인 버튼. 아이디와 비밀번호가 잘 일치하는지? 유효성 검사를 해야하는 위치
    private Login.Response apiResponse;

    //SharedPreferences 사용하기
    private TextView login_result;
    private static Context context;
    private SharedPreferences preferences;


    //화면 시작하기 전에 자동로그인 되는지 체크부터 하기.
    //SharedPreferences auto = getSharedPreferences("autoLogin", Activity.MODE_PRIVATE);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);



        /*companyId = (TextInputEditText) findViewById(R.id.company_id_TIL);
        userId = (TextInputEditText) findViewById(R.id.user_id_TIL);
        password = (TextInputEditText) findViewById(R.id.password_TIL);*/

        companyIdLayout = findViewById(R.id.company_id_TIL);
        userIdLayout = findViewById(R.id.user_id_TIL);
        passwordLayout = findViewById(R.id.password_TIL);

        companyIdEditText = findViewById(R.id.company_id_TIE);
        userIdEditText = findViewById(R.id.user_id_TIE);
        passwordEditText = findViewById(R.id.password_TIE);

        loginButton = (MaterialButton) findViewById(R.id.login_check_button);

        //SharedPreferences 사용하기
        login_result = findViewById(R.id.login_result);
        context = this;

        //getSharedPreferences("파일이름",'모드')
        //모드 => 0 (읽기,쓰기가능)
        //모드 => MODE_PRIVATE (이 앱에서만 사용가능)
        preferences = getSharedPreferences("Codemanager", Activity.MODE_PRIVATE);
        SharedPreferences.Editor preferencesEdit = preferences.edit();

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String companyId = companyIdEditText.getText().toString();
                String userId = userIdEditText.getText().toString();
                String password = passwordEditText.getText().toString();


                //그럼 비어있는 null값 체크는 언제 해줘야 좋을까? 지금인가?
                if (StringExtenstions.isNullOrEmpty(companyId) && StringExtenstions.isNullOrEmpty(userId)
                && StringExtenstions.isNullOrEmpty(password)) {
                    //Null 값 체크 통과
                    if(companyId.equals("coresoft") && userId.equals("2206002") && password.equals("2206002*1")) { //AutoLogin.isChecked()
                        //자동로그인

                    } else {
                        //일반로그인
                    }
                } else {
                    //Null 값 체크 실패
                }

                //회사아이디, 유저아이디, 비밀번호 중 null 값이 있는가?
                //있으면 경고메세지
                //없으면 아래 코드 진행


                new Thread(() -> {
                    Gson gson = new Gson();
                    OkHttpClient okHttpClient = new OkHttpClient();

                    // API URL
                    String apiUrl = "https://api.ujamang.biz/api/v1/auth";



                    //변수 받아서 넣으면
                    // Request Body
                    Login.Request dto = Login.Request.builder()     //이거는 그냥 dto 만드는 코드이고,
                            .memberCompany(companyId)
                            .memberId(userId)
                            .password(password)
                            /*.memberCompany(PreferenceManager.getString(context, "companyId"))
                            .memberId(PreferenceManager.getString(context, "userId"))
                            .password(PreferenceManager.getString(context, "password"))*/
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
                    //내용물을 보낼 수 있도록 json으로 변경후에  RequestBody라는 박스에 포장해주는 것.
                    RequestBody requestBody = RequestBody
                            .create(gson.toJson(dto), MediaType.parse("application/json; charset=utf-8"));

                    //post(requestBody)를 함
                    //builder라는 트럭에다가 배송주소(url)과 내용물(post : body박스)를 담아서 전달한다.
                    Request.Builder builder = new Request.Builder()
                            .url(apiUrl)
                            .post(requestBody);
                    //헤더는 필요하면 쓰는건가? 잘 모른다.     //자꾸 헤더에 토큰을 같이 보내라고 하는데 여기에 보내면 되는거임??_JWT공부중
                    builder.addHeader("Content-type", "application/json");
                    //builder.addHeader("Authorization", "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1bmlxdWVfbmFtZSI6Imt3VkVPeGR3M1pjL2hWSGMvcVFDV2c9PSIsImdyb3Vwc2lkIjoia2dJaWtXc1psSlgvRXhyYkVESXNsUT09IiwibmJmIjoxNjU4ODA2MTc5LCJleHAiOjE2NjAxMDIxNzksImlhdCI6MTY1ODgwNjE3OSwiaXNzIjoiaHR0cHM6Ly91amFtYW5nLmJpeiIsImF1ZCI6Imh0dHBzOi8vdWphbWFuZy5iaXoifQ.ZB9_gTS9tKnLT4PKfe2hC-QaVmAHEsovpmtGPAQFNBA");
                    //이제야 트럭이 API로 출발하는거임. build(); 가 실행 함수.
                    Request request = builder.build();

                    // Async Response
                    okHttpClient.newCall(request).enqueue(new Callback() {
                        @Override
                        public void onFailure(@NotNull Call call, @NotNull IOException e) {
                            //트럭 도착이 실패했습니다.
                            System.out.println("API Response Failure: " + e.toString());
                        }

                        @Override
                        public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                            //트럭이 API에 다녀오는데에 성공했습니다.
                            if (response.isSuccessful()) {
                                //트럭에서 하차한 내용물이 ResponseBody에 담긴다.
                                ResponseBody responseBody = response.body();
                                //내용물을 하차하면서 Login.Response.class 에 매칭을 시킨다. Body 내용물과 class 변수들과 1:1 매칭이 되는 것.
                                apiResponse = gson.fromJson(response.body().string(), Login.Response.class);

                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        //내용물을 검수하는 시간. ok변수는 불량인지 아닌지 확인하는 변수
                                        if (apiResponse.ok) {
                                            //불량이 아닙니다.
                                            //불량이 아니면 내용물들을 모두 SharedPreferences 에 저장한다.
                                            //1:1 노가다 매칭으로 하자.

                                            preferencesEdit.putBoolean("ok", apiResponse.ok);
                                            preferencesEdit.putString("memberCompany", apiResponse.memberCompany);
                                            preferencesEdit.putString("memberId", apiResponse.memberId);
                                            preferencesEdit.putString("memberNo", apiResponse.memberNo);
                                            preferencesEdit.putString("officePositionUserCodeName", apiResponse.officePositionUserCodeName);
                                            preferencesEdit.putString("nameKorean", apiResponse.nameKorean);
                                            preferencesEdit.putString("memberGradeCommonCode", apiResponse.memberGradeCommonCode);
                                            preferencesEdit.putString("memberTypeCommonCode", apiResponse.memberTypeCommonCode);
                                            preferencesEdit.putString("memberTypeCommonCodeName", apiResponse.memberTypeCommonCodeName);
                                            preferencesEdit.putString("freeMemberExpired", apiResponse.freeMemberExpired);
                                            preferencesEdit.putString("memberExpired", apiResponse.memberExpired);
                                            preferencesEdit.putString("serviceStartDate", apiResponse.serviceStartDate);
                                            preferencesEdit.putString("serviceEndDate", apiResponse.serviceEndDate);
                                            preferencesEdit.putString("authToken", apiResponse.authToken);
                                            preferencesEdit.putString("refreshTokenEpochExpires", apiResponse.refreshTokenEpochExpires);
                                            preferencesEdit.putString("refreshToken", apiResponse.refreshToken);
                                            preferencesEdit.apply();

                                            //login_result.setText(preferences.getString("authToken", "none"));

                                            login_result.setText("ok =\n" + preferences.getBoolean("ok", false)
                                                    + "\n AUTHTOKEN =\n" + preferences.getString("authToken", "none")
                                                    + "\n REFRESHTOKENEPOCHEXPIRES =\n" + preferences.getString("refreshTokenEpochExpires", "none")
                                                    + "\n REFRESHTOKEN =\n" + preferences.getString("refreshToken", "none"));

                                            //PreferenceManager.setResponse(context, "apiResponse", apiResponse);
                                            //apiResponse의 모든 body 내용을 한번에 SharedPreferences 저장해주는 메서드를 만들자.
                                            //PreferenceManager.setasdf(context, ""/*key를 줘야하는디?*/, apiResponse);
                                            ///setPreferencesLogin();
                                            ///getPreferencesLogin();

                                            //그리고 다음 액티비티로 이동한다.
                                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                            startActivity(intent);
                                        } else {
                                            //불량입니다.
                                            //내용물이 불량일 경우 같이 딸려온 메세지가 있을 것이다.
                                            //이 메세지는 API에 이미 저장되어 있는 메세지이다.
                                            //아이디가 일치하지 않습니다 또는 비밀번호가 일치하지 않습니다. 이런식으로 오겠지?

                                        }

                                        //if apiResponse.ok
                                        //성공
                                        //else
                                        //실패
                                        //실패하면 message를 응답한다.

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

                //password 오류 검출 코드 : material design tutorial MDC-101 참고
                /*if (!isPasswordValid(passwordEditText.getText())) {
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

    private void setPreferencesLogin() {
        PreferenceManager.setString(context, "ok", apiResponse.ok ? "성공" : "실패");
        PreferenceManager.setString(context, "memberCompany", apiResponse.memberCompany);
        PreferenceManager.setString(context, "memberId", apiResponse.memberId);
        PreferenceManager.setString(context, "memberNo", apiResponse.memberNo);
        PreferenceManager.setString(context, "officePositionUserCodeName", apiResponse.officePositionUserCodeName);
        PreferenceManager.setString(context, "nameKorean", apiResponse.nameKorean);
        PreferenceManager.setString(context, "memberGradeCommonCode", apiResponse.memberGradeCommonCode);
        PreferenceManager.setString(context, "memberTypeCommonCode", apiResponse.memberTypeCommonCode);
        PreferenceManager.setString(context, "memberTypeCommonCodeName", apiResponse.memberTypeCommonCodeName);
        PreferenceManager.setString(context, "freeMemberExpired", apiResponse.freeMemberExpired);
        PreferenceManager.setString(context, "memberExpired", apiResponse.memberExpired);
        PreferenceManager.setString(context, "serviceStartDate", apiResponse.serviceStartDate);
        PreferenceManager.setString(context, "serviceEndDate", apiResponse.serviceEndDate);
        PreferenceManager.setString(context, "authToken", apiResponse.authToken);
        PreferenceManager.setString(context, "refreshTokenEpochExpires", apiResponse.refreshTokenEpochExpires);
        PreferenceManager.setString(context, "refreshToken", apiResponse.refreshToken);

    }

    //Preferences에서 꺼내오는 메소드
    private void getPreferencesLogin() {
        //getString(KEY,KEY값이 없을때 대체)
        login_result.setText("AUTHTOKEN =\n" + PreferenceManager.getString(context, "authToken")
                + "\n REFRESHTOKENEPOCHEXPIRES =\n" + PreferenceManager.getString(context, "refreshTokenEpochExpires")
                + "\n REFRESHTOKEN =\n" + PreferenceManager.getString(context, "refreshToken"));
    }

    /*PreferenceManager.setString(context, "companyId", companyIdEditText.getText().toString());
                PreferenceManager.setString(context, "userId", userIdEditText.getText().toString());
                PreferenceManager.setString(context, "password", passwordEditText.getText().toString());
                Toast.makeText(context,"저장되었습니다.", Toast.LENGTH_SHORT);
                getPreferences();*/

    //StringExtenstions.isNullOrEmpty(CompanyId);

    /*//tutorial에서 적은 8글자 비번 검사 함수임.
    private boolean isPasswordValid(@Nullable Editable text) {
        return text != null && text.length() >= 8;
    }*/

}