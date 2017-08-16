package com.example.zach.bbva;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * Created by zhangwenpurdue on 8/15/2017.
 */



public class Adapter extends RecyclerView.Adapter<DetailsFragment.ResultHolder> {
    private Context mContext;
    ArrayList<ResultsItem> resultsItemsArrayList;
    public Adapter(Context context, ArrayList<ResultsItem> list) {
        this.mContext = context;
        this.resultsItemsArrayList = list;
    }
    @Override
    public DetailsFragment.ResultHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_location, parent, false);
        final DetailsFragment.ResultHolder holder = new DetailsFragment.ResultHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(DetailsFragment.ResultHolder holder, final int position) {
        final ResultsItem resultsItem = resultsItemsArrayList.get(position);
        holder.mID.setText(resultsItem.getId());
        holder.mName.setText(resultsItem.getName());
        holder.mAddress.setText(resultsItem.getRating() + "");
        final int p = position;
        holder.mDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putInt("pos", position);
                DetailsFragment detailsFragment = new DetailsFragment();
                detailsFragment.setArguments(bundle);
                ((AppCompatActivity)mContext).getSupportFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.container, new DetailsFragment()).commit();
            }
        });


    }

    @Override
    public int getItemCount() {
        return resultsItemsArrayList.size();
    }
}

