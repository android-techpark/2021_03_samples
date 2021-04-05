package superb.techpark.ru.lesson6.mvvm;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import superb.techpark.ru.lesson6.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends Fragment {

    private LoginViewModel mLoginViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.login_fragment, container, false);
    }

    @SuppressWarnings("ConstantConditions")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mLoginViewModel = new ViewModelProvider(getActivity()).get(LoginViewModel.class);

        final Button loginBtn = view.findViewById(R.id.login_btn);

        mLoginViewModel.getProgress()
                .observe(getViewLifecycleOwner(), new MyMyObserver(loginBtn));

        final EditText login = view.findViewById(R.id.login);
        final EditText password = view.findViewById(R.id.password);
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mLoginViewModel.login(login.getText().toString(), password.getText().toString());
            }
        });
    }

    private class MyMyObserver implements Observer<LoginViewModel.LoginState> {
        private final Button loginBtn;

        public MyMyObserver(Button loginBtn) {
            this.loginBtn = loginBtn;
        }

        @Override
        public void onChanged(LoginViewModel.LoginState loginState) {
            if (loginState == LoginViewModel.LoginState.FAILED) {
                loginBtn.setEnabled(true);
                loginBtn.setBackgroundColor(getResources().getColor(android.R.color.holo_red_dark));
            } else if (loginState == LoginViewModel.LoginState.ERROR) {
                loginBtn.setBackgroundColor(getResources().getColor(android.R.color.holo_orange_light));
                loginBtn.setEnabled(true);
            } else if (loginState == LoginViewModel.LoginState.IN_PROGRESS) {
                loginBtn.setBackgroundColor(getResources().getColor(android.R.color.holo_blue_light));
                loginBtn.setEnabled(false);
            } else if (loginState == LoginViewModel.LoginState.SUCCESS) {
                Toast.makeText(getContext(), "Success login", Toast.LENGTH_LONG).show();
                Router router = (Router) getActivity();
                if (router != null) {
                    router.openLessons();
                }
            } else {
                loginBtn.setBackground(getContext().getDrawable(android.R.drawable.btn_default));
                loginBtn.setEnabled(true);
            }
        }
    }
}
