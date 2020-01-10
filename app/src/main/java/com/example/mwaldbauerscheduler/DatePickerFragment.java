package com.example.mwaldbauerscheduler;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.DatePicker;

import androidx.fragment.app.DialogFragment;

import java.sql.Time;
import java.time.Instant;
import java.util.Date;
import java.time.LocalDate;
import java.util.Calendar;

public class DatePickerFragment extends DialogFragment
        implements DatePickerDialog.OnDateSetListener {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current date as the default date in the picker
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        // Create a new instance of DatePickerDialog and return it
        return new DatePickerDialog(getActivity(), this, year, month, day);
    }

    public void onDateSet(DatePicker view, int year, int month, int day) {
//        java.util.Date testDate = new Date(System.currentTimeMillis());
//        testDate.setYear(2019);
//        public java.sql.Date newDate = new java.sql.Date(testDate.setYear(2019));

        Calendar cal = Calendar.getInstance();

        cal.set( cal.YEAR, year);
        cal.set( cal.MONTH, month+1);
        cal.set( cal.DATE, day );

        cal.set( cal.HOUR_OF_DAY, 0 );
        cal.set( cal.MINUTE, 0 );
        cal.set( cal.SECOND, 0 );
        cal.set( cal.MILLISECOND, 0 );
//        LocalDate localDate = LocalDate.of(year, month + 1, day);
//        Instant instantDate = Instant.from(localDate);
        Date date = new java.sql.Date(cal.getTime().getTime());

        Intent intent = new Intent();
        intent.putExtra("date", date);
        startActivity(intent);

//        return date;
    }
}

//
//import android.app.DatePickerDialog;
//import android.app.TimePickerDialog;
//import android.os.Bundle;
//import android.view.Menu;
//import android.view.MenuItem;
//import android.view.View;
//import android.widget.Button;
//import android.widget.DatePicker;
//import android.widget.EditText;
//import android.widget.TimePicker;
//
//import com.example.mwaldbauerscheduler.R;
//
//import java.util.Calendar;
//
//public class DatePickerFragment implements View.OnClickListener {
//
//    Button btnDatePicker, btnTimePicker;
//    EditText txtDate, txtTime;
//    private int mYear, mMonth, mDay, mHour, mMinute;
//
//
//    setContentView(R.layout.activity_main);
//
//    btnDatePicker = (Button)findViewById(R.id.btn_date);
//    btnTimePicker = (Button)findViewById(R.id.btn_time);
//    txtDate = (EditText)findViewById(R.id.in_date);
//    txtTime = (EditText)findViewById(R.id.in_time);
//
//    btnDatePicker.setOnClickListener(this);
//    btnTimePicker.setOnClickListener(this);
//
//
//
//    @Override
//    public void onClick(View v) {
//
//        if (v == btnDatePicker) {
//
//            // Get Current Date
//            final Calendar c = Calendar.getInstance();
//            mYear = c.get(Calendar.YEAR);
//            mMonth = c.get(Calendar.MONTH);
//            mDay = c.get(Calendar.DAY_OF_MONTH);
//
//
//            DatePickerDialog datePickerDialog = new DatePickerDialog(this,
//                    new DatePickerDialog.OnDateSetListener() {
//
//                        @Override
//                        public void onDateSet(DatePicker view, int year,
//                                              int monthOfYear, int dayOfMonth) {
//
//                            txtDate.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
//
//                        }
//                    }, mYear, mMonth, mDay);
//            datePickerDialog.show();
//        }
//        if (v == btnTimePicker) {
//
//            // Get Current Time
//            final Calendar c = Calendar.getInstance();
//            mHour = c.get(Calendar.HOUR_OF_DAY);
//            mMinute = c.get(Calendar.MINUTE);
//
//            // Launch Time Picker Dialog
//            TimePickerDialog timePickerDialog = new TimePickerDialog(this,
//                    new TimePickerDialog.OnTimeSetListener() {
//
//                        @Override
//                        public void onTimeSet(TimePicker view, int hourOfDay,
//                                              int minute) {
//
//                            txtTime.setText(hourOfDay + ":" + minute);
//                        }
//                    }, mHour, mMinute, false);
//            timePickerDialog.show();
//        }
//    }
//
//
//}
