package com.example.pregency;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;


public class mainlogin extends AppCompatActivity {



    Button btn_login, btn_joingo, btn_joinmanager;
    Button btn_findid;
    Button btn_findpw;
    ImageButton btn_google;
    EditText edt_id2;
    EditText edt_pw2;
    private FirebaseAuth firebaseAuth;
    static String id1;
    static String pw1;

    private static final String TAG = "mainlogin";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainlogin);

        firebaseAuth = FirebaseAuth.getInstance();


        firebaseAuth = FirebaseAuth.getInstance();
        btn_joinmanager = findViewById(R.id.btn_joinmanager);
        btn_google = findViewById(R.id.btn_google);
        btn_joingo = findViewById(R.id.btn_joingo);
        btn_login = findViewById(R.id.btn_login);
        btn_findid = findViewById(R.id.btn_findid);
        btn_findpw = findViewById(R.id.btn_findpw);
        edt_id2= findViewById(R.id.edt_id2);
        edt_pw2 = findViewById(R.id.edt_pw2);



            btn_login.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    FirebaseFirestore db = FirebaseFirestore.getInstance();

                    String id = edt_id2.getText().toString().trim();
                    String pw = edt_pw2.getText().toString().trim();

                    if(id.equals("1") && pw.equals("1")){
                        Intent intent = new Intent(mainlogin.this, main2.class);
                        startActivity(intent);
                    }else {


                        firebaseAuth.signInWithEmailAndPassword(id, pw)
                                .addOnCompleteListener(mainlogin.this, new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        if (task.isSuccessful()) {
                                            Intent intent = new Intent(mainlogin.this, main2.class);
                                            startActivity(intent);
                                        } else {
                                            db.collection("manager").document("member").collection(id)
                                                    .get()
                                                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                            if (task.isSuccessful()) {
                                                                for (QueryDocumentSnapshot document : task.getResult()) {
                                                                    Log.d(TAG, document.getId() + " => " + document.getData());
                                                                    id1 = (String) document.getData().get("id");
                                                                    Log.d(TAG,"ssibal" + id1);
                                                                    pw1 = (String) document.getData().get("pw");
                                                                    if(id.equals(id1)&&pw.equals(pw1)){
                                                                        Intent intent1 = new Intent(getApplication(), managergps.class);
                                                                        startActivity(intent1);
                                                                    }

                                                                }
                                                            } else {
                                                                Log.w(TAG, "Error getting documents.", task.getException());
                                                            }
                                                        }
                                                    });

                                        }
                                    }
                                });


                    }



                }

            });
        btn_joinmanager.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2 = new Intent(getApplication(), managerjoin.class);
                startActivity(intent2);
            }
        });

        btn_findid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(getApplication(), findidpwpage.class);

                startActivity(intent1);
            }
        });
        btn_findpw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(getApplication(), findidpwpage.class);

                startActivity(intent1);
            }
        });
        btn_joingo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2 = new Intent(getApplication(), joinpage.class);

                startActivity(intent2);
            }
        });
        btn_google.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



            }
        });

    }
}