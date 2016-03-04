package com.don.galaxydefender.controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.don.galaxydefender.logic.Player;

import java.awt.Rectangle;

/**
 * Created by Carsten on 01.11.2015.
 */
//TODO: Feintuning der Geschwindigkeiten, Buttons für die Schusseingabe etc.
public class Game_Controller {
    private static final String TAG = "package com.don.galaxydefender.controller;";
    private final int buttonWidth = 156;
    private int m_iSizeX, m_iSizeY;
    private int m_iScreenSizeX, m_iScreenSizeY;
    private Texture m_TextureController, m_TextureButtonA, m_TextureButtonB, m_TextureButtonX, m_TextureButtonY;
    private int m_iTouchX1, m_iTouchY1, m_iTouchX2, m_iTouchY2;
    private Sprite m_spriteController, m_spriteButtonA, m_spriteButtonB, m_spriteButtonX, m_spriteButtonY;
    private int m_iMidPointX, m_iMidPointY;
    private int m_iDirX, m_iDirY;
    private float m_fSpeedPercent;
    private float m_fDamper = 0.4f;
    private boolean m_bButtonA, m_bButtonB, m_bButtonX, m_bButtonY;

    //Constructor
    public Game_Controller(int screenSizeX, int screenSizeY) {
        this.m_iScreenSizeX = screenSizeX;
        this.m_iScreenSizeY = screenSizeY;
        m_iSizeX = this.m_iScreenSizeY / 3;
        m_iSizeY = m_iSizeX;
        this.m_TextureController = new Texture(Gdx.files.internal("img/Controller/Controller.png"));
        this.m_TextureButtonA = new Texture(Gdx.files.internal("img/Controller/Button_A.png"));
        this.m_TextureButtonB = new Texture(Gdx.files.internal("img/Controller/Button_B.png"));
        this.m_TextureButtonX = new Texture(Gdx.files.internal("img/Controller/Button_X.png"));
        this.m_TextureButtonY = new Texture(Gdx.files.internal("img/Controller/Button_Y.png"));
        m_spriteController = new Sprite(this.m_TextureController);
        m_spriteController.setSize(m_iSizeX, m_iSizeY);

        m_spriteButtonA = new Sprite(this.m_TextureButtonA);
        m_spriteButtonA.setSize(buttonWidth, buttonWidth);
        m_spriteButtonB = new Sprite(this.m_TextureButtonB);
        m_spriteButtonB.setSize(buttonWidth, buttonWidth);
        m_spriteButtonX = new Sprite(this.m_TextureButtonX);
        m_spriteButtonX.setSize(buttonWidth, buttonWidth);
        m_spriteButtonY = new Sprite(this.m_TextureButtonY);
        m_spriteButtonY.setSize(buttonWidth, buttonWidth);

        //m_spriteController = new Sprite(this.m_TextureController, m_iSizeX, m_iSizeY);
        m_iMidPointX = m_iSizeX / 2;
        m_iMidPointY = m_iScreenSizeY - m_iSizeY / 2;
        m_bButtonA = false;
        m_bButtonB = false;
        m_bButtonX = false;
        m_bButtonY = false;
        m_fSpeedPercent = 0;
    }


    public void render(SpriteBatch batch) {
        batch.draw(m_spriteController, 0, 0);
        batch.draw(m_spriteButtonA, m_iScreenSizeX - 2 * buttonWidth, 0);
        batch.draw(m_spriteButtonB, m_iScreenSizeX - buttonWidth, buttonWidth);
        batch.draw(m_spriteButtonX, m_iScreenSizeX - 3 * buttonWidth, buttonWidth);
        batch.draw(m_spriteButtonY, m_iScreenSizeX - 2 * buttonWidth, 2 * buttonWidth);

    }

