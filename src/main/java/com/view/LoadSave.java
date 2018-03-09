package com.view;

import java.awt.Color;
import java.awt.Font;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextArea;

import com.manager.SaveManager;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Tansel
 */
public class LoadSave extends BomberPanel {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private JTextArea save;

    public LoadSave(JButton[] load) {
        title = new JLabel("Load / Save", JLabel.LEFT);

        final SaveManager saves = new SaveManager();
        String saveText = "Save #\tLevel\tLives\tScore\n"
                + "---------------------------------------------------------------------------------------------------\n";
        for (int i = 0; i < saves.size(); i++)
            saveText += (i + 1) + ".\t" + saves.getFile().get(i).getLevel() + "\t" + saves.getFile().get(i).getLives()
                    + "\t" + saves.getFile().get(i).getScore() + "\n\n";

        for (int i = 0; i < 10; i++) {
            if (i < saves.size())
                load[i].setVisible(true);
        }

        for (int i = 0; i < 10; i++)
            add(load[i]);

        title.setBounds(140, 10, 300, 45);
        Font font = new Font("Tempus Sans ITC", Font.BOLD, 40);
        title.setFont(font);

        save = new JTextArea(saveText);
        save.setEditable(false);
        save.setBackground(new Color(205, 131, 6, 100));
        save.setRows(10);
        save.setColumns(10);
        save.setBounds(180, 70, 310, 360);
        save.setMargin(new Insets(2, 2, 2, 2));

        add(save);
        add(title);
    }
}
