package com.manager;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collections;

import com.model.Score;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Tansel
 */
public class HighScoreManager extends FileManager {

    private ArrayList<Score> scores;

    private static final String HIGH_SCORES_FILE = "scores.dat";

    public HighScoreManager() {
        scores = new ArrayList<Score>();
    }

    public ArrayList<Score> getFile() {
        loadFile();
        sort();
        return scores;
    }

    private void sort() {
        Collections.sort(scores);
    }

    @Override
    public void addFile( String name, int score) {
        loadFile();
        if (scores.size() <= 10)
            scores.add(new Score(name, score));
        else if (score >= scores.get(scores.size() - 1).getScore())
            scores.add(new Score(name, score));
        sort();
        for (int i = 10; i < scores.size(); i++)
            scores.remove(i);
        updateFile();
    }

    @SuppressWarnings("unchecked")
    @Override
    public void loadFile() {
        try {
            inputStream = new ObjectInputStream(new FileInputStream(HIGH_SCORES_FILE));
            scores = (ArrayList<Score>) inputStream.readObject();
            inputStream.close();
        } catch (FileNotFoundException e) {
        } catch (IOException e) {
        } catch (ClassNotFoundException e) {
        }

        finally {
            try {
                if (outputStream != null) {
                    outputStream.flush();
                    outputStream.close();
                }
            } catch (IOException e) {
            }
        }
    }

    @Override
    public void updateFile() {
        try {
            outputStream = new ObjectOutputStream(new FileOutputStream(HIGH_SCORES_FILE));
            outputStream.writeObject(scores);
        } catch (FileNotFoundException e) {
        } catch (IOException e) {
        }

        finally {
            try {
                if (outputStream != null) {
                    outputStream.flush();
                    outputStream.close();
                }
            } catch (IOException e) {
            }
        }
    }

    @Override
    public int size() {
        loadFile();
        return scores.size();
    }

}
