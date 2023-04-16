package com.example.topquiz.model;

public class User {
    private String mFirstName;
    private int testodelete;
    private int mScore = 0;

    public int getScore() {
        return mScore;
    }

    public String getFirstName() {
        return mFirstName;
    }

    public void setFirstName(String firstName) {
        mFirstName = firstName;
    }

    public void scoreIncrease(){
        mScore++;
    }

    public void scoreDecrease(){
        if (mScore >= 1){
            mScore--;
        }
    }
}
