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

import javax.swing.ImageIcon;

public class Brick {

    private int x, y;
    private Image image;
    boolean visible;

    public Brick(int x, int y) {
        image = new ImageIcon(this.getClass().getResource("/images/brick.png")).getImage();
        visible = true;
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
