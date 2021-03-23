package ru.mail.techpark.lesson4;

import android.app.Application;

public class MyApplication extends Application {
    private boolean mFlag = true;

    @Override
    public void onCreate() {
        super.onCreate();
    }

    public boolean getFlag() {
        return mFlag;
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }
}
