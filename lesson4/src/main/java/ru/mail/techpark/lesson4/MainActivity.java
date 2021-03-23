package ru.mail.techpark.lesson4;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    private final View.OnClickListener mBatteryClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(MainActivity.this, BatteryLevelActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_DOCUMENT);
            startActivity(intent);
        }
    };

    private final View.OnClickListener mTimerClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(MainActivity.this, TimerActivity.class);
            startActivity(intent);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.battery_activity_button).setOnClickListener(mBatteryClickListener);
        findViewById(R.id.timer_activity_button).setOnClickListener(mTimerClickListener);
        findViewById(R.id.chooser_activity_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // fixed service chooser
                Intent intent = new Intent();
                intent.setAction("ru.mail.techpark.test_chooser");
                intent.setPackage(getPackageName());
                startActivity(intent);
            }
        });
    }
}
