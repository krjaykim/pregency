package com.example.pregency;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.UiThread;
import androidx.fragment.app.FragmentActivity;

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
import com.naver.maps.map.util.MarkerIcons;

import java.util.ArrayList;
import java.util.List;

public class managergps extends FragmentActivity
        implements OnMapReadyCallback {

    static String location;
    static String sensor;
    private static final String TAG = "gps";
    private FirebaseAuth firebaseAuth;
    //static double longitude;
    //static double lat;
    String[] iot = new String[]{"iot1", "iot2", "iot3", "iot4", "iot5"};
    static double[] lat = new double[5];
    static double[] longitude = new double[5];
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    List<Marker> markers = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.managergps);

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

        for (int i = 0; i < iot.length; i++) {
            int j = i;
            DocumentReference docRef = db.collection("machines").document(iot[i]);
            docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if(task.isSuccessful()){
                        InfoWindow infoWindow = new InfoWindow();
                        DocumentSnapshot document = task.getResult();
                        if (document.exists()) {
                            Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                            location = (String) document.getData().get("location");
                            sensor = (String) document.getData().get("sensorvalue");
                            assert location != null;
                            String[] latLong = location.split(",");
//                            Log.d(TAG,"log : " +latLong[0]);
                            if(sensor.equals("0")) {
                                lat[j] = Double.parseDouble(latLong[0]);
                                longitude[j] = Double.parseDouble(latLong[1]);
                                Marker marker = new Marker();
                                CameraUpdate cameraUpdate = CameraUpdate.scrollTo(new LatLng(lat[j], longitude[j]));
                                marker.setPosition(new LatLng(lat[j], longitude[j]));
                                Log.d(TAG, "whereru" + lat[0] + " / " + "test : " + longitude[0]);
                                marker.setOnClickListener(new Overlay.OnClickListener() {
                                    @Override
                                    public boolean onClick(@NonNull Overlay overlay) {
                                        if (overlay instanceof Marker) {
                                            Marker marker = (Marker) overlay;
                                            if (marker.getInfoWindow() != null) {
                                                infoWindow.close();
                                                Toast.makeText(managergps.this, "InfoWindow Close.", Toast.LENGTH_LONG).show();

                                            } else {
                                                infoWindow.open(marker);
                                                Toast.makeText(managergps.this, "InfoWindow Open.", Toast.LENGTH_LONG).show();
                                                infoWindow.setAdapter(new InfoWindow.DefaultTextAdapter(managergps.this) {
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
                                            return false;
                                        }
                                        return false;

                                    }

                                });
                                naverMap.moveCamera(cameraUpdate);
                                markers.add(marker);
                                for (Marker m : markers) {
                                    m.setMap(naverMap);
                                }
                            }else if(sensor.equals("1")){
                                lat[j] = Double.parseDouble(latLong[0]);
                                longitude[j] = Double.parseDouble(latLong[1]);
                                Marker marker = new Marker();
                                marker.setIconTintColor(Color.YELLOW);
                                CameraUpdate cameraUpdate = CameraUpdate.scrollTo(new LatLng(lat[j], longitude[j]));
                                marker.setPosition(new LatLng(lat[j], longitude[j]));
                                Log.d(TAG, "whereru" + lat[0] + " / " + "test : " + longitude[0]);
                                marker.setOnClickListener(new Overlay.OnClickListener() {
                                    @Override
                                    public boolean onClick(@NonNull Overlay overlay) {
                                        if (overlay instanceof Marker) {
                                            Marker marker = (Marker) overlay;
                                            if (marker.getInfoWindow() != null) {
                                                infoWindow.close();
                                                Toast.makeText(managergps.this, "InfoWindow Close.", Toast.LENGTH_LONG).show();

                                            } else {
                                                infoWindow.open(marker);
                                                Toast.makeText(managergps.this, "InfoWindow Open.", Toast.LENGTH_LONG).show();
                                                infoWindow.setAdapter(new InfoWindow.DefaultTextAdapter(managergps.this) {
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
                                            return false;
                                        }
                                        return false;

                                    }

                                });
                                naverMap.moveCamera(cameraUpdate);
                                markers.add(marker);
                                for (Marker m : markers) {
                                    m.setMap(naverMap);
                                }

                            }else if (sensor.equals("2")){
                                lat[j] = Double.parseDouble(latLong[0]);
                                longitude[j] = Double.parseDouble(latLong[1]);
                                Marker marker = new Marker();
                                marker.setIcon(MarkerIcons.BLACK);
                                marker.setIconTintColor(Color.RED);
                                CameraUpdate cameraUpdate = CameraUpdate.scrollTo(new LatLng(lat[j], longitude[j]));
                                marker.setPosition(new LatLng(lat[j], longitude[j]));
                                Log.d(TAG, "whereru" + lat[0] + " / " + "test : " + longitude[0]);
                                marker.setOnClickListener(new Overlay.OnClickListener() {
                                    @Override
                                    public boolean onClick(@NonNull Overlay overlay) {
                                        if (overlay instanceof Marker) {
                                            Marker marker = (Marker) overlay;
                                            if (marker.getInfoWindow() != null) {
                                                infoWindow.close();
                                                Toast.makeText(managergps.this, "InfoWindow Close.", Toast.LENGTH_LONG).show();

                                            } else {
                                                infoWindow.open(marker);
                                                Toast.makeText(managergps.this, "InfoWindow Open.", Toast.LENGTH_LONG).show();
                                                infoWindow.setAdapter(new InfoWindow.DefaultTextAdapter(managergps.this) {
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
                                            return false;
                                        }
                                        return false;

                                    }

                                });
                                naverMap.moveCamera(cameraUpdate);
                                markers.add(marker);
                                for (Marker m : markers) {
                                    m.setMap(naverMap);
                                }
                            }
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
}