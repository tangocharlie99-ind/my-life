package com.mylife.app.data;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface FoodDao {
    @Insert void insertAll(List<FoodDbEntry> foods);

    @Query("SELECT * FROM food_db ORDER BY name ASC")
    List<FoodDbEntry> getAll();

    @Query("SELECT * FROM food_db WHERE name LIKE '%' || :q || '%'")
    List<FoodDbEntry> search(String q);

    @Query("SELECT COUNT(*) FROM food_db")
    int count();
}
