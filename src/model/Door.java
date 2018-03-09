package model;

import java.awt.Image;
import java.awt.Rectangle;

import javax.swing.ImageIcon;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Tansel
 */
public class Door {

    private int x, y;
    private Image image;
    private boolean visible;

    public Door(int x, int y) {
        image = new ImageIcon(this.getClass().getResource("/images/door.png")).getImage();
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

}
