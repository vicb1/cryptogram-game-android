package edu.gatech.seclass.crypto6300;

import java.io.Serializable;

public class GameRecord  implements Serializable {
    private int id;
    private int playerId;
    private int cryptogramId;
    private Status status;
    private int remainingAttempts;
    private String encryptedPhrase;
    private String playerSolution;

    public int getPlayerId() {
        return playerId;
    }

    public void setPlayerId(int playerId) {
        this.playerId = playerId;
    }

    public int getCryptogramId() {
        return cryptogramId;
    }

    public void setCryptogramId(int cryptogramId) {
        this.cryptogramId = cryptogramId;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public int getRemainingAttempts() {
        return remainingAttempts;
    }

    public void setRemainingAttempts(int remainingAttempts) {
        this.remainingAttempts = remainingAttempts;
    }

    public String getEncryptedPhrase() {
        return encryptedPhrase;
    }

    public void setEncryptedPhrase(String encryptedPhrase) {
        this.encryptedPhrase = encryptedPhrase;
    }

    public String getPlayerSolution() {
        return playerSolution;
    }

    public void setPlayerSolution(String playerSolution) {
        this.playerSolution = playerSolution;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
