package com.example.mwaldbauerscheduler.Activities;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.mwaldbauerscheduler.Entities.Course;
import com.example.mwaldbauerscheduler.MyReceiver;
import com.example.mwaldbauerscheduler.R;
import com.example.mwaldbauerscheduler.TermListActivity;
import com.example.mwaldbauerscheduler.ViewModel.SchedulerViewModel;

import java.util.Date;

import static com.example.mwaldbauerscheduler.Activities.HomeActivity.CHANNEL_ID;
import static com.example.mwaldbauerscheduler.Activities.HomeActivity.notificationManager;
import static com.example.mwaldbauerscheduler.Activities.HomeActivity.schedulerValidator;
import static com.example.mwaldbauerscheduler.Constants.COURSE_ID_KEY;
import static com.example.mwaldbauerscheduler.Constants.TERM_ID_KEY;

public class CourseEditorActivity extends AppCompatActivity {

    private SchedulerViewModel mViewModel;
    private EditText mEditNameView;
    private EditText mEditStartMonthView;
    private EditText mEditStartDayView;
    private EditText mEditStartYearView;
    private EditText mEditEndMonthView;
    private EditText mEditEndDayView;
    private EditText mEditEndYearView;
    private Spinner mEditCompletionStatusView;
    private EditText mEditMentorNameView;
    private EditText mEditMentorPhoneView;
    private EditText mEditMentorEmailView;
    private EditText mEditNoteView;
    private int termID;
    private int courseID;
    private boolean mNewCourse;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_editor);

        mEditNameView = findViewById(R.id.edit_course);
        mEditStartDayView = findViewById(R.id.course_edit_start_date_day);
        mEditStartMonthView = findViewById(R.id.course_edit_start_date_month);
        mEditStartYearView = findViewById(R.id.course_edit_start_date_year);
        mEditEndDayView = findViewById(R.id.course_edit_end_date_day);
        mEditEndMonthView = findViewById(R.id.course_edit_end_date_month);
        mEditEndYearView = findViewById(R.id.course_edit_end_date_year);
        mEditCompletionStatusView = findViewById(R.id.edit_completion_status);
        mEditMentorNameView = findViewById(R.id.edit_mentor_name);
        mEditMentorPhoneView = findViewById(R.id.edit_mentor_phone);
        mEditMentorEmailView = findViewById(R.id.edit_mentor_email);
        mEditNoteView = findViewById(R.id.edit_note2);


        final Button button = findViewById(R.id.button_save);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                String courseName = mEditNameView.getText().toString();
                String startDay = mEditStartDayView.getText().toString();
                String startMonth = mEditStartMonthView.getText().toString();
                String startYear = mEditStartYearView.getText().toString();
                String endDay = mEditEndDayView.getText().toString();
                String endMonth = mEditEndMonthView.getText().toString();
                String endYear = mEditEndYearView.getText().toString();
                String completionStatus = mEditCompletionStatusView.getSelectedItem().toString();
                String mentorName = mEditMentorNameView.getText().toString();
                String mentorPhone = mEditMentorPhoneView.getText().toString();
                String mentorEmail = mEditMentorEmailView.getText().toString();
                String notes = mEditNoteView.getText().toString();

                String resultStatement = "Valid";

                //Check Course Name
                if (TextUtils.isEmpty(mEditNameView.getText())) {
                    resultStatement = "Please enter a Course name.";}

                boolean dateResult = schedulerValidator.checkDate(startDay, startMonth, startYear);

                if (dateResult == false) {
                    resultStatement = "Course start date is invalid.";
                }

                dateResult = schedulerValidator.checkDate(endDay, endMonth, endYear);

                if (dateResult == false) {
                    resultStatement = "Course end date is invalid.";
                }

                Log.i("Course validity statement", resultStatement); //** Remove later

                if (resultStatement != "Valid") {

                    Toast.makeText(
                            getApplicationContext(),
                            resultStatement,
                            Toast.LENGTH_LONG).show();

                } else {

                    Date startDate = new Date(Integer.parseInt(startYear)-1900,
                            Integer.parseInt(startMonth)-1,
                            Integer.parseInt(startDay));

                    Date endDate = new Date(Integer.parseInt(endYear)-1900,
                            Integer.parseInt(endMonth)-1,
                            Integer.parseInt(endDay));

                    mViewModel.saveCourse(courseName, startDate, endDate, completionStatus,
                            termID, mentorName, mentorPhone, mentorEmail, notes);
                    finish();
                }
            }
        });

        final Button courseListButton = findViewById(R.id.button_addAssessment);
        courseListButton.setOnClickListener(v -> {

            if (mNewCourse == true) {
                Toast.makeText(
                        getApplicationContext(),
                        "You must save a course before you can add assessments to it.",
                        Toast.LENGTH_LONG).show();
            } else {
                Intent intent = new Intent(this, AssessmentListActivity.class);
                intent.putExtra(COURSE_ID_KEY, courseID);
                startActivity(intent);
            }
        });

        final Button shareNotesButton = findViewById(R.id.button_share_notes2);
        shareNotesButton.setOnClickListener(v -> {

            if (TextUtils.isEmpty(mEditNoteView.getText())) {
                Toast.makeText(
                        getApplicationContext(),
                        "Please write a note first.",
                        Toast.LENGTH_LONG).show();
            } else {
                String note = mEditNoteView.getText().toString();
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, note);
                sendIntent.setType("text/plain");

                Intent shareIntent = Intent.createChooser(sendIntent, null);
                startActivity(shareIntent);

            }
        });

        final Button deleteButton = findViewById(R.id.deleteButton);
        deleteButton.setOnClickListener(v -> {

            if (mNewCourse == true) { // see if we have a course
                Toast.makeText(
                        getApplicationContext(),
                        "You must save a course before you can delete it.",
                        Toast.LENGTH_LONG).show();
            } else {
                Course course = mViewModel.mLiveCourse.getValue();

                if (mViewModel.getAllAssessmentsByCourseID(course.getCourseID()).isEmpty()) { //check that course has no assessments attached
                    mViewModel.deleteCourse(course);
                    Toast.makeText(
                            getApplicationContext(),
                            "Selected course deleted.",
                            Toast.LENGTH_LONG).show();
                    finish();
                }

                else {
                    Toast.makeText(
                            getApplicationContext(),
                            "Selected course cannot be deleted. Delete its assessments first.",
                            Toast.LENGTH_LONG).show();
                }

            }
        });

        final Button deleteAlarmsButton = findViewById(R.id.deleteAlarmsButton);
        deleteAlarmsButton.setOnClickListener(v -> {
            notificationManager.cancelAll();;
            Toast.makeText(
                    getApplicationContext(),
                    "All Alarms Deleted.",
                    Toast.LENGTH_LONG).show();
        });

        final Button alarmCourseStartButton = findViewById(R.id.alarmCourseStart);
        alarmCourseStartButton.setOnClickListener(v -> {

            String startDay = mEditStartDayView.getText().toString();
            String startMonth = mEditStartMonthView.getText().toString();
            String startYear = mEditStartYearView.getText().toString();

            if (schedulerValidator.checkDate(startDay, startMonth, startYear) == false) {
                return;
            }

            setAlert(new Date(Integer.parseInt(startYear)-1900,
                    Integer.parseInt(startMonth)-1,
                    Integer.parseInt(startDay)));

            Toast.makeText(
                    getApplicationContext(),
                    "Alarm set.",
                    Toast.LENGTH_LONG).show();
        });

        final Button alarmCourseEndButton = findViewById(R.id.alarmCourseEnd);
        alarmCourseEndButton.setOnClickListener(v -> {

            String endDay = mEditEndDayView.getText().toString();
            String endMonth = mEditEndMonthView.getText().toString();
            String endYear = mEditEndYearView.getText().toString();

            if (schedulerValidator.checkDate(endDay, endMonth, endYear) == false) {
                return;
            }

            setAlert(new Date(Integer.parseInt(endYear)-1900,
                    Integer.parseInt(endMonth)-1,
                    Integer.parseInt(endDay)));
            Toast.makeText(
                    getApplicationContext(),
                    "Alarm set.",
                    Toast.LENGTH_LONG).show();
        });

        initViewModel();
    }

    private void initViewModel() {

        mViewModel = ViewModelProviders.of(this)
                .get(SchedulerViewModel.class);

        mViewModel.mLiveCourse.observe(this, new Observer<Course>() { // this observer isn't called after postValue is called
            @Override
            public void onChanged(Course course) {

                if (course != null) {//set current data if course already exists

                    Date startDate = course.getStartDate();
                    Date endDate = course.getStopDate();
                    String startDay = String.valueOf(startDate.getDate());
                    String startMonth = String.valueOf(startDate.getMonth() + 1);
                    String startYear = String.valueOf(startDate.getYear() + 1900);
                    String endDay = String.valueOf(endDate.getDate());
                    String endMonth = String.valueOf(endDate.getMonth() + 1);
                    String endYear = String.valueOf(endDate.getYear() + 1900);

                    String completionStatus = course.getCompletionStatus();
                    int completionOption = 0;
                    switch(completionStatus) {
                        case "Plan To Take":
                            completionOption = 0;
                            break;
                        case "In Progress":
                            completionOption = 1;
                            break;
                        case "Completed":
                            completionOption = 2;
                            break;
                        case "Dropped":
                            completionOption = 3;
                    }

                    mEditNameView.setText(course.getCourse());
                    mEditStartDayView.setText(startDay);
                    mEditStartMonthView.setText(startMonth);
                    mEditStartYearView.setText(startYear);
                    mEditEndDayView.setText(endDay);
                    mEditEndMonthView.setText(endMonth);
                    mEditEndYearView.setText(endYear);
                    mEditCompletionStatusView.setSelection(completionOption);
                    mEditMentorNameView.setText(course.getMentor());
                    mEditMentorPhoneView.setText(course.getMentorPhone());
                    mEditMentorEmailView.setText(course.getMentorEmail());
                    mEditNoteView.setText(course.getNotes());

                }
            }
        });

        Bundle extras = getIntent().getExtras();
        if (extras == null) { //This will change after we allow editing
            setTitle("New Course");
            termID = extras.getInt(TERM_ID_KEY);
            mNewCourse = true;
        } else {
            setTitle("Edit Course");
            termID = extras.getInt(TERM_ID_KEY);
            courseID = extras.getInt(COURSE_ID_KEY);
            mViewModel.loadCourseData(courseID);
        }
    }

    //add the code block below to each activity along with the include reference in the XML
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
    public void onBackPressed() {
        Toast.makeText(
                getApplicationContext(),
                "Insertion or change canceled.",
                Toast.LENGTH_LONG).show();
        finish();
    }

    @Override
    public boolean onSupportNavigateUp(){
        onBackPressed();
        return true;
    }

    public void setAlert(Date date){
        long millis = date.getTime();
        //For reference for adding alters again with this awful Date system.
        //System.currentTimeMillis() or millis += 83940000; //11:19PM

        Intent notifyIntent = new Intent(this, MyReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast
                (this, 0, notifyIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarmManager = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, millis, pendingIntent);
    }
}
