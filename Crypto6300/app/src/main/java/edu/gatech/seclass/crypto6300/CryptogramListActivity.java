package edu.gatech.seclass.crypto6300;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static edu.gatech.seclass.crypto6300.GamePlayActivity.CRYPTOGRAM;
import static edu.gatech.seclass.crypto6300.GamePlayActivity.GAMERECORD;

public class CryptogramListActivity extends AppCompatActivity implements UnsolvedCryptogramAdapter.CryptogramSelectListener {
    public static final String PLAYER = "player";
    private Player mPlayer;
    private RecyclerView mRecyclerView;
    private GamePlayStatsForPlayer mGamePlayStatsForPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crytogram_list);
        Intent intent = getIntent();
        mPlayer = (Player)intent.getSerializableExtra(PLAYER);
        mRecyclerView = findViewById(R.id.unsolved_cryptogram_recycler);
        loadData();
    }

    @Override
    protected void onPostResume(){
        super.onPostResume();
        loadData();
    }

    @Override
    public void onCryptogramSelected(Cryptogram cryptogram) {
        Intent intent = new Intent(this, GamePlayActivity.class);
        intent.putExtra(GamePlayActivity.PLAYER, mPlayer);
        intent.putExtra(CRYPTOGRAM, cryptogram);
        Map<Integer, GameRecord> gameRecordMap = mGamePlayStatsForPlayer.getPlayedGameRecord();
        if (gameRecordMap != null && !gameRecordMap.isEmpty()) {
            GameRecord gameRecord = gameRecordMap.get(cryptogram.getId());
            if (gameRecord != null) {
                intent.putExtra(GAMERECORD, gameRecord);
            }
        }
        startActivity(intent);
    }

    private void loadData(){
        mGamePlayStatsForPlayer = Player.getUnsolvedCryptoList(mPlayer.getUserId());
        Map<Status, List<Cryptogram>> unSolvedCryptogramMap= mGamePlayStatsForPlayer.getUnsolvedCryptograms();
        List<Cryptogram> unSolvedCryptograms = new ArrayList<>();
        unSolvedCryptograms.addAll(unSolvedCryptogramMap.get(Status.UNSTARTED));
        unSolvedCryptograms.addAll(unSolvedCryptogramMap.get(Status.INPROGRESS));
        if (mGamePlayStatsForPlayer.getUnsolvedCryptograms().size()>0) {
            UnsolvedCryptogramAdapter unsolvedCryptogramAdapter =
                    new UnsolvedCryptogramAdapter(unSolvedCryptograms, unSolvedCryptogramMap.get(Status.UNSTARTED).size(), this);
            mRecyclerView.setAdapter(unsolvedCryptogramAdapter);
            mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        }
    }
}