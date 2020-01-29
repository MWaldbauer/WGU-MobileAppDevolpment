package com.example.mwaldbauerscheduler.Activities;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationManagerCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mwaldbauerscheduler.Entities.Course;
import com.example.mwaldbauerscheduler.MyReceiver;
import com.example.mwaldbauerscheduler.MyValidator;
import com.example.mwaldbauerscheduler.R;
import com.example.mwaldbauerscheduler.TermListActivity;
import com.example.mwaldbauerscheduler.View.CourseListAdapter;
import com.example.mwaldbauerscheduler.ViewModel.SchedulerViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Date;
import java.util.List;

import static com.example.mwaldbauerscheduler.Constants.COURSE_ID_KEY;
import static com.example.mwaldbauerscheduler.Constants.TERM_ID_KEY;

public class CourseListActivity extends AppCompatActivity {

    public static final String CHANNEL_ID = "MWScheduler";
    public static SchedulerViewModel mSchedulerViewModel;
    public Course selectedCourse = null;
    public static NotificationManagerCompat notificationManager;
    public static boolean hasTerm;
    private int termID;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        createNotificationChannel();
        setContentView(R.layout.activity_courselist);
        notificationManager = NotificationManagerCompat.from(this);

        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        final CourseListAdapter adapter = new CourseListAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        checkBundle();

        mSchedulerViewModel = new ViewModelProvider(this).get(SchedulerViewModel.class);

        if (hasTerm == false) {

            //if we have no terms, our observer is for all courses.
            mSchedulerViewModel.getAllCourses().observe(this, new Observer<List<Course>>() {
                @Override
                public void onChanged(@Nullable final List<Course> courses) {
                    adapter.setCourses(courses);
                }
            });
        } else {
            //display on Courses that are part of the term

            mSchedulerViewModel.getAllCoursesByTermIDLive(termID).observe(this, new Observer<List<Course>>() {
                @Override
                public void onChanged(@Nullable final List<Course> courses) {
                    adapter.setCourses(courses);
                }
            });
        }

        //new course fab //also need to add a fab for adding selected course to term. Paperclip icon maybe?
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (hasTerm == false) {
                    Toast.makeText(
                            getApplicationContext(),
                            "Courses can only be added from the term details screen.",
                            Toast.LENGTH_LONG).show();
                } else {
                    Intent intent = new Intent(CourseListActivity.this, CourseEditorActivity.class);
                    intent.putExtra(TERM_ID_KEY, termID);
                    startActivity(intent);
                }
            }
        });

        //Details and edit course fab
        FloatingActionButton fab2 = findViewById(R.id.fab2);
        fab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CourseListActivity.this, CourseEditorActivity.class);
                selectedCourse = adapter.getSelectedCourse();

                if (selectedCourse == null){
                    Toast.makeText(
                            getApplicationContext(),
                            "Please select a course first.",
                            Toast.LENGTH_LONG).show();
                    return;

                }

                intent.putExtra(COURSE_ID_KEY, selectedCourse.getCourseID()); //this'll be Course
                intent.putExtra(TERM_ID_KEY, selectedCourse.getTermID());
                startActivity(intent);
            }
        });
    }

    private void checkBundle() {

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            hasTerm = true;
            termID = extras.getInt(TERM_ID_KEY);

        } else {
          hasTerm = false;
        }

    }

    public void onResume() {
        super.onResume();
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent;
        switch (item.getItemId()) {
            case R.id.action_terms: //Need to update icons
                intent = new Intent(CourseListActivity.this, TermListActivity.class);
                startActivity(intent); //this probably can be startActivity now!
                return true;

            case R.id.action_settings: //these can be changed when I know what to put here also need to update menu resource
                intent = new Intent(CourseListActivity.this, AssessmentListActivity.class);
                startActivity(intent);

                //once actions are decided, propagate changes to all activities.
                return true;

            case R.id.action_favorite:
                // User chose the "Favorite" action, mark the current item
                intent = new Intent(CourseListActivity.this, CourseListActivity.class);
                startActivity(intent);
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

    public void setAlert(Date date){ // Do I need this?
        Intent notifyIntent = new Intent(this,MyReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast
                (this, 0, notifyIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarmManager = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis()+20000,pendingIntent);
    }

    public static void clearAllAlerts(){
        notificationManager.cancelAll();
    } // Do I need this?

    private void createNotificationChannel() { // Do I need this?
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
