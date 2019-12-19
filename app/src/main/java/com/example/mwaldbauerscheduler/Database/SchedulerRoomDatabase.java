package com.example.mwaldbauerscheduler.Database;

import android.content.Context;
import android.util.Log;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.mwaldbauerscheduler.DAO.AssessmentDao;
import com.example.mwaldbauerscheduler.DAO.CourseDao;
import com.example.mwaldbauerscheduler.DAO.TermDao;
import com.example.mwaldbauerscheduler.Entities.Assessment;
import com.example.mwaldbauerscheduler.Entities.Course;
import com.example.mwaldbauerscheduler.Entities.Term;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Term.class, Course.class, Assessment.class}, version = 1, exportSchema = false)
@TypeConverters({Converters.class})
public abstract class SchedulerRoomDatabase extends RoomDatabase {

    public abstract TermDao termDao();
    public abstract CourseDao courseDao();
    public abstract AssessmentDao assessmentDao();

    private static volatile SchedulerRoomDatabase INSTANCE;
//    private static final int NUMBER_OF_THREADS = 4;
//    static final ExecutorService databaseWriteExecutor =
//            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public static SchedulerRoomDatabase getDatabase(final Context context) {
        Log.i("getDatabase called (Term)", ""); //** Remove later
        if (INSTANCE == null) {
            synchronized (SchedulerRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            SchedulerRoomDatabase.class, "scheduler_database")
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
