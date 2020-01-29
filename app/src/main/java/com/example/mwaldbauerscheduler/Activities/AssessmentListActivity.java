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

import com.example.mwaldbauerscheduler.Entities.Assessment;
import com.example.mwaldbauerscheduler.MyReceiver;
import com.example.mwaldbauerscheduler.R;
import com.example.mwaldbauerscheduler.TermListActivity;
import com.example.mwaldbauerscheduler.View.AssessmentListAdapter;
import com.example.mwaldbauerscheduler.View.CourseListAdapter;
import com.example.mwaldbauerscheduler.ViewModel.SchedulerViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Date;
import java.util.List;

import static com.example.mwaldbauerscheduler.Constants.ASSESSMENT_ID_KEY;
import static com.example.mwaldbauerscheduler.Constants.COURSE_ID_KEY;

public class AssessmentListActivity extends AppCompatActivity {

    public static final String CHANNEL_ID = "MWScheduler";
    public static SchedulerViewModel mSchedulerViewModel;
    public Assessment selectedCourse = null;
    public static NotificationManagerCompat notificationManager;
    public static boolean hasCourse;
    private int courseID;
    private Assessment selectedAssessment;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        createNotificationChannel();
        setContentView(R.layout.activity_assessmentlist);
        notificationManager = NotificationManagerCompat.from(this);

        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        final AssessmentListAdapter adapter = new AssessmentListAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        checkBundle();

        mSchedulerViewModel = new ViewModelProvider(this).get(SchedulerViewModel.class);

        if (hasCourse == false) {

            //if we have no courses, our observer is for all assessments.
            mSchedulerViewModel.getAllAssessments().observe(this, new Observer<List<Assessment>>() {
                @Override
                public void onChanged(@Nullable final List<Assessment> assessments) {
                    adapter.setAssessments(assessments);
                }
            });
        } else {
            //display Assessments that are part of the course

            mSchedulerViewModel.getAllAssessmentsByCourseIDLive(courseID).observe(this, new Observer<List<Assessment>>() {
                @Override
                public void onChanged(@Nullable final List<Assessment> assessments) {
                    adapter.setAssessments(assessments);
                }
            });
        }

        //new assessment fab
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (hasCourse == false) {
                    Toast.makeText(
                            getApplicationContext(),
                            "Assessments can only be added from the course details screen.",
                            Toast.LENGTH_LONG).show();
                } else {
                    Intent intent = new Intent(AssessmentListActivity.this, AssessmentEditorActivity.class);
                    intent.putExtra(COURSE_ID_KEY, courseID);
                    startActivity(intent);
                }
            }
        });

        //Details and edit assessment fab
        FloatingActionButton fab2 = findViewById(R.id.fab2);
        fab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AssessmentListActivity.this, AssessmentEditorActivity.class);
                selectedAssessment = adapter.getSelectedAssessment();

                if (selectedAssessment == null){
                    Toast.makeText(
                            getApplicationContext(),
                            "Please select a assessment first.",
                            Toast.LENGTH_LONG).show();
                    return;
                }

                intent.putExtra(COURSE_ID_KEY, selectedAssessment.getCourseID());
                intent.putExtra(ASSESSMENT_ID_KEY, selectedAssessment.getAssessmentID()); //this'll be Assessment
                startActivity(intent);
            }
        });
    }

    private void checkBundle() {

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            hasCourse = true;
            courseID = extras.getInt(COURSE_ID_KEY);

        } else {
            hasCourse = false;
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
                intent = new Intent(this, TermListActivity.class);
                startActivity(intent); //this probably can be startActivity now!
                return true;

            case R.id.action_settings: //these can be changed when I know what to put here also need to update menu resource
                intent = new Intent(this, AssessmentListActivity.class);
                startActivity(intent);

                //once actions are decided, propagate changes to all activities.
                return true;

            case R.id.action_favorite:
                // User chose the "Favorite" action, mark the current item
                intent = new Intent(this, CourseListActivity.class);
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
