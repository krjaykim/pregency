package com.example.pregency;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pregency.dayonddosil.geo;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class itemregister extends AppCompatActivity {

    TextView text_location;
    EditText edt_itemnum;
    Button btn_register, btn_location;
    CheckBox check_fire;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    String sss;
    static String location;

    private static final String TAG = "itemregister";

    private FirebaseAuth firebaseAuth;

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
        setContentView(R.layout.activity_itemregister);


        firebaseAuth = FirebaseAuth.getInstance();

        btn_location = findViewById(R.id.btn_location);
        text_location = findViewById(R.id.text_location);
        btn_register = findViewById(R.id.btn_register);
        check_fire = findViewById(R.id.check_fire);

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String location = text_location.getText().toString().trim();

                FirebaseUser user = firebaseAuth.getCurrentUser();

                Map<String, String> iotnum =new HashMap<>();

                iotnum.put("location", location);
                String uid = user.getUid();
                String machineName = findMachineName( uid );

                DocumentReference washingtonRef = db.collection("machines").document(machineName);

                washingtonRef
                        .update("location", location)
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

                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference reference = database.getReference("user");
                reference.child(uid).setValue(iotnum);


                if(check_fire.isChecked()){

                    Intent intent = new Intent(getApplication(), itemgpsmanger.class);
                    startActivity(intent);
                }else{

                    Intent intent1 = new Intent(getApplication(), main2.class);
                    startActivity(intent1);

                }





            }
        });
        btn_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2 = new Intent(itemregister.this, geo.class);
                startActivityForResult(intent2, 1000);

            }
        });

    }
    @Override protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1000) {
            if (resultCode == RESULT_OK) {
                sss = data.getStringExtra("ResultMsg");
                Toast.makeText(this, "RESULT_OK : " + sss, Toast.LENGTH_SHORT).show();
            } else if (resultCode == RESULT_CANCELED) {
                String resultMsg = data.getStringExtra("ResultMsg");
                Toast.makeText(this, "RESULT_CANCELED : " + sss, Toast.LENGTH_SHORT).show();
            } else {

            }
        }
        text_location.setText(sss);
    }





}