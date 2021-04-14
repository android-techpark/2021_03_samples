package ru.mail.techpark.lesson7_2;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.SeekBar;

import androidx.appcompat.app.AppCompatActivity;

/**
 * Activity для демонстрации работы с {@link SharedPreferences}. Показывает чтение и запись
 * различных типов данных.
 */
public class SharedPreferencesActivity extends AppCompatActivity {

    private final static String KEY_CHECK = "check";
    private final static String KEY_TEXT = "text";
    private final static String KEY_SEEK_PROGRESS = "seek_progress";
    private final static String KEY_FLOAT = "float_value";

    private CheckBox mCheckBox;
    private EditText mEditString;
    private SeekBar mSeekBar;
    private EditText mEditFloat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shared_preferences);

        mCheckBox = findViewById(R.id.checkbox);
        mEditString = findViewById(R.id.edit_text);
        mSeekBar = findViewById(R.id.seek);
        mEditFloat = findViewById(R.id.edit_text_float);
    }

    @Override
    protected void onStart() {
        super.onStart();

        SharedPreferences prefs = getPreferences(MODE_PRIVATE);
        mCheckBox.setChecked(prefs.getBoolean(KEY_CHECK, false));
        mEditString.setText(prefs.getString(KEY_TEXT, ""));
        mSeekBar.setProgress(prefs.getInt(KEY_SEEK_PROGRESS, 50));

        float f = prefs.getFloat(KEY_FLOAT, -1f);
        mEditFloat.setText(String.valueOf(f));
    }

    @Override
    protected void onStop() {
        super.onStop();

        SharedPreferences.Editor editor = getPreferences(MODE_PRIVATE).edit();
        editor.putBoolean(KEY_CHECK, mCheckBox.isChecked());
        editor.putString(KEY_TEXT, mEditString.getText().toString());
        editor.putInt(KEY_SEEK_PROGRESS, mSeekBar.getProgress());
        editor.putFloat(KEY_FLOAT, Float.parseFloat(mEditFloat.getText().toString()));
        editor.apply();
    }
}
