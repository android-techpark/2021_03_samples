package superp.techpark.ru.lesson3;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.util.Random;

import ru.techpark.lesson3.lib.ModuleFragment;
import superp.techpark.ru.lesson3.fragment_part.activity.CooperationActivity;
import superp.techpark.ru.lesson3.fragment_part.activity.DynamicFragmentActivity;
import superp.techpark.ru.lesson3.fragment_part.activity.DynamicFragmentFixedActivity;
import superp.techpark.ru.lesson3.fragment_part.activity.LayoutFragmentActivity;
import superp.techpark.ru.lesson3.fragment_part.activity.MultiActionActivity;
import superp.techpark.ru.lesson3.fragment_part.activity.StateLossActivity;
import superp.techpark.ru.lesson3.fragment_part.activity.TransactionActivityById;
import superp.techpark.ru.lesson3.fragment_part.activity.TransactionActivityByTag;

public class FirstActivity extends BaseActivity {

    public static final String STATE = "state";
    private TextView mText;

    private static String sString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d("Lifecycle", "myString " + sString);
        mText = findViewById(R.id.dynamic_text);
//        restoreState(savedInstanceState);
        findViewById(R.id.second).setOnClickListener(v ->
                startActivity(new Intent(FirstActivity.this, SecondActivity.class))
        );

        findViewById(R.id.set_text).setOnClickListener(v -> {
            String text = String.valueOf(new Random().nextInt());
            mText.setText(text);
            sString = text;
        });

        initFragmentRelated();
    }

    private void restoreState(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            String string = savedInstanceState.getString(STATE);
            mText.setText(string);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
//        outState.putString(STATE, mText.getText().toString());
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }

    private void initFragmentRelated() {
        findViewById(R.id.btn_layout_fragment).setOnClickListener(view ->
                startActivity(new Intent(FirstActivity.this, LayoutFragmentActivity.class))
        );

        findViewById(R.id.btn_dynamic_fragment).setOnClickListener(view ->
                startActivity(new Intent(FirstActivity.this, DynamicFragmentActivity.class))
        );
        findViewById(R.id.btn_dynamic_fragment_fixed).setOnClickListener(view ->
                startActivity(new Intent(FirstActivity.this, DynamicFragmentFixedActivity.class))
        );

        findViewById(R.id.btn_multi_action).setOnClickListener(view ->
                startActivity(new Intent(FirstActivity.this, MultiActionActivity.class))
        );

        findViewById(R.id.btn_transactions_by_id).setOnClickListener(view ->
                startActivity(new Intent(FirstActivity.this, TransactionActivityById.class))
        );
        findViewById(R.id.btn_transactions_by_tag).setOnClickListener(view ->
                startActivity(new Intent(FirstActivity.this, TransactionActivityByTag.class))
        );

        findViewById(R.id.btn_state_loss).setOnClickListener(view ->
                startActivity(new Intent(FirstActivity.this, StateLossActivity.class))
        );

        findViewById(R.id.hello_module).setOnClickListener(view ->
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.container, new ModuleFragment(), "tag")
                    .commitAllowingStateLoss()
        );

        findViewById(R.id.btn_coop).setOnClickListener(view ->
                startActivity(new Intent(FirstActivity.this, CooperationActivity.class))
        );
    }
}
