package com.mylife.app.data;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface JournalDao {
    @Insert long insert(JournalEntry e);
    @Update void update(JournalEntry e);
    @Delete void delete(JournalEntry e);

    @Query("SELECT * FROM journal_entries ORDER BY timestamp DESC")
    List<JournalEntry> getAll();

    @Query("SELECT * FROM journal_entries WHERE timestamp >= :start AND timestamp < :end ORDER BY timestamp ASC")
    List<JournalEntry> getForRange(long start, long end);

    @Query("SELECT COUNT(*) FROM journal_entries")
    int count();
}
