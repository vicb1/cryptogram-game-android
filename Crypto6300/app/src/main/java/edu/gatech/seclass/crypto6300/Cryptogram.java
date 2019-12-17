package edu.gatech.seclass.crypto6300;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Cryptogram implements Serializable {
    private int id;
    private String name;
    private String solution;
    private Map<Difficulty, Integer> attemptsAllowed;

    //generate random phrase, make sure no more than 1 letter will be encrypted into the same random letter.
    public String generateEncryptedPhrase() {
        Map<Character, Character> letterMap = new HashMap<>();
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < solution.length(); i++) {
            char c = solution.charAt(i);
            if (isLowercaseLetter(c) || isUppercaseLetter(c)) {
                if (letterMap.containsKey(c)) {
                    builder.append(letterMap.get(c));
                } else {
                    boolean isLowercaseLetter = isLowercaseLetter(c);
                    char randomLetter = getRandomShiftForChar(c, isLowercaseLetter);
                    while (letterMap.values().contains(randomLetter)) {
                        randomLetter = getRandomShiftForChar(c, isLowercaseLetter);
                    }
                    letterMap.put(c, randomLetter);
                    if (isLowercaseLetter) {
                        letterMap.put((char) (c + 'A' - 'a'), (char) (randomLetter + 'A' - 'a'));
                    } else {
                        letterMap.put((char) (c + 'a' - 'A'), (char) (randomLetter + 'a' - 'A'));
                    }
                    builder.append(randomLetter);
                }
            } else {
                builder.append(c);
            }
        }
        return builder.toString();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSolution() {
        return solution;
    }

    public void setSolution(String solution) {
        this.solution = solution;
    }

    public Map<Difficulty, Integer> getAttemptsAllowed() {
        return attemptsAllowed;
    }

    public void setAttemptsAllowed(Map<Difficulty, Integer> attemptsAllowed) {
        this.attemptsAllowed = attemptsAllowed;
    }

    //make sure generated letter is not the same as the original
    private char getRandomShiftForChar(char c, boolean isLowercase) {
        Random random = new Random();
        int rInt = random.nextInt(26); //0~25
        if (isLowercase) {
            while (rInt == c - 'a') {
                rInt = random.nextInt(26);
            }
            return (char) (rInt + 'a');
        } else {
            while (rInt == c - 'A') {
                rInt = random.nextInt(26);
            }
            return (char) (rInt + 'A');
        }
    }

    private boolean isLowercaseLetter(char c) {
        return c >= 'a' && c <= 'z';
    }

    private boolean isUppercaseLetter(char c) {
        return c >= 'A' && c <= 'Z';
    }
}
