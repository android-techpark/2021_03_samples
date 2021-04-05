package superb.techpark.ru.lesson6.mvp;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import superb.techpark.ru.lesson6.R;

public class MVPActivity extends Activity implements LoginContract.View {

    private LoginPresenter loginPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loginPresenter = new LoginPresenter(this);
        findViewById(R.id.login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginPresenter.onLoginClicked("some login", "some password");
            }
        });
    }


    @Override
    public void showLessonsScreen() {
        // show new fragment
    }

    @Override
    public void loginError() {
        findViewById(R.id.fragment_container).setVisibility(View.VISIBLE);

    }

    @Override
    public void notifyErrorMsg() {
        Toast.makeText(this, "Erro!", Toast.LENGTH_SHORT).show();
    }
}


