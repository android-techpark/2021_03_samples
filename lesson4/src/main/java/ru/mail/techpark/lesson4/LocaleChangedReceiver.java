package ru.mail.techpark.lesson4;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.util.Locale;

public class LocaleChangedReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (action != null) {
            if (Intent.ACTION_LOCALE_CHANGED.equals(action)) {
                Log.d(
                        "LocaleChangedReceiver",
                        "Current locale changed to -> " + getCurrentLocaleName(context)
                );
            }
        }
    }

    private String getCurrentLocaleName(Context context) {
        Locale locale = context.getResources().getConfiguration().locale;
        return locale.getDisplayName();
    }
}
