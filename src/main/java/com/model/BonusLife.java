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
public class BonusLife extends Bonus {

    public BonusLife(int x, int y) {
        super(x, y);
        try {
            image = ImageIO.read(this.getClass().getResource("/images/bonuses/bonus4.png"));
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Override
    public void getBonus( Bomberman bomberman) {
        bomberman.setLives(bomberman.getLives() + 1);
    }

}
