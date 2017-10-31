package com.example.hyun.drawnav;

import android.Manifest;
import android.app.FragmentManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    main2 frag1;
    private  static final int REQ_PERMISSION =2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        // Intent에서 닉네임 정보 불러와서 네비게이션에 입력하기
        View nav_header_view = navigationView.getHeaderView(0);
        TextView nav_nick = (TextView)nav_header_view.findViewById(R.id.nickText);
        Intent intent = getIntent();
        final String nickFromIntent = intent.getExtras().getString("userNick");
        final String idFromIntent = intent.getExtras().getString("userId");
        nav_nick.setText(nickFromIntent);

        frag1 = new main2();


        getSupportFragmentManager().beginTransaction().replace(R.id.content_main, frag1).commit();

        //지도 권한 확인
        int permissionCHK = ContextCompat.checkSelfPermission(this, Manifest.permission.INTERNET);
        if(permissionCHK == PackageManager.PERMISSION_DENIED){

            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    REQ_PERMISSION);
        }


        // 네비게이션 헤더 클릭이벤트
        nav_header_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("", "헤더 클릭 이동합니다");
                Intent intentNav = new Intent(getApplicationContext(), InfoUpdateActivity.class);
                intentNav.putExtra("userNick", nickFromIntent);
                intentNav.putExtra("userId", idFromIntent);
                startActivity(intentNav);
            }
        });
        /*LinearLayout header = (LinearLayout) nav_header_view.findViewById(R.id.header);
        header.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });*/

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

    }

    //드로우 열고 닫고
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

//setting 버튼 클릭시
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    //네비게이션 아이템 클릭시 이동, 화면 전환
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        FragmentManager ft = getFragmentManager();

        int id = item.getItemId();
        if(id ==R.id.home)
        {
            getSupportFragmentManager().beginTransaction().replace(R.id.content_main, frag1).commit();
        }
        else if(id ==R.id.search)
        {
            Intent intent = new Intent(getApplicationContext(), map.class);
            startActivity(intent);

        }
        else if(id==R.id.event)
        {
            Intent intent = new Intent(getApplicationContext(), hashtag.class);
            startActivity(intent);

        }
        else if(id== R.id.game)
        {
            Intent intent = new Intent(getApplicationContext(), randomgame.class);
            startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
