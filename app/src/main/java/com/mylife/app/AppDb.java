package com.mylife.app;
import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
@Database(entities = {Entry.class, Item.class}, version = 2, exportSchema = false)
public abstract class AppDb extends RoomDatabase {
    public abstract EntryDao dao();
    public abstract ItemDao items();
    private static AppDb inst;
    public static synchronized AppDb get(Context c) {
        if (inst == null) inst = Room.databaseBuilder(c.getApplicationContext(), AppDb.class, "mylife.db")
                .allowMainThreadQueries().fallbackToDestructiveMigration().build();
        return inst;
    }
}
