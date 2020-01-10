package com.example.mwaldbauerscheduler.View;

import android.content.Context;
import android.util.Log;
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

    @Override //I don't think this code block is being executed.
    public void onBindViewHolder(CourseListAdapter.SchedulerViewHolder holder, int position) {
        if (mAllCourses != null) {
            Course current = mAllCourses.get(position);
            holder.schedulerItemView.setText(current.getCourse());
//            Log.i("Course name in CourseListAdapter",current.getCourse()); //** Remove later
        } else {
            // Covers the case of data not being ready yet.
            holder.schedulerItemView.setText("No CoursesActivity");
        }
    }

    public Course getSelectedCourse(){
        return selectedCourse;
    }

    public void setCourses(List<Course> courses){
        mAllCourses = courses;
        notifyDataSetChanged();
        Log.i("Course name set in CourseListAdapter",""); //** Remove later
    }

    // getItemCount() is called many times, and when it is first called,
    // mAllCourses has not been updated (means initially, it's null, and we can't return null).
    @Override
    public int getItemCount() {
        if (mAllCourses != null)
            return mAllCourses.size();
        else return 0;
    }
}
