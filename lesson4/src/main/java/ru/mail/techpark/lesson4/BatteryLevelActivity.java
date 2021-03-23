package ru.mail.techpark.lesson4;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.annotation.Nullable;

public class BatteryLevelActivity extends Activity {
    private BroadcastReceiver mBatteryLevelReceiver;
    private TextView mBatteryLevelView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.battery_state_activity);
        mBatteryLevelView = findViewById(R.id.battery_level);
        mBatteryLevelReceiver = new BatteryLevelReceiver();
        IntentFilter batteryIntentFilter = new IntentFilter();
        batteryIntentFilter.addAction(Intent.ACTION_BATTERY_CHANGED);
        registerReceiver(mBatteryLevelReceiver, batteryIntentFilter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("BatteryLevelActivity", "onDestroy()");
        unregisterReceiver(mBatteryLevelReceiver);
    }

    private class BatteryLevelReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            int batteryLevel = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
            if (batteryLevel != -1) {
                mBatteryLevelView.setText("Battery level = " + batteryLevel);
            }
        }
    }
}
