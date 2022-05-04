package com.example.pregency.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.fragment.app.Fragment;

import com.example.pregency.R;
import com.example.pregency.dayonddosil.gps;


public class tv_fragment extends Fragment {

    private WebView mWebView;
    ImageButton call_119, btn_map;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View fragment = inflater.inflate(R.layout.fragment_tv_fragment, container, false);

//        mWebView = (WebView)fragment.findViewById(R.id.wv);
//        mWebView.setWebViewClient(new WebViewClient());
//        mWebView.getSettings().setJavaScriptEnabled(true);
//
//        mWebView.loadUrl("http://172.30.1.26:8000/");

        btn_map = fragment.findViewById(R.id.btn_map);
        btn_map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), gps.class);
                startActivity(intent);
            }
        });
        call_119 = fragment.findViewById(R.id.call_119);
        call_119.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri = Uri.parse("tel:119");
                Intent intent = new Intent(Intent.ACTION_DIAL, uri);

                startActivity(intent);
            }
        });







        return fragment;
    }
}