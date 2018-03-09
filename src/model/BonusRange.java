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
public class BonusRange extends Bonus {

    public BonusRange(int x, int y) {
        super(x, y);
        image = new ImageIcon(this.getClass().getResource("/images/bonuses/bonus2.png")).getImage();
    }

    @Override
    public void getBonus( Bomberman bomberman) {
        bomberman.setRange(bomberman.getRange() + 1);
    }
}
