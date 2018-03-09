package view;

import java.awt.Color;
import java.awt.Font;
import java.awt.Insets;

import javax.swing.JLabel;
import javax.swing.JTextArea;

import manager.HighScoreManager;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Tansel
 */
public class HighScores extends BomberPanel {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private JLabel bg = new JLabel();
    private JTextArea score;

    public HighScores() {
        title = new JLabel("High Scores", JLabel.LEFT);

        HighScoreManager scores = new HighScoreManager();
        String highScoreText = "Score#\tName\t\tScore\n"
                + "----------------------------------------------------------------------------------------------------------------------------\n";
        if (!scores.getFile().isEmpty())
            for (int i = 0; i < scores.size(); i++)
                highScoreText += (i + 1) + ".\t" + scores.getFile().get(i).getName() + "\t\t"
                        + scores.getFile().get(i).getScore() + "\n\n";

        title.setBounds(140, 10, 300, 45);
        Font font = new Font("Tempus Sans ITC", Font.BOLD, 40);
        title.setFont(font);

        bg.setOpaque(true);
        bg.setBackground(new Color(205, 131, 6, 100));
        bg.setBounds(135, 5, 510, 430);

        score = new JTextArea(highScoreText);
        score.setEditable(false);
        score.setBackground(new Color(205, 131, 6, 100));
        score.setRows(10);
        score.setColumns(10);
        score.setBounds(180, 70, 415, 350);
        score.setMargin(new Insets(2, 2, 2, 2));

        add(score);
        add(title);
        add(bg);
    }

}
