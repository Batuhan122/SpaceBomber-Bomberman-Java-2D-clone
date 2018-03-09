package com.view;

/*
 * This is the Map class of the Bomberman Game.
 * This class is meant to be Bomberman Game Engine itself
 * Due to classification, I gave it "Map" as the name,
 * Because it creates map and its objects. It makes sense, doesn't it?
 */

/**
 *
 * @author Tansel
 */
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;

import com.manager.HighScoreManager;
import com.model.Bomb;
import com.model.Bomberman;
import com.model.Bonus;
import com.model.BonusBombStack;
import com.model.BonusBombType;
import com.model.BonusLife;
import com.model.BonusRange;
import com.model.BonusSpeed;
import com.model.BonusSuperBomber;
import com.model.Brick;
import com.model.Door;
import com.model.Enemy;
import com.model.Explosion;
import com.model.Level1Enemy;
import com.model.Level2Enemy;
import com.model.Level3Enemy;
import com.model.Level4Enemy;

//Since MAP is a JPanel and uses Timer to make some actions:
public class Map extends JPanel implements ActionListener {

    private static final long serialVersionUID = 1L;
    // //////////////////////////////////////////////////////////////////////////
    // These are the some initial values to initialize MAP object/engine
    // //////////////////////////////////////////////////////////////////////////
    private final static int height = 11, width = 13;
    private int[][] grid = new int[height][width];
    private Random r = new Random();
    private int level;

    // //////////////////////////////////////////////////////////////////////////
    // These are the timer related integers.
    // //////////////////////////////////////////////////////////////////////////
    private int timeCounter; // We have limited time to finish the level
    private int timeCounterDoubler; // Explained later
    private int bombermanCounter; // Once Bomberman dies, it waits a little to
                                  // resurrect
    private int bombermanSpeed; // Speed boost is not permanent
    private int bombermanSuper; // Now superman is not some imaginary character
                                // :P
    private Timer timer; // At least, timer itself!

    // //////////////////////////////////////////////////////////////////////////
    // HERE, ALL OBJECTS ON THE SCREEN ARE BEING INITIALIZED
    // //////////////////////////////////////////////////////////////////////////
    private Rectangle[] walls = new Rectangle[30]; // Unbreakable Walls
    private ArrayList<Brick> bricks = new ArrayList<Brick>(); // Bricks
    private ArrayList<Enemy> enemies = new ArrayList<Enemy>(); // Enemies
    private Bomberman bomberman; // Bomberman Itself
    private ArrayList<Bomb> bombs = new ArrayList<Bomb>(); // Bombs
    private ArrayList<Explosion> explosions = new ArrayList<Explosion>(); // Explosions
    private Door door; // Door to exit level once cleaned
    private ArrayList<Bonus> bonus = new ArrayList<Bonus>(); // Bonuses

    // //////////////////////////////////////////////////////////////////////////
    // These are some variables that map uses
    // //////////////////////////////////////////////////////////////////////////
    private Image bgImage; // Map's Background Image
    private Clip explode; // When a bomb explodes
    private URL url2; // Filepath for the audios
    private AudioInputStream audioIn2; // Audio inputs
    private Sequence sequence;
    private Sequencer music; // Background music
    private Font font = new Font("Tempus Sans ITC", Font.PLAIN, 14);

    // //////////////////////////////////////////////////////////////////////////
    // We need to check some ceratin events with booleans!
    // //////////////////////////////////////////////////////////////////////////
    private boolean gameOver; // If user failed
    private boolean levelCompleted; // If all enemies are dead and bomberman
                                    // entered the door
    public boolean pause; // Game can be paused, hell yeah!
    private boolean isDoorShot; // Don't exlode the door, or you'll regret it!

    // //////////////////////////////////////////////////////////////////////////
    // //////////////////////////// THIS IS CONSTRUCTOR
    // /////////////////////////
    // //////////////////////////////////////////////////////////////////////////

