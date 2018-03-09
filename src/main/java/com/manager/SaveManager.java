package com.manager;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import com.model.Bomberman;
import com.model.Save;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Tansel
 */
public class SaveManager extends FileManager {

    private ArrayList<Save> saves;

    private static final String SAVES_FILE = "saves.dat";

    public SaveManager() {
        saves = new ArrayList<Save>();
    }

    public ArrayList<Save> getFile() {
        loadFile();
        return saves;
    }

    public void addFile( Bomberman bomberman) {
        loadFile();
        if (saves.size() < 10)
            saves.add(new Save(bomberman.getLevel(), bomberman.getLives(), bomberman.getScore(),
                    bomberman.getBombStack(), bomberman.getRange(), bomberman.getBombType()));
        else {
            saves.remove(0);
            saves.add(new Save(bomberman.getLevel(), bomberman.getLives(), bomberman.getScore(),
                    bomberman.getBombStack(), bomberman.getRange(), bomberman.getBombType()));
        }
        updateFile();
    }

    @SuppressWarnings("unchecked")
    @Override
    public void loadFile() {
        try {
            inputStream = new ObjectInputStream(new FileInputStream(SAVES_FILE));
            saves = (ArrayList<Save>) inputStream.readObject();
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
            }

            catch (IOException e) {
            }
        }
    }

    @Override
    public void updateFile() {
        try {
            outputStream = new ObjectOutputStream(new FileOutputStream(SAVES_FILE));
            outputStream.writeObject(saves);
        } catch (FileNotFoundException e) {
        } catch (IOException e) {
        }

        finally {
            try {
                if (outputStream != null) {
                    outputStream.flush();
                    outputStream.close();
                }
            }

            catch (IOException e) {
            }
        }
    }

    @Override
    public int size() {
        loadFile();
        return saves.size();
    }

}
