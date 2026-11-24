package com.mylife.app;
import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
@Database(entities = {Entry.class}, version = 1, exportSchema = false)
public abstract class AppDb extends RoomDatabase {
    public abstract EntryDao dao();
    private static AppDb inst;
    public static synchronized AppDb get(Context c) {
        if (inst == null) inst = Room.databaseBuilder(c.getApplicationContext(), AppDb.class, "mylife.db")
                .allowMainThreadQueries().build();
        return inst;
    }
}
