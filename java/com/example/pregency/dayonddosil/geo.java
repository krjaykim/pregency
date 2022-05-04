package com.example.pregency.dayonddosil;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.pregency.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class geo extends AppCompatActivity {

    static String sss;
    static String et3;
    EditText editText3;

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private static final String TAG = "geo";

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
        setContentView(R.layout.activity_geo);

        firebaseAuth = FirebaseAuth.getInstance();

        editText3 = findViewById(R.id.editText3);
        // 지오코딩(GeoCoding) : 주소,지명 => 위도,경도 좌표로 변환
        //     위치정보를 얻기위한 권한을 획득, AndroidManifest.xml
        //    ACCESS_FINE_LOCATION : 현재 나의 위치를 얻기 위해서 필요함
        //    INTERNET : 구글서버에 접근하기위해서 필요함

        final TextView tv = (TextView) findViewById(R.id.textView99); // 결과창

        Button b2 = (Button)findViewById(R.id.button99);
        Button b4 = (Button)findViewById(R.id.button100);




        final Geocoder geocoder = new Geocoder(this);


        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<Address> list = null;


               et3 = editText3.getText().toString();
                try {
                    list = geocoder.getFromLocationName(
                            et3, // 지역 이름
                            10); // 읽을 개수
                } catch (IOException e) {
                    e.printStackTrace();
                    Log.e("test","입출력 오류 - 서버에서 주소변환시 에러발생");
                }

                if (list != null) {
                    if (list.size() == 0) {
                        tv.setText("해당되는 주소 정보는 없습니다");
                    } else {
                        Address addr = list.get(0);
                        double lat = addr.getLatitude();
                        double lon = addr.getLongitude();

                        sss = String.format("%f , %f", lat, lon);
                        tv.setText(sss);
                        //          list.get(0).getCountryName();  // 국가명
                        //          list.get(0).getLatitude();        // 위도
                        //          list.get(0).getLongitude();    // 경도

                        FirebaseUser user = firebaseAuth.getCurrentUser();

                        Map<String, String> iotnum =new HashMap<>();

                        iotnum.put("realmap", et3);
                        String uid = user.getUid();
                        String machineName = findMachineName( uid );

                        DocumentReference washingtonRef = db.collection("machines").document(machineName);

// Set the "isCapital" field of the city 'DC'
                        washingtonRef
                                .update("realmap", et3)
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
                    }
                }
            }

        });
        b4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.putExtra("ResultMsg", sss);
                setResult(RESULT_OK, intent);
                finish();



            }
        });









    } // end of onCreate
} // end of class