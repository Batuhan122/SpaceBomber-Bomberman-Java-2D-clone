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
public class BonusBombStack extends Bonus {

    public BonusBombStack(int x, int y) {
        super(x, y);
        image = new ImageIcon(this.getClass().getResource("/images/bonuses/bonus1.png")).getImage();
    }

    @Override
    public void getBonus( Bomberman bomberman) {
        bomberman.setBombStack(bomberman.getBombStack() + 1);
    }
}
