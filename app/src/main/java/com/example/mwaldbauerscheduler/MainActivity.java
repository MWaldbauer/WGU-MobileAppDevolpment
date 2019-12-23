package com.example.mwaldbauerscheduler;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.mwaldbauerscheduler.Activities.NewTermActivity;
import com.example.mwaldbauerscheduler.Entities.Term;
import com.example.mwaldbauerscheduler.View.SchedulerListAdapter;
import com.example.mwaldbauerscheduler.ViewModel.SchedulerViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static final int NEW_TERM_ACTIVITY_REQUEST_CODE = 1;
    private SchedulerViewModel mSchedulerViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        final SchedulerListAdapter adapter = new SchedulerListAdapter(this);
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

        //testing Data retrieval
        Term testTerm = new Term("House",null,null);
        mSchedulerViewModel.insertTerm(testTerm);
        List<Term> testTermList = mSchedulerViewModel.getAllTerms().getValue();
        //Generates Null Pointer
        //Log.i("Term changed in Main Activity", testTermList.iterator().next().getTerm()); //** Remove later

        //new term fab
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, NewTermActivity.class);
                startActivityForResult(intent, NEW_TERM_ACTIVITY_REQUEST_CODE);
            }
        });
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == NEW_TERM_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            Term term = new Term(data.getStringExtra(NewTermActivity.EXTRA_REPLY),
                    null,
                    null);
            mSchedulerViewModel.insertTerm(term);

            Log.i("Term name in MainActivity",term.getTerm()); //** Remove later

        } else {
            Toast.makeText(
                    getApplicationContext(),
                    R.string.empty_not_saved,
                    Toast.LENGTH_LONG).show();
        }
    }
}
