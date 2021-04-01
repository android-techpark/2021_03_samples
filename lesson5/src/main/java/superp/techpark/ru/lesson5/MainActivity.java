package superp.techpark.ru.lesson5;

import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import superp.techpark.ru.lesson5.network.NetworkActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.widget.Toast;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class MainActivity extends AppCompatActivity implements Repository.Callback {

    private final View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(final View view) {
            final Intent intent;
            switch (view.getId()) {
                case R.id.looper_and_handler:
                    intent = new Intent(MainActivity.this, HandlerActivity.class);
                    break;
                case R.id.thread_pool:
                    intent = new Intent(MainActivity.this, ThreadPoolActivity.class);
                    break;
                case R.id.intent_service:
                    intent = new Intent(MainActivity.this, IntentServiceActivity.class);
                    break;
                case R.id.network:
                    intent = new Intent(MainActivity.this, NetworkActivity.class);
                    break;
                case R.id.async_tasks:
                    intent = new Intent(MainActivity.this, AsyncTaskActivity.class);
                    break;
                default:
                    throw new UnsupportedOperationException("Wrong view id " + view.getId());
            }
            startActivity(intent);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                //
            }
        });
        Repository instance = Repository.getInstance();
        instance.executeMyOperation();
        instance.subscribe(this);




        View view = findViewById(R.id.looper_and_handler);
//        view.post(new Runnable() {
//            @Override
//            public void run() {
//                System.out.println(Thread.currentThread().getName());
//            }
//        });
        view.setOnClickListener(clickListener);
        view.post(new Runnable() {
            @Override
            public void run() {
                System.out.println("");
            }
        });
        findViewById(R.id.thread_pool).setOnClickListener(clickListener);
        findViewById(R.id.intent_service).setOnClickListener(clickListener);
        findViewById(R.id.network).setOnClickListener(clickListener);
        findViewById(R.id.async_tasks).setOnClickListener(clickListener);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Repository.getInstance().unsubscribe(this);
    }

    @Override
    public void onResult(String fsfsd) {
        Toast.makeText(this, fsfsd, Toast.LENGTH_SHORT).show();
    }
}
