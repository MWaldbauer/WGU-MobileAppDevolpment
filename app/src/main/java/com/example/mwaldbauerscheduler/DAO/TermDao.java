package com.example.mwaldbauerscheduler.DAO;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Query;

import com.example.mwaldbauerscheduler.Entities.Course;
import com.example.mwaldbauerscheduler.Entities.Term;

import java.util.List;

@Dao
public interface TermDao extends BaseDao<Term> {

    @Query("SELECT * FROM term_table ORDER BY startDate ASC")
    LiveData<List<Term>> getAllTerms();

    @Query("DELETE FROM term_table")
    void deleteTermTable();

    @Query("SELECT * FROM course_table WHERE course = :term ORDER BY startDate ASC") //this is also janked
    LiveData<List<Course>> getCoursesAttachedToTerm(String term);
}
