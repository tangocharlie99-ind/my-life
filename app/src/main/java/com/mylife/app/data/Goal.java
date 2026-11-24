package com.mylife.app.data;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "goals")
public class Goal {
    @PrimaryKey(autoGenerate = true) public long id;
    public String title;
    public String description;
    public long targetDate;
    public int progressPercent;
    public int streakDays;
    public boolean active;

    public Goal() { }
}
