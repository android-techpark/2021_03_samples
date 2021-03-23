package ru.mail.techpark.lesson4;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

public class TimerActivity extends Activity {
    public static final String ACTION_UPDATE_TIMER = "action_update_time";
    public static final String ACTION_TIMER_FINISHED = "action_timer_finished";
    public static final String ACTION_TIMER_STOPPED = "action_timer_stopped";

    private TimerStateReceiver mTimerStateReceiver;
    private TextView mTimerView;

    private final View.OnClickListener mExplicitStartTimerClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(TimerActivity.this, TimerService.class);
            intent.setAction(TimerService.ACTION_START_TIMER);
            intent.putExtra(TimerService.EXTRA_TIMER_VALUE_SECONDS, 30L);
            startService(intent);
        }
    };
    private final View.OnClickListener mStopTimerClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(TimerActivity.this, TimerService.class);
            intent.setAction(TimerService.ACTION_STOP_TIMER);
            startService(intent);
        }
    };

    private final View.OnClickListener mImplicitStartTimerClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(TimerService.ACTION_START_TIMER);
            intent.putExtra(TimerService.EXTRA_TIMER_VALUE_SECONDS, 30L);
            intent.setPackage(getPackageName());
            startService(intent);
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.timer_activity);
        mTimerView = findViewById(R.id.time);
        mTimerStateReceiver = new TimerStateReceiver();
        registerLocalTimerStateReceiver();
        findViewById(R.id.explicit_start).setOnClickListener(mExplicitStartTimerClickListener);
        findViewById(R.id.implicit_start).setOnClickListener(mImplicitStartTimerClickListener);
        findViewById(R.id.stop).setOnClickListener(mStopTimerClickListener);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterLocalTimerStateReceiver();
    }

    private void registerStandardTimerStateReceiver() {
        registerReceiver(mTimerStateReceiver, createTimerStateIntentFilters());
    }

    private void unregisterStandardTimerStateReceiver() {
        unregisterReceiver(mTimerStateReceiver);
    }

    private void registerLocalTimerStateReceiver() {
        getLocalBroadcastManager().registerReceiver(
                mTimerStateReceiver,
                createTimerStateIntentFilters()
        );
    }

    private void unregisterLocalTimerStateReceiver() {
        getLocalBroadcastManager().unregisterReceiver(mTimerStateReceiver);
    }

    private LocalBroadcastManager getLocalBroadcastManager() {
        return LocalBroadcastManager.getInstance(this);
    }

    private IntentFilter createTimerStateIntentFilters() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ACTION_UPDATE_TIMER);
        intentFilter.addAction(ACTION_TIMER_FINISHED);
        intentFilter.addAction(ACTION_TIMER_STOPPED);
        return intentFilter;
    }

    private class TimerStateReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action != null) {
                switch (action) {
                    case ACTION_UPDATE_TIMER: {
                        long timerValue = intent.getLongExtra(TimerService.EXTRA_CURRENT_TIMER_VALUE, -1);
                        if (timerValue >= 0) {
                            mTimerView.setText("Timer: " + timerValue);
                        }
                        break;
                    }
                    case ACTION_TIMER_FINISHED: {
                        mTimerView.setText("Timer finished!");
                        break;
                    }
                    case ACTION_TIMER_STOPPED: {
                        mTimerView.setText("Timer stopped by user!");
                        break;
                    }
                }
            }
        }
    }
}
