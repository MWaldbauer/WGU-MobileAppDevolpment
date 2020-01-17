package com.example.mwaldbauerscheduler.Database;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.mwaldbauerscheduler.DAO.AssessmentDao;
import com.example.mwaldbauerscheduler.DAO.CourseDao;
import com.example.mwaldbauerscheduler.DAO.TermDao;
import com.example.mwaldbauerscheduler.Entities.Assessment;
import com.example.mwaldbauerscheduler.Entities.Course;
import com.example.mwaldbauerscheduler.Entities.Term;

import java.time.Instant;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Term.class, Course.class, Assessment.class}, version = 5, exportSchema = false)
@TypeConverters({Converters.class})
public abstract class SchedulerRoomDatabase extends RoomDatabase {

    public abstract TermDao termDao();
    public abstract CourseDao courseDao();
    public abstract AssessmentDao assessmentDao();

    private static volatile SchedulerRoomDatabase INSTANCE;

    public static SchedulerRoomDatabase getDatabase(final Context context) {
        Log.i("getDatabase called (Term)", ""); //** Remove later
        if (INSTANCE == null) {
            synchronized (SchedulerRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            SchedulerRoomDatabase.class, "scheduler_database")
                            .fallbackToDestructiveMigration()
                            .allowMainThreadQueries()
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }

//    private static RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback() {
//        @Override
//        public void onOpen(@NonNull SupportSQLiteDatabase db) {
//            super.onOpen(db);
//            databaseWriteExecutor.execute(() -> {
//            TermDao dao = INSTANCE.termDao();
//            //dao.deleteAll();
//            java.util.Date testDate = new Date(System.currentTimeMillis());
//            java.sql.Date newDate = new java.sql.Date(testDate.getTime());
//
//            Term term = new Term("Summer 2019", newDate, newDate);;
//
//            });
//
//        }
//    };
}
