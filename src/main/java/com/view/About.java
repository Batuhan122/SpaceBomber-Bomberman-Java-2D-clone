package com.view;

import java.awt.Color;
import java.awt.Font;
import java.awt.Insets;

import javax.swing.JLabel;
import javax.swing.JTextArea;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Tansel
 */
public class About extends BomberPanel {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private JTextArea about;

    public About() {
        title = new JLabel("About", JLabel.LEFT);

        String aboutText = "Space Bomber v1.0.2 \n"
                + "----------------------------------------------------------------------------------------------------------"
                + "\n Bilkent Computer Science\n Fall 2010\n CS319 Project Group 1J\n\n" + " Developers: \n"
                + "\tTansel Altınel" + "\n\n Special Thanks To:" + "\n\thttp://www.java-forums.org"
                + "\n\thttp://www.java-tips.org" + "\n\tOracle forums & Java API";
        title.setBounds(140, 10, 200, 45);
        Font font = new Font("Tempus Sans ITC", Font.BOLD, 40);
        title.setFont(font);

        about = new JTextArea(aboutText);
        about.setEditable(false);
        about.setEnabled(false);
        about.setAutoscrolls(true);
        about.setBackground(new Color(205, 131, 6, 100));
        about.setRows(10);
        about.setColumns(10);
        about.setBounds(180, 90, 425, 330);
        about.setMargin(new Insets(2, 2, 2, 2));

        add(about);
        add(title);
    }
}
