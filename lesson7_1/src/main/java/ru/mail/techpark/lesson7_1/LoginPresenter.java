package ru.mail.techpark.lesson7_1;

public interface LoginPresenter {

    void onSingInClicked(String login, String pass);

    void onViewDestroyed();

    interface View {

        void showProgress();

        void hideProgress();

        void showError(String login);

        void showNextScreen();
    }
}
