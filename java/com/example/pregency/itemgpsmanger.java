package com.example.pregency;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class itemgpsmanger extends AppCompatActivity {

    Button btn_signupfinsh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_itemgpsmanger);

        btn_signupfinsh = findViewById(R.id.btn_signupfinish);

        btn_signupfinsh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplication(), managerjoin.class);
                startActivity(intent);
            }
        });

        // https://jeongchul.tistory.com/287?category=493892
    }
}