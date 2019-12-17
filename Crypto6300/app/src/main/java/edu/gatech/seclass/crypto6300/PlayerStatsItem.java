package edu.gatech.seclass.crypto6300;

import java.io.Serializable;

public class PlayerStatsItem implements Serializable {

    public PlayerStatsItem(String firstname, String username, String difficulty, int numWins, int numLoses) {
        this.firstname = firstname;
        this.username = username;
        this.difficulty = difficulty;
        this.numWins = numWins;
        this.numLoses = numLoses;
    }

    private String firstname;
    private String username;
    private String difficulty;
    private int numWins;
    private int numLoses;

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public int getNumWins() {
        return numWins;
    }

    public void setNumWins(int numWins) {
        this.numWins = numWins;
    }

    public int getNumLoses() {
        return numLoses;
    }

    public void setNumLoses(int numLoses) {
        this.numLoses = numLoses;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }


}
