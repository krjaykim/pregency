package com.example.pregency;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class membermodi extends AppCompatActivity {

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    private static final String TAG = "joinpage";
    private FirebaseAuth firebaseAuth;
    Button btn_join;
    EditText
            edt_joinname, edt_joinnick, edt_joinid, edt_joinpw, edt_joinpwcheck, edt_joinnum, edt_iotnum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_membermodi);


        firebaseAuth = FirebaseAuth.getInstance();


        edt_iotnum = findViewById(R.id.edt_iotnum);
        btn_join = findViewById(R.id.m_btn_join);
        edt_joinname = findViewById(R.id.edt_joinname);
        edt_joinnick = findViewById(R.id.edt_joinnick);
        edt_joinid = findViewById(R.id.m_edt_joinid);
        edt_joinpw = findViewById(R.id.m_edt_joinpw);
        edt_joinpwcheck = findViewById(R.id.edt_joinpwcheck);
        edt_joinnum = findViewById(R.id.edt_joinnum);


        btn_join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String email = edt_joinid.getText().toString().trim();
            String pw = edt_joinpw.getText().toString().trim();
            String pwcheck = edt_joinpwcheck.getText().toString().trim();



                                FirebaseUser user = firebaseAuth.getCurrentUser();

                                String id = edt_joinid.getText().toString().trim();
                                String iotnum = edt_iotnum.getText().toString().trim();
                                String uid = user.getUid();
                               // String id = edt_joinid.getText().toString().trim();
                               // String pw = edt_joinpw.getText().toString().trim();
                                String name = edt_joinname.getText().toString().trim();
                                String num = edt_joinnum.getText().toString().trim();
                                String nick = edt_joinnick.getText().toString().trim();

                                //????????? ???????????? ?????????????????? ????????????????????? ??????
                                CollectionReference members = db.collection("members");
                               Map<String, String> userinfo = new HashMap<>();

                                userinfo.put("email",email);
                                userinfo.put("uid", uid);
                                userinfo.put("name", name);
                                userinfo.put("num", num);
                                userinfo.put("nick", nick);

                                members.document("member").set(userinfo);





                                FirebaseDatabase database = FirebaseDatabase.getInstance();
                                DatabaseReference reference = database.getReference("user");
                                reference.child(uid).setValue(userinfo);



                                //????????? ?????????????????? ?????? ????????? ????????????.
                                Intent intent = new Intent(membermodi.this, itemregister.class);
                                startActivity(intent);
                                finish();
                                Toast.makeText(membermodi.this, "??????????????? ?????????????????????.", Toast.LENGTH_SHORT).show();



                    //???????????? ?????????

            }
        });

    }


}
