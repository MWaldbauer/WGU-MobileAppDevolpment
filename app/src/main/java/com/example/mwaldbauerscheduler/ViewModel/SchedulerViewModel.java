package com.example.mwaldbauerscheduler.ViewModel;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.mwaldbauerscheduler.Database.SchedulerRepository;
import com.example.mwaldbauerscheduler.Entities.Assessment;
import com.example.mwaldbauerscheduler.Entities.Course;
import com.example.mwaldbauerscheduler.Entities.Term;

import java.util.List;

public class SchedulerViewModel extends AndroidViewModel {

    private SchedulerRepository mRepository;

    //Live Data
    public LiveData<List<Term>> mAllTerms; //checking if public helps
    private LiveData<List<Course>> mAllCourses;
    private LiveData<List<Course>> mAllCoursesByTerm;
    private LiveData<List<Assessment>> mAllAssessments;
    private LiveData<List<Assessment>> mAllAssessmentsByCourseID;

    public SchedulerViewModel (Application application) {
        super(application);
        Log.i("(Term) SchedulerViewModel called", ""); //** Remove later
        mRepository = new SchedulerRepository(application);
        mAllTerms = mRepository.getAllTerms();
        mAllCourses = mRepository.getAllCourses();
        mAllCoursesByTerm = mRepository.getAllCoursesByTerm();
        mAllAssessments = mRepository.getAllAssessments();
        mAllAssessmentsByCourseID = mRepository.getAllAssessmentsByCourseID();
    }

    //Live Data ViewModel
    public LiveData<List<Term>> getAllTerms() {
        Log.i("getallTerms called",""); //** Remove later
        return mAllTerms;
    }
    public LiveData<List<Course>> getAllCourses() {
        return mAllCourses;
    };
    public LiveData<List<Course>> getAllCoursesByTerm() {
        return mAllCoursesByTerm;
    };
    public LiveData<List<Assessment>> getAllAssessments() {
        return mAllAssessments;
    };
    public LiveData<List<Assessment>> getAllAssessmentsByCourseID() {
        return mAllAssessmentsByCourseID;
    };

    //inserts
    public void insertTerm(Term term) {
        Log.i("Term name in SchedulerViewModel",term.getTerm()); //** Remove later
        mRepository.insertTerm(term);}

    public void insertCourse(Course course) {mRepository.insertCourse(course);}

    public void insertAssessment(Assessment assessment)
    {mRepository.insertAssessment(assessment);}

    //updates
    public void updateTerm(Term term) {mRepository.updateTerm(term);}

    public void updateCourse(Course course) {mRepository.updateCourse(course);}

    public void updateAssessment(Assessment assessment)
    {mRepository.updateAssessment(assessment);}

    //deletes
    public void deleteTerm(Term term) {mRepository.deleteTerm(term);}

    public  void deleteCourse(Course course) {mRepository.deleteCourse(course);}

    public void deleteAssessment(Assessment assessment) {mRepository.deleteAssessment(assessment);}

    //delete Tables

    public void deleteAllTerms() {mRepository.deleteAllTerms();}

    public void deleteAllCourses() {mRepository.deleteAllCourses();}

    public void deleteAllAssessments() {mRepository.deleteAllAssessments();}

}
