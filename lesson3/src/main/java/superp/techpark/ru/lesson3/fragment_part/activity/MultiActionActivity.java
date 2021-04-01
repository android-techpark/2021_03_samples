package superp.techpark.ru.lesson3.fragment_part.activity;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import superp.techpark.ru.lesson3.BaseActivity;
import superp.techpark.ru.lesson3.R;
import superp.techpark.ru.lesson3.fragment_part.fragment.GreenFragment;
import superp.techpark.ru.lesson3.fragment_part.fragment.RedFragment;


/**
 * Activity для демонстрации того, что в рамках транзакции может выполняться любое количество
 * операций с фрагментами.
 */
public class MultiActionActivity extends BaseActivity {

    private static int sNextNum = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multi_action);

        if (savedInstanceState == null) {
            toggleState();
        }

        findViewById(R.id.btn_switch).setOnClickListener(view -> toggleState());
    }

    private void toggleState() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        Fragment top = getSupportFragmentManager().findFragmentById(R.id.top_container);
        Fragment bottom = getSupportFragmentManager().findFragmentById(R.id.bottom_container);

        if (top != null && top.isAdded()) {
            transaction.remove(top);
            transaction.add(R.id.bottom_container, GreenFragment.newInstance(sNextNum++));
        } else if (bottom != null && bottom.isAdded()) {
            transaction.remove(bottom);
            transaction.add(R.id.top_container, new RedFragment());
        } else {
            transaction.add(R.id.top_container, new RedFragment());
        }

        transaction.addToBackStack("sdsds");
        transaction.commit();
    }
}
