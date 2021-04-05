package superb.techpark.ru.lesson6;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import superb.techpark.ru.lesson6.network.ApiRepo;
import superb.techpark.ru.lesson6.network.UserApi;

@SuppressWarnings("WeakerAccess")
public class AuthRepo {

    private final ApiRepo mApiRepo;
    public AuthRepo(ApiRepo apiRepo) {
        mApiRepo = apiRepo;
    }

    @NonNull
    public static AuthRepo getInstance(Context context) {
        return ApplicationModified.from(context).getAuthRepo();
    }

    private String mCurrentUser;
    private MutableLiveData<AuthProgress> mAuthProgress;

    public LiveData<AuthProgress> login(@NonNull String login, @NonNull String password) {
        if (TextUtils.equals(login, mCurrentUser) && mAuthProgress.getValue() == AuthProgress.IN_PROGRESS) {
            return mAuthProgress;
        } else if (!TextUtils.equals(login, mCurrentUser) && mAuthProgress != null) {
            mAuthProgress.postValue(AuthProgress.FAILED);
        }
        mCurrentUser = login;
        mAuthProgress = new MutableLiveData<>(AuthProgress.IN_PROGRESS);
        login(mAuthProgress, login, password);
        return mAuthProgress;
    }


    /* for MVP sample */
    public void login(@NonNull final String login, @NonNull final String password, final LoginCallback callback) {
        UserApi userApi = mApiRepo.getUserApi();
        userApi.getAll().enqueue(new Callback<List<UserApi.UserPlain>>() {
            @Override
            public void onResponse(Call<List<UserApi.UserPlain>> call, Response<List<UserApi.UserPlain>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<UserApi.UserPlain> users = response.body();
                    if (hasUserCredentials(users, login, password)) {
                        callback.onSuccess();
                        return;
                    }
                }
                callback.onError();
            }

            @Override
            public void onFailure(Call<List<UserApi.UserPlain>> call, Throwable t) {
                callback.onError();
            }
        });

    }

    private void login(final MutableLiveData<AuthProgress> progress, @NonNull final String login, @NonNull final String password) {
        UserApi api = mApiRepo.getUserApi();
        api.getAll().enqueue(new Callback<List<UserApi.UserPlain>>() {
            @Override
            public void onResponse(Call<List<UserApi.UserPlain>> call,
                                   Response<List<UserApi.UserPlain>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<UserApi.UserPlain> users = response.body();
                    if (hasUserCredentials(users, login, password)) {
                        progress.postValue(AuthProgress.SUCCESS);
                        return;
                    }
                }
                progress.postValue(AuthProgress.FAILED);
            }

            @Override
            public void onFailure(Call<List<UserApi.UserPlain>> call, Throwable t) {
                progress.postValue(AuthProgress.FAILED);
            }
        });
    }

    private static boolean hasUserCredentials(List<UserApi.UserPlain> users,
                                              String login,
                                              String pass) {
        for (UserApi.UserPlain user : users) {
            if (TextUtils.equals(user.name, login) && TextUtils.equals(user.password, pass)) {
                return true;
            }
        }
        return false;
    }

    public enum AuthProgress {
        IN_PROGRESS,
        SUCCESS,
        FAILED
    }

    public interface LoginCallback {
        void onSuccess();
        void onError();
    }
}
