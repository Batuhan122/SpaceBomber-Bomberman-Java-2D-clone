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
public class BonusBombStack extends Bonus {

    public BonusBombStack(int x, int y) {
        super(x, y);
        try {
            image = ImageIO.read(this.getClass().getResource("/images/bonuses/bonus1.png"));
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Override
    public void getBonus( Bomberman bomberman) {
        bomberman.setBombStack(bomberman.getBombStack() + 1);
    }
}
