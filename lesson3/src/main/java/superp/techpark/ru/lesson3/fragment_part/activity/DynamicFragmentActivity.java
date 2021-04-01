package superp.techpark.ru.lesson3.fragment_part.activity;

import android.os.Bundle;

import superp.techpark.ru.lesson3.BaseActivity;
import superp.techpark.ru.lesson3.R;
import superp.techpark.ru.lesson3.fragment_part.fragment.BlueFragment;
import superp.techpark.ru.lesson3.fragment_part.fragment.RedFragment;


/**
 * Activity для демонстрации динамического создания активити
 * операций с фрагментами.
 */
public class DynamicFragmentActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dynamic_fragment);
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.fragment_container, new BlueFragment())
                .commit();
    }
}
