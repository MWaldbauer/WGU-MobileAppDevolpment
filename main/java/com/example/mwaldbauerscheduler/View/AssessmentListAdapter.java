package com.example.mwaldbauerscheduler.View;

import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.mwaldbauerscheduler.Entities.Assessment;
import com.example.mwaldbauerscheduler.R;

import java.util.List;

@SuppressLint("Instantiatable")
public class AssessmentListAdapter extends RecyclerView.Adapter<AssessmentListAdapter.SchedulerViewHolder>  {//need update to identify selected assessment

    class SchedulerViewHolder extends RecyclerView.ViewHolder { //need an adapter for each activity with a Recycler View
        private final TextView schedulerItemView;

        private SchedulerViewHolder(View itemView) {
            super(itemView);
            schedulerItemView = itemView.findViewById(R.id.textView);
        }
    }

    private final LayoutInflater mInflater;
    private List<Assessment> mAllAssessments; // Cached copy of assessments
    public AssessmentListAdapter(Context context) { mInflater = LayoutInflater.from(context); }
    public Assessment selectedAssessment;

    @Override
    public AssessmentListAdapter.SchedulerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.recyclerview_item, parent, false);
        return new AssessmentListAdapter.SchedulerViewHolder(itemView);
    }

    @Override //I don't think this code block is being executed.
    public void onBindViewHolder(AssessmentListAdapter.SchedulerViewHolder holder, int position) {

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                selectedAssessment = mAllAssessments.get(position);
                notifyDataSetChanged();
            }
        });

        if (mAllAssessments != null) {
            Assessment current = mAllAssessments.get(position);
            holder.schedulerItemView.setText(current.getAssessment());

            if (current == selectedAssessment) { //add another Assessment then tests
                holder.itemView.setBackgroundColor(Color.parseColor("#000000"));
            }
            else
            {holder.itemView.setBackgroundColor(Color.parseColor("#d3d3d3"));
            }

        }
        else {
            // Covers the case of data not being ready yet.
            holder.schedulerItemView.setText("No Assessments");
        }

    }

    public Assessment getSelectedAssessment(){
        return selectedAssessment;
    }

    public void setAssessments(List<Assessment> assessments){
        mAllAssessments = assessments;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (mAllAssessments != null)
            return mAllAssessments.size();
        else return 0;
    }
}


