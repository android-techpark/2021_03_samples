package ru.park;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {


    int counter = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        int id = R.id.counter;
        TextView view = findViewById(id);
        view.setTextColor(Color.RED);
        Context context = this;

        Button button = findViewById(R.id.buttonCounter);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int app_name = R.string.app_name;
                view.setText(String.valueOf(counter));
                counter++;
            }
        });

        Button buttonLaunch = findViewById(R.id.launchSecond);
        buttonLaunch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SecondActivity.class);
                intent.putExtra(Constants.EXTRA_INT, counter-1);
                startActivity(intent);
            }
        });
    }
}