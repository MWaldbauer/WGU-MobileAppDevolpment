package com.example.mwaldbauerscheduler.ViewModel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.mwaldbauerscheduler.Database.SchedulerRepository;
import com.example.mwaldbauerscheduler.Entities.Assessment;
import com.example.mwaldbauerscheduler.Entities.Course;
import com.example.mwaldbauerscheduler.Entities.Term;

import java.util.Date;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class SchedulerViewModel extends AndroidViewModel {

    private SchedulerRepository mRepository;
    private String term;
    private String course;
    private int termID;
    private int courseID;
    private Executor executor = Executors.newSingleThreadExecutor();

    //Live Data
    public LiveData<List<Term>> mAllTerms; //checking if public helps
    private LiveData<List<Course>> mCoursesAttachedToTerm;
    private LiveData<List<Course>> mAllCourses;
    private LiveData<List<Course>> mAllCoursesByTermIDLive;
    public List<Course> mAllCoursesByTermID;
    private LiveData<List<Assessment>> mAllAssessments;
    public List<Assessment> mAllAssessmentsByCourseID;
    private LiveData<List<Assessment>> mAllAssessmentsByCourseIDLive;

    //Mutable Live Data
    public MutableLiveData<Term> mLiveTerm = new MutableLiveData<>();
    public MutableLiveData<Course> mLiveCourse = new MutableLiveData<>();
    public MutableLiveData<Assessment> mLiveAssessment = new MutableLiveData<>();


    public SchedulerViewModel (Application application) {
        super(application);
        mRepository = new SchedulerRepository(application);
        mAllTerms = mRepository.getAllTerms();
        mCoursesAttachedToTerm = mRepository.getCoursesAttachedToTerm(term);
        mAllCourses = mRepository.getAllCourses();
        mAllCoursesByTermIDLive = mRepository.getAllCoursesByTermIDLive(termID);
        mAllCoursesByTermID = mRepository.getAllCoursesByTermID(termID);
        mAllAssessments = mRepository.getAllAssessments();
        mAllAssessmentsByCourseID = mRepository.getAllAssessmentsByCourseID(courseID);
        mAllAssessmentsByCourseIDLive = mRepository.getAllAssessmentsByCourseIDLive(courseID);
    }

    //Live Data ViewModel
    public LiveData<List<Term>> getAllTerms() { return mAllTerms;    }

    public LiveData<List<Course>> getCoursesAttachedToTerm(String term) {
        return mCoursesAttachedToTerm;
    };

    public LiveData<List<Course>> getAllCourses() {
        return mAllCourses;
    };
    public LiveData<List<Course>> getAllCoursesByTermIDLive(int termID) {
        return mRepository.getAllCoursesByTermIDLive(termID);
    };

    public List<Course> getAllCoursesByTermID(int termID) {
        return mRepository.getAllCoursesByTermID(termID);
    };

    public LiveData<List<Assessment>> getAllAssessments() {
        return mAllAssessments;
    };
    public List<Assessment> getAllAssessmentsByCourseID(int courseID) {
        return mRepository.getAllAssessmentsByCourseID(courseID);
    };
    
    public LiveData<List<Assessment>> getAllAssessmentsByCourseIDLive(int assessmentID) {
        return mRepository.getAllAssessmentsByCourseIDLive(assessmentID);
    };

    //special getter for Assessment End Dates as those always match Course's
    public Course getCourseByID(final int courseID) {return mRepository.getCourseByID(courseID);
    }

    //inserts
    public void insertTerm(Term term) {mRepository.insertTerm(term);}

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

    public void deleteCourse(Course course) {mRepository.deleteCourse(course);}

    public void deleteAssessment(Assessment assessment) {mRepository.deleteAssessment(assessment);}

    //delete Tables

    public void deleteAllTerms() {mRepository.deleteAllTerms();}

    public void deleteAllCourses() {mRepository.deleteAllCourses();}

    public void deleteAllAssessments() {mRepository.deleteAllAssessments();}

    //mutable data threads

    public void loadTermData(final int termID) {
        executor.execute(() -> {
            Term term = mRepository.getTermByID(termID);
            mLiveTerm.postValue(term);
        });
    }

    public void loadCourseData(final int courseID) {
        executor.execute(() -> {
            Course course = mRepository.getCourseByID(courseID);
            mLiveCourse.postValue(course);
        });
    }

    public void loadAssessmentData(final int assessmentID) {
        executor.execute(() -> {
            Assessment assessment = mRepository.getAssessmentByID(assessmentID);
            mLiveAssessment.postValue(assessment);
        });
    }

    public void saveTerm(String termName, Date startDate, Date endDate) {
        Term term = mLiveTerm.getValue();

        if (term == null) {
            term = new Term(termName, startDate, endDate);

        } else {
            //existing
            term.setTerm(termName);
            term.setStartDate(startDate);
            term.setStopDate(endDate);
        }

        mRepository.insertTerm(term);
    }

    public void saveCourse(String courseName, Date startDate, Date endDate, String completionStatus,
        int termID, String mentorName, String mentorPhone, String mentorEmail, String notes) {
        Course course = mLiveCourse.getValue();

        if (course == null) {
            course = new Course(courseName, startDate, false, endDate,
                false, completionStatus, termID, mentorName, mentorPhone, mentorEmail,
                notes);

        } else {
            //existing
            course.setCourse(courseName);
            course.setStartDate(startDate);
            course.setStopDate(endDate);
            course.setCompletionStatus(completionStatus);
            course.setTermID(termID);
            course.setMentor(mentorName);
            course.setMentorPhone(mentorPhone);
            course.setMentorEmail(mentorEmail);
            course.setNotes(notes);
        }

        mRepository.insertCourse(course);
    }

    public void saveAssessment(String assessmentName, Date goalDate, Date endDate, String notes,
                           int courseID) {
        Assessment assessment = mLiveAssessment.getValue();

        if (assessment == null) {
            assessment = new Assessment(assessmentName, goalDate, endDate, false,
                    notes, courseID);

        } else {
            //existing
            assessment.setAssessment(assessmentName);
            assessment.setGoalDate(goalDate);
            assessment.setStopDate(endDate);
            assessment.setNotes(notes);
            assessment.setCourseID(courseID);
        }

        mRepository.insertAssessment(assessment);
    }
}
