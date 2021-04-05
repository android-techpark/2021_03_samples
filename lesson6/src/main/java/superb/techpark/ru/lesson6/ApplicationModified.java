package superb.techpark.ru.lesson6;

import android.app.Application;
import android.content.Context;

import superb.techpark.ru.lesson6.network.ApiRepo;

public class ApplicationModified extends Application {

    private ApiRepo mApiRepo;
    private AuthRepo mAuthRepo;

    @Override
    public void onCreate() {
        super.onCreate();
        mApiRepo = new ApiRepo();
        mAuthRepo = new AuthRepo(mApiRepo);
    }

    public AuthRepo getAuthRepo() {
        return mAuthRepo;
    }

    public ApiRepo getApis() {
        return mApiRepo;
    }

    public static ApplicationModified from(Context context) {
        return (ApplicationModified) context.getApplicationContext();
    }
}

