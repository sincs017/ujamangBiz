package com.ujamang.biz;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.ujamang.biz.ui.drawer.codemanager.CodeActivity;
import com.ujamang.biz.ui.dashboard.DashboardFragment;
import com.ujamang.biz.ui.drawer.DrawerFragment;
import com.ujamang.biz.ui.drawer.notice.NoticeActivity;
import com.ujamang.biz.ui.drawer.notice.NoticeExam;
import com.ujamang.biz.ui.drawer.notice.detail.NoticeDetailActivity;
import com.ujamang.biz.ui.project.ProjectFragment;
import com.ujamang.biz.ui.schedule.ScheduleFragment;
import com.ujamang.biz.ui.setting.SettingFragment;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    //appBar
    private MaterialToolbar mToolbar;

    //bottomNavigationView
    private BottomNavigationView bottomNavigationView;
    private NavController navController;        //이게 뭘까? 귀찮게 안해주고 컨트롤러로 한방에 연동시켜주는 거였네.
                                                //좋은 건데, 여기서는 사용 못할 듯. 직접 연결해줘야할거 같다.
    private DashboardFragment dashboardFragment;
    private ProjectFragment projectFragment;
    private ScheduleFragment scheduleFragment;
    private SettingFragment settingFragment;
    private DrawerFragment drawerFragment;

    //drawerLayout
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle toggle;
    private NavigationView drawerNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //appBar
        mToolbar = findViewById(R.id.main_toolbar);
        setSupportActionBar(mToolbar);
        //메인페이지의 appBar에서는 뒤로가기 버튼이 필요가 없다.
        /*//appBar 뒤로가기버튼
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);*/

        //bottomNavigationView
        bottomNavigationView = findViewById(R.id.main_bottom_nav_view);
        navController = Navigation.findNavController(this, R.id.main_frame_layout);
        //bottom 각각 연결
        dashboardFragment = new DashboardFragment();
        projectFragment = new ProjectFragment();
        scheduleFragment = new ScheduleFragment();
        settingFragment = new SettingFragment();
        drawerFragment = new DrawerFragment();

        //drawerLayout
        drawerLayout = findViewById(R.id.main_drawerLayout);
        drawerNavigationView = findViewById(R.id.main_drawer_nav);
        //toggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.start, R.string.close);
        //drawerLayout.addDrawerListener(toggle);
        //toggle.syncState();

        //getSupportActionBar().setDisplayHomeAsUpEnabled(true); //메인페이지의 appBar에서는 뒤로가기 버튼이 필요가 없다.
        drawerNavigationView.setNavigationItemSelectedListener(this);


        // 초기화면 설정은 따로 안해주려고 한다. 왜냐? res/navigation에 정의 되어 있기 때문, 근데 거기랑 연결이 안되어 있으면 초기화면 설정을 해줘야 할 것 같다.
        // bottomNavigationView 네비게이션 5개 버튼을 controller로 걸어버렸는데,
        /*NavigationUI.setupWithNavController(bottomNavigationView, navController);*/
        // 5번째 바텀 버튼은 drawer를 불러내야해서 별도로 (오버라이드 느낌으로?) 이벤트를 걸어주게 될 것이다.
        // 여기서 에러가 발생하게 된다면, 위에 컨트롤러를 해제하고 각각의 프레그먼트를 하나씩 따로 연결시켜주어야 할 것이다.
        // (왜냐면 겹쳐서 발생하는 에러일 수 있으니까)
        NavigationBarView navigationBarView = findViewById(R.id.main_bottom_nav_view);
        navigationBarView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId())
                {
                    case R.id.navigation_dashboard:
                        getSupportFragmentManager().beginTransaction().replace(R.id.main_frame_layout, dashboardFragment).commit();
                        return true;
                    case R.id.navigation_project:
                        getSupportFragmentManager().beginTransaction().replace(R.id.main_frame_layout, projectFragment).commit();
                        /*어딘가 페이지(fragment)가 겹쳐보이는 현상이 발생하는데, 일단은 큰 문제 없이 오류는 안남.
                        * Hello blank fragment라는 textView가 fragment_project.xml에는 작성되어 있지 않은데,
                        * 실제 페이지에서는 textView가 보이네? 근데 오류는 안남. */
                        return true;
                    case R.id.navigation_schedule:
                        getSupportFragmentManager().beginTransaction().replace(R.id.main_frame_layout, scheduleFragment).commit();
                        return true;
                    case R.id.navigation_setting:
                        getSupportFragmentManager().beginTransaction().replace(R.id.main_frame_layout, settingFragment).commit();
                        return true;
                    case R.id.navigation_drawer:
                        if (!drawerLayout.isDrawerOpen(GravityCompat.START)) {
                        drawerLayout.openDrawer(GravityCompat.START);
                        } else drawerLayout.closeDrawer(GravityCompat.END);

                        return true;
                }
                return false;
            }
        });

    }

    //appBar 왼쪽 메뉴 누르는건가? 아니 오른쪽 메뉴다.
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.main_toolbar_menu, menu);
        return true;
    }

    /*//bottom 5번째 버튼 눌렀을 때 drawer 열리는거 해보는 중이었음
    @Override
    public void onDrawerClosed(View drawerView){
        super.onDrawerClosed(drawerView);

    }*/

    //drawerLayout AppBar의 메뉴항목 눌렀을 때 drawerLayout 나타나게 하는 이벤트 설정
    //뒤로가기 버튼을 3줄짜리 메뉴 아이콘으로 바꿔주는 코드? 구체적으로는 잘 모르겠다.
    //정확히 알았다. 이거는 왼쪽 상단 메뉴 눌리게 해주는 이벤트임. drawer 목록들 쫙 펼치고 switch case 사용해서
    //누르는 이벤트임.
    //메인페이지에서는 이거 이벤트는 필요 없고, 뒤로가기 버튼만 있으면 됨.
    //(최종) 아니지. 메인페이지에서는 그냥 왼쪽 버튼이 필요가 없네.
    /*@Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
        *//*if (toggle.onOptionsItemSelected(item)) {
            return true;
        }
        return true;*//*
    }*/

    //drawerLayout 항목 클릭했을 때 이벤트 작성
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_company:
                Toast.makeText(this, "Company", Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_customer:
                Toast.makeText(this, "Customer", Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_sharingSite:
                Toast.makeText(this, "SharingSite", Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_sharingReference:
                Toast.makeText(this, "SharingReference", Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_notice:
                Toast.makeText(this, "Notice", Toast.LENGTH_SHORT).show();
                Intent intent_notice = new Intent(MainActivity.this, NoticeActivity.class);
                startActivity(intent_notice);
                break;
            //마이페이지
            case R.id.nav_myPage_myInforUpdate:
                Toast.makeText(this, "MyInforUpdate", Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_myPage_passwordChange:
                Toast.makeText(this, "PasswordChange", Toast.LENGTH_SHORT).show();
                break;
            //설정
            case R.id.nav_setting_codeManager:
                Toast.makeText(this, "CodeManager", Toast.LENGTH_SHORT).show();
                Intent intent_codeManager = new Intent(MainActivity.this, CodeActivity.class);
                startActivity(intent_codeManager);
                break;
            case R.id.nav_setting_userManager:
                Toast.makeText(this, "UserManager", Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_setting_turnService:
                Toast.makeText(this, "TurnService", Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_setting_companyInforUpdate:
                Toast.makeText(this, "CompanyInforUpdate", Toast.LENGTH_SHORT).show();
                break;
            //고객센터
            case R.id.nav_customerCenter_VOC:
                Toast.makeText(this, "VOC", Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_customerCenter_termsAndConditions:
                Toast.makeText(this, "TermsAndConditions", Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_customerCenter_personalInforHandlingPolicy:
                Toast.makeText(this, "PersonalInforHandlingPolicy", Toast.LENGTH_SHORT).show();
                break;

        }
        return true;
    }
}