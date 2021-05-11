package superp.techpark.ru.lesson10;

import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class MessageActivity extends AppCompatActivity {
    public static final String EXTRA_TEXT = "extra_text";

    private TextView mMessageText;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);

        mMessageText = findViewById(R.id.text_message);

        String message = getIntent().getStringExtra(EXTRA_TEXT);
        mMessageText.setText(message);
    }
}
