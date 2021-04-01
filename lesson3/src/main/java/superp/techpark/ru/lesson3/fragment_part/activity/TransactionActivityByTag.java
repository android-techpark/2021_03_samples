package superp.techpark.ru.lesson3.fragment_part.activity;

import android.os.Bundle;
import android.widget.CheckBox;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import superp.techpark.ru.lesson3.R;
import superp.techpark.ru.lesson3.fragment_part.fragment.BlueFragment;
import superp.techpark.ru.lesson3.fragment_part.fragment.GreenFragment;
import superp.techpark.ru.lesson3.fragment_part.fragment.RedFragment;


/**
 * Activity для демонстрации работы с транзакциями фрагментов и знакомством с понятием back stack.
 * В примере можно в произвольном порядке добавлять/удалять фрагменты и наблюдать за поведением
 * кнопки back на устройстве в зависимости от состояния флага "Add to back stack".
 *
 * В отличии от TransactionActivityById в данной активити поиск нужных фрагментов на экране
 * реализован с использованием механизма тегов.
 */
public class TransactionActivityByTag extends AppCompatActivity {

    private final static String BLUE = "BLUE";
    private final static String GREEN = "GREEN";
    private final static String RED = "RED";
    private CheckBox backStackCheckbox;

    public static final String TRANSACTION_RED = "TRANSACTION_RED";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction);

        backStackCheckbox = findViewById(R.id.checkbox);

        findViewById(R.id.btn_add_fragment).setOnClickListener(view -> addFragment());
        findViewById(R.id.btn_remove_fragment).setOnClickListener(view -> removeFragment());
        findViewById(R.id.rollback).setOnClickListener(v -> rollback());
    }

    private void rollback() {
        getSupportFragmentManager()
                .popBackStack(TRANSACTION_RED, 0);
    }

    private void addFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();

        RedFragment red = (RedFragment) fragmentManager.findFragmentByTag(RED);
        GreenFragment green = (GreenFragment) fragmentManager.findFragmentByTag(GREEN);
        BlueFragment blue = (BlueFragment) fragmentManager.findFragmentByTag(BLUE);

        String name = null;
        if (red == null) {
            transaction.replace(R.id.red_container, new RedFragment(), RED);
            name = TRANSACTION_RED;
        } else if (green == null) {
            transaction.replace(R.id.green_container, GreenFragment.newInstance(123), GREEN);
        } else if (blue == null) {
            transaction.replace(R.id.blue_container, new BlueFragment(), BLUE);
        }

        if (backStackCheckbox.isChecked()) {
            transaction.addToBackStack(name);
        }
        transaction.commit();
    }

    private void removeFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();

        RedFragment red = (RedFragment) fragmentManager.findFragmentByTag(RED);
        GreenFragment green = (GreenFragment) fragmentManager.findFragmentByTag(GREEN);
        BlueFragment blue = (BlueFragment) fragmentManager.findFragmentByTag(BLUE);

        if (blue != null && blue.isAdded()) {
            transaction.remove(blue);
        } else if (green != null && green.isAdded()) {
            transaction.remove(green);
        } else if (red != null && red.isAdded()) {
            transaction.remove(red);
        }

        if (backStackCheckbox.isChecked()) {
            transaction.addToBackStack(null);
        }
        transaction.commit();
    }
}
