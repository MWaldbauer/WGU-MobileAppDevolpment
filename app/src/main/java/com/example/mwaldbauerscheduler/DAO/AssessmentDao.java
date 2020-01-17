package com.example.mwaldbauerscheduler.DAO;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Query;

import com.example.mwaldbauerscheduler.Entities.Assessment;

import java.util.List;

@Dao
public interface AssessmentDao extends BaseDao<Assessment> {

    @Query("SELECT * FROM assessment_table ORDER BY goalDate ASC")
    LiveData<List<Assessment>> getAssessments();

    @Query("SELECT * FROM assessment_table WHERE course = :course ORDER BY goalDate ASC")
    LiveData<List<Assessment>> getAssessmentsByCourse(String course);

    @Query("DELETE FROM assessment_table")
    void deleteAssessmentTable();

}
