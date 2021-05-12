package superp.techpark.ru.lesson10_testing;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import androidx.annotation.NonNull;

public class UserRepo implements SharedPreferences.OnSharedPreferenceChangeListener {

    private final Context mContext;
    private static final String USERNAME = "username";
    private UserNameListener mUserNameListener;
    private final NameValidator mNameValidator;

    public UserRepo(Context context,
                    NameValidator nameValidator) {
        mContext = context;
        mNameValidator = nameValidator;
    }

    public void updateName(@NonNull String newName) {
        if (mNameValidator.isValid(newName)) {
            PreferenceManager.getDefaultSharedPreferences(mContext)
                    .edit()
                    .putString(USERNAME, newName)
                    .apply();
        }
    }

    @NonNull
    public String getName() {
        String name = PreferenceManager.getDefaultSharedPreferences(mContext)
                .getString(USERNAME, "");
        return name == null ? "" : name;
    }

    public void setOnUserNameListener(UserNameListener listener) {
        if (mUserNameListener == null) {
            PreferenceManager.getDefaultSharedPreferences(mContext).registerOnSharedPreferenceChangeListener(this);
        }
        mUserNameListener = listener;
    }

    public void removeListeners() {
        mUserNameListener = null;
        PreferenceManager.getDefaultSharedPreferences(mContext).unregisterOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if (USERNAME.equals(key) && mUserNameListener != null) {
            mUserNameListener.onNameChanged();
        }
    }

    interface UserNameListener {
        void onNameChanged();
    }
}
