package ru.techpark.lesson2;

import android.graphics.Color;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class DataSource {
    private final List<NewsModel> mData;
    private static DataSource sInstance;

    private String[] mStrings = new String[]{
            "test1",
            "test2",
            "test3",
            "test4",
            "test5",
            "test6",
            "test7"
    };

    private int[] mColors = new int[]{
            Color.RED,
            Color.BLACK,
            Color.GREEN,
            Color.GRAY,
            Color.BLUE,
            Color.YELLOW
    };

    public DataSource() {
        mData = new ArrayList<>();
        Random random = new Random();

        for (int i = 0; i < 100; i++) {
            String title = mStrings[random.nextInt(mStrings.length)];
            String date = String.valueOf(new Date(Math.abs(random.nextInt())));
            int color = mColors[random.nextInt(mColors.length)];
            mData.add(new NewsModel(title, date, color));
        }
    }

    public List<NewsModel> getRemoteData() {
        return mData;
    }

    public synchronized static DataSource getInstance() {
        if (sInstance == null) {
            sInstance = new DataSource();
        }
        return sInstance;
    }
}
