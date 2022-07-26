package com.ujamang.biz.ui.drawer.codemanager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.ujamang.biz.R;

import java.util.Objects;

public class CodeDetailCreate extends AppCompatActivity {

    //appbar
    private MaterialToolbar mToolbar;

    //액티비티에서 액티비티로 데이터 전달
    private MaterialButton createButton;
    private TextInputEditText textInputEditText;
    //private TextInputLayout textInputLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_code_detail_create);

        //appbar
        mToolbar = findViewById(R.id.codeManager_toolbar);
        setSupportActionBar(mToolbar);
        //appbar 뒤로가기버튼
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        textInputEditText = (TextInputEditText) findViewById(R.id.code_detail_create_TIE);

        //액티비티에서 액티비티로 데이터 전달
        createButton = findViewById(R.id.code_detail_create_button);
        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String input = Objects.requireNonNull(textInputEditText.getText()).toString();

                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);       // 이거 3개는 뭐임?
                sendIntent.putExtra(Intent.EXTRA_TEXT, input);  // developers 다른 앱으로
                sendIntent.setType("text/plain");               // 간단한 데이터 보내기 참고했음

                Intent shareIntent = Intent.createChooser(sendIntent, null);
                startActivity(shareIntent);



                Intent intent = new Intent(CodeDetailCreate.this, CodeDetailActivity.class);
                intent.putExtra("data", input);
                startActivity(intent);

                //내일 와서 하자. 근데 내일은 토요일이다^^ ㅋ
                //ActivityResultContracts.StartActivityForResult(intent, input);

                //intent.putExtra("text", input);
                //startActivity(intent);
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