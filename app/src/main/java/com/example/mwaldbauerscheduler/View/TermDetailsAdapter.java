package com.example.mwaldbauerscheduler.View;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.mwaldbauerscheduler.Entities.Term;
import com.example.mwaldbauerscheduler.R;

import java.util.List;

public class TermDetailsAdapter extends RecyclerView.Adapter<TermDetailsAdapter.SchedulerViewHolder> {

    class SchedulerViewHolder extends RecyclerView.ViewHolder { //need an adapter for each activity with a Recycler View
        private final TextView schedulerItemView;

        private SchedulerViewHolder(View itemView) {
            super(itemView);
            schedulerItemView = itemView.findViewById(R.id.textView);
        }
    }

    private final LayoutInflater mInflater;
    private List<Term> mAllTerms; // Cached copy of words

    public TermDetailsAdapter(Context context) { mInflater = LayoutInflater.from(context); }

    @Override
    public SchedulerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.recyclerview_item, parent, false);
        return new SchedulerViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(SchedulerViewHolder holder, int position) {
        if (mAllTerms != null) {
            Term current = mAllTerms.get(position);
            holder.schedulerItemView.setText(current.getTerm());

        } else {
            // Covers the case of data not being ready yet.
            holder.schedulerItemView.setText("No Terms");
        }
    }

    public void setTerms(List<Term> terms){
        mAllTerms = terms;
        notifyDataSetChanged();
    }


    @Override
    public int getItemCount() {
        if (mAllTerms != null)
            return mAllTerms.size();
        else return 0;
    }


}
