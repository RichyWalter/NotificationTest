package de.richardwaler.notificationtest;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.content.Intent;
import android.app.Notification;
import android.provider.Settings;
import android.view.View;


public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private static final int notification_one = 101;
    private static final int notification_two = 102;
    private MainUi mainUI;

    private NotificationHelper notificationHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            setContentView(R.layout.activity_main_oreo);
            notificationHelper = new NotificationHelper(this);
            mainUI = new MainUi(findViewById(R.id.activity_main));
        }else{
            setContentView(R.layout.activity_main_fallback);
            final Button button = findViewById(R.id.notification_fallback);
            button.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    sendFallbackNotification();
                }
            });
        }
    }

//fallback notification for Android SDK < 26

    public void sendFallbackNotification(){
        //set and configure Notification
        final NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setContentTitle(getString(R.string.fallbackNotification_title))
                .setContentText(getString(R.string.fallbackNotification_text))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setVibrate(new long[] { 1000, 1000})
                .setSound(Settings.System.DEFAULT_NOTIFICATION_URI)
                .setLights(0xff00ffff, 350, 150);
        final int notificationId = 1;

        final NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);

        // notificationId is a unique int for each notification that you must define
        notificationManager.notify(notificationId, mBuilder.build());
    }

//Post the notifications//

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void postNotification(int id, String title) {
        Notification.Builder notificationBuilder = null;
        switch (id) {
            case notification_one:
                notificationBuilder = notificationHelper.getNotification1(title,
                        getString(R.string.channel_one_body));
                break;

            case notification_two:
                notificationBuilder = notificationHelper.getNotification2(title,
                        getString(R.string.channel_two_body));
                break;
        }

        if (notificationBuilder != null) {
            notificationHelper.notify(id, notificationBuilder);
        }
    }

//Load the settings screen for the selected notification channel//

    public void goToNotificationSettings(String channel) {
        Intent i = new Intent(Settings.ACTION_CHANNEL_NOTIFICATION_SETTINGS);
        i.putExtra(Settings.EXTRA_APP_PACKAGE, getPackageName());
        i.putExtra(Settings.EXTRA_CHANNEL_ID, channel);
        startActivity(i);
    }

//Implement our onClickListeners//

    class MainUi implements View.OnClickListener {
        final EditText editTextOne;
        final EditText editTextTwo;

        private MainUi(View root) {
            editTextOne = (EditText) root.findViewById(R.id.channel_one_text);
            ((Button) root.findViewById(R.id.post_to_channel_one)).setOnClickListener(this);
            ((Button) root.findViewById(R.id.channel_one_settings)).setOnClickListener(this);

            editTextTwo = (EditText) root.findViewById(R.id.channel_two_text);
            ((Button) root.findViewById(R.id.post_to_channel_two)).setOnClickListener(this);
            ((Button) root.findViewById(R.id.channel_two_settings)).setOnClickListener(this);

        }

//Retrieve the contents of each EditText//

        private String getChannelOneText() {
            if (editTextOne != null) {
                return editTextOne.getText().toString();
            }
            return "";
        }

        private String getChannelTwoText() {
            if (editTextOne != null) {
                return editTextTwo.getText().toString();
            }
            return "";
        }

        @RequiresApi(api = Build.VERSION_CODES.O)
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.post_to_channel_one:
                    postNotification(notification_one, getChannelOneText());
                    break;

                case R.id.channel_one_settings:
                    goToNotificationSettings(NotificationHelper.CHANNEL_ONE_ID);
                    break;

                case R.id.post_to_channel_two:
                    postNotification(notification_two, getChannelTwoText());
                    break;

                case R.id.channel_two_settings:
                    goToNotificationSettings(NotificationHelper.CHANNEL_TWO_ID);
                    break;


            }
        }
    }
}