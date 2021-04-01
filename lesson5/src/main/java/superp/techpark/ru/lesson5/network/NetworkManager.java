package superp.techpark.ru.lesson5.network;

import android.util.Log;

import java.io.IOException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * Singleton-класс для исполнения сетевых запросов
 */
class NetworkManager {

    private static final NetworkManager INSTANCE = new NetworkManager();
    private static final String TAG = "NETWORK";

    private final OkHttpClient client = new OkHttpClient();
    private final Executor executor = Executors.newSingleThreadExecutor();
    private volatile OnRequestCompleteListener mListener;

    private NetworkManager() {
    }

    static NetworkManager getInstance() {
        return INSTANCE;
    }

    void get(final String url, final OnRequestCompleteListener listener) {
        addListener(listener);
        final Request request = new Request.Builder()
                .url(url)
                .addHeader("auth","bafgdssdf")
                .build();
        performRequest(request);
    }

    void addListener(OnRequestCompleteListener listener) {
        mListener = listener;
    }

    void clear() {
        mListener = null;
    }

    private void performRequest(final Request request) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                final String body = getBody(request);
                if (mListener != null) {
                    mListener.onRequestComplete(body);
                }
            }
        });
    }

    private String getBody(final Request request) {
        try {
            final Response response = client.newCall(request).execute();
            try (ResponseBody body = response.body()) {
                if (response.isSuccessful() && body != null) {
                    return body.string();
                }
            }
        } catch (IOException e) {
            Log.e(TAG, "Fail to perform request", e);
        }
        return null;
    }

    /**
     * В реальных и семестровых проектах обязательно должна быть обработка ошибок. В данном случае
     * мы имеем callback только для успешного выполнения.
     */
    public interface OnRequestCompleteListener {
        void onRequestComplete(final String body);
    }
}
