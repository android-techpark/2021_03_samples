package superp.techpark.ru.lesson10;

import android.app.NotificationManager;
import android.content.Context;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import androidx.core.app.NotificationCompat;

public class MessagingService extends FirebaseMessagingService {

    public static final String TAG = "Firebase";
    private static final int NOTIFICATION_ID_SIMPLE = 4;

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        Log.d(TAG, "From: " + remoteMessage);

        if (remoteMessage.getData().size() > 0) {
            Log.d(TAG, "Message data payload: " + remoteMessage.getData());
            //
        }

        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {
            String title = remoteMessage.getNotification().getTitle();
            String body = remoteMessage.getNotification().getBody();
            Log.d(TAG, "Message Notification Body: " + body);

            showMessageNotification(title, body);
        }
    }

    @Override
    public void onNewToken(final String token) {
        super.onNewToken(token);
        Log.d(MessagingService.TAG, "Refreshed token: " + token);

        // отправляем токен на наш сервер
        sendRegistrationToServer(token);

    }


    private void sendRegistrationToServer(String refreshedToken) {
        Log.d(MessagingService.TAG, "Should send token to out server " + refreshedToken);
    }


    public void showMessageNotification(String title, String message) {
        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        if (manager == null)
            return;

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, MainActivity.CHANNEL_DEFAULT);

        builder.setSmallIcon(R.drawable.ic_announcement)
                .setContentTitle(title)
                .setContentText(message)
                .setAutoCancel(true);

        manager.notify(NOTIFICATION_ID_SIMPLE, builder.build());
    }
}
