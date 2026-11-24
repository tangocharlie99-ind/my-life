package com.mylife.app;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
@Entity
public class Item {
    @PrimaryKey(autoGenerate = true) public long id;
    public String name;
    public String category;
    public double qty;
    public String unit;
    public double threshold;
    public long expiry; // 0 = none
}
