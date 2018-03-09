package com.view;

import java.awt.Color;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;

import com.manager.SettingsManager;
import com.model.Settings;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Tansel
 */
public class ChangeSettings extends BomberPanel implements ItemListener {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private JLabel subTitle = new JLabel("Hardware Acceleration", JLabel.LEFT);
    private JLabel subTitle2 = new JLabel("Please Choose an Acceleration Method", JLabel.LEFT);
    private JLabel subTitle3 = new JLabel("System Information", JLabel.LEFT);
    private JTextArea info;
    private JTextArea info2;
    private JTextArea info3;
    private JCheckBox checkbox = new JCheckBox("Yes, I want to use hardware acceleration.");
    private JComboBox<String> combobox;

    private JButton save = new JButton("Save Settings");
    private SettingsManager settings;
    private boolean acceleration;

    public ChangeSettings() {
        title = new JLabel("Change Settings", JLabel.LEFT);
        settings = new SettingsManager();

        acceleration = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice()
                .getDefaultConfiguration().getImageCapabilities().isAccelerated()
                && GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration()
                        .getImageCapabilities().isTrueVolatile();

        final String[] renderer = new String[2];
        renderer[0] = "Direct3D";
        renderer[1] = "OpenGL";

        final String inf = "This option allow you to use your default graphic adapter to render images and uses your GPU instead of CPU."
                + " Thus, your other applications can run without interference.\nPlease not that if your hardware does not support acceleration,"
                + " checking the box will not force hardware and settings will set to defaults.";

        String inf2 = "Does your hardware support acceleration: ";
        if (acceleration)
            inf2 += "YES";
        else
            inf2 += "NO";

        String inf3 = "Java version: \t\t" + System.getProperty("java.version") + "\nOperating System: \t"
                + System.getProperty("os.name")
                + "\n\nPlease note that Direct3D rendering will not work on platforms other than Windows. Use OpenGL instead."
                + "\nRecommended Java version > 1.6.0_20";

        combobox = new JComboBox<String>(renderer);
        combobox.setSelectedIndex(settings.getSettings().openGL());
        info = new JTextArea(inf);
        info2 = new JTextArea(inf2);
        info3 = new JTextArea(inf3);

        title.setBounds(140, 10, 320, 48);
        Font font = new Font("Tempus Sans ITC", Font.BOLD, 40);
        title.setFont(font);

        subTitle.setBounds(150, 100, 250, 25);
        font = new Font("Tempus Sans ITC", Font.BOLD, 20);
        subTitle.setFont(font);

        subTitle2.setBounds(150, 245, 440, 25);
        subTitle2.setFont(font);

        subTitle3.setBounds(150, 320, 440, 25);
        subTitle3.setFont(font);

        info.setEditable(false);
        info.setLineWrap(true);
        info.setWrapStyleWord(true);
        info.setBounds(155, 148, 480, 70);
        info.setMargin(new Insets(2, 2, 2, 2));
        info.setBackground(new Color(205, 131, 6, 100));

        info2.setEditable(false);
        info2.setLineWrap(true);
        info2.setWrapStyleWord(true);
        info2.setBounds(155, 220, 480, 17);
        info2.setMargin(new Insets(2, 2, 2, 2));
        info2.setBackground(new Color(205, 131, 6, 100));

        info3.setEditable(false);
        info3.setLineWrap(true);
        info3.setWrapStyleWord(true);
        info3.setBounds(155, 350, 480, 100);
        info3.setMargin(new Insets(2, 2, 2, 2));
        info3.setBackground(new Color(205, 131, 6, 100));

        if (acceleration && settings.getSettings().getAcceleration() == 1) {
            checkbox.setSelected(true);
            combobox.setEnabled(true);
        }
        if (!acceleration || !checkbox.isSelected()) {
            checkbox.setSelected(false);
            combobox.setEnabled(false);
        }

        checkbox.setBorderPaintedFlat(true);
        checkbox.setBounds(150, 130, 260, 15);
        checkbox.addItemListener(this);

        combobox.setBounds(150, 275, 100, 20);

        save.setBounds(530, 520, 115, 20);
        save.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed( ActionEvent e) {
                saveSettings();
            }
        });

        add(title);
        add(subTitle);
        add(subTitle2);
        add(subTitle3);
        add(info);
        add(info2);
        add(info3);
        add(checkbox);
        add(combobox);
        add(save);
    }

    private void saveSettings() {
        if (acceleration) {

            if (checkbox.isSelected())
                settings.addFile(new Settings(1, combobox.getSelectedIndex()));
            else
                settings.addFile(new Settings(0, combobox.getSelectedIndex()));
        } else
            settings.addFile(new Settings(0, combobox.getSelectedIndex()));

        JOptionPane.showMessageDialog(this, "Changes to settings will effect the game after restart!",
                "Settings Saved!", JOptionPane.WARNING_MESSAGE);
    }

    @Override
    public void itemStateChanged( ItemEvent e) {
        if (checkbox.isSelected()) {
            checkbox.setSelected(true);
            combobox.setEnabled(true);
        } else {
            checkbox.setSelected(false);
            combobox.setEnabled(false);
        }
    }
}
