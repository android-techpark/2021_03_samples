package superb.techpark.ru.lesson6.mvp;

interface LoginContract {
    interface Presenter {
        void onLoginClicked(String login, String password);

        void onBackClicked();

        void onRetry();
    }

    interface View  {
        void showLessonsScreen();
        void loginError();
        void notifyErrorMsg();
    }

}
