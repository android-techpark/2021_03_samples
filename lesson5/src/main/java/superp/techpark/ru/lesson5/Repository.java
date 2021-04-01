package superp.techpark.ru.lesson5;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Repository {

    static ExecutorService sExecutor = Executors.newSingleThreadExecutor();
    static Repository sMySingleton;

    public List<Callback> mStringList;

    public Repository(List<Callback> stringList) {
        mStringList = stringList;
    }

    public void subscribe(Callback callback) {
        mStringList.add(callback);
    }

    public void unsubscribe(Callback callback) {
        int index = mStringList.indexOf(callback);
        if (index > 0) {
            mStringList.remove(index);
        }
    }

    public void executeMyOperation() {
        /* heavy op */
        sExecutor.submit(new Runnable() {
            @Override
            public void run() {
                try {
//            HttpURLConnection connection = (HttpURLConnection) new URL("fsfd").openConnection();
//            connection.connect();
//            InputStream inputStream = connection.getInputStream();
//            while (inputStream.read() != -1) {
//
//            }
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                for (Callback callback : mStringList) {
                    callback.onResult("My Result");
                }

            }
        });

    }

    interface Callback {
        void onResult(String fsfsd);
    }

    public static synchronized Repository getInstance() {
        if (sMySingleton == null) {
            sMySingleton = new Repository(new ArrayList<Callback>());
        }
        return sMySingleton;
    }
}
