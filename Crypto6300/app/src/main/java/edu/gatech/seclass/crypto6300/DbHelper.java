package edu.gatech.seclass.crypto6300;

import android.content.SharedPreferences;

//hold a single instance of db connection and sharedpreferences
public class DbHelper {
    private static SqliteConnection connection;

    public static SqliteConnection getConnection() {
        return connection;
    }

    //make sure it will be only called once.
    public static void setConnection(SqliteConnection connection) {
        DbHelper.connection = connection;
    }
}
