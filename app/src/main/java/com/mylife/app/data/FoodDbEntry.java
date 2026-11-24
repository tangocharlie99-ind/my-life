package com.mylife.app.data;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "food_db")
public class FoodDbEntry {
    @PrimaryKey(autoGenerate = true) public long id;
    public String name;
    public int calories;
    public int proteinGrams;
    public int carbGrams;
    public String serving;

    public FoodDbEntry() { }
}
