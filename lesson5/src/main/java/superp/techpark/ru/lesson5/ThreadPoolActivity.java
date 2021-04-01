package superp.techpark.ru.lesson5;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Демонстрация работы с Executor'ом и Thread Pool'ом с фиксированным числом потоков.
 * Представленный код добавляет задачи в очередь Executor'у, а сами задачи имитируют пятисекундную
 * работу и выводят соответствующие сообщения в Logcat. Для удобства чтения логов задачи выводят
 * сообщения с отступом.
 */
public class ThreadPoolActivity extends AppCompatActivity {

    private static final int THREAD_COUNT = 5;

    private int taskId;

    int threadCount = Runtime.getRuntime().availableProcessors() + 1;

//    private final ExecutorService executorService = Executors.newFixedThreadPool(5);
    private final ExecutorService executorServiceNetwork = Executors.newFixedThreadPool(2);
    private final ExecutorService executorServiceDatabase = Executors.newFixedThreadPool(1);
    private final ThreadPoolExecutor executorService = new ThreadPoolExecutor(3, 3, 1, TimeUnit.MINUTES,
            new LinkedBlockingQueue<Runnable>(2),
            new RejectedExecutionHandler() {
                    @Override
                    public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
                        Log.d("Tag", String.format("Task was %d rejected due to capacity 1", taskId));
                    }
    });// или просто Executors.newFixedThreadPool(THREAD_COUNT);
    // [1 2 3]
    // [4, 5] - LinkedBlockingQueue
    // [6,7,8] - reject !


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        executorService.shutdown();
        executorService.allowCoreThreadTimeOut(false);
        setContentView(R.layout.activity_thread_pool);

        findViewById(R.id.add_task).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                executorService.execute(createTask());
            }
        });
    }



    private Runnable createTask() {
        final int id = taskId++;
        return new Runnable() {
            @Override
            public void run() {
                Log.d("TASK", String.format("%sTask %d started on thread '%s'",
                        getPadding(id), id, Thread.currentThread().getName()));
                try {
                    Thread.sleep(5000L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                Log.d("TASK", String.format("%sTask %d finished on thread '%s'",
                        getPadding(id), id, Thread.currentThread().getName()));
            }
        };
    }

    private static String getPadding(final int id) {
        final int padding = id % THREAD_COUNT;
        final StringBuilder builder = new StringBuilder();
        for (int i = 0; i < padding; i++) {
            builder.append("    ");
        }
        return builder.toString();
    }
}
