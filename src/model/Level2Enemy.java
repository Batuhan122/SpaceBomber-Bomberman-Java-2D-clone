package model;

import java.util.ArrayList;

import javax.swing.ImageIcon;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Tansel
 */
public class Level2Enemy extends Enemy {

    public Level2Enemy(int x, int y) {
        super(x, y);
        image = new ImageIcon(this.getClass().getResource("/images/enemies/enemy2.png")).getImage();
    }

    @Override
    public void move( ArrayList<Brick> bricks, ArrayList<Bomb> bombs, ArrayList<Explosion> explosions) {
        x += dx;
        y += dy;

        for (int i = 0; i < 30; i++) {
            if (getBounds().intersects(walls[i])) {
                x -= dx;
                y -= dy;
            }
        }

        for (int i = 0; i < bombs.size(); i++) {
            if (getBounds().intersects(bombs.get(i).getBounds())) {
                x -= dx;
                y -= dy;
            }
        }

        for (int i = 0; i < explosions.size(); i++) {
            if (getBounds().intersects(explosions.get(i).getBounds()))
                visible = false;
        }

        if (x < 1)
            x = 1;
        if (x > 604)
            x = 604;
        if (y < 1)
            y = 1;
        if (y > 504)
            y = 504;
    }
}
