package superb.techpark.ru.lesson6.mvvm;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import superb.techpark.ru.lesson6.R;

public class MainActivity extends AppCompatActivity implements Router {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new LoginFragment(), "Login")
                    .commit();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        ///
    }

    @Override
    public void openLessons() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, new LessonsFragment(), "Lessons")
                .commit();
    }
}
