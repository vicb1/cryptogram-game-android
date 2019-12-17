package edu.gatech.seclass.crypto6300;

import android.database.Cursor;

import java.io.Serializable;
import java.util.List;

public abstract class User implements Serializable {
    private int userId;
    private String firstName;
    private String lastName;
    private String userName;
    private UserType userType;

    User(){}

    //if login succeed, return userId, or else return -1
    public static User login(String username){
        Cursor cursor = DbHelper.getConnection().getUser(username);
        if (!cursor.moveToFirst()) {
            return null;
        } else {
            int id = cursor.getInt(cursor.getColumnIndex(SqliteConnection.USER_TABLE_ID));
            String userType = cursor.getString(cursor.getColumnIndex(SqliteConnection.USER_TABLE_USERTYPE));
            User user;
            if (userType.equals(UserType.Admin.name())){
                user = new Admin();
                user.setUserType(UserType.Admin);
            } else {
                user = new Player();
                user.setUserType(UserType.Player);
                String difficulty = cursor.getString(cursor.getColumnIndex(SqliteConnection.USER_TABLE_DIFFICULTY));
                ((Player) user).setDifficulty(Difficulty.getDifficulty(difficulty));
                ((Player) user).setNumofWon(cursor.getInt(cursor.getColumnIndex(SqliteConnection.USER_TABLE_NUMOFWON)));
                ((Player) user).setNumofLost(cursor.getInt(cursor.getColumnIndex(SqliteConnection.USER_TABLE_NUMOFLOST)));
            }
            user.setUserId(id);
            user.setFirstName(cursor.getString(cursor.getColumnIndex(SqliteConnection.USER_TABLE_FIRSTNAME)));
            user.setLastName(cursor.getString(cursor.getColumnIndex(SqliteConnection.USER_TABLE_LASTNAME)));
            user.setUserName(cursor.getString(cursor.getColumnIndex(SqliteConnection.USER_TABLE_USERNAME)));
            return user;
        }
    }

    abstract List<Player> getPlayerStatistics();

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }
}
