package com.mylife.app.data;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "journal_entries")
public class JournalEntry {
    @PrimaryKey(autoGenerate = true) public long id;
    public long timestamp;
    public String category;
    public String description;
    public int mood;
    public int energy;

    public JournalEntry() { }
}
