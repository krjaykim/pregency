package com.example.pregency;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.pregency.fragment.call_fragment;
import com.example.pregency.fragment.home_fragment;
import com.example.pregency.fragment.method_fragment;
import com.example.pregency.fragment.setting_fragment;
import com.example.pregency.fragment.tv_fragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class main2 extends AppCompatActivity {



    home_fragment fragment1;
    tv_fragment fragment2;
    method_fragment fragment3;
    call_fragment fragment4;
    setting_fragment fragment5;


    BottomNavigationView nav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

            fragment1 = new home_fragment();
            fragment2 = new tv_fragment();
            fragment3 = new method_fragment();
            fragment4 = new call_fragment();
            fragment5 = new setting_fragment();



            nav = findViewById(R.id.nav);

            // frame 영역에 화면(fragment) 띄우기
            getSupportFragmentManager().beginTransaction().replace(R.id.frame, fragment1).commit();

            // nav 에 있는 item 선택시 화면 전환하기
            nav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                        if (item.getItemId() == R.id.home_item){
                            // fragment1
                            getSupportFragmentManager().beginTransaction().replace(R.id.frame, fragment1).commit();
                        }else if(item.getItemId() == R.id.tv_item){
                            getSupportFragmentManager().beginTransaction().replace(R.id.frame, fragment2).commit();
                        }else if(item.getItemId() == R.id.method_item){
                            getSupportFragmentManager().beginTransaction().replace(R.id.frame, fragment3).commit();
                        }else if(item.getItemId() == R.id.call_item) {
                            getSupportFragmentManager().beginTransaction().replace(R.id.frame, fragment4).commit();
                        }else if(item.getItemId() == R.id.setting_item) {
                            getSupportFragmentManager().beginTransaction().replace(R.id.frame, fragment5).commit();
                        }


                        return true;
                }
            });

        }
    }

