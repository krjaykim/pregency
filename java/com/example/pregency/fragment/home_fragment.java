package com.example.pregency.fragment;

import android.content.Intent;
import android.net.Uri;
import android.nfc.Tag;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.StringRequest;
import com.bumptech.glide.Glide;
import com.example.pregency.LodingActivity;
import com.example.pregency.R;
import com.example.pregency.dayonddosil.gps;
import com.example.pregency.mainlogin;
import com.example.pregency.managergps;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.annotations.Nullable;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.sql.Date;
import java.text.SimpleDateFormat;


public class home_fragment extends Fragment {


    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    private DatabaseReference mDatabase;
    private static final String TAG = "home_fragment";
    private FirebaseAuth firebaseAuth;

    ImageButton btn_firecall, btn_fireextin, btn_cctv;
    TextView todaydate, txt_temp, txt_hum, txt_location;
    ConstraintLayout main1;


    FirebaseFirestore db = FirebaseFirestore.getInstance();


    long now = System.currentTimeMillis();
    Date date = new Date(now);
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    String getTime = dateFormat.format(date);
    String temp="";
   static String url;
   static String location;
   static String sensor;
    static String a ;
    StringRequest request;

    // 기기마다 각자 값들이 다르기 때문에 만들어줌
    String findMachineName( String uid ) {
        CollectionReference colRef = db.collection("machines").document("iot1").collection(uid);
        if( colRef != null ) {
            //uid 콜렉션이 존재함.
            return "iot1";
        }
        colRef = db.collection("machines").document("iot2").collection(uid);
        if( colRef != null ) {
            //uid 콜렉션이 존재함.
            return "iot2";
        }
        return "";
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View fragment = inflater.inflate(R.layout.fragment_home_fragment, container, false);

        firebaseAuth = FirebaseAuth.getInstance();

        mDatabase = FirebaseDatabase.getInstance().getReference();
        main1 = fragment.findViewById(R.id.main1);
        btn_cctv = fragment.findViewById(R.id.btn_cctv);
        txt_location = fragment.findViewById(R.id.txt_location);
        btn_fireextin = fragment.findViewById(R.id.btn_fireextin);
        btn_firecall = fragment.findViewById(R.id.btn_firecall);
        todaydate = fragment.findViewById(R.id.todaydate);
        txt_hum = fragment.findViewById(R.id.txt_hum);
        txt_temp = fragment.findViewById(R.id.txt_temp);

        todaydate.setText(getTime);


        FirebaseUser user = firebaseAuth.getCurrentUser();
        String uid = user.getUid();

        // 위에서 만든 자신이 어떤 기기에 속해있는지 찾아주는곳
        String machineName = findMachineName(uid);

        // 데이터들을 가져오는 찾는 구간
        DocumentReference docRef = db.collection("machines").document(machineName);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                        url = (String) document.getData().get("url");
                        location = (String) document.getData().get("realmap");

                        txt_location.setText(location);

                    } else {
                        Log.d(TAG, "No such document");
                    }
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                }

            }
        });


        // 실시간 데이터 대기 구문 ( 화면 바꿔주는 구간 )
        final DocumentReference docRef1 = db.collection("machines").document(machineName);
        docRef1.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot snapshot,
                                @Nullable FirebaseFirestoreException e) {
                if (e != null) {
                    Log.w(TAG, "Listen failed.", e);
                    return;
                }

                if (snapshot != null && snapshot.exists()) {
                    Log.d(TAG, "Current data: " + snapshot.getData());
                    sensor = (String) snapshot.getData().get("sensorvalue");
                    a = (String) snapshot.getData().get("aurdo");
                    Log.d(TAG, "where" + sensor);
                    if(sensor.equals("0")){
                        main1.setBackgroundResource(R.drawable.advanceimg);
                    }else if (sensor.equals("1")) {
                        main1.setBackgroundResource(R.drawable.advaneimgg);
                    } else if (sensor.equals("2")) {
                        main1.setBackgroundResource(R.drawable.emergencyimg);
                    }
                } else {
                    Log.d(TAG, "Current data: null");
                }
            }
        });






        btn_firecall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri = Uri.parse("tel:119");

                Intent intent = new Intent(Intent.ACTION_DIAL, uri);

                startActivity(intent);
            }
        });



        btn_fireextin.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                int status = motionEvent.getAction();

                if(status == motionEvent.ACTION_DOWN){
                    a = "누름";
                    DocumentReference washingtonRef = db.collection("machines").document(machineName);

                    washingtonRef
                            .update("aurdo", a)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Log.d(TAG, "DocumentSnapshot successfully updated!");
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.w(TAG, "Error updating document", e);
                                }
                            });
                }
                if(status == motionEvent.ACTION_DOWN){
                    a = "뗌";
                    DocumentReference washingtonRef = db.collection("machines").document(machineName);

                    washingtonRef
                            .update("aurdo", a)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Log.d(TAG, "DocumentSnapshot successfully updated!");
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.w(TAG, "Error updating document", e);
                                }
                            });
                }

                return false;
            }
        });


     //   a = true;


        btn_cctv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "name"+url);
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                startActivity(intent);
            }
        });

        txt_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), gps.class);
                startActivity(intent);
            }
        });




        return fragment;

    }
}