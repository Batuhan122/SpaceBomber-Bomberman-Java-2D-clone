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
public class Save implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private int level;
    private int lives;
    private int score;
    private int bombStack;
    private int range;
    private int bombType;

    public Save(int level, int lives, int score, int bombStack, int range, int bombType) {
        this.level = level;
        this.lives = lives;
        this.score = score;
        this.bombStack = bombStack;
        this.range = range;
        this.bombType = bombType;
    }

    public int getLevel() {
        return level;
    }

    public int getLives() {
        return lives;
    }

    public int getScore() {
        return score;
    }

    public int getBombStack() {
        return bombStack;
    }

    public int getRange() {
        return range;
    }

    public int getBombType() {
        return bombType;
    }
}
