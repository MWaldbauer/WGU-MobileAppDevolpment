package com.example.mwaldbauerscheduler.DAO;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Query;

import com.example.mwaldbauerscheduler.Entities.Course;
import com.example.mwaldbauerscheduler.Entities.Term;

import java.util.List;

@Dao
public interface CourseDao extends BaseDao<Course> {

    @Query("SELECT * FROM course_table ORDER BY startDate ASC")
    LiveData<List<Course>> getCourses();

    @Query("SELECT * FROM course_table WHERE term = :term ORDER BY startDate ASC")
    LiveData<List<Course>> getCoursesByTerm(String term);

    @Query("DELETE FROM course_table")
    void deleteCoursesTable();

}
