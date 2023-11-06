package com.example.playfulmath.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.playfulmath.R;
import com.example.playfulmath.model.ProfileModel;

import java.util.List;

public class RankingListAdapter extends RecyclerView.Adapter<RankingListAdapter.ViewHolder> {

    private List<ProfileModel> userList;

    public RankingListAdapter(List<ProfileModel> userList) {
        this.userList = userList;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView usernameTextView, scoreTextView, numberTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            usernameTextView = itemView.findViewById(R.id.rankingListUserNameTextView);
            scoreTextView = itemView.findViewById(R.id.rankingListScoreTextView);
            numberTextView = itemView.findViewById(R.id.rankingListNumberTextView);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ranking_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ProfileModel user = userList.get(position);
        holder.usernameTextView.setText(user.getUsername());
        holder.scoreTextView.setText(String.valueOf(user.getScore()));
        holder.numberTextView.setText(String.valueOf(position + 1));
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }
}