package com.example.mwaldbauerscheduler.View;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.mwaldbauerscheduler.Entities.Term;
import com.example.mwaldbauerscheduler.R;

import java.util.List;

public class SchedulerListAdapter extends RecyclerView.Adapter<SchedulerListAdapter.SchedulerViewHolder> {

    class SchedulerViewHolder extends RecyclerView.ViewHolder {
        private final TextView schedulerItemView;

        private SchedulerViewHolder(View itemView) {
            super(itemView);
            schedulerItemView = itemView.findViewById(R.id.textView);
        }
    }

    private final LayoutInflater mInflater;
    private List<Term> mAllTerms; // Cached copy of words

    public SchedulerListAdapter(Context context) { mInflater = LayoutInflater.from(context); }

    @Override
    public SchedulerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.recyclerview_item, parent, false);
        return new SchedulerViewHolder(itemView);
    }

    @Override //I don't think this code block is being executed.
    public void onBindViewHolder(SchedulerViewHolder holder, int position) {
        Log.i("Binding a term", ""); //** Remove later
        if (mAllTerms != null) {
            Term current = mAllTerms.get(position);
            holder.schedulerItemView.setText(current.getTerm());
            Log.i("Term name in SchedulerListAdapter",current.getTerm()); //** Remove later
        } else {
            // Covers the case of data not being ready yet.
            holder.schedulerItemView.setText("No Terms");
        }
    }

    public void setTerms(List<Term> terms){
        mAllTerms = terms;
        notifyDataSetChanged();
        Log.i("Term name set in SchedulerListAdapter",""); //** Remove later
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
