package edu.gatech.seclass.crypto6300;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class PlayerStatisticsActivity extends AppCompatActivity {
    public static final String IS_ADMIN = "is_admin";
    private int mUserId;
    private RecyclerView mView;
    private Boolean mIsAdmin;
    private LinearLayout mHeaderLayout;
    private TextView mHeaderFirstName;
    private TextView mHeaderWon;
    private TextView mHeaderLost;
    private TextView mHeaderUsername;
    private TextView mHeaderDifficulty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_statistics);
        mIsAdmin = getIntent().getBooleanExtra(IS_ADMIN, false);
        mView = findViewById(R.id.player_list_recycler);
        mHeaderLayout = findViewById(R.id.stat_th);
        mHeaderFirstName = findViewById(R.id.stat_txt_th_fn);
        mHeaderWon = findViewById(R.id.stat_txt_th_won);
        mHeaderLost = findViewById(R.id.stat_txt_th_lost);
        mHeaderUsername = findViewById(R.id.stat_txt_th_username);
        mHeaderDifficulty = findViewById(R.id.stat_txt_th_difficulty);

        generateStats();
    }

    private void generateStats(){
        Cursor cur = DbHelper.getConnection().getAllPlayerStats();
        ArrayList<PlayerStatsItem> playerStatsList = new ArrayList<>();

        if (cur.moveToFirst()) {
            do {
                String firstName = cur.getString(cur.getColumnIndex(SqliteConnection.USER_TABLE_FIRSTNAME));
                String userName = cur.getString(cur.getColumnIndex(SqliteConnection.USER_TABLE_USERNAME));
                String diff = cur.getString(cur.getColumnIndex(SqliteConnection.USER_TABLE_DIFFICULTY));
                int wins =  cur.getInt(cur.getColumnIndex(SqliteConnection.USER_TABLE_NUMOFWON));
                int lose =  cur.getInt(cur.getColumnIndex(SqliteConnection.USER_TABLE_NUMOFLOST));

                PlayerStatsItem item = new PlayerStatsItem(firstName, userName, diff, wins, lose);
                playerStatsList.add(item);
            }
            while (cur.moveToNext());
        }

        if (!mIsAdmin) {
            mHeaderLayout.removeView(mHeaderUsername);
            mHeaderLayout.removeView(mHeaderDifficulty);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    0,
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    1f);
            mHeaderFirstName.setLayoutParams(params);
            mHeaderWon.setLayoutParams(params);
            mHeaderLost.setLayoutParams(params);
        }

        PlayerStatsAdapter statsAdapter = new PlayerStatsAdapter(playerStatsList, mIsAdmin);
        mView.setAdapter(statsAdapter);
        mView.setLayoutManager(new LinearLayoutManager(this));
    }
}
