package com.example.pregency;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class managerjoin extends AppCompatActivity {

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private static final String TAG = "manaberjoin";
    private FirebaseAuth firebaseAuth;


    EditText m_edt_joinid,m_edt_joinpw;
    Button m_btn_join;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_managerjoin);

        firebaseAuth = FirebaseAuth.getInstance();


        m_edt_joinid = findViewById(R.id.m_edt_joinid);
        m_edt_joinpw = findViewById(R.id.m_edt_joinpw);
        m_btn_join = findViewById(R.id.m_btn_join);

        m_btn_join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String id = m_edt_joinid.getText().toString().trim();
                String pw = m_edt_joinpw.getText().toString().trim();

                CollectionReference members = db.collection("manager");

                Map<String, String> userinfo = new HashMap<>();

                userinfo.put("id",id);
                userinfo.put("pw", pw);

                db.collection("manager").document("member").collection(id)
                        .add(userinfo)
                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                Log.d(TAG,"idinput"+documentReference.getId());
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.w(TAG, "Error adding document", e);
                            }
                        });

                Intent intent = new Intent(managerjoin.this, managergps.class);
                startActivity(intent);
                finish();

            }
        });

    }
}