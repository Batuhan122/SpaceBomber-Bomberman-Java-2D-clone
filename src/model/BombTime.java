package model;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Tansel
 */
public class BombTime extends Bomb implements ActionListener {

    private Timer timer;

    public BombTime(int x, int y, int range) {
        super(x, y, range);

        timer = new Timer(3600, this);
        timer.start();
    }

    @Override
    public void actionPerformed( ActionEvent e) {

        visible = false;
    }

}
