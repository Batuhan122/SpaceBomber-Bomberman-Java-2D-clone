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
public class BonusLife extends Bonus {

    public BonusLife(int x, int y) {
        super(x, y);
        image = new ImageIcon(this.getClass().getResource("/images/bonuses/bonus4.png")).getImage();
    }

    @Override
    public void getBonus( Bomberman bomberman) {
        bomberman.setLives(bomberman.getLives() + 1);
    }

}
