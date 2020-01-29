package com.example.mwaldbauerscheduler.Activities;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.mwaldbauerscheduler.Entities.Course;
import com.example.mwaldbauerscheduler.Entities.Term;
import com.example.mwaldbauerscheduler.MyReceiver;
import com.example.mwaldbauerscheduler.R;
import com.example.mwaldbauerscheduler.TermListActivity;
import com.example.mwaldbauerscheduler.ViewModel.SchedulerViewModel;

import java.util.ArrayList;
import java.util.Date;

import static com.example.mwaldbauerscheduler.Activities.HomeActivity.CHANNEL_ID;
import static com.example.mwaldbauerscheduler.Activities.HomeActivity.notificationManager;
import static com.example.mwaldbauerscheduler.Activities.HomeActivity.schedulerValidator;
import static com.example.mwaldbauerscheduler.Constants.EDITING_KEY;
import static com.example.mwaldbauerscheduler.Constants.TERM_ID_KEY;

public class TermEditorActivity extends AppCompatActivity {

    private SchedulerViewModel mViewModel;
    private EditText mEditNameView;
    private EditText mEditStartMonthView;
    private EditText mEditStartDayView;
    private EditText mEditStartYearView;
    private EditText mEditEndMonthView;
    private EditText mEditEndDayView;
    private EditText mEditEndYearView;
    private boolean mNewTerm, mIsEditing;
    private int termID;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_details);

        mEditNameView = findViewById(R.id.edit_term);
        mEditStartDayView = findViewById(R.id.edit_start_date_day);
        mEditStartMonthView = findViewById(R.id.edit_start_date_month);
        mEditStartYearView = findViewById(R.id.edit_start_date_year);
        mEditEndDayView = findViewById(R.id.edit_end_date_day);
        mEditEndMonthView = findViewById(R.id.edit_end_date_month);
        mEditEndYearView = findViewById(R.id.edit_end_date_year);

        if (savedInstanceState != null) {
            mIsEditing = savedInstanceState.getBoolean(EDITING_KEY);
        }

        final Button button = findViewById(R.id.button_save);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                String termName = mEditNameView.getText().toString();
                String startDay = mEditStartDayView.getText().toString();
                String startMonth = mEditStartMonthView.getText().toString();
                String startYear = mEditStartYearView.getText().toString();
                String endDay = mEditEndDayView.getText().toString();
                String endMonth = mEditEndMonthView.getText().toString();
                String endYear = mEditEndYearView.getText().toString();
                String resultStatement = "Valid";

                //Check Term Name
                if (TextUtils.isEmpty(mEditNameView.getText())) {
                        resultStatement = "Please enter a Term name.";}

                boolean dateResult = schedulerValidator.checkDate(startDay, startMonth, startYear);

                if (dateResult == false) {
                    resultStatement = "Term start date is invalid.";
                }

                dateResult = schedulerValidator.checkDate(endDay, endMonth, endYear);

                if (dateResult == false) {
                    resultStatement = "Term end date is invalid.";
                }

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

                    mViewModel.saveTerm(termName, startDate, endDate);
                    finish();
                }
            }
        });

        final Button courseListButton = findViewById(R.id.button_addCourse);
        courseListButton.setOnClickListener(v -> {

            if (mNewTerm == true) {
                Toast.makeText(
                        getApplicationContext(),
                        "You must save a term before you can add courses to it.",
                        Toast.LENGTH_LONG).show();
            } else {
                Intent intent = new Intent(this, CourseListActivity.class);
                intent.putExtra(TERM_ID_KEY, termID);
                startActivity(intent);
            }
        });

        final Button deleteButton = findViewById(R.id.deleteButton);
        deleteButton.setOnClickListener(v -> {

            if (mNewTerm == true) { // see if we have a term
                Toast.makeText(
                        getApplicationContext(),
                        "You must save a term before you can delete it.",
                        Toast.LENGTH_LONG).show();
            } else {
                Term term = mViewModel.mLiveTerm.getValue();

                ArrayList<Course> mArrayList = (ArrayList) mViewModel.getAllCoursesByTermID(term.getTermID());
                if (mArrayList.isEmpty()) {
                    mViewModel.deleteTerm(term);
                    Toast.makeText(
                            getApplicationContext(),
                            "Selected term deleted.",
                            Toast.LENGTH_LONG).show();
                    finish();
                }

                else {
                    Toast.makeText(
                            getApplicationContext(),
                            "Selected term cannot be deleted. Delete its courses first.",
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

        final Button alarmTermStartButton = findViewById(R.id.alarmTermStart);
        alarmTermStartButton.setOnClickListener(v -> {

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

        final Button alarmTermEndButton = findViewById(R.id.alarmTermEnd);
        alarmTermEndButton.setOnClickListener(v -> {

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

        mViewModel.mLiveTerm.observe(this, new Observer<Term>() { // this observer isn't called after postValue is called
            @Override
            public void onChanged(Term term) {

                if (term != null && mIsEditing == false) {//set current data if term already exists

                    Date startDate = term.getStartDate();
                    Date endDate = term.getStopDate();
                    String startDay = String.valueOf(startDate.getDate());
                    String startMonth = String.valueOf(startDate.getMonth() + 1);
                    String startYear = String.valueOf(startDate.getYear() + 1900);
                    String endDay = String.valueOf(endDate.getDate());
                    String endMonth = String.valueOf(endDate.getMonth() + 1);
                    String endYear = String.valueOf(endDate.getYear() + 1900);

                    mEditNameView.setText(term.getTerm());
                    mEditStartDayView.setText(startDay);
                    mEditStartMonthView.setText(startMonth);
                    mEditStartYearView.setText(startYear);
                    mEditEndDayView.setText(endDay);
                    mEditEndMonthView.setText(endMonth);
                    mEditEndYearView.setText(endYear);
                }
            }
        });

        Bundle extras = getIntent().getExtras();
        if (extras == null) {
            setTitle("New Term");
            mNewTerm = true;
        } else {
            setTitle("Edit Term");
            termID = extras.getInt(TERM_ID_KEY);
            mViewModel.loadTermData(termID);
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

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putBoolean(EDITING_KEY, true);
        super.onSaveInstanceState(outState);
    }
}


