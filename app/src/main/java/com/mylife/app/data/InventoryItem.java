package com.mylife.app.data;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "inventory")
public class InventoryItem {
    @PrimaryKey(autoGenerate = true) public long id;
    public String name;
    public String category;
    public double quantity;
    public String unit;
    public double lowStockThreshold;
    public long expiryDate;

    public InventoryItem() { }
}
