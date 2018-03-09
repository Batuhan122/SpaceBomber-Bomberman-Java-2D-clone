package com.model;

import java.io.IOException;

import javax.imageio.ImageIO;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Tansel
 */
public class BonusRange extends Bonus {

    public BonusRange(int x, int y) {
        super(x, y);
        try {
            image = ImageIO.read(this.getClass().getResource("/images/bonuses/bonus2.png"));
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Override
    public void getBonus( Bomberman bomberman) {
        bomberman.setRange(bomberman.getRange() + 1);
    }
}
