package com.example.mwaldbauerscheduler.Activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationManagerCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.mwaldbauerscheduler.Entities.Course;
import com.example.mwaldbauerscheduler.Entities.Course;
import com.example.mwaldbauerscheduler.Entities.Term;
import com.example.mwaldbauerscheduler.MainActivity;
import com.example.mwaldbauerscheduler.MyReceiver;
import com.example.mwaldbauerscheduler.MyValidator;
import com.example.mwaldbauerscheduler.R;
import com.example.mwaldbauerscheduler.View.CourseListAdapter;
import com.example.mwaldbauerscheduler.View.CourseListAdapter;
import com.example.mwaldbauerscheduler.ViewModel.SchedulerViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Date;
import java.util.List;

import static com.example.mwaldbauerscheduler.MainActivity.CHANNEL_ID;
import static com.example.mwaldbauerscheduler.MainActivity.notificationManager;


public class CoursesActivity extends AppCompatActivity {

    //    public static final String CHANNEL_ID = "MWScheduler";
    final int NEW_COURSE_ACTIVITY_REQUEST_CODE = 10;
    final int COURSE_DETAIL_ACTIVITY_REQUEST_CODE = 20;
    SchedulerViewModel mSchedulerViewModel;
    //    public static final MyValidator schedulerValidator = new MyValidator();
    Course selectedCourse = null;
    final int RESULT_DELETE = -2;
//    public static NotificationManagerCompat notificationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_courses);

//        Toolbar toolbar = findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//        toolbar.setNavigationIcon(R.drawable.ic_toc_black_24dp);

        //getSupportActionBar().
        //getSupportActionBar().setIcon(R.drawable.ic_toc_black_24dp);

        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        final CourseListAdapter adapter = new CourseListAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        mSchedulerViewModel = new ViewModelProvider(this).get(SchedulerViewModel.class);

        //observers
        mSchedulerViewModel.getAllCourses().observe(this, new Observer<List<Course>>() {
            @Override
            public void onChanged(@Nullable final List<Course> courses) {
                // Update the cached copy of the words in the adapter.
                adapter.setCourses(courses);
                Log.i("Course changed in Course Activity", ""); //** Remove later
            }
        });

        //test data
        Date sampledate1 = new Date(6,31,2020);
        Date sampledate2 = new Date(12,31,2020);

        java.sql.Date startDateSQL = new java.sql.Date(sampledate1.getTime());
        java.sql.Date endDateSQL = new java.sql.Date(sampledate2.getTime());

        Term fakeTerm = new Term(String.valueOf(System.currentTimeMillis()), startDateSQL, endDateSQL);
        Log.i("Course added to term", String.valueOf(fakeTerm.getTerm())); //This is where we start tomorrow <3

        Course course = new Course(
                "First Course", startDateSQL, false, endDateSQL,
                false, "Plan to Take", fakeTerm.getTerm(),
                "Mr. mentor", "phone number", "name@mail.com");
        mSchedulerViewModel.insertCourse(course);

