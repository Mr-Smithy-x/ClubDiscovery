package com.charlton.clubdiscovery.adapters;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.charlton.clubdiscovery.R;
import com.charlton.clubdiscovery.adapters.holder.ClubHolder;
import com.charlton.clubdiscovery.data.models.response.ClubModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cj on 10/15/16.
 */
public abstract class ClubRecyclerAdapter extends RecyclerView.Adapter<ClubHolder> implements ClubHolder.OnClickListener {

    private List<ClubModel> clubHolderList = new ArrayList<>();

    @Override
    public ClubHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ClubHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.club_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(ClubHolder holder, int position) {
        holder.bind(clubHolderList.get(position), this);
    }


    public void addClubs(List<ClubModel> clubModels) {
        for (ClubModel c : clubModels) {
            clubHolderList.add(c);
            notifyItemInserted(getItemCount());
        }
    }


    public void addClubs(ArrayList<ClubModel> clubModels) {
        for (ClubModel c : clubModels) {
            clubHolderList.add(c);
            notifyItemInserted(getItemCount());
        }
    }


    public void addClubs(ClubModel... clubModels) {
        for (ClubModel c : clubModels) {
            clubHolderList.add(c);
            notifyItemInserted(getItemCount());
        }
    }

    public void removeAll() {
        int size = clubHolderList.size();
        clubHolderList.clear();
        notifyItemRangeRemoved(0, size);
    }


    @Override
    public int getItemCount() {
        return clubHolderList.size();
    }

}
