package com.model;

import java.io.IOException;

import javax.imageio.ImageIO;

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
        try {
            image = ImageIO.read(this.getClass().getResource("/images/enemies/enemy1.png"));
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}
