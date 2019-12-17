package edu.gatech.seclass.crypto6300;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class PlayerActivity extends AppCompatActivity {
    public static final String PLAYER = "player";
    private Player mPlayer;
    private Button mCryptogramToSolveButton;
    private Button mPlayerStatsButton;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        mPlayer = (Player)intent.getSerializableExtra(PLAYER);
        setContentView(R.layout.activity_player);
        mCryptogramToSolveButton = findViewById(R.id.cryptograms_to_solve_button);
        mPlayerStatsButton = findViewById(R.id.player_play_stats_button);
        //todo 4. add new click listener to switch to UnsolvedCryptogram activity, this will also include select Cryptogram and play Cryptogram UIs (fyang88)

        mCryptogramToSolveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchCryptogramListActivity(mPlayer);
            }
        });

        mPlayerStatsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchPlayerStatsActivity();
            }
        });

    }

    private void launchCryptogramListActivity(Player player){
        Intent intent = new Intent(this, CryptogramListActivity.class);
        intent.putExtra(CryptogramListActivity.PLAYER, player);
        startActivity(intent);
    }

    private void launchPlayerStatsActivity() {
        Intent intent = new Intent(this, PlayerStatisticsActivity.class);
        //Intent intent = new Intent(this, PlayerStatsActivity.class);
        intent.putExtra(PlayerStatisticsActivity.IS_ADMIN, false);
        startActivity(intent);
    }
}