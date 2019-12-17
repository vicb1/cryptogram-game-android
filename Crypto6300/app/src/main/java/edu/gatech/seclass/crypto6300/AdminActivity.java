package edu.gatech.seclass.crypto6300;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class AdminActivity extends AppCompatActivity {
    public static final String ADMIN_ID = "admin_id";
    private int mUserId;
    private Button mPlayerStatsButton;
    private Button mAddPlayerButton;
    private Button mAddCryptogramButton;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        Intent intent = getIntent();
        mUserId = intent.getIntExtra(ADMIN_ID, -1);
        mPlayerStatsButton = findViewById(R.id.play_stats_button);
        mAddPlayerButton = findViewById(R.id.add_new_player_button);
        mAddCryptogramButton = findViewById(R.id.add_new_cryptogram_button);

        mPlayerStatsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchPlayerStatsActivity();
            }
        });

        mAddPlayerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchCreatePlayerActivity();
            }
        });

        mAddCryptogramButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchCreateCryptogramActivity();
            }
        });
    }

    private void launchPlayerStatsActivity() {
        Intent intent = new Intent(this, PlayerStatisticsActivity.class);
        //Intent intent = new Intent(this, PlayerStatsActivity.class);
        intent.putExtra(PlayerStatisticsActivity.IS_ADMIN, true);
        startActivity(intent);
    }

    private void launchCreatePlayerActivity() {
        Intent intent = new Intent(this, CreatePlayerActivity.class);
        startActivity(intent);
    }

    private void launchCreateCryptogramActivity() {
        Intent intent = new Intent(this, CreateCryptogramActivity.class);
        startActivity(intent);
    }
}