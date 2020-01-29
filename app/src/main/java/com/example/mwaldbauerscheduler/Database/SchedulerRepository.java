package com.example.mwaldbauerscheduler.Database;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.mwaldbauerscheduler.DAO.AssessmentDao;
import com.example.mwaldbauerscheduler.DAO.CourseDao;
import com.example.mwaldbauerscheduler.DAO.TermDao;
import com.example.mwaldbauerscheduler.Entities.Assessment;
import com.example.mwaldbauerscheduler.Entities.Course;
import com.example.mwaldbauerscheduler.Entities.Term;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class SchedulerRepository {

    public SchedulerRoomDatabase db;
    private TermDao mTermDao;
    private CourseDao mCourseDao;
    private AssessmentDao mAssessmentDao;
    private Executor executor = Executors.newSingleThreadExecutor();

    public LiveData<List<Term>> mAllTerms;
    private LiveData<List<Course>> mCoursesAttachedToTerm;
    private LiveData<List<Course>> mAllCourses;
    private LiveData<List<Assessment>> mAllAssessments;
    private LiveData<List<Course>> mAllCoursesByTermIDLive;
    private LiveData<List<Assessment>> mAllAssessmentsByCourseIDLive;
    public List<Course> mAllCoursesByTermID;
    private List<Assessment> mAllAssessmentsByCourseID;

    private String term;
    private String course;
    private int termID;
    private int courseID;

    public SchedulerRepository(Application application) {
        db = SchedulerRoomDatabase.getDatabase(application);
        mTermDao = db.termDao();
        mCourseDao = db.courseDao();
        mAssessmentDao = db.assessmentDao();

        //Live Data
        mAllTerms = mTermDao.getAllTerms();
        mCoursesAttachedToTerm = mTermDao.getCoursesAttachedToTerm(term);
        mAllCourses = mCourseDao.getCourses();
        mAllCoursesByTermID = mCourseDao.getAllCoursesByTermID(termID);
        mAllCoursesByTermIDLive = mCourseDao.getAllCoursesByTermIDLive(termID);
        mAllAssessments = mAssessmentDao.getAssessments();
        mAllAssessmentsByCourseID = mAssessmentDao.getAllAssessmentsByCourseID(courseID);
        mAllAssessmentsByCourseIDLive = mAssessmentDao.getAllAssessmentsByCourseIDLive(courseID);

    }

    public LiveData<List<Term>> getAllTerms() {
        return mAllTerms;
    }

    public LiveData<List<Course>> getCoursesAttachedToTerm(String term) {
        return mCoursesAttachedToTerm;
    };

    public LiveData<List<Course>> getAllCourses() {
        return mAllCourses;
    };
    public LiveData<List<Course>> getAllCoursesByTermIDLive(int termID) {
        return mCourseDao.getAllCoursesByTermIDLive(termID);
    };

    public List<Course> getAllCoursesByTermID(int termID) {
        return mCourseDao.getAllCoursesByTermID(termID);
    };

    public LiveData<List<Assessment>> getAllAssessments() {
        return mAllAssessments;
    };

    public LiveData<List<Assessment>> getAllAssessmentsByCourseIDLive(int courseID) {
        return mAssessmentDao.getAllAssessmentsByCourseIDLive(courseID);
    };

    public List<Assessment> getAllAssessmentsByCourseID(int courseID) {
        return mAssessmentDao.getAllAssessmentsByCourseID(courseID);
    };

    // You must call this on a non-UI thread or your app will throw an exception. Room ensures
    // that you're not doing any long running operations on the main thread, blocking the UI.

    //Example public void insert (Word word) {new insertAsyncTask(mWordDao).execute(word);}

    //inserts
    public void insertTerm(Term term) {executor.execute(() -> db.termDao().insert(term));
    }

    public void insertCourse(Course course) {executor.execute(() -> db.courseDao().insert(course));}

    public void insertAssessment(Assessment assessment)
        {executor.execute(() -> db.assessmentDao().insert(assessment));}

    //updates
    public void updateTerm(Term term) {new UpdateTermAsyncTask(mTermDao).execute(term);}

    public void updateCourse(Course course) {new UpdateCourseAsyncTask(mCourseDao).execute(course);}

    public void updateAssessment(Assessment assessment)
    {new UpdateAssessmentAsyncTask(mAssessmentDao).execute(assessment);}

    //deletes
    public void deleteTerm(Term term) {new DeleteTermAsyncTask(mTermDao).execute(term);} //this said assessment and worked

    public void deleteCourse(Course course) {new DeleteCourseAsyncTask(mCourseDao).execute(course);}

    public void deleteAssessment(Assessment assessment)
    {new DeleteAssessmentAsyncTask(mAssessmentDao).execute(assessment);}

    public void deleteAllTerms() {new DeleteAllTermsAsyncTask(mTermDao).execute();}

    public void deleteAllCourses() {new DeleteAllCoursesAsyncTask(mCourseDao).execute();}

    public void deleteAllAssessments() {new DeleteAllAssessmentsAsyncTask(mAssessmentDao).execute();}

    public Term getTermByID(int termID) {

        return  db.termDao().getTermByID(termID);
    }

    public Course getCourseByID(int courseID) {

        return  db.courseDao().getCourseByID(courseID);
    }

    public Assessment getAssessmentByID(int assessmentID) {

        return  db.assessmentDao().getAssessmentByID(assessmentID);
    }

    //insertAsyncTasks
    private static class InsertTermAsyncTask extends AsyncTask<Term, Void, Void> {

        private TermDao mAsyncTaskDao;

        private InsertTermAsyncTask(TermDao termDao) {
            mAsyncTaskDao = termDao;
        }

        @Override
        protected Void doInBackground(final Term... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }

    private static class InsertCourseAsyncTask extends AsyncTask<Course, Void, Void> {

        private CourseDao mAsyncTaskDao;

        private InsertCourseAsyncTask(CourseDao courseDao) {
            mAsyncTaskDao = courseDao;
        }

        @Override
        protected Void doInBackground(final Course... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }

    private static class InsertAssessmentAsyncTask extends AsyncTask<Assessment, Void, Void> {

        private AssessmentDao mAsyncTaskDao;

        private InsertAssessmentAsyncTask(AssessmentDao assessmentDao) {
            mAsyncTaskDao = assessmentDao;
        }

        @Override
        protected Void doInBackground(final Assessment... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }

    //updateAsyncTasks

    private static class UpdateTermAsyncTask extends AsyncTask<Term, Void, Void> {

        private TermDao mAsyncTaskDao;

        private UpdateTermAsyncTask(TermDao termDao) {
            mAsyncTaskDao = termDao;
        }

        @Override
        protected Void doInBackground(final Term... params) {
            mAsyncTaskDao.update(params[0]);
            return null;
        }
    }

    private static class UpdateCourseAsyncTask extends AsyncTask<Course, Void, Void> {

        private CourseDao mAsyncTaskDao;

        private UpdateCourseAsyncTask(CourseDao courseDao) {
            mAsyncTaskDao = courseDao;
        }

        @Override
        protected Void doInBackground(final Course... params) {
            mAsyncTaskDao.update(params[0]);
            return null;
        }
    }

    private static class UpdateAssessmentAsyncTask extends AsyncTask<Assessment, Void, Void> {

        private AssessmentDao mAsyncTaskDao;

        private UpdateAssessmentAsyncTask(AssessmentDao assessmentDao) {
            mAsyncTaskDao = assessmentDao;
        }

        @Override
        protected Void doInBackground(final Assessment... params) {
            mAsyncTaskDao.update(params[0]);
            return null;
        }
    }

    //deleteAsyncTasks

    private static class DeleteTermAsyncTask extends AsyncTask<Term, Void, Void> {

        private TermDao mAsyncTaskDao;

        private DeleteTermAsyncTask(TermDao termDao) {
            mAsyncTaskDao = termDao;
        }

        @Override
        protected Void doInBackground(final Term... params) {
            mAsyncTaskDao.delete(params[0]);
            return null;
        }
    }

    private static class DeleteCourseAsyncTask extends AsyncTask<Course, Void, Void> {

        private CourseDao mAsyncTaskDao;

        private DeleteCourseAsyncTask(CourseDao courseDao) {
            mAsyncTaskDao = courseDao;
        }

        @Override
        protected Void doInBackground(final Course... params) {
            mAsyncTaskDao.delete(params[0]);
            return null;
        }
    }

    private static class DeleteAssessmentAsyncTask extends AsyncTask<Assessment, Void, Void> {

        private AssessmentDao mAsyncTaskDao;

        private DeleteAssessmentAsyncTask(AssessmentDao assessmentDao) {
            mAsyncTaskDao = assessmentDao;
        }

        @Override
        protected Void doInBackground(final Assessment... params) {
            mAsyncTaskDao.delete(params[0]);
            return null;
        }
    }

    //deleteAll calls

    private static class DeleteAllTermsAsyncTask extends AsyncTask<Term, Void, Void> {

        private TermDao mAsyncTaskDao;

        private DeleteAllTermsAsyncTask(TermDao termDao) {
            mAsyncTaskDao = termDao;
        }

        @Override
        protected Void doInBackground(final Term... params) {
            mAsyncTaskDao.deleteTermTable();
            return null;
        }
    }

    private static class DeleteAllCoursesAsyncTask extends AsyncTask<Course, Void, Void> {

        private CourseDao mAsyncTaskDao;

        private DeleteAllCoursesAsyncTask(CourseDao courseDao) {
            mAsyncTaskDao = courseDao;
        }

        @Override
        protected Void doInBackground(final Course... params) {
            mAsyncTaskDao.deleteCoursesTable();
            return null;
        }
    }

    private static class DeleteAllAssessmentsAsyncTask extends AsyncTask<Assessment, Void, Void> {

        private AssessmentDao mAsyncTaskDao;

        private DeleteAllAssessmentsAsyncTask(AssessmentDao assessmentDao) {
            mAsyncTaskDao = assessmentDao;
        }

        @Override
        protected Void doInBackground(final Assessment... params) {
            mAsyncTaskDao.deleteAssessmentTable();
            return null;
        }
    }
}
