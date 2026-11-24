package com.mylife.app.data;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface InventoryDao {
    @Insert long insert(InventoryItem item);

    @Query("SELECT * FROM inventory ORDER BY category ASC, name ASC")
    List<InventoryItem> getAll();

    @Query("SELECT * FROM inventory WHERE category = :cat")
    List<InventoryItem> getByCategory(String cat);

    @Query("SELECT * FROM inventory WHERE quantity <= lowStockThreshold OR (expiryDate > 0 AND expiryDate <= :thresholdMillis)")
    List<InventoryItem> getLowOrExpiring(long thresholdMillis);

    @Query("DELETE FROM inventory WHERE id = :id")
    void deleteById(long id);
}
