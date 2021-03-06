package com.model;

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
import java.awt.event.KeyEvent;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Bomb {

    private int x, y, range;
    private Image image;
    protected boolean visible;

    public Bomb(int x, int y, int range) {
        try {
            image = ImageIO.read(this.getClass().getResource("/images/bomb.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        visible = true;
        this.x = x;
        this.y = y;
        this.range = range;
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

    public boolean isVisible() {
        return visible;
    }

    public void setVisible( boolean visible) {
        this.visible = visible;
    }

    public int getRange() {
        return range;
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, 50, 50);
    }

    public void keyPressed( KeyEvent e) {
    }
}
