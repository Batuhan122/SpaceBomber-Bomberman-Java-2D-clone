package com.manager;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import com.model.Settings;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Tansel
 */
public class SettingsManager extends FileManager {

    private Settings settings;

    private final static String SETTINGS_FILE = "settings.dat";

    public SettingsManager() {
    }

    public Settings getSettings() {
        loadFile();
        return settings;
    }

    public void addFile( Settings settings) {
        loadFile();
        this.settings = settings;
        updateFile();

    }

    @Override
    public void loadFile() {
        try {
            inputStream = new ObjectInputStream(new FileInputStream(SETTINGS_FILE));
            settings = (Settings) inputStream.readObject();
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
            outputStream = new ObjectOutputStream(new FileOutputStream(SETTINGS_FILE));
            outputStream.writeObject(settings);
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
        if (settings == null)
            return 0;
        else
            return 1;
    }

}
