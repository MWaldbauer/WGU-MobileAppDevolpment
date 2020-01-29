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
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.mwaldbauerscheduler.Entities.Term;
import com.example.mwaldbauerscheduler.MyReceiver;
import com.example.mwaldbauerscheduler.MyValidator;
import com.example.mwaldbauerscheduler.R;
import com.example.mwaldbauerscheduler.TermListActivity;
import com.example.mwaldbauerscheduler.ViewModel.SchedulerViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Date;

public class HomeActivity extends AppCompatActivity {

    public static final String CHANNEL_ID = "MWScheduler";
    public static NotificationManagerCompat notificationManager;
    public static final MyValidator schedulerValidator = new MyValidator();

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_home);
            createNotificationChannel();
            notificationManager = NotificationManagerCompat.from(this);

            Button termsButton = findViewById(R.id.termsButton);
            termsButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(HomeActivity.this, TermListActivity.class);
                    startActivity(intent);
                }
            });

            Button coursesButton = findViewById(R.id.coursesButton);
            coursesButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(HomeActivity.this, CourseListActivity.class);
                    startActivity(intent);
                }
            });

            RelativeLayout relativeLayout = findViewById(R.id.relativeLayoutHome);
            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.MATCH_PARENT,
                    RelativeLayout.LayoutParams.WRAP_CONTENT);

            Button btnAssessments = new Button(this);
            btnAssessments.setText("Assessments");
            btnAssessments.setLayoutParams(layoutParams);
            layoutParams.addRule(RelativeLayout.BELOW, R.id.coursesButton);
            layoutParams.addRule(RelativeLayout.CENTER_IN_PARENT);
            relativeLayout.addView(btnAssessments);

            btnAssessments.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(HomeActivity.this, AssessmentListActivity.class);
                    startActivity(intent);
                }
            });

        }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent;
        switch (item.getItemId()) {
            case R.id.action_terms:
                intent = new Intent(HomeActivity.this, TermListActivity.class);
                startActivity(intent); //this probably can be startActivity now!
                return true;

            case R.id.action_settings:
                intent = new Intent(HomeActivity.this, AssessmentListActivity.class);
                startActivity(intent);
                return true;

            case R.id.action_favorite: //names are off
                intent = new Intent(HomeActivity.this, CourseListActivity.class);
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
