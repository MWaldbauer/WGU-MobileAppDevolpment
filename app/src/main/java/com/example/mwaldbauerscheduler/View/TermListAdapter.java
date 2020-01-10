package com.example.mwaldbauerscheduler.View;

import android.content.Context;
import android.graphics.Color;
import android.net.sip.SipSession;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.example.mwaldbauerscheduler.Entities.Term;
import com.example.mwaldbauerscheduler.R;

import java.util.List;

public class TermListAdapter extends RecyclerView.Adapter<TermListAdapter.SchedulerViewHolder> {

    class SchedulerViewHolder extends RecyclerView.ViewHolder { //need an adapter for each activity with a Recycler View
        private final TextView schedulerItemView;


        public SchedulerViewHolder(View itemView) {
            super(itemView);
            schedulerItemView = itemView.findViewById(R.id.textView);
            //itemView.setOnClickListener((View.OnClickListener) this); //this should be safe?
        }
    }


    //private int selectedPos = RecyclerView.NO_POSITION;
    private final LayoutInflater mInflater;
    private List<Term> mAllTerms; // Cached copy of terms
    public TermListAdapter(Context context) { mInflater = LayoutInflater.from(context); }
    public Term selectedTerm;

    @Override
    public SchedulerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.recyclerview_item, parent, false);
        return new SchedulerViewHolder(itemView);
    }

    @Override //I don't think this code block is being executed.
    public void onBindViewHolder(SchedulerViewHolder holder, int position) {

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                selectedTerm = mAllTerms.get(position);
                notifyDataSetChanged();
            }
        });


        if (mAllTerms != null) {
            Term current = mAllTerms.get(position);
            holder.schedulerItemView.setText(current.getTerm());

            //highlights selected term will graphical bug if multiple terms are clicked quickly
            if (current == selectedTerm){
                holder.itemView.setBackgroundColor(Color.parseColor("#000000"));
            }


//            Log.i("Term name in TermListAdapter",current.getTerm()); //** Remove later
        } else {
            // Covers the case of data not being ready yet.
            holder.schedulerItemView.setText("No Terms");
        }
    }

    public Term getSelectedTerm(){
        return selectedTerm;
    }

    public void setTerms(List<Term> terms){
        mAllTerms = terms;
        notifyDataSetChanged();
        Log.i("Term name set in TermListAdapter",""); //** Remove later
    }

    // getItemCount() is called many times, and when it is first called,
    // mAllTerms has not been updated (means initially, it's null, and we can't return null).
    @Override
    public int getItemCount() {
        if (mAllTerms != null)
            return mAllTerms.size();
        else return 0;
    }


}
