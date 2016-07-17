package com.don.galaxydefender.android.logic;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;

public class Player extends Living {

    public Player() {
        this.m_iHeight = Gdx.graphics.getWidth() / 20;
        this.m_iWidth = this.m_iHeight * 3;
        this.m_fCoordX = 20;
        this.m_fCoordY = Gdx.graphics.getHeight() / 2;
        this.m_iCenterX = (int) (this.m_fCoordX + (this.m_iWidth / 2));
        this.m_iCenterY = (int) (this.m_fCoordY + (this.m_iHeight / 2));
        this.m_Rectangle = new Rectangle(this.m_fCoordX, this.m_fCoordY, this.m_iWidth, this.m_iHeight);
        this.m_bIsAlive = true;
        this.m_fSpeedX = 10;
        this.m_fSpeedY = 10;
        this.m_Texture = new Texture(Gdx.files.internal("img/Spaceship_kleiner.png"));
        this.m_iHealthPoints = 100;
        this.m_iShieldPoints = 100;
        this.m_bShooter = true;
        this.m_bShotLock = false;
        this.shootingInterval = 1000000000;
    }

    @Override
    public float getCoordX() {
        return this.m_fCoordX;
    }

    @Override
    public float getCoordY() {
        return this.m_fCoordY;
    }

    @Override
    public int getWidth() {
        return this.m_iWidth;
    }

    @Override
    public int getHeight() {
        return this.m_iHeight;
    }

    @Override
    public Texture getTexture() {
        return this.m_Texture;
    }

    @Override
    public float getSpeedX() {
        return super.getSpeedX();
    }

    @Override
    public float getSpeedY() {
        return super.getSpeedY();
    }

    @Override
    public void move(float speedX, float speedY) {
        this.m_fCoordX += speedX;
        this.m_fCoordY += speedY; // -=, da die Y-Achse nach unten von 0 bis 1080 läuft.

        //Prüfung ob Objekt aus dem Display herausragt und Rücksetzung
        if (this.m_fCoordX >= (Gdx.graphics.getWidth() - this.m_iWidth))
            this.m_fCoordX = Gdx.graphics.getWidth() - this.m_iWidth;
        if (this.m_fCoordX <= 0)
            this.m_fCoordX = 0;
        if (this.m_fCoordY >= Gdx.graphics.getHeight())
            this.m_fCoordY = Gdx.graphics.getHeight();
        if (this.m_fCoordY <= this.m_iHeight)
            this.m_fCoordY = this.m_iHeight;

        updateRectangle();
        updateCenter();
    }
}
