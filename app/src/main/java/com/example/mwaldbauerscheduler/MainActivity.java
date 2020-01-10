package com.example.mwaldbauerscheduler;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
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

import com.example.mwaldbauerscheduler.Activities.CoursesActivity;
import com.example.mwaldbauerscheduler.Activities.NewTermActivity;
import com.example.mwaldbauerscheduler.Activities.TermDetailsActivity;
import com.example.mwaldbauerscheduler.Entities.Term;
import com.example.mwaldbauerscheduler.View.TermListAdapter;
import com.example.mwaldbauerscheduler.ViewModel.SchedulerViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static final String CHANNEL_ID = "MWScheduler";
    public static final int NEW_TERM_ACTIVITY_REQUEST_CODE = 1;
    public static final int TERM_DETAIL_ACTIVITY_REQUEST_CODE = 2;
    private SchedulerViewModel mSchedulerViewModel;
    public static final MyValidator schedulerValidator = new MyValidator();
    public Term selectedTerm = null;
    public static final int RESULT_DELETE = -2;
    public static NotificationManagerCompat notificationManager;
//    java.util.Date testDate = new Date(System.currentTimeMillis());
//    public java.sql.Date newDate = new java.sql.Date(testDate.getTime());

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        createNotificationChannel();
        setContentView(R.layout.activity_main);
         notificationManager = NotificationManagerCompat.from(this);
        //toolbar

