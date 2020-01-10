package com.example.mwaldbauerscheduler.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;

import com.example.mwaldbauerscheduler.R;

public class CourseDetailsActivity extends AppCompatActivity {

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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_details);
    }
}
