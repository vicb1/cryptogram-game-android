package edu.gatech.seclass.crypto6300;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Map;

public class GamePlayActivity extends AppCompatActivity {
    public static final String PLAYER = "player";
    public static final String CRYPTOGRAM ="cryptogram";
    public static final String GAMERECORD= "gamerecord";
    private Player mPlayer;
    private String mEncryptedPhrase;
    private int mAllowedAttempts;

    private Cryptogram mCryptogram;
    private GameRecord mGameRecord;

    private TextView mTitleView;
    private TextView mAllowedAttemptsView;
    private TextView mEncryptedPhraseView;
    private EditText mInputPhraseView;
    private Button mSaveAndQuit;
    private Button mSubmit;

    private boolean restarted;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_play);
        Intent intent = getIntent();
        mPlayer = (Player)intent.getSerializableExtra(PLAYER);
        mCryptogram = (Cryptogram) intent.getSerializableExtra(CRYPTOGRAM);
        mGameRecord = (GameRecord) intent.getSerializableExtra(GAMERECORD);
        mTitleView = findViewById(R.id.game_title);
        mAllowedAttemptsView = findViewById(R.id.game_attempts);
        mEncryptedPhraseView = findViewById(R.id.game_encrypted_phrase);
        mInputPhraseView = findViewById(R.id.game_input);
        mSaveAndQuit = findViewById(R.id.save_and_quit);
        mSubmit = findViewById(R.id.submit);

        mTitleView.setText(mCryptogram.getName());
        mAllowedAttempts =
                mGameRecord == null
                        ? mCryptogram.getAttemptsAllowed().get(mPlayer.getDifficulty())
                        : mGameRecord.getRemainingAttempts();
        mAllowedAttemptsView.setText("Attempts Left: "+ mAllowedAttempts);
        mEncryptedPhrase =
                mGameRecord == null
                        ? mCryptogram.generateEncryptedPhrase()
                        : mGameRecord.getEncryptedPhrase();
        mEncryptedPhraseView.setText(mEncryptedPhrase);
        if (mGameRecord != null) {
            mInputPhraseView.setText(mGameRecord.getPlayerSolution());
        }

        mSaveAndQuit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SynctoDB(false);
            }
        });

        mSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SynctoDB(true);
            }
        });
    }

    private void SynctoDB(boolean isSubmit){
        String playerSolution = mInputPhraseView.getText().toString();
        boolean isNewRecord = mGameRecord == null;
        if (isNewRecord) {
            mGameRecord = new GameRecord();
            mGameRecord.setPlayerId(mPlayer.getUserId());
            mGameRecord.setCryptogramId(mCryptogram.getId());
            mGameRecord.setEncryptedPhrase(mEncryptedPhrase);
        }
        boolean isSolved = playerSolution.equals(mCryptogram.getSolution());
        boolean won = false, lost = false;
        if (isSubmit){
            mGameRecord.setRemainingAttempts(--mAllowedAttempts);
            if (isSolved) {
                mGameRecord.setStatus(Status.COMPLETE);
                won = true;
                // update numofWon
                mPlayer.updateStatistics(true, mPlayer.getUserName());
            } else {
                mGameRecord.setStatus(Status.INPROGRESS);
            }
            if (mAllowedAttempts == 0) {
                mGameRecord.setStatus(Status.COMPLETE);
                lost = true;
                //update numofLost
                mPlayer.updateStatistics(false, mPlayer.getUserName());
            }
        } else {
            mGameRecord.setRemainingAttempts(mAllowedAttempts);
            mGameRecord.setStatus(Status.INPROGRESS);
        }
        mGameRecord.setPlayerSolution(playerSolution);
        if (isNewRecord){
            DbHelper.getConnection().insertGameRecord(mGameRecord);
        } else {
            DbHelper.getConnection().updateGameRecord(mGameRecord);
        }


        if (won){
            //show "You won"
            showAlertDialog(true);
        }
        else if (lost) {
            showAlertDialog(false);
        }
        else if (!isSolved && isSubmit) {  // submitted and not won. alert that failed and restart
            showFailedAlertDialog();
        } else {
            GamePlayActivity.this.finish();
        }
    }

    private void showAlertDialog(boolean won){
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.DialogTheme);
        if (won) {
            builder.setMessage(R.string.you_won);
        }
        else {
            builder.setMessage(R.string.you_lost);
        }
        builder.setPositiveButton(
                R.string.OK,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                        GamePlayActivity.this.finish();
                    }
                });
        AlertDialog dialog= builder.create();
        dialog.show();
        TextView textView = (TextView) dialog.findViewById(android.R.id.message);
        if (textView != null) {
            textView.setTextColor(getResources().getColor(R.color.color_orange));
            textView.setTextSize(getResources().getDimension(R.dimen.medium_font));
        }
    }


    public void onCryptogramSelected_restarted(Cryptogram cryptogram) {
        GamePlayStatsForPlayer mGamePlayStatsForPlayer = Player.getUnsolvedCryptoList(mPlayer.getUserId());

        // copy of method from CryptogramListActivity.java
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

    private void showFailedAlertDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.DialogTheme);
        builder.setMessage("Your attempt was not correct");
        builder.setPositiveButton(
                R.string.OK,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                        restarted = true;
                      GamePlayActivity.this.recreate();
                       GamePlayActivity.this.finish();
                       onCryptogramSelected_restarted(mCryptogram);

                    }
                });
        AlertDialog dialog= builder.create();
        dialog.setCancelable(false); //Prevent closing dialog by going back.
        dialog.setCanceledOnTouchOutside(false); //Prevent closing dialog by clicking outside of dialog.
        dialog.show();
        TextView textView = (TextView) dialog.findViewById(android.R.id.message);
        if (textView != null) {
            textView.setTextColor(getResources().getColor(R.color.color_orange));
            textView.setTextSize(getResources().getDimension(R.dimen.medium_font));
        }
    }
}
