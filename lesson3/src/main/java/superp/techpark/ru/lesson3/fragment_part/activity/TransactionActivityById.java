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
 */
public class TransactionActivityById extends AppCompatActivity {

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
        getSupportFragmentManager().popBackStack(TRANSACTION_RED, 0);
    }

    private void addFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();

        RedFragment red = (RedFragment) fragmentManager.findFragmentById(R.id.red_container);
        GreenFragment green = (GreenFragment) fragmentManager.findFragmentById(R.id.green_container);
        BlueFragment blue = (BlueFragment) fragmentManager.findFragmentById(R.id.blue_container);

        String name = null;
        if (red == null) {
            transaction.replace(R.id.red_container, new RedFragment());
            name = TRANSACTION_RED;
        } else if (green == null) {
            transaction.replace(R.id.green_container, GreenFragment.newInstance(123));
        } else if (blue == null) {
            transaction.replace(R.id.blue_container, new BlueFragment());
        }

        if (backStackCheckbox.isChecked()) {
            transaction.addToBackStack(name);
        }
        transaction.commit();
    }

    private void removeFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();

        RedFragment red = (RedFragment) fragmentManager.findFragmentById(R.id.red_container);
        GreenFragment green = (GreenFragment) fragmentManager.findFragmentById(R.id.green_container);
        BlueFragment blue = (BlueFragment) fragmentManager.findFragmentById(R.id.blue_container);

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
