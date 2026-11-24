package com.mylife.app.data;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface GoalDao {
    @Insert long insert(Goal g);

    @Query("SELECT * FROM goals WHERE active = 1 ORDER BY targetDate ASC")
    List<Goal> getActive();

    @Query("SELECT * FROM goals ORDER BY targetDate ASC")
    List<Goal> getAll();

    @Query("SELECT COUNT(*) FROM goals")
    int count();
}
