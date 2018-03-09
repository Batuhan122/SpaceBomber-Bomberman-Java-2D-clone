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
public class BonusBombType extends Bonus {

    public BonusBombType(int x, int y) {
        super(x, y);
        image = new ImageIcon(this.getClass().getResource("/images/bonuses/bonus3.png")).getImage();
    }

    @Override
    public void getBonus( Bomberman bomberman) {
        bomberman.setBombType(1);
    }
}
