package com.example.mwaldbauerscheduler.DAO;

import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

public interface BaseDao<T> {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(T obj);

    @Update()
    void update(T obj);

    @Delete()
    void delete(T obj);

}
