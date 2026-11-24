package com.mylife.app;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
@Entity
public class Entry {
    @PrimaryKey(autoGenerate = true) public long id;
    public String type;
    public String text;
    public int value;
    public long time;
}