    public Map() {
        addKeyListener(new TAdapter()); // We're adding the KeyListener to the
                                        // Map JPanel
        setFocusable(true); // To use keyboard input, JPanel must be Focusable
        requestFocus();
        setDoubleBuffered(true); // To prevent some possible flickerings
        setLayout(null); // Since we don't use any buttons or such, no layout
                         // needed
        setSize(650, 550); // Size is decided already
        setOpaque(true); // Well, anything is possible

        bomberman = null; // This is for initial value due to creation of Map
                          // Class during execution of game

        // This sets the background image!
        try {
            bgImage = ImageIO.read(this.getClass().getResource("/images/bgimage.jpg"));
        } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }

        url2 = this.getClass().getClassLoader().getResource("sfx/explosion.wav"); // Explosion
                                                                                  // sound

        // This sets the audio for the game
        try {
            // Opening audio input streams.
            sequence = MidiSystem.getSequence(new File(this.getClass().getResource("/sfx/musicLow.mid").toURI()));
            music = MidiSystem.getSequencer();
            music.setSequence(sequence);
            music.open();
            audioIn2 = AudioSystem.getAudioInputStream(url2);
            // Get a sound clip resource.
            // music = AudioSystem.getClip();
            explode = AudioSystem.getClip();
            // Open audio clip and load samples from the audio input stream.
            // music.open( audioIn);
            explode.open(audioIn2);
        } catch (FileNotFoundException e) {
            System.err.println(e.toString());
        } catch (UnsupportedAudioFileException e) {
            System.err.println(e.toString());
        } catch (IOException e) {
            System.err.println(e.toString());
        } catch (LineUnavailableException e) {
            System.err.println(e.toString());
        } catch (MidiUnavailableException e) {
            System.err.println(e.toString());
        } catch (InvalidMidiDataException e) {
            System.err.println(e.toString());
        } catch (URISyntaxException e) {
            System.err.println(e.toString());
        }

        // //////////////////////////////////////////////////////////////////////////
        // First, assigning the unbreakable walls as invisible Rectangles. This
        // is constant, same for every level.
        // //////////////////////////////////////////////////////////////////////////
        int wall = 0;
        for (int i = 0; i < height; i++)
            for (int j = 0; j < width; j++) {
                if ((i % 2 == 1) && (j % 2 == 1)) {
                    grid[i][j] = 3;
                    walls[wall] = new Rectangle(j * 50, i * 50, 50, 50);
                    wall++;
                } else
                    grid[i][j] = 0;
            }

