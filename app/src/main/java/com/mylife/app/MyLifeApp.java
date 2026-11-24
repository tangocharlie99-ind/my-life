package com.mylife.app;

import android.app.Application;

import com.mylife.app.data.AppDatabase;
import com.mylife.app.data.DefaultData;

import java.util.concurrent.Executors;

public class MyLifeApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Executors.newSingleThreadExecutor().execute(() -> {
            try {
                if (AppDatabase.get(this).foodDao().count() == 0) {
                    AppDatabase.get(this).foodDao().insertAll(DefaultData.foods());
                }
            } catch (Exception ignored) { }
        });
    }
}
