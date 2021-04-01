package superp.techpark.ru.lesson3;

import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class BaseActivity extends AppCompatActivity {


    @Override
    protected void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(getLogTag(), "onCreate");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(getLogTag(), "onStart");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(getLogTag(), "onRestart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(getLogTag(), "onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(getLogTag(), "onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(getLogTag(), "onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(getLogTag(), "onDestroy " + isChangingConfigurations());
    }

    @Override
    protected void onSaveInstanceState(final Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.d(getLogTag(), "onSaveInstanceState");
    }

    @Override
    protected void onRestoreInstanceState(final Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Log.d(getLogTag(), "onRestoreInstanceState");
    }

    private String getLogTag() {
        return getClass().getSimpleName() + " Lifecycle";
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

//         Checks the orientation of the screen
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            Toast.makeText(this, "landscape", Toast.LENGTH_SHORT).show();
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT){
            Toast.makeText(this, "portrait", Toast.LENGTH_SHORT).show();
        }
    }
}
