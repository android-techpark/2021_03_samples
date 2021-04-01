package superp.techpark.ru.lesson5;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;
import android.text.TextUtils;
import android.util.Log;

/**
 * IntentService, который с задержкой в 5 секунд выводит в Logcat переданную строку в верхнем или
 * нижнем регистре. Также сервис поддерживает работу в foreground режиме с отображением
 * уведомления в статус-баре.
 */
public class DemoIntentService extends IntentService {

    private static final String TAG = "DemoIntentService";

    public static final String ACTION_TO_UPPER = "TO_UPPER";
    public static final String ACTION_TO_LOWER = "TO_LOWER";

    public static final String EXTRA_STRING = "STRING";
    public static final String EXTRA_FOREGROUND = "FOREGROUND";

    public DemoIntentService() {
        super(TAG);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy");
    }

    @Override
    protected void onHandleIntent(@Nullable final Intent intent) {
        if (intent == null || TextUtils.isEmpty(intent.getAction())) {
            return;
        }

        final String data = intent.getExtras().getString(EXTRA_STRING);
        final boolean isForeground = intent.getBooleanExtra(EXTRA_FOREGROUND, false);

        if (isForeground) {
            startForeground(1, getNotification());
        }

        Log.d(TAG, String.format("Start action '%s'", intent.getAction()));

        switch (intent.getAction()) {
            case ACTION_TO_UPPER:
                handleToUpper(data);
                break;
            case ACTION_TO_LOWER:
                handleToLower(data);
                break;
        }

        if (isForeground) {
            stopForeground(true);
        }
    }

    private void handleToUpper(final String data) {
        try {
            Thread.sleep(5000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Log.d(TAG, "Result: " + data.toUpperCase());
    }

    private void handleToLower(final String data) {
        try {
            Thread.sleep(5000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Log.d(TAG, "Result: " + data.toLowerCase());
    }

    private Notification getNotification() {
        return new NotificationCompat.Builder(this, createNotificationChannel("01", "def"))
                .setSmallIcon(R.drawable.ic_foreground)
                .setContentTitle(getString(R.string.working))
                .build();
    }


    private String createNotificationChannel(String channelId, String name) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                NotificationChannel chan = new NotificationChannel(channelId,
                        name, NotificationManager.IMPORTANCE_NONE);
                chan.setLightColor(Color.BLUE);
                chan.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);
                NotificationManager service = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                service.createNotificationChannel(chan);
                return channelId;
            } else {
                return "";
        }
    }
}
