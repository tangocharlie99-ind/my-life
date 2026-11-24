package com.mylife.app;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import java.util.List;
@Dao
public interface ItemDao {
    @Insert long insert(Item i);
    @Query("SELECT * FROM Item ORDER BY category, name") List<Item> all();
    @Query("UPDATE Item SET qty = :q, expiry = 0 WHERE id = :id") void restock(long id, double q);
    @Query("DELETE FROM Item WHERE id = :id") void delete(long id);
}
