package com.example.pregency;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class joinpage extends AppCompatActivity {

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    private static final String TAG = "joinpage";
    private FirebaseAuth firebaseAuth;
    Button btn_join;
    EditText
            edt_joinname, edt_joinnick, edt_joinid, edt_joinpw, edt_joinpwcheck, edt_joinnum, edt_iotnum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_joinpage);


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

            final String email = edt_joinid.getText().toString().trim();
            String pw = edt_joinpw.getText().toString().trim();
            String pwcheck = edt_joinpwcheck.getText().toString().trim();

                if (pw.equals(pwcheck)) {
                    Log.d(TAG, "등록버튼" + email + "," + pw);
                    final ProgressDialog mdialog = new ProgressDialog(joinpage.this);
                    mdialog.setMessage("가입중입니다....");
                    mdialog.show();

                    firebaseAuth.createUserWithEmailAndPassword(email, pw).addOnCompleteListener(joinpage.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            //가입 성공시
                            if (task.isSuccessful()) {
                                mdialog.dismiss();

                                FirebaseUser user = firebaseAuth.getCurrentUser();

                                String id = edt_joinid.getText().toString().trim();
                                String iotnum = edt_iotnum.getText().toString().trim();
                                String email = user.getEmail();
                                String uid = user.getUid();
                                String name = edt_joinname.getText().toString().trim();
                                String num = edt_joinnum.getText().toString().trim();
                                String nick = edt_joinnick.getText().toString().trim();

                                //해쉬맵 테이블을 파이어베이스 데이터베이스에 저장
                                CollectionReference members = db.collection("members");
                               Map<String, String> userinfo = new HashMap<>();

                                userinfo.put("email",email);
                                userinfo.put("uid", uid);
                                userinfo.put("name", name);
                                userinfo.put("num", num);
                                userinfo.put("nick", nick);
                                members.document("member").set(userinfo);

                                db.collection("machines").document(iotnum).collection(id)
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



                                FirebaseDatabase database = FirebaseDatabase.getInstance();
                                DatabaseReference reference = database.getReference("user");
                                reference.child(uid).setValue(userinfo);



                                //가입이 이루어져을시 가입 화면을 빠져나감.
                                Intent intent = new Intent(joinpage.this, itemregister.class);
                                startActivity(intent);
                                finish();
                                Toast.makeText(joinpage.this, "회원가입에 성공하셨습니다.", Toast.LENGTH_SHORT).show();

                            } else {
                                mdialog.dismiss();
                                Toast.makeText(joinpage.this, "이미 존재하는 아이디 입니다.", Toast.LENGTH_SHORT).show();
                                return;  //해당 메소드 진행을 멈추고 빠져나감.
                            }
                    }
                });


                    //비밀번호 오류시
                }else{

                    Toast.makeText(joinpage.this, "비밀번호가 틀렸습니다. 다시 입력해 주세요.", Toast.LENGTH_SHORT).show();
                    return;
                }
            }
        });

    }


}
