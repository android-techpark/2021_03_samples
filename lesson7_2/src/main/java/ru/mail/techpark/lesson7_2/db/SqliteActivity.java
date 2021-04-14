package ru.mail.techpark.lesson7_2.db;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import java.util.Collection;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import ru.mail.techpark.lesson7_2.R;


/**
 * Activity для демонстрации примера работы с SQLite при помощи выделенного
 * менеджера (см. {@link DbManager})
 */
public class SqliteActivity extends AppCompatActivity {

    private final DbManager.ReadAllListener<String> readListener = new DbManager.ReadAllListener<String>() {
        @Override
        public void onReadAll(final Collection<String> allItems) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    showStringList(allItems);
                }
            });
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final DbManager manager = DbManager.getInstance(this);
        setContentView(R.layout.activity_sqlite);
        final EditText editText = findViewById(R.id.edit_text);
        findViewById(R.id.add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                manager.insert(editText.getText().toString());
                editText.setText("");
            }
        });
        findViewById(R.id.enumerate).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                manager.readAll(readListener);
            }
        });
        findViewById(R.id.clean).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                manager.clean();
            }
        });
    }

    private void showStringList(final Collection<String> list) {
        new AlertDialog.Builder(this)
                .setItems(list.toArray(new String[0]), null)
                .show();
    }
}
