package com.example.pregency;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.example.pregency.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class managergpsdetail extends AppCompatActivity {

    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    ImageButton btn_web, btn_live, btn_call;

    private static final String TAG = "managergps_detail";
    private FirebaseAuth firebaseAuth;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    static String phone;

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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.managergpsdetail);

        firebaseAuth = FirebaseAuth.getInstance();

        btn_web = findViewById(R.id.btn_web);
        btn_call = findViewById(R.id.btn_call);
        btn_live = findViewById(R.id.btn_live);

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
                        phone = (String) document.getData().get("number");




                    } else {
                        Log.d(TAG, "No such document");
                    }
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                }

            }
        });

//        웝버튼을 눌렀을때
        btn_web.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(""));
                startActivity(intent);
            }
        });

//        라이브 버튼을 눌렀을때
        btn_live.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(""));
                startActivity(intent);
            }
        });

//        전화 버튼을 눌렀을때
        btn_call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG,"sss"+phone);
                Uri uri = Uri.parse("tel:"+phone);

                Intent intent = new Intent(Intent.ACTION_DIAL, uri);

                startActivity(intent);
            }
        });


    }
}