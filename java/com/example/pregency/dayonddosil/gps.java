package com.example.pregency.dayonddosil;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.UiThread;
import androidx.fragment.app.FragmentActivity;

import com.example.pregency.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.naver.maps.geometry.LatLng;
import com.naver.maps.map.CameraUpdate;
import com.naver.maps.map.MapFragment;
import com.naver.maps.map.NaverMap;
import com.naver.maps.map.OnMapReadyCallback;
import com.naver.maps.map.overlay.InfoWindow;
import com.naver.maps.map.overlay.Marker;
import com.naver.maps.map.overlay.Overlay;

public class gps extends FragmentActivity
        implements OnMapReadyCallback{

    static String location;
    private static final String TAG = "gps";
    private FirebaseAuth firebaseAuth;
    static double longitude;
    static double lat;

    FirebaseFirestore db = FirebaseFirestore.getInstance();

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
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gps);

        firebaseAuth = FirebaseAuth.getInstance();

        MapFragment mapFragment = (MapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        if (mapFragment == null) {
            mapFragment = MapFragment.newInstance();
            getSupportFragmentManager().beginTransaction().add(R.id.map, mapFragment).commit();
        }
        mapFragment.getMapAsync(this);
    }
    @UiThread
    @Override
    public void onMapReady(@NonNull NaverMap naverMap) {
        FirebaseUser user = firebaseAuth.getCurrentUser();
        String uid = user.getUid();

        String machineName = findMachineName(uid);


        DocumentReference docRef = db.collection("machines").document(machineName);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    InfoWindow infoWindow = new InfoWindow();
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                        location = (String) document.getData().get("location");
                        assert location != null;
                        String[] latLong = location.split(", ");
                        lat = Double.parseDouble(latLong[0]);
                        longitude = Double.parseDouble(latLong[1]);

                        Log.d("테스트", String.valueOf(lat));
                        Log.d("테스트", String.valueOf(longitude));

                        Marker marker = new Marker();
                        CameraUpdate cameraUpdate = CameraUpdate.scrollTo(new LatLng(lat, longitude));
                        marker.setPosition(new LatLng(lat,longitude));
                        marker.setOnClickListener(new Overlay.OnClickListener() {
                            @Override
                            public boolean onClick(@NonNull Overlay overlay) {
                                if (overlay instanceof Marker) {
                                    Marker marker = (Marker) overlay;
                                    if (marker.getInfoWindow() != null) {
                                        infoWindow.close();
                                        Toast.makeText(gps.this, "InfoWindow Close.", Toast.LENGTH_LONG).show();

                                    }
                                    else {
                                        infoWindow.open(marker);
                                        Toast.makeText(gps.this, "InfoWindow Open.", Toast.LENGTH_LONG).show();
                                        infoWindow.setAdapter(new InfoWindow.DefaultTextAdapter(gps.this) {
                                            @NonNull
                                            @Override
                                            public CharSequence getText(@NonNull InfoWindow infoWindow) {
                                                infoWindow.setOnClickListener(new Overlay.OnClickListener() {
                                                    @Override
                                                    public boolean onClick(@NonNull Overlay overlay) {
                                                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://172.30.1.51:8000/"));
                                                        startActivity(intent);
                                                        return false;
                                                    }
                                                });

                                                return "설치장소";

                                            }
                                        });
                                    }
                                    return true;
                                }
                                return false;

                            }

                        });
                        naverMap.moveCamera(cameraUpdate);
                        marker.setMap(naverMap);
                    } else {
                        Log.d(TAG, "No such document");
                    }
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });
    }
}