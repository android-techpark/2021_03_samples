package ru.mail.techpark.lesson7_1;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;
import ru.mail.techpark.lesson7_1.presentation.LoginPresenterImpl;
import ru.mail.techpark.lesson7_1.repository.CompositeRepository;
import ru.mail.techpark.lesson7_1.repository.LocalCredentialsRepository;
import ru.mail.techpark.lesson7_1.repository.NetworkCredentialsRepository;

public class MainActivity extends AppCompatActivity implements LoginPresenter.View {

    private LoginPresenter mLoginPresenter;
    private EditText mLoginFiled;
    private EditText mPassField;
    private ProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        View singIn = findViewById(R.id.signin);

        mLoginPresenter = new LoginPresenterImpl(this,
                new CompositeRepository(
                        new NetworkCredentialsRepository(),
                        new LocalCredentialsRepository(getApplicationContext())
                )
        );

        mLoginFiled = findViewById(R.id.login);
        mPassField = findViewById(R.id.pass);
        mProgressBar = findViewById(R.id.progress);
        setColor(Color.GREEN);
        singIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setColor(Color.GRAY);
                mLoginPresenter.onSingInClicked(
                        mLoginFiled.getText().toString(),
                        mPassField.getText().toString()
                );
            }
        });
    }

    private void setColor(int color) {
        mLoginFiled.setBackgroundColor(color);
        mPassField.setBackgroundColor(color);
    }

    @Override
    protected void onDestroy() {
        mLoginPresenter.onViewDestroyed();
        super.onDestroy();
    }

    @Override
    public void showProgress() {
        mProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        mProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void showError(String login) {
        setColor(Color.RED);
    }

    @Override
    public void showNextScreen() {
        Intent intent = new Intent(this, SecondActivity.class);
        startActivity(intent);
    }
}
