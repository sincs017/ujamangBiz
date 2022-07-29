package com.ujamang.biz.ui.drawer.notice.detail;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.gson.Gson;
import com.ujamang.biz.R;
import com.ujamang.biz.model.dto.notice.user.NoticeUser;
import com.ujamang.biz.model.dto.notice.user.NoticeUserDetail;
import com.ujamang.biz.ui.drawer.notice.NoticeItem;

import java.io.IOException;
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

public class NoticeDetailActivity extends AppCompatActivity {
    //공지사항 세부사항 액티비티

    //appbar
    private MaterialToolbar mToolbar;

    //TextView
    private TextView TV_notice_title;
    private TextView TV_notice_writer;
    private TextView TV_notice_registerDate;
    private TextView TV_notice_content;

    //SharedPreferences 정보 이용
    private SharedPreferences preferences;
    //dto 매칭
    private NoticeUserDetail.Response noticeUserDetail;
    //Okhttp API 호출 이용
    private OkHttpClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice_detail);

        //이놈은 LoginActivity에서 저장한 쉐어프리퍼렌스 값을 다른 액티비티에서도 불러오기 위해 무조건 작성해야하는 한줄 코드이다.
        preferences = getSharedPreferences("Codemanager", Activity.MODE_PRIVATE); //preferences.getString("authToken", "none");

        TV_notice_title = findViewById(R.id.TV_notice_title);
        TV_notice_writer = findViewById(R.id.TV_notice_writer);
        TV_notice_registerDate = findViewById(R.id.TV_notice_registerDate);
        TV_notice_content = findViewById(R.id.TV_notice_content);

        //appbar
        mToolbar = findViewById(R.id.notice_toolbar);
        setSupportActionBar(mToolbar);
        //appbar 뒤로가기버튼
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        client = new OkHttpClient();
        requestAsyncGetHttp();

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

    public void requestAsyncGetHttp() {
        try {
            //TODO [Gson 선언]
            Gson gson = new Gson();

            //TODO [전송 url 정의 실시]
            String url = "https://api.ujamang.biz//api/v1/Notice/member/coresoft/2022062000001/1";    //얘는 http가 아니고 https라고 써야 한다.
            //=> 관련 에러 문장 CLEARTEXT communication to api.ujamang.biz not permitted by network security policy

            //System.out.println();

            //TODO [파라미터값 선언 실시 : 세부사항 불러오기에는 파라미터가 필요한게 없다.]
            Map params = new HashMap();

            // debug돌려보니까 url 뒷부분에 파라미터로 하나씩 추가되더라.

            //TODO [url에 파라미터 추가 실시]
            HttpUrl.Builder httpBuilder = HttpUrl.get(url).newBuilder();
            Set set = params.keySet();
            Iterator iterator = set.iterator();
            while (iterator.hasNext()) {    //실제로 한바퀴 돌 때마다 url 뒷부분에 파라미터로 추가해주는 코드임.
                String key = (String) iterator.next();
                httpBuilder.addQueryParameter(key, String.valueOf(params.get(key))); //TODO [쿼리 파람 추가]
            }

            //TODO [OK HTTP 객체 선언 실시]
            OkHttpClient client = new OkHttpClient();
            Request.Builder request = new Request.Builder();
            //Bearer에서 B는 대문자로 해라. 나머지 코드는 잘 들어 맞았다. 토닥토닥
            request.addHeader("Authorization", "Bearer " + preferences.getString("authToken", "none"));
            request.url(httpBuilder.build()); //TODO [httpBuilder 추가]

            //아래 3줄 코드는 authToken이 잘 들어갔는지 확인해보려고 실험으로 적은 코드다.
            Log.d("", preferences.getString("authToken", "none"));
            String a = preferences.getString("authToken", "none");
            System.out.println(a);

            Log.d("---", "---");
            Log.d("//===========//", "================================================");
            Log.d("", "\n" + "[A_OkHttp > requestAsyncGetHttp() 메소드 : OK HTTP 비동기 GET 요청 실시]");
            Log.d("", "\n" + "[" + "요청 주소 : " + String.valueOf(url) + "]");
            Log.d("", "\n" + "[" + "전송 값 : " + String.valueOf(params.toString()) + "]");
            Log.d("", "\n" + "[" + "전송 형태 : " + String.valueOf(httpBuilder.build()) + "]");
            Log.d("//===========//", "================================================");
            Log.d("---", "---");

            //TODO [비동기 처리 (enqueue 사용)]
            client.newCall(request.build()).enqueue(new Callback() {
                //TODO [성공한 경우]
                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    final String responseCode = String.valueOf(response.toString());
                    final String responseData = response.body().string();  //response.body().string() 코드는 한 액티비티에 한번만 사용할 수 있다. 아니면 에러남.
                    try {
                        runOnUiThread(new Runnable() {
                            public void run() {
                                Log.d("---", "---");
                                Log.w("//===========//", "================================================");
                                Log.d("", "\n" + "[A_OkHttp > requestAsyncGetHttp() 메소드 : OK HTTP 비동기 GET 요청 성공]");
                                Log.d("", "\n" + "[" + "응답 코드 : " + String.valueOf(responseCode) + "]");
                                Log.d("","\n"+"["+"응답 값 : " + String.valueOf(responseData)+"]");
                                Log.w("//===========//", "================================================");
                                Log.d("---", "---");

                                //ResponseBody responseBody = response.body();

                                //이거 부장님이 도와주셨다.
                                //NoticeUser클래스 안에 static으로 Response 클래스를 써서 한번 더 덮어주면 된다.
                                noticeUserDetail = gson.fromJson(responseData, NoticeUserDetail.Response.class);

                                System.out.println("title : " + noticeUserDetail.title);
                                System.out.println("registerDate : " + noticeUserDetail.registerDate);
                                System.out.println("content : " + noticeUserDetail.content);

                                TV_notice_title.setText(noticeUserDetail.title);
                                TV_notice_registerDate.setText(noticeUserDetail.registerDate);
                                TV_notice_content.setText(noticeUserDetail.content);

                                //boolean으로 성공은 띄웠다. 일단 성공은 떴으니까 authToken도 잘 맞고, 바로 윗줄에 클래스 매칭도 잘 된거다.
                                //근데 이제 이거 가지고 recyclerview에 띄우는 것과, authToken을 가지고 refreshToken과 함께 interceptor도 해줘야한다.
                                //여기까지가 220728 저녁 8시. 퇴근이다.
                                //et_ok.setText(noticeUser.ok ? "성공" : "실패");
                                //System.out.println(noticeUser.totalCount);
                                /*System.out.println("ok : " + (noticeUser.ok ? "성공" : "실패"));
                                System.out.println("totalCount : " + noticeUser.totalCount);
                                System.out.println("idx : " + noticeUser.item.get(0).idx);
                                System.out.println("title : " + noticeUser.item.get(0).title);
                                System.out.println("hits : " + noticeUser.item.get(0).hits);
                                System.out.println("registerDate : " + noticeUser.item.get(0).registerDate);
                                System.out.println("idx : " + noticeUser.item.get(1).idx);
                                System.out.println("title : " + noticeUser.item.get(1).title);
                                System.out.println("hits : " + noticeUser.item.get(1).hits);
                                System.out.println("registerDate : " + noticeUser.item.get(1).registerDate);*/
                                //System.out.println(noticeUser.item.get(2).idx);
                                //et_totalCount.setText(String.valueOf(noticeUser.totalCount));

                                /*for (int i=0; i<2; i++){
                                    NoticeItem noticeItem = new NoticeItem(noticeUser.item.get(i).title, noticeUser.item.get(i).registerDate);
                                    mArrayList.add(noticeItem);
                                    mAdapter.notifyDataSetChanged();
                                }*/
                            }
                        });
                    } catch (Exception e){

                    }

                }

                //TODO [실패한 경우]
                @Override
                public void onFailure(Call call, IOException e) {
                    final String responseCode = String.valueOf(e.toString());
                    final String responseData = String.valueOf(e.getMessage());
                    runOnUiThread(new Runnable() {
                        public void run() {
                            //et_blank.setText("실패패패");
                            Log.d("---", "---");
                            Log.e("//===========//", "================================================");
                            Log.d("", "\n" + "[A_OkHttp > requestAsyncGetHttp() 메소드 : OK HTTP 비동기 GET 요청 실패]");
                            Log.d("", "\n" + "[" + "에러 코드 : " + String.valueOf(responseCode) + "]");
                            Log.d("", "\n" + "[" + "에러 값 : " + String.valueOf(responseData) + "]");
                            Log.e("//===========//", "================================================");
                            Log.d("---", "---");
                        }
                    });
                }
            });
        } catch (final Exception e) {
            runOnUiThread(new Runnable() {
                public void run() {
                    Log.d("---", "---");
                    Log.e("//===========//", "================================================");
                    Log.d("", "\n" + "[A_OkHttp > requestAsyncGetHttp() 메소드 : OK HTTP 비동기 GET 요청 실패 - CATCH]");
                    Log.d("", "\n" + "[" + "에러 값 : " + String.valueOf(e.getMessage()) + "]");
                    Log.e("//===========//", "================================================");
                    Log.d("---", "---");
                }
            });
            e.printStackTrace();
        }
    }
}