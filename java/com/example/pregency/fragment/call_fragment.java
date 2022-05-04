package com.example.pregency.fragment;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.example.pregency.R;
import com.example.pregency.dayonddosil.Chatmain;


public class call_fragment extends Fragment {

    Button go_board, go_call;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {



        View fragment = inflater.inflate(R.layout.fragment_call_fragment, container, false);

        go_board = fragment.findViewById(R.id.go_board);
        go_call = fragment.findViewById(R.id.go_call);

        go_board.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), Chatmain.class);

                startActivity(intent);
            }

        });

        go_call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:010-1234-1234"));

                // call 기능에 대한 권한 체크하기
                if(ContextCompat.checkSelfPermission(getContext(),
                        Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED){
                    ActivityCompat.requestPermissions(getActivity(),
                            new String[]{Manifest.permission.CALL_PHONE}, 0);
                }


                startActivity(intent);
            }
        });


        // 화면에 대한 구성 시작

        // App에 저장된 정보를 불러오기
        SharedPreferences shared = getActivity().getPreferences(Context.MODE_PRIVATE);

        // 저장된 데이터 꺼내기
        // (저장된 데이터, 오류시 처리할 문구)
        String data =  shared.getString("data","TextView");


        // Inflate the layout for this fragment
        return fragment;
    }
}