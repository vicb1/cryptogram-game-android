package edu.gatech.seclass.crypto6300;

import java.util.List;
import java.util.Map;

// this is a class that will organize the data from DB to get all information needed to get unsolved cryptograms
// and corresponding game record if goes to game play screen.
public class GamePlayStatsForPlayer {
    private Map<Status, List<Cryptogram>> unsolvedCryptograms;
    private Map<Integer, GameRecord> playedGameRecord;

    public Map<Status, List<Cryptogram>> getUnsolvedCryptograms() {
        return unsolvedCryptograms;
    }

    public void setUnsolvedCryptograms(Map<Status, List<Cryptogram>> unsolvedCryptograms) {
        this.unsolvedCryptograms = unsolvedCryptograms;
    }

    public Map<Integer, GameRecord> getPlayedGameRecord() {
        return playedGameRecord;
    }

    public void setPlayedGameRecord(Map<Integer, GameRecord> playedGameRecord) {
        this.playedGameRecord = playedGameRecord;
    }
}
