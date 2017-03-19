package com.tutorialsbuzz.rxandroidsample.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tutorialsbuzz.rxandroidsample.MainActivity;
import com.tutorialsbuzz.rxandroidsample.Model.ExampleFragmentAndName;
import com.tutorialsbuzz.rxandroidsample.R;

import java.util.List;

/**
 * Created by YA55 on 2/2/2017.
 */
public class ExampleAdapter extends RecyclerView.Adapter<ExampleAdapter.ViewHolder> {

    List<ExampleFragmentAndName> mExample;
    Context mContext;

    public ExampleAdapter(Context context, List<ExampleFragmentAndName> examples) {
        this.mContext = context;
        this.mExample = examples;
    }

    @Override
    public ExampleAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater
                .from(mContext)
                .inflate(R.layout.example_list_items, parent, false);
        return new ViewHolder(v);
    }


    @Override
    public void onBindViewHolder(ExampleAdapter.ViewHolder holder, final int position) {
        holder.mNameDisplay.setText(mExample.get(position).mExampleName);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                MainActivity mainActivity = (MainActivity) mContext;

                ((MainActivity) mContext).showFragment(mExample.get(position).mFragment);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mExample.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public final TextView mNameDisplay;

        public ViewHolder(View itemView) {
            super(itemView);
            mNameDisplay = (TextView) itemView.findViewById(R.id.name_display);
        }
    }

}