    public float[] checkInputMovement() {
        boolean touch1 = false;
        boolean touch2 = false;
        float[] movement;
        movement = new float[2];

        m_iTouchX1 = Gdx.input.getX(0);
        m_iTouchY1 = Gdx.input.getY(0);

        m_iTouchX2 = Gdx.input.getX(1);
        m_iTouchY2 = Gdx.input.getY(1);

        if (m_iTouchX1 <= m_iSizeX && m_iTouchY1 >= (m_iScreenSizeY - m_iSizeY) && Gdx.input.isTouched(0)) {
            touch1 = true;
            m_fSpeedPercent = calculateSpeed(m_iTouchX1, m_iTouchY1);

        } else if (m_iTouchX2 <= m_iSizeX && m_iTouchY2 >= m_iScreenSizeY - m_iSizeY && Gdx.input.isTouched(1)) {
            m_fSpeedPercent = calculateSpeed(m_iTouchX2, m_iTouchY2);
        }
        else {
            m_fSpeedPercent = 0;
        }

        if (!(touch1) && m_iTouchX1 >= (m_iScreenSizeX - 3 * buttonWidth) && m_iTouchY1 >= (m_iScreenSizeY - 3 * buttonWidth) && Gdx.input.isTouched(0)) {
            whichButtonPressed(m_iTouchX1, m_iTouchY1);
        } else if (!(touch2) && m_iTouchX2 >= (m_iScreenSizeX - 3 * buttonWidth) && m_iTouchY2 >= (m_iScreenSizeY - 3 * buttonWidth) && Gdx.input.isTouched(1)) {
            whichButtonPressed(m_iTouchX2, m_iTouchY2);
        } else {
            m_bButtonA = false;
            m_bButtonB = false;
            m_bButtonX = false;
            m_bButtonY = false;
        }
        movement[0] = (m_iDirX * m_fSpeedPercent * m_fDamper);
        movement[1] = -(m_iDirY * m_fSpeedPercent * m_fDamper);
        return movement;


    }

    public char checkInputButtons() {
        char buttonPressed;
        if (m_bButtonA) {
            Gdx.app.log(TAG, "A pressed!");
            buttonPressed = 'A';
        } else if (m_bButtonB) {
            Gdx.app.log(TAG, "B pressed!");
            buttonPressed = 'B';
        } else if (m_bButtonX) {
            Gdx.app.log(TAG, "X pressed!");
            buttonPressed = 'X';
        } else if (m_bButtonY) {
            Gdx.app.log(TAG, "Y pressed!");
            buttonPressed = 'Y';
        } else {
            buttonPressed = ' ';
        }
        return buttonPressed;
    }


    /**
     * Methode rechnet Vektor zu Mittelpunkt des Steuerkreuzes. Gibt die Vektorlänge in Prozent
     * zur maximalen Vektorlänge von m_SpriteLeft.Height / 2 zurück.
     * Setzt gleichzeitig den Vektor für die Bewegungsrichtung.
     *
     * @param X Touchinput x-Wert
     * @param Y Touchinput y-Wert
     * @return Länge des Richtungsvektors in Prozent
     */

    private float calculateSpeed(int X, int Y) {


        m_iDirX = X - m_iMidPointX;
        m_iDirY = Y - m_iMidPointY;

        return (float) (Math.sqrt(m_iDirX * m_iDirX + m_iDirY * m_iDirY) / (m_spriteController.getHeight() / 2));


    }

    /**
     * Methode ermittelt, welcher der Buttons gedrückt ist.
     *
     * @param X Touchinput x-Wert
     * @param Y Touchinput y-Wert
     * @return void
     */

    private void whichButtonPressed(int X, int Y) {
        //Button X
        if (X <= m_iScreenSizeX - buttonWidth * 2 && Y >= m_iScreenSizeY - 2 * buttonWidth && Y <= m_iScreenSizeY - buttonWidth) {
            Gdx.app.log(TAG, "X pressed!");
            m_bButtonX = true;
        } else if (X >= m_iScreenSizeX - buttonWidth * 2 && X <= m_iScreenSizeX - buttonWidth) {
            //Button A
            if (Y >= m_iScreenSizeY - buttonWidth) {
                Gdx.app.log(TAG, "A pressed!");
                m_bButtonA = true;
            }
            //Button Y
            else if (Y <= m_iScreenSizeY - 2 * buttonWidth) {
                Gdx.app.log(TAG, "Y pressed!");
                m_bButtonY = true;
            } else {
                m_bButtonA = false;
                m_bButtonB = false;
                m_bButtonX = false;
                m_bButtonY = false;
            }
        } else if (Y >= m_iScreenSizeY - 2 * buttonWidth && Y <= m_iScreenSizeY - buttonWidth) {
            Gdx.app.log(TAG, "B pressed!");
            m_bButtonB = true;
        } else {
            m_bButtonA = false;
            m_bButtonB = false;
            m_bButtonX = false;
            m_bButtonY = false;
        }

    }
}
