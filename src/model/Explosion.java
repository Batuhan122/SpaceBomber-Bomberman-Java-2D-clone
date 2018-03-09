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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.Timer;

public class Explosion implements ActionListener {
    private int x, y;
    private Image image;
    private boolean visible;
    private Timer timer;

    public Explosion(int x, int y) {
        image = new ImageIcon(this.getClass().getResource("/images/explosion.gif")).getImage();
        visible = true;
        this.x = x;
        this.y = y;
        timer = new Timer(700, this);
        timer.start();
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

    public Rectangle getBounds() {
        return new Rectangle(x + 2, y + 2, 46, 45);
    }

    @Override
    public void actionPerformed( ActionEvent e) {
        visible = false;
    }
}
