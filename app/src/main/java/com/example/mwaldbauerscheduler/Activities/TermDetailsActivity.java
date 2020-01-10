package com.example.mwaldbauerscheduler.Activities;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mwaldbauerscheduler.MainActivity;
import com.example.mwaldbauerscheduler.MyReceiver;
import com.example.mwaldbauerscheduler.R;

import java.util.Date;

import static com.example.mwaldbauerscheduler.MainActivity.RESULT_DELETE;
import static com.example.mwaldbauerscheduler.MainActivity.notificationManager;
import static com.example.mwaldbauerscheduler.MainActivity.schedulerValidator;

public class TermDetailsActivity extends AppCompatActivity {

    //public static final int NEW_DATEPICKER_ACTIVITY_REQUEST_CODE = 2;
    public static final String EXTRA_REPLY_NAME = "com.example.android.name.REPLY";
    public static final String EXTRA_REPLY_START_DATE_MONTH = "com.example.android.startdatemonth.REPLY";
    public static final String EXTRA_REPLY_START_DATE_DAY = "com.example.android.startdateday.REPLY";
    public static final String EXTRA_REPLY_START_DATE_YEAR = "com.example.android.startdateyear.REPLY";
    public static final String EXTRA_REPLY_END_DATE_MONTH = "com.example.android.enddatemonth.REPLY";
    public static final String EXTRA_REPLY_END_DATE_DAY = "com.example.android.enddateday.REPLY";
    public static final String EXTRA_REPLY_END_DATE_YEAR = "com.example.android.enddateyear.REPLY";

    private EditText mEditNameView;
    private EditText mEditStartMonthView;
    private EditText mEditStartDayView;
    private EditText mEditStartYearView;
    private EditText mEditEndMonthView;
    private EditText mEditEndDayView;
    private EditText mEditEndYearView;


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
        mEditNameView.setText(getIntent().getStringExtra("Term name"));
        mEditStartDayView.setText(String.valueOf(getIntent().getIntExtra("Term start day", 0)));
        mEditStartMonthView.setText(String.valueOf(getIntent().getIntExtra("Term start month", 0)));
        mEditStartYearView.setText(String.valueOf(getIntent().getIntExtra("Term start year", 0)));
        mEditEndDayView.setText(String.valueOf(getIntent().getIntExtra("Term end day", 0)));
        mEditEndMonthView.setText(String.valueOf(getIntent().getIntExtra("Term end month", 0)));
        mEditEndYearView.setText(String.valueOf(getIntent().getIntExtra("Term end year", 0)));


        final Button button = findViewById(R.id.button_save);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) { //this needs to be modified to read Term Name AND DATE listed on the page
                Intent replyIntent = new Intent();
                //need a better checker here for valid input

                String term = mEditNameView.getText().toString();
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

                Log.i("Term validity statement", resultStatement); //** Remove later

                if (resultStatement != "Valid") {

                    Toast.makeText(
                            getApplicationContext(),
                            resultStatement,
                            Toast.LENGTH_LONG).show();

                } else {

                    replyIntent.putExtra(EXTRA_REPLY_NAME, term);
                    replyIntent.putExtra(EXTRA_REPLY_START_DATE_DAY, Integer.parseInt(startDay));
                    replyIntent.putExtra(EXTRA_REPLY_START_DATE_MONTH, Integer.parseInt(startMonth));
                    replyIntent.putExtra(EXTRA_REPLY_START_DATE_YEAR, Integer.parseInt(startYear));
                    replyIntent.putExtra(EXTRA_REPLY_END_DATE_DAY, Integer.parseInt(endDay));
                    replyIntent.putExtra(EXTRA_REPLY_END_DATE_MONTH, Integer.parseInt(endMonth));
                    replyIntent.putExtra(EXTRA_REPLY_END_DATE_YEAR, Integer.parseInt(endYear));
                    setResult(RESULT_OK, replyIntent);
                    finish();
                }
//                finish();
            }
        });

        final Button deleteButton = findViewById(R.id.deleteButton);
        deleteButton.setOnClickListener(v -> {
            Intent replyIntent = new Intent();
            setResult(RESULT_DELETE, replyIntent);
            finish();
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

            boolean dateResult;

            if (schedulerValidator.checkDate(startDay, startMonth, startYear) == false) {
                return;
            }

            setAlert(new Date(Integer.parseInt(startDay),
                    Integer.parseInt(startMonth),
                    Integer.parseInt(startYear)));

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

            boolean dateResult;

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
        //setAlert Button (change to Button)

        //setAlert(new Date(12, 31, 2019));


    }

    //add the code block below to each activity along with the include reference in the XML

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //add additional actions
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp(){
        onBackPressed();
        return true;
    }

    public void setAlert(Date date){

        long millis = date.getTime();
        //millis += 83940000; //11:19PM
        Intent notifyIntent = new Intent(this, MyReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast
                (this, 0, notifyIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarmManager = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, millis,pendingIntent);
    }

    //System.currentTimeMillis()

}




    //    public void showDatePickerDialog(View v) {
//        Intent intent = new Intent();
//        DialogFragment newFragment = new DatePickerFragment();
//        startActivity(newFragment, intent, NEW_DATEPICKER_ACTIVITY_REQUEST_CODE);
//
//
//        newFragment.show(getSupportFragmentManager(), "datePicker");
//        newFragment.onActivityResult();
//    }


    //need a listener for DatePickerFragment that sets date on this page. Fragment needs an interface.