        // The reason Timer being defined here is that when you reinitialize,
        // you should not create a new Timer
        timer = new Timer(20, this);
        timer.setInitialDelay(500); // Game will star after a half of a second
    }

    // We initialize the MAP class for its instances, this is where a real Game
    // Engine initializes itself
    public void initialize( Bomberman bomberman, int level) {
        // To use keyboard input, JPanel must be Focusable
        setFocusable(true);
        requestFocus();

        // Clean start for restarts and next levels
        bricks.clear();
        bonus.clear();
        enemies.clear();
        bombs.clear();
        explosions.clear();

        for (int i = 0; i < height; i++)
            for (int j = 0; j < width; j++) {
                if ((i % 2 == 1) && (j % 2 == 1)) {
                    grid[i][j] = 3;
                } else
                    grid[i][j] = 0;
            }

        if (bomberman == null)
            bomberman = new Bomberman();
        else
            this.bomberman = bomberman; // Created or copied bomberman depend on
                                        // new game or level up
        bomberman.setX(0); // Bomberman goes to first square-
        bomberman.setY(0); // -for the next level
        bomberman.setLevel(level); // Sets the level of Map for load/save
                                   // feature

        this.level = level; // Set the level for current level

        // //////////////////////////////////////////////////////////////////////////
        // Second, assigning the breakable walls: BRICKS (this works randomly
        // for every initialization)
        // //////////////////////////////////////////////////////////////////////////
        int counter = 0;
        while (counter < 50) {
            for (int i = 0; i < height; i++)
                for (int j = 0; j < width; j++) {
                    if ((i == 0) && (j == 0))
                        j = j + 2;
                    else if ((i == 1) && (j == 0))
                        j++;
                    if (r.nextBoolean() && (grid[i][j] == 0) && (counter < 50)) {
                        grid[i][j] = 4;
                        counter++;
                        bricks.add(new Brick(j * 50, i * 50));
                    }
                }
        }

        // //////////////////////////////////////////////////////////////////////////
        // Third, assigning the enemies: ENEMY (this works randomly for ever
        // initialization)
        // //////////////////////////////////////////////////////////////////////////
        counter = 0;
        for (int i = 0; i < height; i++)
            for (int j = 0; j < width; j++) {
                if (grid[i][j] == 0 && counter < (5 + level - 1) && (i > 3 && j > 3)) {
                    counter++;

                    // Well, higher the level more challenging the area
                    switch (level) {
                    case 1: // Level 1
                        enemies.add(new Level1Enemy(j * 50, i * 50));
                        break;
                    case 2: // Level 2
                        if (counter < 4)
                            enemies.add(new Level1Enemy(j * 50, i * 50));
                        else
                            enemies.add(new Level2Enemy(j * 50, i * 50));
                        break;
                    case 3: // Level 3
                        if (counter < 3)
                            enemies.add(new Level1Enemy(j * 50, i * 50));
                        else if (counter < 6)
                            enemies.add(new Level2Enemy(j * 50, i * 50));
                        else
                            enemies.add(new Level3Enemy(j * 50, i * 50));
                        break;
                    case 4: // Level 4
                        if (counter < 3)
                            enemies.add(new Level2Enemy(j * 50, i * 50));
                        else
                            enemies.add(new Level3Enemy(j * 50, i * 50));
                        break;
                    case 5: // Level 5
                        enemies.add(new Level3Enemy(j * 50, i * 50));
                        break;
                    default: // Level 6++ and so on, bloody hell ever after!
                        enemies.add(new Level4Enemy(j * 50, i * 50));
                    }
                }

            }

        // //////////////////////////////////////////////////////////////////////////
        // Third, assigning the level exit door: DOOR (this works randomly,
        // places under a brick)
        // //////////////////////////////////////////////////////////////////////////
        Brick b1 = bricks.get(r.nextInt(50));
        door = new Door(b1.getX(), b1.getY());
        // //////////////////////////////////////////////////////////////////////////
        // Third, assigning the bonuses: BONUS (this works randomly, places
        // under a brick)
        // //////////////////////////////////////////////////////////////////////////
        int bo = 0;
        do {
            b1 = bricks.get(r.nextInt(49));
            bo++;
            switch (r.nextInt(7)) {
            case 1:
                bonus.add(new BonusRange(b1.getX(), b1.getY()));
                break;
            case 2:
                if (bomberman.getBombType() == 0)
                    bonus.add(new BonusBombType(b1.getX(), b1.getY()));
                else
                    bo--;
                break;
            case 3:
                bonus.add(new BonusBombStack(b1.getX(), b1.getY()));
                break;
            case 4:
                bonus.add(new BonusLife(b1.getX(), b1.getY()));
                break;
            case 5:
                bonus.add(new BonusSpeed(b1.getX(), b1.getY()));
                break;
            case 6:
                bonus.add(new BonusSuperBomber(b1.getX(), b1.getY()));
                break;
            }
        } while (bo < 2);

        gameOver = false; // Game cannot be over before started
        pause = false; // We wont stop the player befre s/he decides to
        levelCompleted = false; // No pain no gain
        isDoorShot = false; // Door is not exploded yet
        timeCounter = 180 + level * 10; // How many seconds one level will be
        timeCounterDoubler = 0; // Some insignificant instances
        bombermanCounter = 0; // When bomberman dies, it need a little while to
                              // be resurrected
        bombermanSpeed = 21; // When speed bonus held, it will only last for 20
                             // seconds
        bombermanSuper = 16; // When superman bonus held, it will only last for
                             // 15 seconds

        // Finally, add and start the timer, let the game begin!
        timer.restart(); // Every time a new level achieved, timer must be start
                         // from the 0
        music.start();
        music.setTempoInMPQ(500000);
        music.setLoopCount(Sequencer.LOOP_CONTINUOUSLY);
    }

    // //////////////////////////////////////////////////////////////////////////
    // ///// THIS IS WHERE WE DRAW EVERYTHING TO THE SCREEN
    // /////////////////////
    // //////////////////////////////////////////////////////////////////////////
    @Override
    public void paintComponent( Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        g2d.setFont(font);

        // Draw Background image
        g2d.drawImage(bgImage, 0, 0, null);

        // Draw all Explosions
        for (int i = 0; i < explosions.size(); i++)
            g2d.drawImage(explosions.get(i).getImage(), explosions.get(i).getX(), explosions.get(i).getY(), null);

        // Draw all Enemies
        for (int i = 0; i < enemies.size(); i++)
            g2d.drawImage(enemies.get(i).getImage(), enemies.get(i).getX(), enemies.get(i).getY(), null);

        // Draw all Bricks
        for (int i = 0; i < bricks.size(); i++)
            g2d.drawImage(bricks.get(i).getImage(), bricks.get(i).getX(), bricks.get(i).getY(), null);

        // Draw all Bombs
        for (int i = 0; i < bombs.size(); i++)
            g2d.drawImage(bombs.get(i).getImage(), bombs.get(i).getX(), bombs.get(i).getY(), null);

        // Draw Bomberman itself
        if (bomberman.getVisible())
            g2d.drawImage(bomberman.getImage(), bomberman.getX(), bomberman.getY(), null);

        // Draw all the bonuses
        for (int i = 0; i < bonus.size(); i++)
            if (bonus.get(i).isVisible())
                g2d.drawImage(bonus.get(i).getImage(), bonus.get(i).getX(), bonus.get(i).getY(), null);

        // Draw the level exit door
        if (door.isVisible())
            g2d.drawImage(door.getImage(), door.getX(), door.getY(), null);

        // =============================================================//
        // Draw strings
        g2d.setColor(Color.WHITE);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // This is the CLOCK, player need to know how much time s/he has left.
        if (timeCounter > 30 || timeCounterDoubler <= 25 && timeCounter >= 0) // If
                                                                              // there
                                                                              // is
                                                                              // less
                                                                              // then
                                                                              // 30
                                                                              // seconds,
                                                                              // flicker!
                                                                              // Cool
                                                                              // ha?
            g2d.drawString("Time: " + timeCounter / 60 + " : " + timeCounter % 60, 280, 10);

        // This is how many lives bomberman has left
        g2d.drawString("Lives: " + bomberman.getLives(), 0, 10);

        // This is bomberman's score, try to get as highest!
        g2d.drawString("Score: " + bomberman.getScore(), 570, 10);

        // This is the level, which level you're in
        g2d.drawString("Level: " + level, 5, 545);

        if (bombermanSpeed < 21)
            g2d.drawString("Speed: " + bombermanSpeed, 280, 545);
        if (bombermanSuper < 16)
            g2d.drawString("SuperBomber: " + bombermanSuper, 245, 545);

        // If game is paused, print a PAUSED string on the screen
        if (pause) {
            g2d.setColor(Color.RED);
            g2d.drawString("<< PAUSED >>", 275, 260);
        }

        // If level is completed, print the victory on the screen
        if (levelCompleted) {
            g2d.setColor(Color.RED);
            g2d.drawString("<< LEVEL COMPLETED >>", 250, 260);
        }

        // If player failed, then inform him/her that s/he is dead
        if (gameOver) {
            g2d.setColor(Color.RED);
            g2d.drawString("<< YOU ARE DEAD >>", 250, 260);
        }

        Toolkit.getDefaultToolkit().sync(); // This is for some crossplatform
                                            // incompatibilities and syncronized
                                            // refreshing.
        g2d.dispose(); // No drawing leak
        g.dispose(); // Nothing leaks!
    }

    // //////////////////////////////////////////////////////////////////////////
    // ///// WHAT HAPPENS EVERY TIMER CYCLE IS BEING DECIDED HERE
    // ///////////////
    // //////////////////////////////////////////////////////////////////////////
    @Override
    public void actionPerformed( ActionEvent e) {

        // Move the bomberman if it is not dead. Now a dead man cannot move, can
        // he?
        if (bomberman.getVisible())
            bomberman.move(bricks, explosions, enemies);

        // If bomberman dies, we need a little time to resurrect him!
        else {
            bombermanCounter++;
            // Not it is time to revive him
            if (bombermanCounter == 60) {
                bomberman.setVisible(true);
                bomberman.setX(0);
                bomberman.setY(0);
                bombermanCounter = 0;
            }
        }

        // If bomberman steps into gates of heaven, we reward him with the next
        // level
        if (bomberman.getBounds().intersects(door.getBounds()) && enemies.isEmpty())
            levelCompleted = true;

        // If bomberman holds the speed boost, of course it won't be permanent
        if (bomberman.getSpeed() == 2 && timeCounterDoubler == 50) {
            if (bomberman.getVisible()) {
                bombermanSpeed--;
                if (bombermanSpeed == 0) {
                    bomberman.setSpeed(1);
                    bombermanSpeed = 21;
                }
            }
        }
        if (bomberman.getSpeed() == 1) {
            bombermanSpeed = 21;
        }

        // If bomberman holds the superman bonus, it won't be permanent
        if (bomberman.getSuperman() && timeCounterDoubler == 50) {
            bombermanSuper--;
            if (bombermanSuper == 0) {
                bomberman.setSuperman(false);
                bombermanSuper = 16;
            }
        }
        // If bomberman steps into a holy area(bonus), we reward him with the
        // most unimaginable things
        for (int i = 0; i < bonus.size(); i++)
            if (bomberman.getBounds().intersects(bonus.get(i).getBounds())) {
                bonus.get(i).getBonus(bomberman);
                bonus.remove(i);
            }

        // Check the Enemies if they're still alive
        for (int i = 0; i < enemies.size(); i++) {
            if (enemies.get(i).isVisible())
                // If they are STILL ALIVE, move the them to Aperture Science
                enemies.get(i).move(bricks, bombs, explosions);
            else {
                // If an enemy dies of course this get us some points! This
                // depends on enemy user killed
                if (enemies.get(i) instanceof Level4Enemy)
                    bomberman.setScore(bomberman.getScore() + 13);
                else if (enemies.get(i) instanceof Level3Enemy)
                    bomberman.setScore(bomberman.getScore() + 10);
                else if (enemies.get(i) instanceof Level2Enemy)
                    bomberman.setScore(bomberman.getScore() + 7);
                else if (enemies.get(i) instanceof Level1Enemy)
                    bomberman.setScore(bomberman.getScore() + 5);
                // If it is dead, send it to the graveyard!
                enemies.remove(i);
            }
        }

        // Enemies must choose a path some times, like every second?
        if (timeCounterDoubler == 50) {
            for (int i = 0; i < enemies.size(); i++) {
                enemies.get(i).choosePath();
            }
        }

        // Bomb explodes, lots of explosion. Hard to handle them!
        for (int i = 0; i < bombs.size(); i++) {
            boolean wallsDontExplode; // Well, metallic walls don't explode
            boolean onlyOneBrickExplodes; // Only one brick can be destroyed on
                                          // one column or row

            // Every bomb gets its own explosion
            if (!bombs.get(i).isVisible()) // If the bomb exlodes, it gets
                                           // INvisible
            {
                explode.setFramePosition(0);
                explode.start(); // Play the explosion sound, which is very loud
                                 // btw.
                // Now we've come to the beginning, this is big bang. First
                // explosion gets created!
                Explosion explosion = new Explosion(bombs.get(i).getX(), bombs.get(i).getY());
                // If the door gets exploded, there will be some consequences...
                if (explosion.getBounds().intersects(door.getBounds()))
                    isDoorShot = true;
                // This is the initial explosion, breaking point, zero center!
                explosions.add(explosion);

                /*
                 * Here what I did: Firstly, explosions cannot get over the
                 * metallic walls, so I needed to stop them before the metallic
                 * walls. No more explosions beyond metallic walls. Secondly, if
                 * a brick is on the way, it gets destroyed, but the bricks
                 * beyond it have to be safe. So, only one brick gets destroyed
                 * at a time, and even if the range reaches other bricks,
                 * explosion cannot reach them.
                 * 
                 * Finally, level exit door and bonuses are revelaed by the
                 * exploded bricks. So, when a brick get destroyed, we gotta
                 * reveal the mystery behind it.
                 * 
                 * I'll only explain detalis one for loop, others work same. So
                 * remain silent.
                 */
                // ------------------------------------------------------------//
                for (int j = 1; j <= bombs.get(i).getRange(); j++) {
                    explosion = new Explosion(bombs.get(i).getX() + (j * 50), bombs.get(i).getY());
                    wallsDontExplode = true; // Of course walls don't explode!
                    onlyOneBrickExplodes = true; // Of course only one brick
                                                 // explodes on one row or
                                                 // column

                    // Checking if explosion gets to any wall
                    for (int k = 0; k < 30; k++) {
                        // If any wall on the way, no need to continue, just
                        // give it up and break
                        if (explosion.getBounds().intersects(walls[k])) {
                            wallsDontExplode = false; // Somebody tried to
                                                      // explode a wall, what
                                                      // a pity.
                            break;
                        }
                    }

                    for (int k = 0; k < bricks.size(); k++) {
                        // If any brick on the way only the brick gets
                        // destroyed, so destroy it and add just one explosion
                        if (explosion.getBounds().intersects(bricks.get(k).getBounds())) {
                            onlyOneBrickExplodes = false; // Well well, no more
                                                          // brick to explode
                            explosions.add(explosion);

                            // If the door is under the Brick, reveal it
                            if (door.getBounds().intersects(bricks.get(k).getBounds()))
                                door.setVisible(true);
                            // If the bonus is under the Brick, reveal it
                            for (int bon = 0; bon < bonus.size(); bon++)
                                if (bonus.get(bon).getBounds().intersects(bricks.get(k).getBounds()))
                                    bonus.get(bon).setVisible(true);
                            // Then remove the brick
                            bricks.remove(k);
                            break;
                        }
                    }

                    // If no wall or brick on the way, then no reason to not
                    // explode!
                    if (wallsDontExplode && onlyOneBrickExplodes)
                        explosions.add(explosion);
                    // If a wall or brick is on the way, then no reason to
                    // continue
                    else if (!onlyOneBrickExplodes || !wallsDontExplode)
                        break;
                    // If door is shot, then we must know this
                    if (explosion.getBounds().intersects(door.getBounds()))
                        isDoorShot = true;
                    // If a bonus get shot, it should die
                    for (int bon = 0; bon < bonus.size(); bon++)
                        if (bonus.get(bon).getBounds().intersects(explosion.getBounds()))
                            bonus.remove(bon);
                }
                // ------------------------------------------------------------//
                for (int j = 1; j <= bombs.get(i).getRange(); j++) {
                    wallsDontExplode = true;
                    onlyOneBrickExplodes = true;
                    explosion = new Explosion(bombs.get(i).getX() - (j * 50), bombs.get(i).getY());

                    for (int k = 0; k < 30; k++) {
                        if (explosion.getBounds().intersects(walls[k])) {
                            wallsDontExplode = false;
                            break;
                        }
                    }

                    for (int k = 0; k < bricks.size(); k++) {
                        if (explosion.getBounds().intersects(bricks.get(k).getBounds())) {
                            onlyOneBrickExplodes = false;
                            explosions.add(explosion);
                            if (door.getBounds().intersects(bricks.get(k).getBounds()))
                                door.setVisible(true);
                            // If the bonus is under the Brick, reveal it
                            for (int bon = 0; bon < bonus.size(); bon++)
                                if (bonus.get(bon).getBounds().intersects(bricks.get(k).getBounds()))
                                    bonus.get(bon).setVisible(true);
                            bricks.remove(k);
                            break;
                        }
                    }

                    if (wallsDontExplode && onlyOneBrickExplodes)
                        explosions.add(explosion);
                    else if (!onlyOneBrickExplodes)
                        break;
                    else if (!wallsDontExplode)
                        break;
                    if (explosion.getBounds().intersects(door.getBounds()))
                        isDoorShot = true;
                    for (int bon = 0; bon < bonus.size(); bon++)
                        if (bonus.get(bon).getBounds().intersects(explosion.getBounds()))
                            bonus.remove(bon);
                }
                // ------------------------------------------------------------//
                for (int j = 1; j <= bombs.get(i).getRange(); j++) {
                    wallsDontExplode = true;
                    onlyOneBrickExplodes = true;
                    explosion = new Explosion(bombs.get(i).getX(), bombs.get(i).getY() + (j * 50));

                    for (int k = 0; k < 30; k++) {
                        if (explosion.getBounds().intersects(walls[k])) {
                            wallsDontExplode = false;
                            break;
                        }
                    }

                    for (int k = 0; k < bricks.size(); k++) {
                        if (explosion.getBounds().intersects(bricks.get(k).getBounds())) {
                            onlyOneBrickExplodes = false;
                            explosions.add(explosion);
                            if (door.getBounds().intersects(bricks.get(k).getBounds()))
                                door.setVisible(true);
                            // If the bonus is under the Brick, reveal it
                            for (int bon = 0; bon < bonus.size(); bon++)
                                if (bonus.get(bon).getBounds().intersects(bricks.get(k).getBounds()))
                                    bonus.get(bon).setVisible(true);
                            bricks.remove(k);
                            break;
                        }
                    }

                    if (wallsDontExplode && onlyOneBrickExplodes)
                        explosions.add(explosion);
                    else if (!onlyOneBrickExplodes)
                        break;
                    else if (!wallsDontExplode)
                        break;
                    if (explosion.getBounds().intersects(door.getBounds()))
                        isDoorShot = true;
                    for (int bon = 0; bon < bonus.size(); bon++)
                        if (bonus.get(bon).getBounds().intersects(explosion.getBounds()))
                            bonus.remove(bon);
                }
                // ------------------------------------------------------------//
                for (int j = 1; j <= bombs.get(i).getRange(); j++) {
                    wallsDontExplode = true;
                    onlyOneBrickExplodes = true;
                    explosion = new Explosion(bombs.get(i).getX(), bombs.get(i).getY() - (j * 50));

                    for (int k = 0; k < 30; k++) {
                        if (explosion.getBounds().intersects(walls[k])) {
                            wallsDontExplode = false;
                            break;
                        }
                    }

                    for (int k = 0; k < bricks.size(); k++) {
                        if (explosion.getBounds().intersects(bricks.get(k).getBounds())) {
                            onlyOneBrickExplodes = false;
                            explosions.add(explosion);
                            if (door.getBounds().intersects(bricks.get(k).getBounds()))
                                door.setVisible(true);
                            // If the bonus is under the Brick, reveal it
                            for (int bon = 0; bon < bonus.size(); bon++)
                                if (bonus.get(bon).getBounds().intersects(bricks.get(k).getBounds()))
                                    bonus.get(bon).setVisible(true);
                            bricks.remove(k);
                            break;
                        }
                    }

                    if (wallsDontExplode && onlyOneBrickExplodes)
                        explosions.add(explosion);
                    else if (!onlyOneBrickExplodes)
                        break;
                    else if (!wallsDontExplode)
                        break;
                    if (explosion.getBounds().intersects(door.getBounds()))
                        isDoorShot = true;
                    for (int bon = 0; bon < bonus.size(); bon++)
                        if (bonus.get(bon).getBounds().intersects(explosion.getBounds()))
                            bonus.remove(bon);
                }
                // ------------------------------------------------------------//
                // Forgot about the bomb which creates explosion? Better not,
                // because it gets removed!
                bombs.remove(i);
            }
        }

        // Check the explosions if they finished, of course check first if there
        // are any explosions at all!
        if (!explosions.isEmpty()) {
            // Check the explosion if it finally burned out
            if (!explosions.get(0).isVisible()) {
                explosions.clear(); // If burned out, clear them all
                explode.stop(); // Stop the explosion sound
                // Well, door is shot, now you have to face consequences!
                if (isDoorShot) {
                    for (int en = 0; en < 3; en++)
                        enemies.add(new Level2Enemy(door.getX(), door.getY()));
                    isDoorShot = false;
                }
            }
        }

        // Here, we calculate the CLOCK
        if (timeCounterDoubler == 50) {
            timeCounter--;
            timeCounterDoubler = 0;
            // If time is finished, then there will be some consequences
            if (timeCounter == 0)
                timeUp();
        }
        timeCounterDoubler++;

        if (bomberman.getLives() <= 0) // YOU ARE DEAD MAN! YOU ARE DEAD!
            gameOver = true;

        if (gameOver && timeCounterDoubler == 50) // Well, game is over,
                                                  // literally
            gameOver();

        if (levelCompleted && timeCounterDoubler == 50) // Advancing to the next
                                                        // level!
            nextLevel();

        repaint();
    }

    public void pause() {
        timer.stop();
        bomberman.walkSoundStop();
        music.stop();
    }

    public void resume() {
        this.setFocusable(true);
        timer.start();
        music.start();
        music.setLoopCount(Sequencer.LOOP_CONTINUOUSLY);
    }

    private void timeUp() {
        music.setTempoInMPQ(200000);
        enemies.add(new Level4Enemy(1, 1));
        enemies.add(new Level4Enemy(1, 501));
        enemies.add(new Level4Enemy(601, 1));
        enemies.add(new Level4Enemy(601, 501));
    }

    private void menu() {
        pause();
        this.setFocusable(false);
    }

    private void nextLevel() {
        // For next level, we must to clear everything we have! Now we don't
        // want some funny bugs, do we?
        bricks.clear();
        bonus.clear();
        enemies.clear();
        bombs.clear();
        explosions.clear();

        bomberman.setSpeed(1); // Speed boost won't go to the next level
        bomberman.setSuperman(false); // Superman boost won't go to the next
                                      // level

        initialize(bomberman, level + 1);
    }

    private void gameOver() {
        bomberman.setSpeed(1);
        bomberman.setSuperman(false);
        timer.stop();

        HighScoreManager scores = new HighScoreManager();
        if (scores.size() < 10) {
            String highScoreName = (String) JOptionPane.showInputDialog(this,
                    "You've made a high score: " + bomberman.getScore() + "\nPlease enter your name:",
                    "Congratulations!", JOptionPane.INFORMATION_MESSAGE);
            if (highScoreName != null)
                scores.addFile(highScoreName, bomberman.getScore());
        } else if (scores.size() == 10
                && bomberman.getScore() > scores.getFile().get(scores.getFile().size() - 1).getScore()) {
            String highScoreName = (String) JOptionPane.showInputDialog(this,
                    "You've made a high score: " + bomberman.getScore() + "\nPlease enter your name:",
                    "Congratulations!", JOptionPane.INFORMATION_MESSAGE);
            if (highScoreName != null)
                scores.addFile(highScoreName, bomberman.getScore());
        }

        // For destruction that you're responsible, we must to clear everything
        // we have! Now we don't want some funny bugs, do we?
        bricks.clear();
        bonus.clear();
        enemies.clear();
        bombs.clear();
        explosions.clear();

        this.setFocusable(false);
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public int getLevel() {
        return level;
    }

    public Bomberman getBomberman() {
        return bomberman;
    }

    private class TAdapter extends KeyAdapter {

        @Override
        public void keyTyped( KeyEvent e) {
        }

        @Override
        public void keyReleased( KeyEvent e) {

            int key = e.getKeyCode();

            if (!pause)
                bomberman.keyReleased(e);
            if (key == KeyEvent.VK_UP || key == KeyEvent.VK_DOWN || key == KeyEvent.VK_RIGHT || key == KeyEvent.VK_LEFT)
                bomberman.walkSoundStop();
        }

        @Override
        public void keyPressed( KeyEvent e) {
            int key = e.getKeyCode();

            if (gameOver)
                return;

            if (!pause || !bomberman.getVisible())
                bomberman.keyPressed(e);

            if (key == KeyEvent.VK_ENTER)
                for (int i = 0; i < bombs.size(); i++)
                    bombs.get(i).keyPressed(e);

            if (key == KeyEvent.VK_ESCAPE)
                menu();

            if (key == KeyEvent.VK_SPACE)
                if (bombs.size() < bomberman.getBombStack())
                    bombs.add(bomberman.placeBomb());

            if (key == KeyEvent.VK_F11)
                nextLevel();
        }
    }
}
