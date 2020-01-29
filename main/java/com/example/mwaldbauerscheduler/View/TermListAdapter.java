package com.example.mwaldbauerscheduler.View;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mwaldbauerscheduler.Entities.Course;
import com.example.mwaldbauerscheduler.Entities.Term;
import com.example.mwaldbauerscheduler.R;
import com.example.mwaldbauerscheduler.TermListActivity;

import java.util.List;

public class TermListAdapter extends RecyclerView.Adapter<TermListAdapter.SchedulerViewHolder> {

    class SchedulerViewHolder extends RecyclerView.ViewHolder { //need an adapter for each activity with a Recycler View
        private final TextView schedulerItemView;


        public SchedulerViewHolder(View itemView) {
            super(itemView);
            schedulerItemView = itemView.findViewById(R.id.textView);
        }
    }


    //private int selectedPos = RecyclerView.NO_POSITION;
    private final LayoutInflater mInflater;
    private List<Term> mAllTerms; // Cached copy of terms
    private LiveData<List<Course>> mAllCoursesByTerm = null; // Cached copy of courses
    private List<Course> mAllCoursesByTermID = null;
    public TermListAdapter(Context context) { mInflater = LayoutInflater.from(context); }
    private Term selectedTerm;


    @Override
    public SchedulerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.recyclerview_item, parent, false);
        return new SchedulerViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(SchedulerViewHolder holder, int position) {

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                selectedTerm = mAllTerms.get(position); //mAllTerms is being grabbed somehow. I don't know how.
                //setAllCoursesByTerm(selectedTerm.getTerm());
                setAllCoursesByTermID(selectedTerm.getTermID()); //mAllCoursesByTermID is, however not.

                notifyDataSetChanged();
            }
        });

        if (selectedTerm != null) {

            //selectedTerm.getClasses(); I need to make this method exist.
            //This allows us to check if there are any classes in selectedTerm, to then delete in TermListActivity
            //This call doesn't need to be here for that purpose I don't think!


        }

        if (mAllTerms != null) {
            Term current = mAllTerms.get(position);
            holder.schedulerItemView.setText(current.getTerm());

            //highlights selected term will graphical bug if multiple terms are clicked quickly
            if (current == selectedTerm){
                holder.itemView.setBackgroundColor(Color.parseColor("#000000"));
            }
            else
                {holder.itemView.setBackgroundColor(Color.parseColor("#d3d3d3"));
            }

        } else {
            // Covers the case of data not being ready yet.
            holder.schedulerItemView.setText("No Terms");
        }
    }

    public Term getSelectedTerm(){
        return selectedTerm;
    }

//    private List<Course> getAllCoursesByTerm(Term term) {
//
//        selectedTerm.getAttachedCourses();
//    }

    public void setTerms(List<Term> terms){
        mAllTerms = terms;
        notifyDataSetChanged();
    }

    public void setAllCoursesByTermID(int termID){
        mAllCoursesByTermID =
        TermListActivity //this isn't actually working. Deubg tomorrow
                        .mSchedulerViewModel.getAllCoursesByTermID(selectedTerm.getTermID());
        notifyDataSetChanged();
    }


    @Override
    public int getItemCount() {
        if (mAllTerms != null)
            return mAllTerms.size();
        else return 0;
    }


}
