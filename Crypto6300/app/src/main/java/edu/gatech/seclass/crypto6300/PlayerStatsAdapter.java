package edu.gatech.seclass.crypto6300;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class PlayerStatsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private ArrayList<PlayerStatsItem> mPlayerStatsList;
    private Context mContext;
    private Boolean mIsAdmin;

    public PlayerStatsAdapter(ArrayList<PlayerStatsItem> playerStatList, Boolean isAdmin ){
        mPlayerStatsList = playerStatList;
        mIsAdmin = isAdmin;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        View itemView;
        if (mIsAdmin) {
            itemView = layoutInflater.inflate(R.layout.admin_stat, parent, false);
        }
        else {
            itemView = layoutInflater.inflate(R.layout.player_stat, parent, false);
        }
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((PlayerStatsAdapter.ViewHolder) holder).bindData(mPlayerStatsList.get(position), position);
    }

    @Override
    public int getItemCount() {
        return mPlayerStatsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private LinearLayout rowLayout;
        private TextView txtFirstName;
        private TextView txtWon;
        private TextView txtLost;
        private TextView txtUsername;
        private TextView txtDifficulty;

        public ViewHolder(View itemView) {
            super(itemView);
            if (mIsAdmin) {
                rowLayout = (LinearLayout) itemView.findViewById(R.id.admin_stat_view);
                txtFirstName = rowLayout.findViewById(R.id.admin_stat_txt_fn);
                txtWon = rowLayout.findViewById(R.id.admin_stat_txt_numofwon);
                txtLost = rowLayout.findViewById(R.id.admin_stat_txt_numoflost);
                txtUsername = rowLayout.findViewById(R.id.admin_stat_txt_username);
                txtDifficulty = rowLayout.findViewById(R.id.admin_stat_txt_difficulty);
            }
            else {
                rowLayout = (LinearLayout)itemView.findViewById(R.id.player_stat_view);
                txtFirstName = rowLayout.findViewById(R.id.player_stat_txt_fn);
                txtWon = rowLayout.findViewById(R.id.player_stat_txt_numofwon);
                txtLost = rowLayout.findViewById(R.id.player_stat_txt_numoflost);
            }
        }

        public void bindData(PlayerStatsItem playerItem, int row) {
            if (row % 2 == 0) {
                rowLayout.setBackgroundResource(R.color.color_light_grey);
            }
            else {
                rowLayout.setBackgroundResource(R.color.color_dark_grey);
            }
            txtFirstName.setText(playerItem.getFirstname());
            txtWon.setText(Integer.toString(playerItem.getNumWins()));
            txtLost.setText(Integer.toString(playerItem.getNumLoses()));

            if (mIsAdmin) {
                txtUsername.setText(playerItem.getUsername());
                txtDifficulty.setText(playerItem.getDifficulty());
            }
        }
    }
}
