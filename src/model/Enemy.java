package model;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Tansel
 */

import java.awt.Image;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Random;

public class Enemy {

    protected int random;
    protected int x;
    protected int y;
    protected int dx;
    protected int dy;
    protected int height;
    protected int width;
    protected Image image;
    protected boolean visible;

    protected Random r = new Random();
    protected Rectangle[] walls = new Rectangle[30]; // Unbreakable Walls

    protected int speed = 1;

    public Enemy(int x, int y) {
        visible = true;
        this.x = x;
        this.y = y;
        height = 46;
        width = 46;

        int count = 0;
        for (int i = 0; i < 11; i++)
            for (int j = 0; j < 13; j++) {
                if ((i % 2 == 1) && (j % 2 == 1)) {
                    walls[count] = new Rectangle(j * 50, i * 50, 50, 50);
                    count++;
                }
            }
    }

    public void choosePath() {
        random = r.nextInt(4);
        switch (random) {
        // RIGHT
        case 0:
            dx = speed;
            dy = 0;
            break;
        // LEFT
        case 1:
            dx = -speed;
            dy = 0;
            break;
        // UP
        case 2:
            dy = -speed;
            dx = 0;
            break;
        // DOWN
        case 3:
            dy = speed;
            dx = 0;
            break;
        }

    }

    public void move( ArrayList<Brick> bricks, ArrayList<Bomb> bombs, ArrayList<Explosion> explosions) {
        x += dx;
        y += dy;

        for (int i = 0; i < 30; i++) {
            if (getBounds().intersects(walls[i])) {
                x -= dx;
                y -= dy;
            }
        }

        for (int i = 0; i < bricks.size(); i++) {
            if (getBounds().intersects(bricks.get(i).getBounds())) {
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

    public Image getImage() {
        return image;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    // public void setX( int x)
    // {
    // this.x = x;
    // }
    //
    // public void setY( int y)
    // {
    // this.y = y;
    // }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible( boolean visible) {
        this.visible = visible;
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }

}
