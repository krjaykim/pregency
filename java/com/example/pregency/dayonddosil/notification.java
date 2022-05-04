package com.example.pregency.dayonddosil;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import com.example.pregency.LodingActivity;
import com.example.pregency.R;

public class notification extends AppCompatActivity {

    NotificationManager manager;

    private static String CHANNEL_ID = "channel1";
    private static String CHANNEL_NAME = "Channel1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        Button button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showNoti1();
            }
        });
    }

    public void showNoti1(){
        manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);




        PendingIntent mPendingIntent = PendingIntent.getActivity(
                notification.this,
                0, // 보통 default값 0을 삽입
                new Intent(getApplicationContext(), LodingActivity.class),
                PendingIntent.FLAG_UPDATE_CURRENT
        );





        NotificationCompat.Builder builder = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            manager.createNotificationChannel(new NotificationChannel(
                    CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH
            ));

            builder = new NotificationCompat.Builder(this, CHANNEL_ID);
        } else {
            builder = new NotificationCompat.Builder(this);
        }

        builder.setContentTitle("Pergency");
        builder.setContentText("★☆화재경보감지☆★");
        builder.setSmallIcon(android.R.drawable.star_off);
        builder.setContentIntent(mPendingIntent);
        Notification noti = builder.build();




        manager.notify(1, noti);
        Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        vibrator.vibrate(1000);
    }
}