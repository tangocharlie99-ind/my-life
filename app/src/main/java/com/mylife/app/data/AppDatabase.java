package com.mylife.app.data;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(
    entities = {JournalEntry.class, FoodDbEntry.class, InventoryItem.class, Goal.class},
    version = 1,
    exportSchema = false
)
public abstract class AppDatabase extends RoomDatabase {
    private static volatile AppDatabase INSTANCE;

    public abstract JournalDao journalDao();
    public abstract FoodDao foodDao();
    public abstract InventoryDao inventoryDao();
    public abstract GoalDao goalDao();

    public static AppDatabase get(Context c) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(
                            c.getApplicationContext(),
                            AppDatabase.class,
                            "mylife.db"
                    ).fallbackToDestructiveMigration().build();
                }
            }
        }
        return INSTANCE;
    }
}
