package superb.techpark.ru.lesson6.network;

import android.content.Context;

import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.moshi.MoshiConverterFactory;
import superb.techpark.ru.lesson6.ApplicationModified;

public class ApiRepo {
    private final UserApi mUserApi;
    private final LessonApi mLessonApi;
    private final OkHttpClient mOkHttpClient;

    public ApiRepo() {
        mOkHttpClient = new OkHttpClient()
                .newBuilder()
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(MoshiConverterFactory.create())
                .baseUrl(new HttpUrl.Builder().scheme("https")
                        .host("tp-android-demo.firebaseio.com")
                        .build())
                .client(mOkHttpClient)
                .build();

        mUserApi = retrofit.create(UserApi.class);
        mLessonApi = retrofit.create(LessonApi.class);
    }

    public UserApi getUserApi() {
        return mUserApi;
    }

    public LessonApi getLessonApi() {
        return mLessonApi;
    }

    public static ApiRepo from(Context context) {
        return ApplicationModified.from(context).getApis();
    }
}

