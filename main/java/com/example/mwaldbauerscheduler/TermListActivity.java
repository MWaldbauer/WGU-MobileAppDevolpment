package com.example.mwaldbauerscheduler;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
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
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.mwaldbauerscheduler.Activities.AssessmentListActivity;
import com.example.mwaldbauerscheduler.Activities.CourseListActivity;
import com.example.mwaldbauerscheduler.Activities.TermEditorActivity;
import com.example.mwaldbauerscheduler.Entities.Term;
import com.example.mwaldbauerscheduler.View.TermListAdapter;
import com.example.mwaldbauerscheduler.ViewModel.SchedulerViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import static com.example.mwaldbauerscheduler.Constants.TERM_ID_KEY;

public class TermListActivity extends AppCompatActivity {

    public static SchedulerViewModel mSchedulerViewModel;
    public Term selectedTerm = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_list);

        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        final TermListAdapter adapter = new TermListAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        mSchedulerViewModel = new ViewModelProvider(this).get(SchedulerViewModel.class);

        //observers
        mSchedulerViewModel.getAllTerms().observe(this, new Observer<List<Term>>() {
            @Override
            public void onChanged(@Nullable final List<Term> terms) {
                adapter.setTerms(terms);
            }
        });

        //new term fab
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TermListActivity.this, TermEditorActivity.class);
                startActivity(intent);
            }
        });

        //Details and edit term fab
        FloatingActionButton fab2 = findViewById(R.id.fab2);
        fab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TermListActivity.this, TermEditorActivity.class);
                selectedTerm = adapter.getSelectedTerm();

                if (selectedTerm == null){
                    Toast.makeText(
                            getApplicationContext(),
                            "Please select a term first.",
                            Toast.LENGTH_LONG).show();
                    return;

                }
                intent.putExtra(TERM_ID_KEY, selectedTerm.getTermID());
                startActivity(intent);
            }
        });
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
            case R.id.action_terms:
                intent = new Intent(TermListActivity.this, TermListActivity.class);
                startActivity(intent); //this probably can be startActivity now!
                return true;

            case R.id.action_settings:
                intent = new Intent(TermListActivity.this, AssessmentListActivity.class);
                startActivity(intent);
                return true;

            case R.id.action_favorite:
                intent = new Intent(TermListActivity.this, CourseListActivity.class);
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
//    public void testAddingItems() {

//        mSchedulerViewModel.deleteAllAssessments();
//        mSchedulerViewModel.deleteAllCourses();
//        mSchedulerViewModel.deleteAllTerms();
//
//        Date startDate = new Date(2019,1,1);
//        Date endDate = new Date(2019,6,31);
////
//        Term fakeTerm = new Term("Spring 2019", startDate, endDate);
//        mSchedulerViewModel.insertTerm(fakeTerm);
//
//        startDate = new Date(2019,7,1);
//        endDate = new Date(2019,12,31);
//
//        fakeTerm = new Term("Winter 2019", startDate, endDate);
//        mSchedulerViewModel.insertTerm(fakeTerm);
//
//        int id = fakeTerm.getTermID();

//        Course course = new Course(
//                "Math 3", startDate, false, endDate,
//                false, "Plan to Take", 57,
//                "Mr. mentor", "888-111-2222", "name@mail.com");
//        mSchedulerViewModel.insertCourse(course); //Fix this it now crashes every time
//
//        course = new Course(
//                "Science 3", startDate, false, endDate,
//                false, "Plan to Take", 57,
//                "Mr. mentor", "888-111-2222", "name@mail.com");
//        mSchedulerViewModel.insertCourse(course); //Fix this it now crashes every time
//
//        int id = course.getCourseID();

//        Assessment assessment = new Assessment("Test 1", endDate, endDate,
//                        false, "Study Hard", 14);
//
//        mSchedulerViewModel.insertAssessment(assessment);
//
//        assessment = new Assessment("Test 2", endDate, endDate,
//                false, "Study Hard", 15);
//
//        mSchedulerViewModel.insertAssessment(assessment);

//    }



}
