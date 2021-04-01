package superp.techpark.ru.lesson3.fragment_part.activity;

import android.os.Bundle;

import superp.techpark.ru.lesson3.BaseActivity;
import superp.techpark.ru.lesson3.R;
import superp.techpark.ru.lesson3.fragment_part.fragment.BlueFragment;


/**
 * Activity для демонстрации динамического создания активити
 * операций с фрагментами с учетом проблемы многократного создания.
 */
public class DynamicFragmentFixedActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dynamic_fragment);
        if (getSupportFragmentManager().findFragmentById(R.id.fragment_container) == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.fragment_container, new BlueFragment())
                    .commit();
        }
    }
}
