package model;

import java.awt.Image;
import java.awt.Rectangle;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Tansel
 */
public class Bonus {

    private int x, y;
    protected Image image;
    private boolean visible;

    public Bonus(int x, int y) {
        visible = false;
        this.x = x;
        this.y = y;
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

    public Rectangle getBounds() {
        return new Rectangle(x, y, 50, 50);
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible( boolean visible) {
        this.visible = visible;
    }

    public void getBonus( Bomberman bomberman) {
    }
}
