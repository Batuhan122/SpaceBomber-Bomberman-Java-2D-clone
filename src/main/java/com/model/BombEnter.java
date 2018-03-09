package com.model;

import java.awt.event.KeyEvent;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Tansel
 */
public class BombEnter extends Bomb {

    public BombEnter(int x, int y, int range) {
        super(x, y, range);
    }

    @Override
    public void keyPressed( KeyEvent e) {

        int key = e.getKeyCode();

        if (key == KeyEvent.VK_ENTER)
            visible = false;
    }

}
