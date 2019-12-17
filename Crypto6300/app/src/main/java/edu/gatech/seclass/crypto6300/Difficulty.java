package edu.gatech.seclass.crypto6300;

import java.io.Serializable;

public enum Difficulty implements Serializable {
    EASY,
    NORMAL,
    HARD;

    public static Difficulty getDifficulty(String str){
        str=str.toUpperCase();
        if (str.equals(EASY.name())){
            return EASY;
        } else if (str.equals(NORMAL.name())){
            return NORMAL;
        } else if (str.equals(HARD.name())){
            return HARD;
        }
        return EASY;
    }
}
