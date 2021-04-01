package superp.techpark.ru.lesson3.fragment_part.activity;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import superp.techpark.ru.lesson3.R;
import superp.techpark.ru.lesson3.fragment_part.fragment.RedFragment;


/**
 * Activity для демонстрации ситуации потери состояния. Для работы примера нужно:
 * <ol>
 *     <li>Нажать кнопку "DO IT!";</li>
 *     <li>Уйти с Activity быстрее, чем за 3 секунды.</li>
 * </ol>
 * Потом повторить указанные действия, но с кнопкой "DO IT AND LOSS".
 */
public class StateLossActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_state_loss);

        findViewById(R.id.btn_do_it).setOnClickListener(view -> doIt(false));

        findViewById(R.id.btn_do_it_allowing_loss).setOnClickListener(view -> doIt(true));
    }

    private void doIt(final boolean allowLoss) {
        new Handler().postDelayed(() -> {
            Log.d("STATE_LOSS", "Performing fragment transaction");
            boolean stateSaved = getSupportFragmentManager().isStateSaved();
            Log.d("STATE_LOSS", "Performing fragment transaction state saved = " + stateSaved);
            FragmentTransaction transaction = getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.container, new RedFragment());
            if (allowLoss) {
                transaction.commitAllowingStateLoss();
            } else {
                transaction.commit();
            }
        }, 3000);
    }
}
