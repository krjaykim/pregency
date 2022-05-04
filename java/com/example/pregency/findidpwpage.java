package com.example.pregency;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class findidpwpage extends AppCompatActivity {

    EditText finedt_name,findedt_number,fineedt_name2,findedt_number2,findedt_id,findbtn_q,findedt_a;
    Button btn_searchid,btn_searchpw,btn_searchpw2;
    ConstraintLayout cl;
    private static final String TAG = "gps";
    private FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    boolean check;
    static String id;

    FirebaseFirestore db = FirebaseFirestore.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_findidpwpage);



        finedt_name = findViewById(R.id.edt_name);
    findedt_number = findViewById(R.id.edt_num);
            fineedt_name2 = findViewById(R.id.edt_pw_name);
    findedt_number2 = findViewById(R.id.edt_pw_num);
            findedt_id = findViewById(R.id.edt_pw_id);
        findbtn_q =findViewById(R.id.edt_q);
                findedt_a = findViewById(R.id.edt_a);

        btn_searchid = findViewById(R.id.btn_searchid);
        btn_searchpw2 = findViewById(R.id.btn_searchpw2);
        cl = findViewById(R.id.cl);
        btn_searchpw = findViewById(R.id.btn_searchpw);
        check = true;

        btn_searchid.setOnClickListener(view -> {
            String name = finedt_name.toString();
            String num  = findedt_number.toString();

            db.collection("members")
                    .whereEqualTo("name", name)
                    .get()
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());
                                id = (String) document.getData().get("email");
                                Log.d(TAG, "where?" + id);
                            }
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    });

            Toast.makeText(this,id+"아이디입니다.",Toast.LENGTH_SHORT).show();
        });



        btn_searchpw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String id = findedt_id.toString();

                FirebaseAuth auth = FirebaseAuth.getInstance();
                String emailAddress = id;

                auth.sendPasswordResetEmail(emailAddress)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Log.d(TAG, "Email sent.");
                                }
                            }
                        });

               /* if(check){
                    cl.setVisibility(View.VISIBLE);
                }else{
                    cl.setVisibility(View.INVISIBLE);
                }
                check = !check;*/

            }
        });

    }

}
