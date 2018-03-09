package model;

import javax.swing.ImageIcon;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Tansel
 */
public class BonusSpeed extends Bonus {

    public BonusSpeed(int x, int y) {
        super(x, y);
        image = new ImageIcon(this.getClass().getResource("/images/bonuses/bonus5.png")).getImage();
    }

    @Override
    public void getBonus( Bomberman bomberman) {
        bomberman.setSpeed(bomberman.getSpeed() + 1);
    }
}
