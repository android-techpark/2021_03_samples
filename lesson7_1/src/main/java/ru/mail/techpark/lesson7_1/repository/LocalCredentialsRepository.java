package ru.mail.techpark.lesson7_1.repository;

import android.content.Context;

import java.util.HashMap;
import java.util.Map;

import ru.mail.techpark.lesson7_1.DBHelper;
import ru.mail.techpark.lesson7_1.dao.Credential;
import ru.mail.techpark.lesson7_1.dao.CredentialsDB;
import ru.mail.techpark.lesson7_1.executors.AppExecutors;


public class LocalCredentialsRepository implements CredentialsRepository {

    private final Map<String, String> mPredefinedCredentials = new HashMap<String, String>() {{
        put("test", "test");
        put("pupkin", "qa");
        put("local", "qa");
    }};

    private final CredentialsDB mDb;

    public LocalCredentialsRepository(Context context) {
        mDb = DBHelper.getInstance(context).getCredentialsDb();
        for (Map.Entry<String, String> entry : mPredefinedCredentials.entrySet()) {
            mDb.getCredentialDao().insertCredential(new Credential(entry.getKey(), entry.getValue()));
        }
    }

    @Override
    public void validateCredentials(final String login, final String pass,
                                    final ValidationCallback validationCallback) {
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Credential foundCredential = mDb.getCredentialDao().get(login);

                if (foundCredential == null) {
                    validationCallback.onNotFound();
                } else {
                    if (foundCredential.pass.equals(pass)) {
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
            }
        });
    }
}
