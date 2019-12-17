package edu.gatech.seclass.crypto6300;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SqliteConnection extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "CryptogramPlay.db";
    //user table
    public static final String USER_TABLE = "user";
    public static final String USER_TABLE_ID = "id";
    public static final String USER_TABLE_USERNAME = "username";
    public static final String USER_TABLE_LASTNAME = "lastname";
    public static final String USER_TABLE_FIRSTNAME = "firstname";
    public static final String USER_TABLE_USERTYPE = "usertype";
    public static final String USER_TABLE_DIFFICULTY = "difficulty";
    public static final String USER_TABLE_NUMOFWON = "numofwon";
    public static final String USER_TABLE_NUMOFLOST = "numoflost";

    //cryptogram table
    public static final String  CRYPTOGRAM_TABLE_ID = "id";
    public static final String  CRYPTOGRAM_TABLE_NAME = "name";
    public static final String  CRYPTOGRAM_TABLE_SOLUTION = "solution";
    public static final String  CRYPTOGRAM_TABLE_EASY = "easy";
    public static final String  CRYPTOGRAM_TABLE_NORMAL = "normal";
    public static final String  CRYPTOGRAM_TABLE_HARD = "hard";

    //gameplay table
    public static final String  GAMEPLAY_TABLE_ID = "id";
    public static final String  GAMEPLAY_TABLE_PLAYERID = "playerid";
    public static final String  GAMEPLAY_TABLE_CRYPTOGRAMID = "cryptogramid";
    public static final String  GAMEPLAY_TABLE_STATUS = "status";
    public static final String  GAMEPLAY_TABLE_REMAININGATTEMPTS = "remainingattempts";
    public static final String  GAMEPLAY_TABLE_ENCRYPTEDPHRASE = "encryptedphrase";
    public static final String  GAMEPLAY_TABLE_PLAYERSOLUTION = "playersolution";

    public SqliteConnection(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(
                "create table if not exists user " +
                        "(id integer primary key, firstname text, lastname text, username text," +
                        " usertype text, difficulty text, numofwon integer, numoflost integer)"
        );
        db.execSQL(
                "create table if not exists cryptogram " +
                        "(id integer primary key, name text, solution text, easy int, normal int, hard int)");
        db.execSQL(
                "create table if not exists gamerecord " +
                        "(id integer primary key, playerid int, cryptogramid int, status text, " +
                        "remainingattempts int, encryptedphrase text, playersolution text)");
        loadAdminData(db);
        loadPlayerData(db);
        loadPlayerDataHard(db);
        loadCryptogramData(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS user");
        onCreate(db);
    }

    public Cursor getUser(String username) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] whereArgs = new String[]{username};
        String queryString = "select * from user where username = ?";
        Cursor res =  db.rawQuery( queryString, whereArgs);
        return res;
    }

    public long insertUser(String firstName, String lastName, String username, UserType userType,
                               Difficulty difficulty) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("firstname", firstName);
        contentValues.put("lastname", lastName);
        contentValues.put("username", username);
        contentValues.put("usertype", userType.name());
        contentValues.put("difficulty", difficulty.name());
        return db.insert("user", null, contentValues);
    }

    public Cursor isUsernameUsed(String username) {
        SQLiteDatabase db = this.getWritableDatabase();
        String[] whereArgs = new String[] {username};
        String queryString = "select * from user where username = ?";
        Cursor res = db.rawQuery(queryString, whereArgs);
        return res;
    }

    public boolean insertGameRecord(GameRecord gameRecord){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("playerid", gameRecord.getPlayerId());
        contentValues.put("cryptogramid", gameRecord.getCryptogramId());
        contentValues.put("status", gameRecord.getStatus().name());
        contentValues.put("remainingattempts", gameRecord.getRemainingAttempts());
        contentValues.put("encryptedphrase", gameRecord.getEncryptedPhrase());
        contentValues.put("playersolution", gameRecord.getPlayerSolution());
        db.insert("gamerecord", null, contentValues);
        return true;
    }

    public boolean updateGameRecord(GameRecord gameRecord){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("playerid", gameRecord.getPlayerId());
        contentValues.put("cryptogramid", gameRecord.getCryptogramId());
        contentValues.put("status", gameRecord.getStatus().name());
        contentValues.put("remainingattempts", gameRecord.getRemainingAttempts());
        contentValues.put("encryptedphrase", gameRecord.getEncryptedPhrase());
        contentValues.put("playersolution", gameRecord.getPlayerSolution());
        db.update("gamerecord", contentValues, "id = ? ", new String[] {String.valueOf(gameRecord.getId())});
        return true;
    }

    public boolean updatePlayer(Player player) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("firstname", player.getFirstName());
        contentValues.put("lastname", player.getLastName());
        contentValues.put("username", player.getUserName());
        contentValues.put("usertype", player.getUserType().name());
        contentValues.put("difficulty", player.getDifficulty().name());
        contentValues.put("numofwon", player.getNumofWon());
        contentValues.put("numoflost", player.getNumofLost());
        db.update("user", contentValues, "id = ? ", new String[] {String.valueOf(player.getUserId())});
        return true;
    }

    public void updateStatistics(boolean won, String username) {
        SQLiteDatabase db = this.getWritableDatabase();
        String[] whereArgs = new String[] {username};
        String queryString;
        if (won) {
            queryString = "update user set numofwon = IFNULL(numofwon, 0) + 1 where username = ?";
        }
        else {
            queryString = "update user set numoflost = IFNULL(numoflost, 0) + 1 where username = ?";
        }
        Cursor cursor = db.rawQuery(queryString, whereArgs);
        cursor.moveToFirst();
        cursor.close();
    }

    private void loadAdminData(SQLiteDatabase db){
        ContentValues contentValues = new ContentValues();
        contentValues.put("firstname", "Admin");
        contentValues.put("lastname", "Admin");
        contentValues.put("username", "CryptoAdmin");
        contentValues.put("usertype", "Admin");
        contentValues.put("difficulty", "easy");
        db.insert("user", null, contentValues);
    }

    private void loadPlayerData(SQLiteDatabase db){
        ContentValues contentValues = new ContentValues();
        contentValues.put("firstname", "Feng");
        contentValues.put("lastname", "Yang");
        contentValues.put("username", "fyang88");
        contentValues.put("usertype", "Player");
        contentValues.put("difficulty", "hard");
        db.insert("user", null, contentValues);
    }

    private void loadPlayerDataHard(SQLiteDatabase db)
    {
        ContentValues contentValues = new ContentValues();
        contentValues.put("firstname", "Oran");
        contentValues.put("lastname", "Wallace");
        contentValues.put("username", "owalla3");
        contentValues.put("usertype", "Player");
        contentValues.put("difficulty", "hard");
        db.insert("user", null, contentValues);
    }

    private void loadCryptogramData(SQLiteDatabase db){
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", "Something to eat 1");
        contentValues.put("solution", "apple");
        contentValues.put("easy", 60);
        contentValues.put("normal", 40);
        contentValues.put("hard", 20);
        db.insert("cryptogram", null, contentValues);
        contentValues = new ContentValues();
        contentValues.put("name", "Something to eat 2");
        contentValues.put("solution", "orange");
        contentValues.put("easy", 60);
        contentValues.put("normal", 40);
        contentValues.put("hard", 20);
        db.insert("cryptogram", null, contentValues);
    }

    public long insertCryptogram(String cryptoName, String cryptoAnswer, String numTriesEasy, String numTriesNormal, String numTriesHard) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", cryptoName);
        contentValues.put("solution", cryptoAnswer);
        contentValues.put("easy", numTriesEasy);
        contentValues.put("normal", numTriesNormal);
        contentValues.put("hard", numTriesHard);
        return db.insert("cryptogram", null, contentValues);
    }

    public Cursor isCryptogramNameUsed(String cryptogramName) {
        SQLiteDatabase db = this.getWritableDatabase();
        String[] whereArgs = new String[] {cryptogramName};
        String queryString = "select * from cryptogram where name = ?";
        Cursor res = db.rawQuery(queryString, whereArgs);
        return res;
    }

    public void reset() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DROP TABLE IF EXISTS user");
        db.execSQL("DROP TABLE IF EXISTS cryptogram");
        db.execSQL("DROP TABLE IF EXISTS gamerecord");
        onCreate(db);
    }

    public Cursor getAllPlayerStats(){
        SQLiteDatabase db = this.getReadableDatabase();
        String[] whereArgs = new String[] {"Admin"};
        String queryString = "select firstname, username, difficulty, numofwon, numoflost from user where usertype != ? order by numofwon desc";
        Cursor res =  db.rawQuery( queryString, whereArgs);
        return res;
    }

    public Cursor getGamePlayStats(int playerId){
        SQLiteDatabase db = this.getReadableDatabase();
        String[] whereArgs = new String[] {String.valueOf(playerId)};
        String queryString = "select A.* , B.* from cryptogram as A left join (select * from gamerecord where playerid=?) as B on A.id= B.cryptogramid";
        Cursor res =  db.rawQuery( queryString, whereArgs);
        return res;
    }
}
