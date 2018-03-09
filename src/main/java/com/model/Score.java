package com.model;

import java.io.Serializable;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Tansel
 */

/*
 * Here is, Score class implements Serializable, because we save the HighScore
 * on a file and we want this file to not editable
 */
public class Score implements Serializable, Comparable<Score> {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private String name;
    private int score;

    public Score(String name, int score) {
        this.name = name;
        this.score = score;
    }

    public String getName() {
        return name;
    }

    public int getScore() {
        return score;
    }

    @Override
    public int compareTo( Score score1) {
        return ((Integer) (score1.getScore())).compareTo(getScore());
    }

}
