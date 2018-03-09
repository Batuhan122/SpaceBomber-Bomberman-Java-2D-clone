package model;

/*
 * To change this template, choose Tools | Templates 
 * and open the template in the editor.
 */

/**
 *
 * @author Tansel
 */
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.ImageIcon;

public class Bomberman {

    private int dx;
    private int dy;
    private int x;
    private int y;
    private int height;
    private int width;
    private boolean visible;
    private Image image;
    private Image up, down, right, left;
    private Image upW, downW, rightW, leftW;

    private int lives;
    private int score;
    private int bombStack;
    private int range;
    private int bombType;
    private int speed;
    private boolean superman;

    private int level;
    private Rectangle[] walls = new Rectangle[30]; // Unbreakable Walls

    protected Clip die, walk;
    private URL url, url2;
    private AudioInputStream audioIn, audioIn2;

    public Bomberman() {

        // Read all images to the memory
        up = new ImageIcon(this.getClass().getResource("/images/bomberman/up.gif")).getImage();
        down = new ImageIcon(this.getClass().getResource("/images/bomberman/down.gif")).getImage();
        right = new ImageIcon(this.getClass().getResource("/images/bomberman/right.gif")).getImage();
        left = new ImageIcon(this.getClass().getResource("/images/bomberman/left.gif")).getImage();
        leftW = new ImageIcon(this.getClass().getResource("/images/bomberman/b_left.gif")).getImage();
        rightW = new ImageIcon(this.getClass().getResource("/images/bomberman/b_right.gif")).getImage();
        upW = new ImageIcon(this.getClass().getResource("/images/bomberman/b_up.gif")).getImage();
        downW = new ImageIcon(this.getClass().getResource("/images/bomberman/b_down.gif")).getImage();
        image = rightW; // Initial image

        // These are unchangeable attributes of bomberman
        x = 0;
        y = 0;
        height = 42;
        width = 42;
        visible = true;
        lives = 3;
        score = 0;
        level = 1;

        // Initialize sounds
        url = this.getClass().getClassLoader().getResource("sfx/die.wav");
        url2 = this.getClass().getClassLoader().getResource("sfx/footstep.wav");
        try {
            audioIn = AudioSystem.getAudioInputStream(url);
            audioIn2 = AudioSystem.getAudioInputStream(url2);
            die = AudioSystem.getClip();
            walk = AudioSystem.getClip();
        } catch (LineUnavailableException ex) {
        } catch (UnsupportedAudioFileException ex) {
        } catch (IOException ex) {
        }

        soundsOpen();

        int count = 0;
        for (int i = 0; i < 11; i++)
            for (int j = 0; j < 13; j++) {
                if ((i % 2 == 1) && (j % 2 == 1)) {
                    walls[count] = new Rectangle(j * 50, i * 50, 50, 50);
                    count++;
                }
            }

        initialize();
    }

    private void initialize() {
        // These are the changeable attributions of bomberman class
        speed = 1;
        bombStack = 1;
        range = 1;
        bombType = 0;
        superman = false;
        image = rightW;
    }

    public void move( ArrayList<Brick> bricks, ArrayList<Explosion> explosions, ArrayList<Enemy> enemies) {
        x += dx;
        y += dy;

        for (int i = 0; i < 30; i++) {
            if (getBounds().intersects(walls[i])) {
                x -= dx;
                y -= dy;
            }
        }

        for (int i = 0; i < bricks.size(); i++) {
            if (getBounds().intersects(bricks.get(i).getBounds())) {
                x -= dx;
                y -= dy;
            }
        }

        for (int i = 0; i < explosions.size(); i++) {
            if (getBounds().intersects(explosions.get(i).getBounds())) {
                if (!superman) {
                    lives--;
                    visible = false;
                    die.start();
                    initialize();
                    break;
                }
            }
        }

        for (int i = 0; i < enemies.size(); i++) {
            if (getBounds().intersects(enemies.get(i).getBounds())) {
                if (superman) {
                    enemies.remove(i);
                    setScore(score + 10);
                } else {
                    lives--;
                    visible = false;
                    initialize();
                    die.start();
                }
            }
        }

        if (x < 1)
            x = 1;
        if (x > 604)
            x = 604;
        if (y < 1)
            y = 1;
        if (y > 504)
            y = 504;
    }

    public int getX() {
        return x;
    }

    public void setX( int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY( int y) {
        this.y = y;
    }

    public Image getImage() {
        return image;
    }

    public int getLives() {
        return lives;
    }

    public void setLives( int live) {
        lives = live;
    }

    public boolean getVisible() {
        return visible;
    }

    public void setVisible( boolean visible) {
        this.visible = visible;
        die.stop();
        die.setFramePosition(0);
    }

    public int getBombType() {
        return bombType;
    }

    public void setBombType( int type) {
        bombType = type;
    }

    public int getBombStack() {
        return bombStack;
    }

    public void setBombStack( int stack) {
        bombStack = stack;
    }

    public int getScore() {
        return score;
    }

    public void setScore( int score) {
        this.score = score;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed( int speed) {
        this.speed = speed;
    }

    public boolean getSuperman() {
        return superman;
    }

    public void setSuperman( boolean superman) {
        this.superman = superman;
    }

    public int getRange() {
        return range;
    }

    public void setRange( int range) {
        this.range = range;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel( int level) {
        this.level = level;
    }

    public Rectangle getBounds() {
        return new Rectangle(x + 4, y + 4, width - 3, height);
    }

    public Bomb placeBomb() {
        if (bombType == 0)
            return (new BombTime(((x + 23) / 50) * 50, ((y + 23) / 50) * 50, range));
        else
            return (new BombEnter(((x + 23) / 50) * 50, ((y + 23) / 50) * 50, range));
    }

    public void walkSound() {
        walk.loop(Clip.LOOP_CONTINUOUSLY);
    }

    public void walkSoundStop() {
        walk.stop();
        walk.setFramePosition(0);
    }

    public void soundsOpen() {
        if (!die.isOpen() && !walk.isOpen()) {
            try {
                die.open(audioIn);
                walk.open(audioIn2);
            } catch (IOException e) {
            } catch (LineUnavailableException e) {
            }
        }
    }

    /*
     * public void soundsClose() { walk.close(); die.close(); }
     */

    public void keyPressed( KeyEvent e) {

        int key = e.getKeyCode();

        if (!walk.isRunning() && (key == KeyEvent.VK_LEFT || key == KeyEvent.VK_RIGHT || key == KeyEvent.VK_UP
                || key == KeyEvent.VK_DOWN)) {
            walk.setFramePosition(0);
            walk.loop(Clip.LOOP_CONTINUOUSLY);
        }

        if (key == KeyEvent.VK_LEFT) {
            dx = -speed;
            image = left;
        }

        if (key == KeyEvent.VK_RIGHT) {
            dx = speed;
            image = right;
        }

        if (key == KeyEvent.VK_UP) {
            dy = -speed;
            image = up;
        }

        if (key == KeyEvent.VK_DOWN) {
            dy = speed;
            image = down;
        }
    }

    public void keyReleased( KeyEvent e) {

        int key = e.getKeyCode();

        if (key == KeyEvent.VK_LEFT) {
            dx = 0;
            image = leftW;
        }

        if (key == KeyEvent.VK_RIGHT) {
            dx = 0;
            image = rightW;
        }

        if (key == KeyEvent.VK_UP) {
            dy = 0;
            image = upW;
        }

        if (key == KeyEvent.VK_DOWN) {
            dy = 0;
            image = downW;
        }
    }
}
