package com.ujamang.biz.ui.drawer.notice;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.ujamang.biz.PreferenceManager;
import com.ujamang.biz.R;
import com.ujamang.biz.model.dto.notice.user.NoticeUser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Headers;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class NoticeExam extends AppCompatActivity {

    private EditText et_ok;
    private EditText et_totalCount;
    private EditText et_item;
    private EditText et_blank;
    private Button button_test;

    private Context context;
    private NoticeUser.Response noticeUser;     //이거는 json in json 을 dto로 변환시켜준 클래스임. 결국 plugin 찾아서 겨우 했는데
                                                //부장님이 Response함수로 더 묶어서 보내주신게 정답이었다.

    //로그인 하자
    private SharedPreferences preferences;
    private OkHttpClient client;

    //List<Items> item 클래스 사용할거임
    //private NoticeUser.Items noticeItems;     //ㄴㄴ 큰별대리님이 해결해주심.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice_exam);

        et_ok = findViewById(R.id.et_ok);
        et_totalCount = findViewById(R.id.et_totalcount);
        et_item = findViewById(R.id.et_item);
        et_blank = findViewById(R.id.et_blank);
        button_test = findViewById(R.id.button_test);


        //이놈은 LoginActivity에서 저장한 쉐어프리퍼렌스 값을 다른 액티비티에서도 불러오기 위해 무조건 작성해야하는 한줄 코드이다.
        preferences = getSharedPreferences("Codemanager", Activity.MODE_PRIVATE); //preferences.getString("authToken", "none");

        client = new OkHttpClient();
        requestAsyncGetHttp();

        //아 리사이클러뷰에 list dto(NoticeUser 띄우고 싶다)
        //ArrayList 공부
        String[] arrayObj = new String[2];
        arrayObj[0] = "one";
        arrayObj[1] = "two";
        //[2]부터는 에러 난다. 왜나면 전체크기가 2칸이기 때문에 0,1 만 들어갈 수 있다.
        ArrayList<String> al = new ArrayList<String>();
        al.add("one");      //ArrayList의 add라는 메서드는 어떤 형태의 데이터 타입도 받을 수 있다. 그렇기 때문에 one two three는 String 타입이 아닌
        al.add("two");      //Object 데이터 타입으로 저장되어 있다.      //근데 ArrayList<String>으로 자료형을 지정해주면 그 자료형으로
        al.add("three");
        for(int i=0; i<al.size(); i++){
            //String value = al.get(i);     //그렇기 때문에 얘는 에러가 나는 것이다.
            String value1 = String.valueOf(al.get(i));   //최근 방식
            String value2 = (String)al.get(i);          //옛날 방식
            System.out.println(al.get(i));      //i=0 one, i=1 two, i=2 three
        }

        /**
         * NoticeUser.Items(int a, String b, int c, String d) 이렇게 하는거냐?
         *
         * NoticeUser.Response noticeUser를 선언하고
         * Response 안에 있는 List<Items> item을 불러와서
         * Items 클래스의 있는 인자값 4개를 자유롭게 불러올 수 있어야 한다. 이거 어떻게 하냐고
         *
         * 큰별대리님이 해결해주셨다.  .... 되게 간단했다. 멋있다.
         * **/


        //String a = preferences.getString("authToken", "none");
        //System.out.println(a);
        //String a = PreferenceManager.getString(context, "authToken").toString();
        //System.out.println(a);

        //System.out.println(PreferenceManager.getString(context, "authToken"));
        //.addHeader("Content-type", "application/json")


    }

    /**
     * ========= [GET 방식 비동기 HTTP 요청 - 쿼리 파라미터 전송 : okhttp document 공식문서] =========
     **/
    /*public void run() throws Exception {
        Request request = new Request.Builder()
                .url("http://api.ujamang.biz//api/v1/notice/member/coresoft/2022062000001?p=1&rpp=15&q=")
                .addHeader("Authorization", "bearer " + preferences.getString("authToken", "none"))
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                et_blank.setText("실패패패");
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                try (ResponseBody responseBody = response.body()) {
                    if (!response.isSuccessful())
                        throw new IOException("Unexpected code " + response);

                    Headers responseHeaders = response.headers();
                    for (int i = 0, size = responseHeaders.size(); i < size; i++) {
                        System.out.println(responseHeaders.name(i) + ": " + responseHeaders.value(i));
                    }

                    System.out.println(responseBody.string());
                }

            }
        });
    }*/


    /**
     * ========= [GET 방식 비동기 HTTP 요청 - 쿼리 파라미터 전송 : 구글링 블로그] =========
     **/
    public void requestAsyncGetHttp() {
        try {
            //TODO [Gson 선언]
            Gson gson = new Gson();

            //TODO [전송 url 정의 실시]
            String url = "https://api.ujamang.biz//api/v1/notice/member/coresoft/2022062000001";    //얘는 http가 아니고 https라고 써야 한다.
                                    //=> 관련 에러 문장 CLEARTEXT communication to api.ujamang.biz not permitted by network security policy

            //TODO [파라미터값 선언 실시]
            Map params = new HashMap();
            params.put("p", 1);
            params.put("rpp", 15);
            params.put("q", "");        //이놈들은 url 뒤에 붙일 파라미터들은 이것들이 있습니다~ 라고 선언하는것. put 해서 집어 넣는거임.
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
                                noticeUser = gson.fromJson(responseData, NoticeUser.Response.class);


                                //boolean으로 성공은 띄웠다. 일단 성공은 떴으니까 authToken도 잘 맞고, 바로 윗줄에 클래스 매칭도 잘 된거다.
                                //근데 이제 이거 가지고 recyclerview에 띄우는 것과, authToken을 가지고 refreshToken과 함께 interceptor도 해줘야한다.
                                //여기까지가 220728 저녁 8시. 퇴근이다.
                                et_ok.setText(noticeUser.ok ? "성공" : "실패");
                                System.out.println(noticeUser.totalCount);
                                System.out.println("idx : " + noticeUser.item.get(0).idx);
                                System.out.println("title : " + noticeUser.item.get(0).title);
                                System.out.println("hits : " + noticeUser.item.get(0).hits);
                                System.out.println("registerDate : " + noticeUser.item.get(0).registerDate);
                                System.out.println("idx : " + noticeUser.item.get(1).idx);
                                System.out.println("title : " + noticeUser.item.get(1).title);
                                System.out.println("hits : " + noticeUser.item.get(1).hits);
                                System.out.println("registerDate : " + noticeUser.item.get(1).registerDate);
                                //System.out.println(noticeUser.item.get(2).idx);
                                et_totalCount.setText(String.valueOf(noticeUser.totalCount));

                                /*Iterator<NoticeUser.Items> iterator1 = noticeUser.item.iterator();
                                while (iterator1.hasNext())
                                    String element = String.valueOf(iterator1.next());*/

                                /** item 변수를 통해서 Items의 4개 인자를 불러오는 방법 어디 없나?*/
                                //List<NoticeUser.Response> list = new ArrayList<NoticeUser.Response>();
                                //String a = String.valueOf(list.get(0));
                                //et_blank.setText(a);


                                //et_item.setText(String.valueOf(noticeUser.item.size()));
                                //et_blank.setText(String.valueOf(noticeUser));

                                for(int i=0; i<5; i++){
                                    System.out.println(noticeUser.item);
                                }

                                //et_item.setText(noticeUser.item.toString());
                                //et_blank.setText("성공공공");
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
                            et_blank.setText("실패패패");
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