package edu.gatech.seclass.crypto6300;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.List;

public class Admin extends User {
    @Override
    List<Player> getPlayerStatistics() {
        return null;
    }

    public static long CreateNewPlayer(String firstName, String lastName, String username, UserType userType, Difficulty difficulty) {
        long rowID = DbHelper.getConnection().insertUser(firstName, lastName, username, userType, difficulty);
        return rowID;
    }

    public static boolean isUsernameUsed(String username) {
        Cursor cursor = DbHelper.getConnection().isUsernameUsed(username);
        if (cursor.moveToFirst()) {
            return true;
        }
        else {
            return false;
        }
    }

    public static long CreateNewCryptogram(String cryptoName, String cryptoAnswer, String numTriesEasy, String numTriesNormal, String numTriesHard) {
        long rowID = DbHelper.getConnection().insertCryptogram(cryptoName, cryptoAnswer, numTriesEasy, numTriesNormal, numTriesHard);
        return rowID;
    }

    public static boolean isCryptogramNameUsed(String cryptoName) {
        Cursor cursor = DbHelper.getConnection().isCryptogramNameUsed(cryptoName);
        if (cursor.moveToFirst()) {
            return true;
        }
        else {
            return false;
        }
    }
}
