package com.example.pregency.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

import com.example.pregency.R;
import com.google.firebase.auth.FirebaseAuth;


public class setting_fragment extends Fragment {

    Button logout;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View fragment = inflater.inflate(R.layout.fragment_setting_fragment, container, false);
        logout = fragment.findViewById(R.id.logout);

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();

            }
        });


        return fragment;
    }
}