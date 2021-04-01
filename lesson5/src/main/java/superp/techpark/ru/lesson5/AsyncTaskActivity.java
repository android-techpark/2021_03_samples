package superp.techpark.ru.lesson5;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.json.JSONObject;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class AsyncTaskActivity extends AppCompatActivity implements ProgressListener {

    private ProgressBar mProgressBar;
    private MyTask mTask;
    private MyRunnable mRunnable;
    private ExecutorService executorService = Executors.newFixedThreadPool(1);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final int maxProgress = 10;
        setContentView(R.layout.activity_asynctask);
        findViewById(R.id.start).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mTask != null) {
                    mTask.cancel(true);
                }
//                mTask = new MyTask(AsyncTaskActivity.this);
//                mTask.execute(maxProgress);
                mRunnable = new MyRunnable(AsyncTaskActivity.this);
                Future<?> submit = executorService.submit(mRunnable);
                try {
                    submit.get();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                mProgressBar.setProgress(0);
                mProgressBar.setVisibility(View.VISIBLE);
            }
        });

        mProgressBar = findViewById(R.id.progress);
        mProgressBar.setMax(maxProgress);

        if (getLastCustomNonConfigurationInstance() != null) {
//            mTask = (MyTask) getLastCustomNonConfigurationInstance();
//            mTask.mProgressListener = this;
            mRunnable = (MyRunnable) getLastCustomNonConfigurationInstance();
            mRunnable.mProgressListener = this;
            mProgressBar.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public Object onRetainCustomNonConfigurationInstance() {
        return mRunnable;
    }

    @Override
    public void onProgressUpdate(int progress) {
        mProgressBar.setProgress(progress);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mRunnable != null) {
            mRunnable.reset();
        }
        if(mTask != null) mTask.reset();
    }

    @Override
    public void onFinish() {
        mProgressBar.setVisibility(View.GONE);
        mTask = null;
        TextView res = findViewById(R.id.result);
        res.setText("Done!");

    }

    static class MyRunnable implements Runnable {

        ProgressListener mProgressListener;

        MyRunnable(ProgressListener mProgressListener) {
            this.mProgressListener = mProgressListener;
        }

        public void reset() {
            mProgressListener = null;
        }

        @Override
        public void run() {
            try {
                for (int i = 0; i <= 10; i++) {
                    Thread.sleep(300);
                    publishProgress(i);
                }
                publishFinish();
                throw new RuntimeException();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        private void publishFinish() {
            new Handler(Looper.getMainLooper()).post(new Runnable() {
                @Override
                public void run() {
                    mProgressListener.onFinish();
                }
            });
        }

        private void publishProgress(final int progress) {
            new Handler(Looper.getMainLooper()).post(
                    new Runnable() {
                        @Override
                        public void run() {
                            mProgressListener.onProgressUpdate(progress);
                        }
                    }
            );
        }
    }

    static class MyTask extends AsyncTask<Integer, Integer, Boolean> {

        @SuppressLint("StaticFieldLeak")
        private ProgressListener mProgressListener;

        public MyTask(ProgressListener ref) {
            mProgressListener = ref;
        }

        public void reset() {
            mProgressListener = null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            if (mProgressListener != null && !isCancelled()) {
                mProgressListener.onProgressUpdate(values[0]);
            }
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            if (mProgressListener != null) {
                mProgressListener.onFinish();
            }
        }

        @Override
        protected Boolean doInBackground(Integer... params) {

            try {
                for (int i = 0; i < params[0]; i++) {
                    Thread.sleep(1000);
                    publishProgress(i);
                    if (isCancelled()) {
                        return false;
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return true;
        }
    }

}

