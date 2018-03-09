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
public class Level1Enemy extends Enemy {

    public Level1Enemy(int x, int y) {
        super(x, y);
        image = new ImageIcon(this.getClass().getResource("/images/enemies/enemy1.png")).getImage();
    }

}
