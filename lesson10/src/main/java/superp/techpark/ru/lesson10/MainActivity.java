package superp.techpark.ru.lesson10;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.firebase.messaging.FirebaseMessaging;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

public class MainActivity extends AppCompatActivity {
    public static final String CHANNEL_DEFAULT = "default";
    public static final String CHANNEL_MESSAGES = "messages";
    public static final String CHANNEL_LIKES = "likes";
    private static final String ACTION_REMOVE_PROGRESS = "remove_progress";
    private static final long[] VIBRATION_PATTERN = {0, 100, 50, 100};
    private static final int LIGHT_COLOR_ARGB = Color.GREEN;
    private static final int NOTIFICATION_ID_SIMPLE = 0;
    private static final int NOTIFICATION_ID_MESSAGE = 1;
    private static final int NOTIFICATION_ID_IMAGE = 2;
    private static final int NOTIFICATION_ID_ONGOING = 3;

    public int mMessageCount;
    private EditText mMessageEdit;
    private NotificationManager mManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mMessageEdit = findViewById(R.id.edit_message);

        mManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        initChannels();

        findViewById(R.id.button_simple).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSimpleNotification();
            }
        });

        findViewById(R.id.button_clear).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearAll();
            }
        });

        findViewById(R.id.button_image).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showBigImageNotification();
            }
        });

        findViewById(R.id.button_send).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showMessageNotification();
            }
        });

        findViewById(R.id.button_ongoing).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showOngoingNotification();
            }
        });

        String action = getIntent().getAction();
        if (!TextUtils.isEmpty(action))
            mManager.cancel(NOTIFICATION_ID_ONGOING);

        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if (!task.isSuccessful()) {
                            Log.d(MessagingService.TAG,  "Fetching FCM registration token failed", task.getException());
                            return;
                        }

                        String newToken = task.getResult();
                        Log.d(MessagingService.TAG, "FCM Token: " + newToken);
                    }
                });
    }

    public void showSimpleNotification() {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_LIKES);

        builder.setSmallIcon(R.drawable.ic_like)
                .setContentTitle(getString(R.string.user_name))
                .setContentText(getString(R.string.new_like))
                .setPriority(NotificationCompat.PRIORITY_LOW)
                .setAutoCancel(true);

        addDefaultIntent(builder);

        mManager.notify(NOTIFICATION_ID_SIMPLE, builder.build());
    }

    public void clearAll() {
        mManager.cancelAll();
    }

    public void showMessageNotification() {
        mMessageCount++;

        String messageToShow = mMessageEdit.getText().toString();

        Bitmap largeIcon = BitmapFactory.decodeResource(getResources(), R.drawable.example_large_icon);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_MESSAGES)
                .setLargeIcon(largeIcon)
                .setSmallIcon(R.drawable.ic_message_black)
                .setContentTitle(getString(R.string.message_name))
                .setContentText(messageToShow)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setVibrate(VIBRATION_PATTERN)
                .setLights(LIGHT_COLOR_ARGB, 1000, 1000)
                .setColor(getResources().getColor(R.color.colorAccent))
                .setAutoCancel(true);


        NotificationCompat.BigTextStyle style = new NotificationCompat.BigTextStyle();
        style.bigText(messageToShow);
        style.setSummaryText(getString(R.string.message_summary, mMessageCount));
        builder.setStyle(style);

        addDefaultIntent(builder);
        addMessageIntent(builder, messageToShow);

        mManager.notify(NOTIFICATION_ID_MESSAGE, builder.build());
    }

    public void showBigImageNotification() {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_DEFAULT);

        builder.setSmallIcon(R.drawable.ic_announcement)
                .setContentTitle(getString(R.string.big_image))
                .setContentText(getString(R.string.simple_description))
                .setColor(getResources().getColor(R.color.colorAccent))
                .setAutoCancel(true);

        Bitmap largeIcon = BitmapFactory.decodeResource(getResources(), R.drawable.example_large_icon);
        Bitmap image = BitmapFactory.decodeResource(getResources(), R.drawable.bg_5);
        NotificationCompat.BigPictureStyle style =
                new NotificationCompat.BigPictureStyle();
        style.bigPicture(image);
        style.bigLargeIcon(largeIcon);

        builder.setStyle(style);

        addDefaultIntent(builder);

        mManager.notify(NOTIFICATION_ID_IMAGE, builder.build());

    }

    public void showOngoingNotification() {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_DEFAULT);

        builder.setSmallIcon(R.drawable.ic_announcement)
                .setContentTitle(getString(R.string.ongoing))
                .setContentText(getString(R.string.infinite_progress))
                .setColor(getResources().getColor(android.R.color.darker_gray))
                .setOngoing(true)
                .setProgress(100, 54, true);

        addDefaultIntent(builder);

        addRemoveProgressIntent(builder);

        mManager.notify(NOTIFICATION_ID_ONGOING, builder.build());
    }

    private void addMessageIntent(NotificationCompat.Builder builder, String message) {

        Intent contentIntent = new Intent(this, MessageActivity.class);
        contentIntent.putExtra(MessageActivity.EXTRA_TEXT, message);

        int flags = PendingIntent.FLAG_CANCEL_CURRENT;
        PendingIntent pendingIntent = PendingIntent.getActivity(
                this,
                1,
                contentIntent,
                flags);

        builder.addAction(new NotificationCompat.Action(0, getString(R.string.show), pendingIntent));
    }

    private void addRemoveProgressIntent(NotificationCompat.Builder builder) {

        Intent contentIntent = new Intent(this, MainActivity.class);
        contentIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        contentIntent.setAction(ACTION_REMOVE_PROGRESS);

        int flags = PendingIntent.FLAG_CANCEL_CURRENT;
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 2, contentIntent, flags);

        builder.addAction(new NotificationCompat.Action(0, getString(R.string.remove), pendingIntent));
    }

    private void addDefaultIntent(NotificationCompat.Builder builder) {

        Intent contentIntent = new Intent(this, MainActivity.class);
        contentIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        contentIntent.putExtra("TIME", System.currentTimeMillis());

        int flags = PendingIntent.FLAG_UPDATE_CURRENT; // отменить старый и создать новый
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, contentIntent, flags);

        builder.setContentIntent(pendingIntent);
    }

    public void initChannels() {
        if (Build.VERSION.SDK_INT < 26)
            return;

        NotificationChannel defaultChannel = new NotificationChannel(
                CHANNEL_DEFAULT,
                getString(R.string.channel_default_name), NotificationManager.IMPORTANCE_DEFAULT);
        mManager.createNotificationChannel(defaultChannel);

        NotificationChannel likesChannel = new NotificationChannel(CHANNEL_LIKES,
                getString(R.string.channel_likes_name), NotificationManager.IMPORTANCE_LOW);
        likesChannel.setDescription(getString(R.string.channel_likes_description));
        mManager.createNotificationChannel(likesChannel);

        NotificationChannel messagesChannel = new NotificationChannel(CHANNEL_MESSAGES,
                getString(R.string.channel_messages_name), NotificationManager.IMPORTANCE_HIGH);
        messagesChannel.setDescription(getString(R.string.channel_messages_description));
        messagesChannel.setVibrationPattern(VIBRATION_PATTERN);
        messagesChannel.enableLights(true);
        messagesChannel.enableVibration(true);
        messagesChannel.setLightColor(LIGHT_COLOR_ARGB);
        mManager.createNotificationChannel(messagesChannel);
    }
}
