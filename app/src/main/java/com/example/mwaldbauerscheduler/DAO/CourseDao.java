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

    @Query("SELECT * FROM course_table WHERE termID = :termID ORDER BY startDate ASC") //this is just jank for now
    LiveData<List<Course>> getAllCoursesByTermIDLive(int termID);

    @Query("SELECT * FROM course_table WHERE termID = :termID ORDER BY startDate ASC")
    List<Course> getAllCoursesByTermID(int termID); // We need a version of this that is a List without Live Data

    @Query("DELETE FROM course_table")
    void deleteCoursesTable();

    @Query("SELECT * FROM course_table WHERE courseID = :courseID") //this is also janked
    Course getCourseByID(int courseID);

}
