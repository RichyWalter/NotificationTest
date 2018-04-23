package de.richardwaler.notificationtest;

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


        //set Notification
        final NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setContentTitle("Test Titel")
                .setContentText("Dies ist ein Test")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);
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
