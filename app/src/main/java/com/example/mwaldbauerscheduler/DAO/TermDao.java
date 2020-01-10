package com.example.mwaldbauerscheduler.DAO;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Query;

import com.example.mwaldbauerscheduler.Entities.Term;

import java.util.List;

@Dao
public interface TermDao extends BaseDao<Term> {

    @Query("SELECT * FROM term_table2 ORDER BY startDate ASC")
    LiveData<List<Term>> getAllTerms();

    @Query("DELETE FROM term_table2")
    void deleteTermTable();

}
