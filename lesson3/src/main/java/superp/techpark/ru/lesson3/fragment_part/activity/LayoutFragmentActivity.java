package superp.techpark.ru.lesson3.fragment_part.activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import superp.techpark.ru.lesson3.R;


/**
 * Activity, которая демонстрирует как Fragment'ы могут быть добавлены через layout.
 */
public class LayoutFragmentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_layout_fragment);
    }
}
