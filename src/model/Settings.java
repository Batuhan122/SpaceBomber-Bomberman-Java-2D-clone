package model;

import java.io.Serializable;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Tansel
 */
public class Settings implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private int hardwareAcceleration;
    private int openGLvsDirect3D;

    public Settings(int hardwareAcceleration, int openGLvsDirect3D) {
        this.hardwareAcceleration = hardwareAcceleration;
        this.openGLvsDirect3D = openGLvsDirect3D;
    }

    public int getAcceleration() {
        return hardwareAcceleration;
    }

    public int openGL() {
        return openGLvsDirect3D;
    }
}
