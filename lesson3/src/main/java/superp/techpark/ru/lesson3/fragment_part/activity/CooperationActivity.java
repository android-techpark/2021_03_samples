package superp.techpark.ru.lesson3.fragment_part.activity;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import superp.techpark.ru.lesson3.R;
import superp.techpark.ru.lesson3.fragment_part.fragment.CooperationFragment;


/**
 * Activity, показывающая механизм взаимодействия Activity и Fragment'а.
 */
public class CooperationActivity extends AppCompatActivity implements CooperationFragment.ReportListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cooperation);

        CooperationFragment fragment = (CooperationFragment) getSupportFragmentManager()
                .findFragmentById(R.id.cooperation);
        if (fragment != null) {
            fragment.setCooperationText("Hello, cooperation!");
        }
    }

    @Override
    public void reportMessage(String cooperationText) {
        Toast.makeText(CooperationActivity.this,
                "Activity listening. Fragment send: " + cooperationText,
                Toast.LENGTH_SHORT).show();
    }
}