        //new Course fab
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CoursesActivity.this, NewCourseActivity.class);
                startActivityForResult(intent, NEW_COURSE_ACTIVITY_REQUEST_CODE);
            }
        });

        FloatingActionButton fab2 = findViewById(R.id.fab2);
        fab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CoursesActivity.this, CourseDetailsActivity.class);
                selectedCourse = adapter.getSelectedCourse();

                if (selectedCourse == null){
                    Toast.makeText(
                            getApplicationContext(),
                            "Please select a course first.",
                            Toast.LENGTH_LONG).show();
                    return;

                }
                Date startDate = selectedCourse.getStartDate();
                Date endDate = selectedCourse.getStopDate();
                intent.putExtra("Course name", selectedCourse.getCourse());
                intent.putExtra("Course start day", startDate.getDate());
                intent.putExtra("Course start month", startDate.getMonth()+1);
                intent.putExtra("Course start year", startDate.getYear()+1900);
                intent.putExtra("Course end day", endDate.getDate());
                intent.putExtra("Course end month", endDate.getMonth()+1);
                intent.putExtra("Course end year", endDate.getYear()+1900);

                startActivityForResult(intent, COURSE_DETAIL_ACTIVITY_REQUEST_CODE);
            }
        });
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //add additional actions
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings: //these can be changed when I know what to put here also need to update menu resource
                //once actions are decided, propagate changes to all activities.
                return true;

            case R.id.action_favorite:
                // User chose the "Favorite" action, mark the current item
                // as a favorite...
                //onBackPressed();
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }

    @Override
    public boolean onSupportNavigateUp(){
        onBackPressed();
        return true;
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data); //the data being stored is all wrong


        if (requestCode == NEW_COURSE_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {

            java.util.Date startDate =  new Date(
                    data.getIntExtra(
                            NewCourseActivity.EXTRA_REPLY_START_DATE_YEAR, 0)-1900,
                    data.getIntExtra(
                            NewCourseActivity.EXTRA_REPLY_START_DATE_MONTH, 0) -1,
                    data.getIntExtra(NewCourseActivity.EXTRA_REPLY_START_DATE_DAY, 0));
            java.util.Date endDate =  new Date(
                    data.getIntExtra(
                            NewCourseActivity.EXTRA_REPLY_END_DATE_YEAR, 0)-1900,
                    data.getIntExtra(
                            NewCourseActivity.EXTRA_REPLY_END_DATE_MONTH, 0)-1,
                    data.getIntExtra(NewCourseActivity.EXTRA_REPLY_END_DATE_DAY, 0));
            java.sql.Date startDateSQL = new java.sql.Date(startDate.getTime());
            java.sql.Date endDateSQL = new java.sql.Date(endDate.getTime());


//            Course course = new Course(data.getStringExtra(NewCourseActivity.EXTRA_REPLY_NAME),
//                    startDateSQL,
//                    endDateSQL);
//            mSchedulerViewModel.insertCourse(course); Update this method here!

//            Log.i("Course name in MainActivity",course.getCourse()); //** Remove later
//            Log.i("Course start date in MainActivity",course.getStartDate().toString()); //** Remove later

        }

        if (requestCode == COURSE_DETAIL_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {

            java.util.Date startDate =  new Date(
                    data.getIntExtra(
                            CourseDetailsActivity.EXTRA_REPLY_START_DATE_YEAR, 0)-1900,
                    data.getIntExtra(
                            CourseDetailsActivity.EXTRA_REPLY_START_DATE_MONTH, 0) -1,
                    data.getIntExtra(CourseDetailsActivity.EXTRA_REPLY_START_DATE_DAY, 0));
            java.util.Date endDate =  new Date(
                    data.getIntExtra(
                            CourseDetailsActivity.EXTRA_REPLY_END_DATE_YEAR, 0)-1900,
                    data.getIntExtra(
                            CourseDetailsActivity.EXTRA_REPLY_END_DATE_MONTH, 0)-1,
                    data.getIntExtra(CourseDetailsActivity.EXTRA_REPLY_END_DATE_DAY, 0));
            java.sql.Date startDateSQL = new java.sql.Date(startDate.getTime());
            java.sql.Date endDateSQL = new java.sql.Date(endDate.getTime());
            String newName = data.getStringExtra(CourseDetailsActivity.EXTRA_REPLY_NAME);

//            Course course = new Course(data.getStringExtra(CourseDetailsActivity.EXTRA_REPLY_NAME),
//                    startDateSQL,
//                    endDateSQL);
            selectedCourse.setCourse(newName);
            selectedCourse.setStartDate(startDateSQL);
            selectedCourse.setStopDate(endDateSQL);
            mSchedulerViewModel.updateCourse(selectedCourse);

        }

        if (requestCode == COURSE_DETAIL_ACTIVITY_REQUEST_CODE && resultCode == RESULT_DELETE) {
            mSchedulerViewModel.deleteCourse(selectedCourse);
            Toast.makeText(
                    getApplicationContext(),
                    "Selected course deleted.",
                    Toast.LENGTH_LONG).show();
        }

        else {
            Toast.makeText(
                    getApplicationContext(),
                    "Course insertion or change canceled.",
                    Toast.LENGTH_LONG).show();
        }
    }


    public void setAlert(Date date){

        Intent notifyIntent = new Intent(this, MyReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast
                (this, 0, notifyIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarmManager = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis()+20000,pendingIntent);
    }

    public static void clearAllAlerts(){
        notificationManager.cancelAll();
    }

    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getString(R.string.channel_name);
            String description = getString(R.string.channel_description);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }



}


