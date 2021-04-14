package ru.mail.techpark.lesson7_2;

import android.os.Bundle;
import android.os.Environment;
import android.widget.TextView;

import java.io.File;

import androidx.appcompat.app.AppCompatActivity;
import ru.mail.techpark.lesson7_2.R;

/**
 * Activity, которая просто отображает пути до различных системных директорий.
 */
public class DirectoriesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_directories);

        String path;

        path = getFilesDir().getAbsolutePath();
        ((TextView) findViewById(R.id.files_dir)).setText(path);

        path = getDir("Internal", MODE_PRIVATE).getAbsolutePath();
        ((TextView) findViewById(R.id.get_dir)).setText(path);

        path = getCacheDir().getAbsolutePath();
        ((TextView) findViewById(R.id.cache_dir)).setText(path);

        File externalDir = getExternalFilesDir(null);
        path = externalDir != null ? externalDir.getAbsolutePath() : "null";
        ((TextView) findViewById(R.id.external_files_dir)).setText(path);

        File externalCacheDir = getExternalCacheDir();
        path = externalCacheDir != null ? externalCacheDir.getAbsolutePath() : "null";
        ((TextView) findViewById(R.id.external_cache_dir)).setText(path);

        path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath();
        ((TextView) findViewById(R.id.downloads_dir)).setText(path);

        path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).getAbsolutePath();
        ((TextView) findViewById(R.id.pictures_dir)).setText(path);
    }
}
