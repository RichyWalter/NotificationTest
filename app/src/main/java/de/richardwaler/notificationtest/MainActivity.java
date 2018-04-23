package de.richardwaler.notificationtest;

import android.provider.Settings;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //set and configure Notification
        final NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setContentTitle("Test Titel")
                .setContentText("Dies ist ein Test")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setVibrate(new long[] { 1000, 1000})
                .setSound(Settings.System.DEFAULT_NOTIFICATION_URI)
                .setLights(0xff00ffff, 350, 150);
        final int notificationId = 1;


        final NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);


        final Button button = findViewById(R.id.notification_button);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // notificationId is a unique int for each notification that you must define
                notificationManager.notify(notificationId, mBuilder.build());
            }
        });


    }
}
