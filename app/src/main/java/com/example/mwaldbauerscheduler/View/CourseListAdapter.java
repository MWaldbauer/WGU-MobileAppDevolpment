package com.example.mwaldbauerscheduler.View;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.mwaldbauerscheduler.Entities.Course;
import com.example.mwaldbauerscheduler.Entities.Term;
import com.example.mwaldbauerscheduler.R;

import java.util.List;

public class CourseListAdapter extends RecyclerView.Adapter<CourseListAdapter.SchedulerViewHolder>  {//need update to identify selected course

    class SchedulerViewHolder extends RecyclerView.ViewHolder { //need an adapter for each activity with a Recycler View
        private final TextView schedulerItemView;

        private SchedulerViewHolder(View itemView) {
            super(itemView);
            schedulerItemView = itemView.findViewById(R.id.textView);
        }
    }

    private final LayoutInflater mInflater;
    private List<Course> mAllCourses; // Cached copy of courses
    public CourseListAdapter(Context context) { mInflater = LayoutInflater.from(context); }
    public Course selectedCourse;

    @Override
    public CourseListAdapter.SchedulerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.recyclerview_item, parent, false);
        return new CourseListAdapter.SchedulerViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(CourseListAdapter.SchedulerViewHolder holder, int position) {

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                selectedCourse = mAllCourses.get(position);
                notifyDataSetChanged();
            }
        });

        if (mAllCourses != null) {
            Course current = mAllCourses.get(position);
            holder.schedulerItemView.setText(current.getCourse());

            if (current == selectedCourse) { //add another Course then tests
                holder.itemView.setBackgroundColor(Color.parseColor("#000000"));
            }
            else
            {holder.itemView.setBackgroundColor(Color.parseColor("#d3d3d3"));
            }

        }
        else {
            // Covers the case of data not being ready yet.
            holder.schedulerItemView.setText("No Courses");
        }

    }

    public Course getSelectedCourse(){
        return selectedCourse;
    }

    public void setCourses(List<Course> courses){
        mAllCourses = courses;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (mAllCourses != null)
            return mAllCourses.size();
        else return 0;
    }
}
