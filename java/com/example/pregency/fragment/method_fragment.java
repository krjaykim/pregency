package com.example.pregency.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.pregency.R;
import com.example.pregency.img.fireclothes_img;
import com.example.pregency.img.firerun_img;
import com.example.pregency.img.method_img;


public class method_fragment extends Fragment {

    ImageButton method_btn;
    ImageButton run_btn;
    ImageButton clothes_btn;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View fragment = inflater.inflate(R.layout.fragment_method_fragment, container, false);


        method_btn = fragment.findViewById(R.id.method_btn);
        run_btn = fragment.findViewById(R.id.run_btn);
        clothes_btn = fragment.findViewById(R.id.clothes_btn);


        method_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), method_img.class);
                startActivity(intent);
            }
        });
        run_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), firerun_img.class);
                startActivity(intent);
            }
        });
        clothes_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), fireclothes_img.class);
                startActivity(intent);
            }
        });
        return fragment;
    }

}