//        Toolbar toolbar = findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//        toolbar.setNavigationIcon(R.drawable.ic_toc_black_24dp);

        //getSupportActionBar().
        //getSupportActionBar().setIcon(R.drawable.ic_toc_black_24dp);

        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        final TermListAdapter adapter = new TermListAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        mSchedulerViewModel = new ViewModelProvider(this).get(SchedulerViewModel.class);


        //observers
        mSchedulerViewModel.getAllTerms().observe(this, new Observer<List<Term>>() {
            @Override
            public void onChanged(@Nullable final List<Term> terms) {
                // Update the cached copy of the words in the adapter.
                adapter.setTerms(terms);
                Log.i("Term changed in Main Activity", ""); //** Remove later
            }
        });

        //new term fab
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, NewTermActivity.class);
                startActivityForResult(intent, NEW_TERM_ACTIVITY_REQUEST_CODE);
            }
        });

        FloatingActionButton fab2 = findViewById(R.id.fab2);
        fab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, TermDetailsActivity.class);
                selectedTerm = adapter.getSelectedTerm();

                if (selectedTerm == null){
                    Toast.makeText(
                            getApplicationContext(),
                            "Please select a term first.",
                            Toast.LENGTH_LONG).show();
                    return;

                }
                Date startDate = selectedTerm.getStartDate();
                Date endDate = selectedTerm.getStopDate();
                intent.putExtra("Term name", selectedTerm.getTerm());
                intent.putExtra("Term start day", startDate.getDate());
                intent.putExtra("Term start month", startDate.getMonth()+1);
                intent.putExtra("Term start year", startDate.getYear()+1900);
                intent.putExtra("Term end day", endDate.getDate());
                intent.putExtra("Term end month", endDate.getMonth()+1);
                intent.putExtra("Term end year", endDate.getYear()+1900);

                startActivityForResult(intent, TERM_DETAIL_ACTIVITY_REQUEST_CODE);
            }
        });

        //mSchedulerViewModel.deleteAllTerms();
        //testing courses
        Intent intent = new Intent(MainActivity.this, CoursesActivity.class);
        startActivityForResult(intent, NEW_TERM_ACTIVITY_REQUEST_CODE);
    }

    public void onResume() {
        super.onResume();
        checkAlerts();
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


        if (requestCode == NEW_TERM_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {

            java.util.Date startDate =  new Date(
                    data.getIntExtra(
                            NewTermActivity.EXTRA_REPLY_START_DATE_YEAR, 0)-1900,
                    data.getIntExtra(
                            NewTermActivity.EXTRA_REPLY_START_DATE_MONTH, 0) -1,
                    data.getIntExtra(NewTermActivity.EXTRA_REPLY_START_DATE_DAY, 0));
            java.util.Date endDate =  new Date(
                    data.getIntExtra(
                            NewTermActivity.EXTRA_REPLY_END_DATE_YEAR, 0)-1900,
                    data.getIntExtra(
                            NewTermActivity.EXTRA_REPLY_END_DATE_MONTH, 0)-1,
                    data.getIntExtra(NewTermActivity.EXTRA_REPLY_END_DATE_DAY, 0));
            java.sql.Date startDateSQL = new java.sql.Date(startDate.getTime());
            java.sql.Date endDateSQL = new java.sql.Date(endDate.getTime());


            Term term = new Term(data.getStringExtra(NewTermActivity.EXTRA_REPLY_NAME),
                    startDateSQL,
                    endDateSQL);
            mSchedulerViewModel.insertTerm(term);

            Log.i("Term name in MainActivity",term.getTerm()); //** Remove later
            Log.i("Term start date in MainActivity",term.getStartDate().toString()); //** Remove later

        }
        else if (requestCode == TERM_DETAIL_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {

            java.util.Date startDate =  new Date(
                    data.getIntExtra(
                            TermDetailsActivity.EXTRA_REPLY_START_DATE_YEAR, 0)-1900,
                    data.getIntExtra(
                            TermDetailsActivity.EXTRA_REPLY_START_DATE_MONTH, 0) -1,
                    data.getIntExtra(TermDetailsActivity.EXTRA_REPLY_START_DATE_DAY, 0));
            java.util.Date endDate =  new Date(
                    data.getIntExtra(
                            TermDetailsActivity.EXTRA_REPLY_END_DATE_YEAR, 0)-1900,
                    data.getIntExtra(
                            TermDetailsActivity.EXTRA_REPLY_END_DATE_MONTH, 0)-1,
                    data.getIntExtra(TermDetailsActivity.EXTRA_REPLY_END_DATE_DAY, 0));
            java.sql.Date startDateSQL = new java.sql.Date(startDate.getTime());
            java.sql.Date endDateSQL = new java.sql.Date(endDate.getTime());
            String newName = data.getStringExtra(TermDetailsActivity.EXTRA_REPLY_NAME);

//            Term term = new Term(data.getStringExtra(TermDetailsActivity.EXTRA_REPLY_NAME),
//                    startDateSQL,
//                    endDateSQL);
            selectedTerm.setTerm(newName);
            selectedTerm.setStartDate(startDateSQL);
            selectedTerm.setStopDate(endDateSQL);
            mSchedulerViewModel.updateTerm(selectedTerm);

        }
        else if (requestCode == TERM_DETAIL_ACTIVITY_REQUEST_CODE && resultCode == RESULT_DELETE) {
            mSchedulerViewModel.deleteTerm(selectedTerm);
            Toast.makeText(
                    getApplicationContext(),
                    "Selected term deleted.",
                    Toast.LENGTH_LONG).show();
        }

        else {
            Toast.makeText(
                    getApplicationContext(),
                    "Term insertion or change canceled.",
                    Toast.LENGTH_LONG).show();
        }
    }


    public void checkAlerts() {


        //notificationManager.cancelAll();;
//
//        ArrayList<Term> mAllTerms = mAllTerms = (ArrayList<Term>) mSchedulerViewModel.getAllTerms().getValue();
//        int i=0;

//        for (Term term: mAllTerms){
//            //mAllTerms.add(i++, term);
//            //setAlerts
//        }

        //modify Dao to add a query that returns a list of Dates that match our searches (3 of them)
        //pass Alerts with specific messasges based on those

        //setAlert(new Date(12, 31, 2019));

        /*
        // Create an explicit intent for an Activity in your app
        Intent intent = new Intent(); // can be this, BlahBlah.class
        //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_search_black_16dp)
                .setContentTitle("Reminder")
                .setContentText("Alarm")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                // Set the intent that will fire when the user taps the notification
                //.setContentIntent(pendingIntent)
                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true); //adding a date/time to this requires a broadcast reciever. And a lot of steps!

        // notificationId is a unique int for each notification that you must define
        notificationManager.notify(4602, builder.build());

        */


    }

    public void setAlert(Date date){

        Intent notifyIntent = new Intent(this,MyReceiver.class);
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
