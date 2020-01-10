package com.example.mwaldbauerscheduler.DAO;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Query;

import com.example.mwaldbauerscheduler.Entities.Assessment;
import com.example.mwaldbauerscheduler.Entities.Course;

import java.util.List;

@Dao
public interface AssessmentDao extends BaseDao<Assessment> {

    @Query("SELECT * FROM assessment_table ORDER BY goalDate ASC")
    LiveData<List<Assessment>> getAssessments();

    @Query("SELECT * FROM assessment_table WHERE courseID = :courseID ORDER BY goalDate ASC")
    LiveData<List<Assessment>> getAssessmentsByCourseID(int courseID);

    @Query("DELETE FROM assessment_table")
    void deleteAssessmentTable();

}
