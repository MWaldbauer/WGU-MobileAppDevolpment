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
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.mwaldbauerscheduler.Entities.Assessment;
import com.example.mwaldbauerscheduler.Entities.Course;
import com.example.mwaldbauerscheduler.MyReceiver;
import com.example.mwaldbauerscheduler.R;
import com.example.mwaldbauerscheduler.TermListActivity;
import com.example.mwaldbauerscheduler.ViewModel.SchedulerViewModel;

import java.util.Date;

import static com.example.mwaldbauerscheduler.Activities.HomeActivity.CHANNEL_ID;
import static com.example.mwaldbauerscheduler.Activities.HomeActivity.notificationManager;
import static com.example.mwaldbauerscheduler.Activities.HomeActivity.schedulerValidator;
import static com.example.mwaldbauerscheduler.Constants.ASSESSMENT_ID_KEY;
import static com.example.mwaldbauerscheduler.Constants.COURSE_ID_KEY;

public class AssessmentEditorActivity extends AppCompatActivity {

    private SchedulerViewModel mViewModel;
    private EditText mEditNameView;
    private EditText mEditGoalMonthView;
    private EditText mEditGoalDayView;
    private EditText mEditGoalYearView;
    private TextView mEditEndMonthView;
    private TextView mEditEndDayView;
    private TextView mEditEndYearView;
    private EditText mEditNoteView;
    private Spinner mEditCompletionStatusView;
    private EditText mEditMentorNameView;
    private EditText mEditMentorPhoneView;
    private EditText mEditMentorEmailView;
    private int courseID;
    private int assessmentID;
    private boolean mNewAssessment;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assessment_editor);

        mEditNameView = findViewById(R.id.edit_assessment);
        mEditGoalDayView = findViewById(R.id.assessment_edit_goal_date_day);
        mEditGoalMonthView = findViewById(R.id.assessment_edit_goal_date_month);
        mEditGoalYearView = findViewById(R.id.assessment_edit_goal_date_year);
        mEditEndDayView = findViewById(R.id.assessment_edit_end_date_day);
        mEditEndMonthView = findViewById(R.id.assessment_edit_end_date_month);
        mEditEndYearView = findViewById(R.id.assessment_edit_end_date_year);

        final Button button = findViewById(R.id.button_save);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                String assessmentName = mEditNameView.getText().toString();
                String goalDay = mEditGoalDayView.getText().toString();
                String goalMonth = mEditGoalMonthView.getText().toString();
                String goalYear = mEditGoalYearView.getText().toString();
                String endDay = mEditEndDayView.getText().toString();
                String endMonth = mEditEndMonthView.getText().toString();
                String endYear = mEditEndYearView.getText().toString();

                String resultStatement = "Valid";

                //Check Assessment Name
                if (TextUtils.isEmpty(mEditNameView.getText())) {
                    resultStatement = "Please enter an Assessment name.";}

                boolean dateResult = schedulerValidator.checkDate(goalDay, goalMonth, goalYear);

                if (dateResult == false) {
                    resultStatement = "Assessment goal date is invalid.";
                }

                dateResult = schedulerValidator.checkDate(endDay, endMonth, endYear);

                if (dateResult == false) {
                    resultStatement = "Assessment end date is invalid.";
                }

                Log.i("Assessment validity statement", resultStatement); //** Remove later

                if (resultStatement != "Valid") {

                    Toast.makeText(
                            getApplicationContext(),
                            resultStatement,
                            Toast.LENGTH_LONG).show();

                } else {

                    Date goalDate = new Date(Integer.parseInt(goalYear)-1900,
                            Integer.parseInt(goalMonth)-1,
                            Integer.parseInt(goalDay));

                    Date endDate = new Date(Integer.parseInt(endYear)-1900,
                            Integer.parseInt(endMonth)-1,
                            Integer.parseInt(endDay));

                    mViewModel.saveAssessment(assessmentName, goalDate, endDate, null, courseID);
                    finish();
                }
            }
        });

        final Button deleteButton = findViewById(R.id.deleteButton);
        deleteButton.setOnClickListener(v -> {

            if (mNewAssessment == true) { // see if we have a assessment
                Toast.makeText(
                        getApplicationContext(),
                        "You must save an assessment before you can delete it.",
                        Toast.LENGTH_LONG).show();
            } else {
                Assessment assessment = mViewModel.mLiveAssessment.getValue();

                    mViewModel.deleteAssessment(assessment);
                    Toast.makeText(
                            getApplicationContext(),
                            "Selected assessment deleted.",
                            Toast.LENGTH_LONG).show();
                    finish();
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

        final Button alarmAssessmentGoalButton = findViewById(R.id.alarmAssessmentGoal);
        alarmAssessmentGoalButton.setOnClickListener(v -> {

            String goalDay = mEditGoalDayView.getText().toString();
            String goalMonth = mEditGoalMonthView.getText().toString();
            String goalYear = mEditGoalYearView.getText().toString();

            if (schedulerValidator.checkDate(goalDay, goalMonth, goalYear) == false) {
                return;
            }

            setAlert(new Date(Integer.parseInt(goalYear)-1900,
                    Integer.parseInt(goalMonth)-1,
                    Integer.parseInt(goalDay)));

            Toast.makeText(
                    getApplicationContext(),
                    "Alarm set.",
                    Toast.LENGTH_LONG).show();
        });

        final Button alarmAssessmentEndButton = findViewById(R.id.alarmAssessmentEnd);
        alarmAssessmentEndButton.setOnClickListener(v -> {

            String endDay = mEditEndDayView.getText().toString();
            String endMonth = mEditEndMonthView.getText().toString();
            String endYear = mEditEndYearView.getText().toString();

            if (schedulerValidator.checkDate(endDay, endMonth, endYear) == false) {
                return;
            }

            setAlert(new Date(Integer.parseInt(endDay),
                    Integer.parseInt(endMonth),
                    Integer.parseInt(endYear)));
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

        mViewModel.mLiveAssessment.observe(this, new Observer<Assessment>() { // this observer isn't called after postValue is called
            @Override
            public void onChanged(Assessment assessment) {

                if (assessment != null) {//set current data if assessment already exists

                    Date goalDate = assessment.getGoalDate();
                    Date endDate = assessment.getStopDate();
                    String goalDay = String.valueOf(goalDate.getDate());
                    String goalMonth = String.valueOf(goalDate.getMonth() + 1);
                    String goalYear = String.valueOf(goalDate.getYear() + 1900);
                    String endDay = String.valueOf(endDate.getDate());
                    String endMonth = String.valueOf(endDate.getMonth() + 1);
                    String endYear = String.valueOf(endDate.getYear() + 1900);
                    courseID = assessment.getCourseID();

                    mEditNameView.setText(assessment.getAssessment());
                    mEditGoalDayView.setText(goalDay);
                    mEditGoalMonthView.setText(goalMonth);
                    mEditGoalYearView.setText(goalYear);
                    mEditEndDayView.setText(endDay);
                    mEditEndMonthView.setText(endMonth);
                    mEditEndYearView.setText(endYear);
                }
            }
        });

        Bundle extras = getIntent().getExtras();
        if (extras.containsKey(ASSESSMENT_ID_KEY) == true) {
            setTitle("Edit Assessment");
            assessmentID = extras.getInt(ASSESSMENT_ID_KEY);
            courseID = extras.getInt(COURSE_ID_KEY);
            mViewModel.loadAssessmentData(assessmentID);
        } else { //This will change after we allow editing
            setTitle("New Assessment");
            courseID = extras.getInt(COURSE_ID_KEY);
            mNewAssessment = true;

            Course course = mViewModel.getCourseByID(courseID);

            Date endDate = course.getStopDate();
            String endDay = String.valueOf(endDate.getDate());
            String endMonth = String.valueOf(endDate.getMonth() + 1);
            String endYear = String.valueOf(endDate.getYear() + 1900);
            mEditEndDayView.setText(endDay);
            mEditEndMonthView.setText(endMonth);
            mEditEndYearView.setText(endYear);
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
