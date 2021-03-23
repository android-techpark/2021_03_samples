package ru.mail.techpark.lesson4;

import android.app.Service;
import android.content.Intent;
import android.os.CountDownTimer;
import android.os.IBinder;
import android.util.Log;

import java.util.concurrent.TimeUnit;

import androidx.annotation.Nullable;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

public class TimerService extends Service {
    public static final String ACTION_START_TIMER = BuildConfig.APPLICATION_ID + ".action_start_timer";
    public static final String ACTION_STOP_TIMER = BuildConfig.APPLICATION_ID + ".action_stop_timer";
    public static final String EXTRA_TIMER_VALUE_SECONDS = "extra_timer_value_seconds";
    public static final String EXTRA_CURRENT_TIMER_VALUE = "extra_timer_value";

    private CountDownTimer mCountDownTimer;

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("TimerService", "onCreate()");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("TimerService", "onStartCommand() intent = " + intent);
        String action = intent.getAction();
        if (action != null) {
            switch (action) {
                case ACTION_START_TIMER: {
                    long timerValue = intent.getLongExtra(EXTRA_TIMER_VALUE_SECONDS, 0);
                    if (timerValue > 0) {
                        startTimer(timerValue);
                    }
                    break;
                }
                case ACTION_STOP_TIMER: {
                    stopTimer();
                    getLocalBroadcastManager().sendBroadcast(new Intent(TimerActivity.ACTION_TIMER_STOPPED));
                    stopSelf();
                    break;
                }
            }
        }
        return START_REDELIVER_INTENT;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("TimerService", "onDestroy()");
    }

    @Override
    public void onTaskRemoved(Intent rootIntent) {
        super.onTaskRemoved(rootIntent);
        Log.d("TimerService", "onTaskRemoved()");
    }

    private void startTimer(long timerSeconds) {
        stopTimer();
        mCountDownTimer = new CountDownTimer(
                TimeUnit.SECONDS.toMillis(timerSeconds),
                500
        ) {
            @Override
            public void onTick(final long millisUntilFinished) {
                final Intent intent = new Intent(TimerActivity.ACTION_UPDATE_TIMER);
                intent.putExtra(EXTRA_CURRENT_TIMER_VALUE, millisUntilFinished / 1000);
                getLocalBroadcastManager().sendBroadcast(intent);
            }

            @Override
            public void onFinish() {
                final Intent intent = new Intent(TimerActivity.ACTION_TIMER_FINISHED);
                getLocalBroadcastManager().sendBroadcast(intent);
            }
        };
        mCountDownTimer.start();
    }

    private LocalBroadcastManager getLocalBroadcastManager() {
        return LocalBroadcastManager
                .getInstance(getApplicationContext());
    }

    private void stopTimer() {
        if (mCountDownTimer != null) {
            mCountDownTimer.cancel();
            mCountDownTimer = null;
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
