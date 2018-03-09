package com.view;

import java.awt.Color;
import java.awt.Font;
import java.awt.Insets;

import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Tansel
 */
public class Help extends BomberPanel {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private JTextArea help;

    public Help() {
        title = new JLabel("Help", JLabel.LEFT);

        String helpText = "How To Play\n\n" + "Move Bomberman\t- Arrow Keys\n" + "Place Bomb\t\t- Space\n"
                + "Pause\t\t- P\n" + "Unpause\t\t- P (while paused)\n"
                + "Explode Bomb(s)\t- Enter (if necessary bonus held)\n" + "Return to menu\t\t- ESC\n"
                + "----------------------------------------------------------------------------------------------------------\n"
                + "What To Do Ingame\n\n"
                + "Kill all enemies before entering the door to advance to the next level; if any enemy is present, door will not be functional."
                + " Door and bonuses are under the bricks, so one has to explode bricks to reveal them. One level can have 0 to 2 bonuses, depends on one's luck."
                + " If time is over, 4 level4 enemies invades the map from the corners. If door gets exploded,"
                + " 3 level2 enemies enter the map through door(like a worm hole, since it's Space)."
                + " Collision with explosions and/or enemies causes the death of bomberman."
                + "There are 5 bonuses implemented:\n- One more live\n- Larger explosion range\n- More bombs to place\n- Explode bomb"
                + " on command\n- 20 seconds speed boost\n- 15 seconds invincibility\n(If you have any suggestion as bonus, please contact me)."
                + "\n----------------------------------------------------------------------------------------------------------\n"
                + "How To Save & Load\n\n"
                + "After starting a new game or loading a game, pressing ESC will lead you to main menu where you can save your game with"
                + " Save button next to the pink Load/Save button.\n"
                + "You can load any game you want from the Load/Save section of game through pink Load/Save button in menu. You can Load a saved game anytime you want."
                + "\n===================\n"
                + "There is no level limitation, one can go on as long as having at least 1 live. If one wants to make really high scores, waiting for time to be"
                + " over and exploding door get new enemies to kill and earn points. Be aware of that this is also very dangerous since enemies are stronger"
                + " and sometimes faster than bomberman. If game starts to slow down, use Save function then exit game."
                + " Now you can load your game with same level and same achievements, though, your level will start from scratch"
                + " and all locations including bonuses will change. It might be worthy exchange till all optimizations implemented."
                + "\n----------------------------------------------------------------------------------------------------------\n"
                + "Known Issues:\n\n" + "All features should work as intended.\n"
                + " Please report any bugs(if present) to tansel[at]tanshaydar.com\n\n"
                + "IMPORTANT!: On linux, some sound libraries can cause conflicts, and OpenJDK might not offer good performance due to lack of compatibility of the game.\n"
                + "----------------------------------------------------------------------------------------------------------\n"
                + "LICENCE INFORMATION\n" + "There is actually no licence since this is just a term project.\n"
                + "Enemy images taken from:      http://www.iconarchive.com/category/movie/alien-vs-predator-2-icons-by-iconshock.html\n"
                + "Bomberman image taken from:   http://sourceforge.net/projects/javabomberman/\n"
                + "All other images and coding made by me, so if you want to use or share, please contact tansel[at]tanshaydar.com\n";

        help = new JTextArea(helpText);
        // help.setBackground( new Color(205,131,6,100));
        help.setLineWrap(true);
        help.setWrapStyleWord(true);
        help.setDragEnabled(false);
        help.setMargin(new Insets(2, 2, 2, 2));
        JScrollPane scrollPane = new JScrollPane(help);
        scrollPane.setBounds(180, 90, 450, 350);
        scrollPane.setBackground(new Color(205, 131, 6, 100));
        scrollPane.setHorizontalScrollBar(null);
        scrollPane.setWheelScrollingEnabled(true);
        scrollPane.updateUI();

        title.setBounds(140, 10, 200, 45);
        Font font = new Font("Tempus Sans ITC", Font.BOLD, 40);
        title.setFont(font);

        add(title);
        add(scrollPane);
    }

}
