package com.ujamang.biz.ui.drawer.notice;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.gson.Gson;
import com.ujamang.biz.PreferenceManager;
import com.ujamang.biz.R;
import com.ujamang.biz.model.dto.notice.user.UserNotice;
import com.ujamang.biz.ui.drawer.codemanager.CodeActivity;
import com.ujamang.biz.ui.drawer.codemanager.CodeDetailActivity;
import com.ujamang.biz.ui.drawer.codemanager.CodeItem;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class NoticeActivity extends AppCompatActivity {
    //공지사항 액티비티

    //appbar
    private MaterialToolbar mToolbar;

    //recyclerview
    private ArrayList<NoticeItem> mArrayList;
    private NoticeUserAdapter mAdapter;
    private int count = -1;

    //SharedPreferences
    private Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice);

        //appbar
        mToolbar = findViewById(R.id.notice_toolbar);
        setSupportActionBar(mToolbar);
        //appbar 뒤로가기버튼
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //recyclerview
        RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.notice_user_recyclerView);
        LinearLayoutManager mLinearlayoutmanager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLinearlayoutmanager);

        mArrayList = new ArrayList<>();
        mAdapter = new NoticeUserAdapter(mArrayList);
        mRecyclerView.setAdapter(mAdapter);

        //임시로 더미데이터 쌓아두기. 지금은 쌓아둘 필요 없음 API 실습 해볼거니까
        /*for (int i=1; i<14; i++){
            CodeItem data = new CodeItem("코드 " + i);
            mArrayList.add(data);
            mAdapter.notifyDataSetChanged();
        }*/

        new Thread(() -> {
            Gson gson = new Gson();
            OkHttpClient okHttpClient = new OkHttpClient();

            //API URL
            String apiUrl = "http://api.ujamang.biz//api/v1/notice/member/coresoft/2022062000001?p=1&rpp=15&q=";

            Request.Builder builder = new Request.Builder().url(apiUrl).get();
            builder.addHeader("Authorization", "Bearer " + PreferenceManager.getString(context, "authToken"));
            //builder.addHeader("Authorization", "Bearer %s", PreferenceManager.getString(context, "authToken"));
            Request request = builder.build();


            okHttpClient.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(@NonNull Call call, @NonNull IOException e) {
                    //트럭 도착이 실패했습니다.
                    System.out.println("API Response Failure: " + e.toString());
                }

                @Override
                public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                    //트럭이 API에 다녀오는데에 성공했습니다.
                    if (response.isSuccessful()) {
                        //트럭에서 하차한 내용물이 ResponseBody에 담긴다.
                        ResponseBody responseBody = response.body();
                        //내용물을 하차하면서 UserNotice.Response.class에 매칭을 시킨다. Body 내용물과 class 변수들과 1:1 매칭이 되는 것.
                    }

                }
            });
            //Response response = okHttpClient.newCall(request).enqueue();


            //변수 받아서 넣으면
            //GET방식으로 호출하는법 구글에 찾아보기기
        }).start();
    }

    //appbar 메뉴 디자인 추가 : 여기서는 검색버튼이 있어야 함.
    @Override
    public boolean onCreateOptionsMenu(@NonNull Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.main_toolbar_menu, menu);
        return true;
    }

    //appbar 메뉴 뒤로가기 이벤트 추가

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:     //뒤로가기
                finish();
                return true;
            /*case R.id.codemanager_detail_create_icon:
                //일단 등록 페이지로 이동은 해야하니까..
                Intent intent = new Intent(NoticeActivity.this, CodeDetailActivity.class);
                startActivity(intent);*/
        }
        return super.onOptionsItemSelected(item);
    }

    //GET방식 비동기 HTTP 요청 - 쿼리 파라미터 전송
    public void requestAsyncGetHttp(){
        try {
            //TODO [전송 url 정의 실시]
            String url = "http://api.ujamang.biz//api/v1/notice/member/coresoft/2022062000001?p=1&rpp=15&q="; //사내 공지사항 목록 API

            //TODO [파라미터값 선언 실시]
            Map params = new HashMap();
            params.put("userId", 1);
            params.put("id", 1);

            //TODO [url에 파라미터 추가 실시]
            HttpUrl.Builder httpBuilder = HttpUrl.get(url).newBuilder();
            Set set = params.keySet();
            Iterator iterator = set.iterator();
            while(iterator.hasNext()){
                String key = (String) iterator.next();
                httpBuilder.addQueryParameter(key, String.valueOf(params.get(key)));
            }

            //TODO [OK HTTP 객체 선언 실시]
            OkHttpClient client = new OkHttpClient();
            Request.Builder request = new Request.Builder();
            request.addHeader("", "");
            request.url(httpBuilder.build()); //TODO [httpBuilder 추가]

            Log.d("---","---");
            Log.d("//===========//","================================================");
            Log.d("","\n"+"[A_OkHttp > requestAsyncGetHttp() 메소드 : OK HTTP 비동기 GET 요청 실시]");
            Log.d("","\n"+"["+"요청 주소 : " + String.valueOf(url)+"]");
            Log.d("","\n"+"["+"전송 값 : " + String.valueOf(params.toString())+"]");
            Log.d("","\n"+"["+"전송 형태 : " + String.valueOf(httpBuilder.build())+"]");
            Log.d("//===========//","================================================");
            Log.d("---","---");

            //TODO [비동기 처리 (enqueue 사용)]
            client.newCall(request.build()).enqueue(new Callback() {
                @Override
                public void onFailure(@NonNull Call call, @NonNull IOException e) {
                    final String responseCode = String.valueOf(e.toString());
                    final String responseData = String.valueOf(e.getMessage());
                    runOnUiThread(new Runnable() {
                        public void run() {
                            Log.d("---","---");
                            Log.e("//===========//","================================================");
                            Log.d("","\n"+"[A_OkHttp > requestAsyncGetHttp() 메소드 : OK HTTP 비동기 GET 요청 실패]");
                            Log.d("","\n"+"["+"에러 코드 : " + String.valueOf(responseCode)+"]");
                            Log.d("","\n"+"["+"에러 값 : " + String.valueOf(responseData)+"]");
                            Log.e("//===========//","================================================");
                            Log.d("---","---");
                        }
                    });
                }

                @Override
                public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                    final String responseCode = String.valueOf(response.toString());
                    final String responseData = response.body().string();

                    runOnUiThread(new Runnable() {
                        public void run() {
                            Log.d("---","---");
                            Log.w("//===========//","================================================");
                            Log.d("","\n"+"[A_OkHttp > requestAsyncGetHttp() 메소드 : OK HTTP 비동기 GET 요청 성공]");
                            Log.d("","\n"+"["+"응답 코드 : " + String.valueOf(responseCode)+"]");
                            Log.d("","\n"+"["+"응답 값 : " + String.valueOf(responseData)+"]");
                            Log.w("//===========//","================================================");
                            Log.d("---","---");
                        }
                    });
                }
            });

        } catch (final Exception e) {
            runOnUiThread(new Runnable() {
                public void run() {
                    Log.d("---","---");
                    Log.e("//===========//","================================================");
                    Log.d("","\n"+"[A_OkHttp > requestAsyncGetHttp() 메소드 : OK HTTP 비동기 GET 요청 실패 - CATCH]");
                    Log.d("","\n"+"["+"에러 값 : " + String.valueOf(e.getMessage())+"]");
                    Log.e("//===========//","================================================");
                    Log.d("---","---");
                }
            });
            e.printStackTrace();
        }
    }
}