package com.mylife.app;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import java.util.List;
@Dao
public interface EntryDao {
    @Insert long insert(Entry e);
    @Query("SELECT * FROM Entry ORDER BY time DESC") List<Entry> all();
}
