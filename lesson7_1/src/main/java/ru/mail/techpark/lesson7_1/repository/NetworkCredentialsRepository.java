package ru.mail.techpark.lesson7_1.repository;

import java.util.HashMap;
import java.util.Map;

import ru.mail.techpark.lesson7_1.executors.AppExecutors;


public class NetworkCredentialsRepository
        implements CredentialsRepository {

    Map<String, String> mCredentials = new HashMap<String,String>() {{
        put("test", "test");
        put("pupkin", "qa");
        put("remote", "remote");
    }};

    @Override
    public void validateCredentials(final String login, final String pass,
                                    final ValidationCallback validationCallback) {
        AppExecutors.getInstance().networkIO().execute(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (mCredentials.containsKey(login) &&
                        mCredentials.get(login).equals(pass)) {
                    AppExecutors.getInstance().mainThread().execute(new Runnable() {
                        @Override
                        public void run() {
                            validationCallback.onSuccess();
                        }
                    });
                } else {
                    AppExecutors.getInstance().mainThread().execute(new Runnable() {
                        @Override
                        public void run() {
                            validationCallback.onError();
                        }
                    });
                }
            }
        });
    }
}
