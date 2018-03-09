package view;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Tansel
 */

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import manager.SaveManager;
import manager.SettingsManager;
import model.Bomberman;
import model.Settings;

public class SpaceBomber extends JFrame {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private JPanel cardPanel;
    private CardLayout layout;

    // Initialization of Buttons and Labels
    protected final JButton play = new JButton(new ImageIcon(this.getClass().getResource("/images/menu/play.png")));
    protected final JButton contin = new JButton(
            new ImageIcon(this.getClass().getResource("/images/menu/continue.png")));
    protected final JButton restart = new JButton("Restart Level");
    protected final JButton loadsave = new JButton(
            new ImageIcon(this.getClass().getResource("/images/menu/loadsave.png")));
    protected final JButton changesettings = new JButton(
            new ImageIcon(this.getClass().getResource("/images/menu/changesettings.png")));
    protected final JButton highscores = new JButton(
            new ImageIcon(this.getClass().getResource("/images/menu/highscores.png")));
    protected final JButton helpB = new JButton(new ImageIcon(this.getClass().getResource("/images/menu/help.png")));
    protected final JButton aboutB = new JButton(new ImageIcon(this.getClass().getResource("/images/menu/about.png")));
    protected final JButton exit = new JButton(new ImageIcon(this.getClass().getResource("/images/menu/exit.png")));
    protected final JButton save = new JButton("Save");
    protected final JButton[] load = new JButton[10];
    protected final JButton[] back = new JButton[5];
    protected final JLabel[] label = new JLabel[6];
    protected final JLabel[] bg = new JLabel[5];
    protected final JLabel ground = new JLabel(
            new ImageIcon(this.getClass().getResource("/images/menu/menubackGround.jpg")));
    protected final JLabel subTitle = new JLabel("No one survives forever...", JLabel.LEFT);

    // Initialization of Panels
    protected final Map map = new Map();
    protected final Menu menu = new Menu();
    protected final Help help = new Help();
    protected final About about = new About();

