package edu.gatech.seclass.crypto6300;

import android.database.Cursor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Player extends User  implements Serializable {
    private Difficulty difficulty;
    private int numofWon;
    private int numofLost;

    public Player(){}

    @Override
    List<Player> getPlayerStatistics() {
        return null;
    }

    public Difficulty getDifficulty() {
        return difficulty;
    }

    public int getNumofWon() {
        return numofWon;
    }

    public void setNumofWon(int numofwon) {
        this.numofWon = numofwon;
    }

    public int getNumofLost() {
        return numofLost;
    }

    public void setNumofLost(int numofLost) {
        this.numofLost = numofLost;
    }

    public void setDifficulty(Difficulty difficulty) {
        this.difficulty = difficulty;
    }

    public static GamePlayStatsForPlayer getUnsolvedCryptoList(int userid){
        GamePlayStatsForPlayer gamePlayStatsForPlayer = new GamePlayStatsForPlayer();
        Cursor cursor= DbHelper.getConnection().getGamePlayStats(userid);
        Map<Integer, GameRecord> gameRecords = new HashMap<>();
        List<Cryptogram> inProgressCryptogram = new ArrayList<>();
        List<Cryptogram> unStartedCryptograms = new ArrayList<>();
        if (cursor.moveToFirst()){
            do { Cryptogram  cryptogram = new Cryptogram();
                cryptogram.setId(cursor.getInt(0));
                cryptogram.setName(cursor.getString(cursor.getColumnIndex(SqliteConnection.CRYPTOGRAM_TABLE_NAME)));
                cryptogram.setSolution(cursor.getString(cursor.getColumnIndex(SqliteConnection.CRYPTOGRAM_TABLE_SOLUTION)));
                Map<Difficulty, Integer> allowedAttemps = new HashMap<>();
                allowedAttemps.put(Difficulty.EASY, cursor.getInt(cursor.getColumnIndex(SqliteConnection.CRYPTOGRAM_TABLE_EASY)));
                allowedAttemps.put(Difficulty.NORMAL, cursor.getInt(cursor.getColumnIndex(SqliteConnection.CRYPTOGRAM_TABLE_NORMAL)));
                allowedAttemps.put(Difficulty.HARD, cursor.getInt(cursor.getColumnIndex(SqliteConnection.CRYPTOGRAM_TABLE_HARD)));
                cryptogram.setAttemptsAllowed(allowedAttemps);

                GameRecord gameRecord = new GameRecord();
                int recordId = cursor.getInt(6);
                if (recordId > 0) {
                    gameRecord.setId(recordId);
                    gameRecord.setPlayerId(cursor.getInt(cursor.getColumnIndex(SqliteConnection.GAMEPLAY_TABLE_PLAYERID)));
                    gameRecord.setCryptogramId(cursor.getInt(cursor.getColumnIndex(SqliteConnection.GAMEPLAY_TABLE_CRYPTOGRAMID)));
                    gameRecord.setEncryptedPhrase(cursor.getString(cursor.getColumnIndex(SqliteConnection.GAMEPLAY_TABLE_ENCRYPTEDPHRASE)));
                    gameRecord.setPlayerSolution(cursor.getString(cursor.getColumnIndex(SqliteConnection.GAMEPLAY_TABLE_PLAYERSOLUTION)));
                    gameRecord.setRemainingAttempts(cursor.getInt(cursor.getColumnIndex( SqliteConnection.GAMEPLAY_TABLE_REMAININGATTEMPTS)));
                    String statusString = cursor.getString(cursor.getColumnIndex( SqliteConnection.GAMEPLAY_TABLE_STATUS));
                    Status status = Status.getStatus(statusString);
                    if (status.equals(Status.INPROGRESS)) {
                        inProgressCryptogram.add(cryptogram);
                        gameRecord.setStatus(status);
                        gameRecords.put(gameRecord.getCryptogramId(), gameRecord);
                    }
                } else {
                    unStartedCryptograms.add(cryptogram);
                }
            } while (cursor.moveToNext());
        }
        Map<Status, List<Cryptogram>> unsolvedCryptograms = new HashMap<>();
        unsolvedCryptograms.put(Status.INPROGRESS, inProgressCryptogram);
        unsolvedCryptograms.put(Status.UNSTARTED, unStartedCryptograms);
        gamePlayStatsForPlayer.setUnsolvedCryptograms(unsolvedCryptograms);
        gamePlayStatsForPlayer.setPlayedGameRecord(gameRecords);
        return gamePlayStatsForPlayer;
    }

    public void updateStatistics(boolean won, String username){
        DbHelper.getConnection().updateStatistics(won, username);
    }
}
