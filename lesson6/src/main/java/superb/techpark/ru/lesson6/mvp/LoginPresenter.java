package superb.techpark.ru.lesson6.mvp;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;

import superb.techpark.ru.lesson6.AuthRepo;


public class LoginPresenter implements LoginContract.Presenter {

    private LoginContract.View mView;
    private final AuthRepo authRepo;
    Handler handler = new Handler(Looper.getMainLooper());

    public LoginPresenter(LoginContract.View view) {
        this.mView = view;
        authRepo = AuthRepo.getInstance((Context) view);
    }


    // or login in MVVM !
    @Override
    public void onLoginClicked(String login, String password) {
        authRepo.login(login, password, new AuthRepo.LoginCallback() {
            @Override
            public void onSuccess() {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        mView.showLessonsScreen();
                    }
                });
            }

            @Override
            public void onError() {
                mView.loginError();
            }
        });
    }

    @Override
    public void onBackClicked() {
    }

    @Override
    public void onRetry() {

    }
}