    public SpaceBomber() {
        // CardPanel Layout
        cardPanel = new JPanel();
        cardPanel.setSize(650, 550);
        layout = new CardLayout();
        cardPanel.setLayout(layout);

        // This is for escaping to menu from game
        map.addFocusListener(new FocusListener() {

            @Override
            public void focusGained( FocusEvent e) {
            }

            @Override
            public void focusLost( FocusEvent e) {
                map.pause();
                if (map.isGameOver()) {
                    contin.setVisible(false);
                    play.setVisible(true);
                    save.setVisible(false);
                    restart.setVisible(false);
                } else {
                    contin.setVisible(true);
                    save.setVisible(true);
                    restart.setVisible(true);
                    play.setVisible(false);
                }
                layout.show(cardPanel, "Menu");
            }
        });

        // Now buttons are getting their attributes
        play.setBounds(235, 15, 180, 90);
        play.setVisible(true);
        play.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed( ActionEvent e) {
                layout.show(cardPanel, "Map");
                map.initialize(new Bomberman(), 1);
                map.requestFocus();
                map.setFocusable(true);
                contin.setVisible(true);
                save.setVisible(true);
                restart.setVisible(true);
                play.setVisible(false);
            }
        });

        contin.setBounds(235, 15, 180, 90);
        contin.setVisible(false);
        contin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed( ActionEvent e) {
                layout.show(cardPanel, "Map");
                map.resume();
                map.requestFocus();
                map.setFocusable(true);
                if (map.pause)
                    map.pause = false;
            }
        });

        restart.setBounds(420, 50, 109, 20);
        restart.setVisible(false);
        restart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed( ActionEvent e) {
                layout.show(cardPanel, "Map");
                map.initialize(map.getBomberman(), map.getBomberman().getLevel());
                map.requestFocus();
                map.setFocusable(true);
            }
        });

        save.setBounds(380, 125, 62, 20);
        save.setVisible(false);
        save.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed( ActionEvent e) {
                SaveManager savemanager = new SaveManager();
                savemanager.getFile();
                savemanager.addFile(map.getBomberman());
                JOptionPane.showMessageDialog(menu, "Successfully Saved!", "Game Saved",
                        JOptionPane.INFORMATION_MESSAGE);
            }
        });

        loadsave.setBounds(275, 110, 100, 50);
        loadsave.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed( ActionEvent e) {
                LoadSave loadSave = new LoadSave(load);
                loadSave.add(back[0]);
                loadSave.add(bg[1]);
                loadSave.add(label[1]);
                cardPanel.add(loadSave, "LoadSave");
                layout.show(cardPanel, "LoadSave");
            }
        });

        changesettings.setBounds(275, 170, 100, 50);
        changesettings.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed( ActionEvent e) {
                ChangeSettings changeSettings = new ChangeSettings();
                changeSettings.add(back[1]);
                changeSettings.add(bg[0]);
                changeSettings.add(label[5]);
                cardPanel.add(changeSettings, "ChangeSettings");
                layout.show(cardPanel, "ChangeSettings");
            }
        });

        highscores.setBounds(275, 230, 100, 50);
        highscores.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed( ActionEvent e) {
                HighScores highScores = new HighScores();
                highScores.add(back[2]);
                highScores.add(bg[2]);
                highScores.add(label[2]);
                cardPanel.add(highScores, "HighScores");
                layout.show(cardPanel, "HighScores");
            }
        });

        helpB.setBounds(275, 290, 100, 50);
        helpB.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed( ActionEvent e) {
                layout.show(cardPanel, "Help");
            }
        });

        aboutB.setBounds(275, 350, 100, 50);
        aboutB.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed( ActionEvent e) {
                layout.show(cardPanel, "About");
            }
        });

        exit.setBounds(275, 410, 100, 50);
        exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed( ActionEvent e) {
                System.exit(0);
            }
        });

        for (int i = 0; i < 5; i++) {
            back[i] = new JButton(new ImageIcon(this.getClass().getResource("/images/menu/back.png")));
            back[i].setBounds(10, 450, 180, 90);
            back[i].addActionListener(new ActionListener() {
                @Override
                public void actionPerformed( ActionEvent e) {
                    layout.show(cardPanel, "Menu");
                }
            });
        }

        for (int i = 0; i < 6; i++) {
            label[i] = new JLabel(new ImageIcon(this.getClass().getResource("/images/menu/background.jpg")));
            label[i].setBounds(0, 0, 650, 550);
        }

        for (int i = 0; i < 10; i++) {
            load[i] = new JButton("Load");
            load[i].setBounds(500, 103 + i * 32, 62, 16);
            load[i].setVisible(false);
        }

        for (int i = 0; i < 5; i++) {
            bg[i] = new JLabel();
            bg[i].setOpaque(true);
            bg[i].setBackground(new Color(205, 131, 6, 100));
            bg[i].setBounds(135, 5, 510, 430);
        }

        ground.setBounds(0, 0, 650, 550);
        subTitle.setBounds(400, 520, 240, 20);
        Font font = new Font("Tempus Sans ITC", Font.BOLD, 20);
        subTitle.setFont(font);
        subTitle.setForeground(Color.BLACK);

        load[0].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed( ActionEvent e) {
                if (JOptionPane.showConfirmDialog(getRootPane(), "Do you want to load this game?", "Loading Game",
                        JOptionPane.YES_NO_OPTION) == 0) {
                    SaveManager saves = new SaveManager();
                    saves.getFile();
                    Bomberman bomberman = new Bomberman();
                    bomberman.setLevel(saves.getFile().get(0).getLevel());
                    bomberman.setLives(saves.getFile().get(0).getLives());
                    bomberman.setScore(saves.getFile().get(0).getScore());
                    bomberman.setBombStack(saves.getFile().get(0).getBombStack());
                    bomberman.setRange(saves.getFile().get(0).getRange());
                    bomberman.setBombType(saves.getFile().get(0).getBombType());
                    map.initialize(bomberman, saves.getFile().get(0).getLevel());
                    layout.show(cardPanel, "Map");
                    map.requestFocus();
                    map.setFocusable(true);
                }
            }
        });

        load[1].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed( ActionEvent e) {
                if (JOptionPane.showConfirmDialog(getRootPane(), "Do you want to load this game?", "Loading Game",
                        JOptionPane.YES_NO_OPTION) == 0) {
                    SaveManager saves = new SaveManager();
                    saves.getFile();
                    Bomberman bomberman = new Bomberman();
                    bomberman.setLevel(saves.getFile().get(1).getLevel());
                    bomberman.setLives(saves.getFile().get(1).getLives());
                    bomberman.setScore(saves.getFile().get(1).getScore());
                    bomberman.setBombStack(saves.getFile().get(1).getBombStack());
                    bomberman.setRange(saves.getFile().get(1).getRange());
                    bomberman.setBombType(saves.getFile().get(1).getBombType());
                    map.initialize(bomberman, saves.getFile().get(1).getLevel());
                    layout.show(cardPanel, "Map");
                    map.requestFocus();
                    map.setFocusable(true);
                }
            }
        });

        load[2].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed( ActionEvent e) {
                if (JOptionPane.showConfirmDialog(getRootPane(), "Do you want to load this game?", "Loading Game",
                        JOptionPane.YES_NO_OPTION) == 0) {
                    SaveManager saves = new SaveManager();
                    saves.getFile();
                    Bomberman bomberman = new Bomberman();
                    bomberman.setLevel(saves.getFile().get(2).getLevel());
                    bomberman.setLives(saves.getFile().get(2).getLives());
                    bomberman.setScore(saves.getFile().get(2).getScore());
                    bomberman.setBombStack(saves.getFile().get(2).getBombStack());
                    bomberman.setRange(saves.getFile().get(2).getRange());
                    bomberman.setBombType(saves.getFile().get(2).getBombType());
                    map.initialize(bomberman, saves.getFile().get(2).getLevel());
                    layout.show(cardPanel, "Map");
                    map.requestFocus();
                    map.setFocusable(true);
                }
            }
        });

        load[3].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed( ActionEvent e) {
                if (JOptionPane.showConfirmDialog(getRootPane(), "Do you want to load this game?", "Loading Game",
                        JOptionPane.YES_NO_OPTION) == 0) {
                    SaveManager saves = new SaveManager();
                    saves.getFile();
                    Bomberman bomberman = new Bomberman();
                    bomberman.setLevel(saves.getFile().get(3).getLevel());
                    bomberman.setLives(saves.getFile().get(3).getLives());
                    bomberman.setScore(saves.getFile().get(3).getScore());
                    bomberman.setBombStack(saves.getFile().get(3).getBombStack());
                    bomberman.setRange(saves.getFile().get(3).getRange());
                    bomberman.setBombType(saves.getFile().get(3).getBombType());
                    map.initialize(bomberman, saves.getFile().get(3).getLevel());
                    layout.show(cardPanel, "Map");
                    map.requestFocus();
                    map.setFocusable(true);
                }
            }
        });

        load[4].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed( ActionEvent e) {
                if (JOptionPane.showConfirmDialog(getRootPane(), "Do you want to load this game?", "Loading Game",
                        JOptionPane.YES_NO_OPTION) == 0) {
                    SaveManager saves = new SaveManager();
                    saves.getFile();
                    Bomberman bomberman = new Bomberman();
                    bomberman.setLevel(saves.getFile().get(4).getLevel());
                    bomberman.setLives(saves.getFile().get(4).getLives());
                    bomberman.setScore(saves.getFile().get(4).getScore());
                    bomberman.setBombStack(saves.getFile().get(4).getBombStack());
                    bomberman.setRange(saves.getFile().get(4).getRange());
                    bomberman.setBombType(saves.getFile().get(4).getBombType());
                    map.initialize(bomberman, saves.getFile().get(4).getLevel());
                    layout.show(cardPanel, "Map");
                    map.requestFocus();
                    map.setFocusable(true);
                }
            }
        });

        load[5].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed( ActionEvent e) {
                if (JOptionPane.showConfirmDialog(getRootPane(), "Do you want to load this game?", "Loading Game",
                        JOptionPane.YES_NO_OPTION) == 0) {
                    SaveManager saves = new SaveManager();
                    saves.getFile();
                    Bomberman bomberman = new Bomberman();
                    bomberman.setLevel(saves.getFile().get(5).getLevel());
                    bomberman.setLives(saves.getFile().get(5).getLives());
                    bomberman.setScore(saves.getFile().get(5).getScore());
                    bomberman.setBombStack(saves.getFile().get(5).getBombStack());
                    bomberman.setRange(saves.getFile().get(5).getRange());
                    bomberman.setBombType(saves.getFile().get(5).getBombType());
                    map.initialize(bomberman, saves.getFile().get(5).getLevel());
                    layout.show(cardPanel, "Map");
                    map.requestFocus();
                    map.setFocusable(true);
                }
            }
        });

        load[6].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed( ActionEvent e) {
                if (JOptionPane.showConfirmDialog(getRootPane(), "Do you want to load this game?", "Loading Game",
                        JOptionPane.YES_NO_OPTION) == 0) {
                    SaveManager saves = new SaveManager();
                    saves.getFile();
                    Bomberman bomberman = new Bomberman();
                    bomberman.setLevel(saves.getFile().get(6).getLevel());
                    bomberman.setLives(saves.getFile().get(6).getLives());
                    bomberman.setScore(saves.getFile().get(6).getScore());
                    bomberman.setBombStack(saves.getFile().get(6).getBombStack());
                    bomberman.setRange(saves.getFile().get(6).getRange());
                    bomberman.setBombType(saves.getFile().get(6).getBombType());
                    map.initialize(bomberman, saves.getFile().get(6).getLevel());
                    layout.show(cardPanel, "Map");
                    map.requestFocus();
                    map.setFocusable(true);
                }
            }
        });

        load[7].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed( ActionEvent e) {
                if (JOptionPane.showConfirmDialog(getRootPane(), "Do you want to load this game?", "Loading Game",
                        JOptionPane.YES_NO_OPTION) == 0) {
                    SaveManager saves = new SaveManager();
                    saves.getFile();
                    Bomberman bomberman = new Bomberman();
                    bomberman.setLevel(saves.getFile().get(7).getLevel());
                    bomberman.setLives(saves.getFile().get(7).getLives());
                    bomberman.setScore(saves.getFile().get(7).getScore());
                    bomberman.setBombStack(saves.getFile().get(7).getBombStack());
                    bomberman.setRange(saves.getFile().get(7).getRange());
                    bomberman.setBombType(saves.getFile().get(7).getBombType());
                    map.initialize(bomberman, saves.getFile().get(7).getLevel());
                    layout.show(cardPanel, "Map");
                    map.requestFocus();
                    map.setFocusable(true);
                }
            }
        });

        load[8].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed( ActionEvent e) {
                if (JOptionPane.showConfirmDialog(getRootPane(), "Do you want to load this game?", "Loading Game",
                        JOptionPane.YES_NO_OPTION) == 0) {
                    SaveManager saves = new SaveManager();
                    saves.getFile();
                    Bomberman bomberman = new Bomberman();
                    bomberman.setLevel(saves.getFile().get(8).getLevel());
                    bomberman.setLives(saves.getFile().get(8).getLives());
                    bomberman.setScore(saves.getFile().get(8).getScore());
                    bomberman.setBombStack(saves.getFile().get(8).getBombStack());
                    bomberman.setRange(saves.getFile().get(8).getRange());
                    bomberman.setBombType(saves.getFile().get(8).getBombType());
                    map.initialize(bomberman, saves.getFile().get(8).getLevel());
                    layout.show(cardPanel, "Map");
                    map.requestFocus();
                    map.setFocusable(true);
                }
            }
        });

        load[9].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed( ActionEvent e) {
                if (JOptionPane.showConfirmDialog(getRootPane(), "Do you want to load this game?", "Loading Game",
                        JOptionPane.YES_NO_OPTION) == 0) {
                    SaveManager saves = new SaveManager();
                    saves.getFile();
                    Bomberman bomberman = new Bomberman();
                    bomberman.setLevel(saves.getFile().get(9).getLevel());
                    bomberman.setLives(saves.getFile().get(9).getLives());
                    bomberman.setScore(saves.getFile().get(9).getScore());
                    bomberman.setBombStack(saves.getFile().get(9).getBombStack());
                    bomberman.setRange(saves.getFile().get(9).getRange());
                    bomberman.setBombType(saves.getFile().get(9).getBombType());
                    map.initialize(bomberman, saves.getFile().get(9).getLevel());
                    layout.show(cardPanel, "Map");
                    map.requestFocus();
                    map.setFocusable(true);
                }
            }
        });

        help.add(back[3]);
        help.add(bg[3]);
        help.add(label[3]);

        about.add(back[4]);
        about.add(bg[4]);
        about.add(label[4]);

        menu.add(play);
        menu.add(contin);
        menu.add(save);
        menu.add(restart);
        menu.add(loadsave);
        menu.add(changesettings);
        menu.add(highscores);
        menu.add(helpB);
        menu.add(aboutB);
        menu.add(exit);
        menu.add(subTitle);
        menu.add(ground);
        menu.add(label[0]);

        cardPanel.add(menu, "Menu");
        cardPanel.add(map, "Map");
        cardPanel.add(help, "Help");
        cardPanel.add(about, "About");

        getContentPane().add(cardPanel);
    }

    private static void setDefaultSettings() {
        SettingsManager settings = new SettingsManager();

        if (settings.size() == 0) {
            final String[] renderer = new String[2];
            renderer[0] = "Direct3D";
            renderer[1] = "OpenGL";

            if (GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration()
                    .getImageCapabilities().isAccelerated()
                    && GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice()
                            .getDefaultConfiguration().getImageCapabilities().isTrueVolatile()) {
                JOptionPane.showMessageDialog(null,
                        "Your graphic card seems to be able to support acceleration."
                                + "\nGame will automatically set to hardware rendering.",
                        "WARNING!", JOptionPane.INFORMATION_MESSAGE);
                System.setProperty("sun.java2d.translaccel", "true");
                System.setProperty("sun.java2d.ddforcevram", "true");

                if (JOptionPane.showOptionDialog(null,
                        "Please choose one of the accelerators:"
                                + "\n(Please note that only Windows systems can use Direct3D)",
                        "Choose Render Method", JOptionPane.NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, renderer,
                        1) == 0) {
                    System.setProperty("sun.java2d.d3d", "true");
                    System.setProperty("sun.java2d.noddraw", "false");
                    settings.addFile(new Settings(1, 0));
                } else {
                    System.setProperty("sun.java2d.opengl", "True");
                    settings.addFile(new Settings(1, 1));
                }

            } else {
                JOptionPane.showMessageDialog(null,
                        "Your graphic card seems to be unable to support hardware acceleration."
                                + "\nGame will automatically set to software rendering."
                                + " Please remember that this can cause some serious performance issues.",
                        "WARNING!", JOptionPane.WARNING_MESSAGE);
            }
        }

        else {
            if (settings.getSettings().getAcceleration() == 1) {
                System.setProperty("sun.java2d.translaccel", "true");
                System.setProperty("sun.java2d.ddforcevram", "true");
                if (settings.getSettings().openGL() == 0) {
                    System.setProperty("sun.java2d.d3d", "true");
                    System.setProperty("sun.java2d.noddraw", "false");
                } else
                    System.setProperty("sun.java2d.opengl", "true");
            }
        }
    }

    /*
     * @param n
     * 
     * @return void Sets the value as seconds to wait for splash screen
     */
    private static void waiting( int n) {
        long t0, t1;
        t0 = System.currentTimeMillis();
        do {
            t1 = System.currentTimeMillis();
        } while ((t1 - t0) < (n * 1000));
    }

    private static void createAndShowGUI() {
        // Make sure we have nice window decorations.
        // JFrame.setDefaultLookAndFeelDecorated(true);
        SpaceBomber sbwc = new SpaceBomber();
        sbwc.setIconImage(new ImageIcon(sbwc.getClass().getResource("/images/enemies/enemy2.png")).getImage());
        sbwc.setSize(656, 578);
        sbwc.setTitle("Space Bomber");
        sbwc.setLocationRelativeTo(null);
        sbwc.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        sbwc.setResizable(false);
        sbwc.setVisible(true);
    }

    public static void main( String[] args) {
        setDefaultSettings();
        waiting(2);
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                createAndShowGUI();
            }
        });
    }

}
