package com.don.galaxydefender.logic;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;

public abstract class Moveable {

    protected float m_fCoordX;
    protected float m_fCoordY;
    protected int m_iWidth;
    protected int m_iHeight;
    protected int m_iCenterX;
    protected int m_iCenterY;
    protected int m_iDamage;
    protected float m_fSpeedX;
    protected float m_fSpeedY;
    protected boolean m_bIsAlive;
    protected Texture m_Texture; //maybe unnecessary
    protected Rectangle m_Rectangle;

    public float getCoordX() {
        return m_fCoordX;
    }

    public void setCoordX(int coordX) {
        this.m_fCoordX = coordX;
    }

    public float getCoordY() {
        return m_fCoordY;
    }

    public void setCoordY(int coordY) {
        this.m_fCoordY = coordY;
    }

    public int getWidth() {
        return m_iWidth;
    }

    public void setWidth(int width) {
        this.m_iWidth = width;
    }

    public int getHeight() {
        return m_iHeight;
    }

    public void setHeight(int height) {
        this.m_iHeight = height;
    }

    public int getCenterX() {
        return m_iCenterX;
    }

    public void setCenterX(int centerX) {
        this.m_iCenterX = centerX;
    }

    public int getCenterY() {
        return m_iCenterY;
    }

    public void setCenterY(int centerY) {
        this.m_iCenterY = centerY;
    }

    public int getDamage() {
        return m_iDamage;
    }

    public void setDamage(int damage) {
        this.m_iDamage = damage;
    }

    public float getSpeedX() {
        return m_fSpeedX;
    }

    public void setSpeedX(float speedX) {
        this.m_fSpeedX = speedX;
    }

    public float getSpeedY() {
        return m_fSpeedY;
    }

    public void setSpeedY(float speedY) {
        this.m_fSpeedY = speedY;
    }

    public boolean getAlive() {
        return m_bIsAlive;
    }

    public void setAlive(boolean alive) {
        this.m_bIsAlive = alive;
    }

    public Texture getTexture() {
        return m_Texture;
    }

    public void setTexture(Texture texture) {
        this.m_Texture = texture;
    }

    public Rectangle getRectangle() {
        return m_Rectangle;
    }

    public void setRectangle(Rectangle m_Rectangle) {
        this.m_Rectangle = m_Rectangle;
    }

    public void move(float speedX, float speedY) {
        this.m_fCoordX += speedX;
        this.m_fCoordY += speedY;

        updateRectangle();
        updateCenter();
    }

    protected void updateRectangle() {
        this.m_Rectangle.x = this.m_fCoordX;
        this.m_Rectangle.y = this.m_fCoordY - this.m_iHeight;   // Höhe des Objekt abrechnen, da der Ursprung des
                                                                // Rectangle links oben ist und nicht links unten.
    }

    protected void updateCenter() {
        this.m_iCenterX = (int) (this.m_fCoordX + (this.m_iWidth / 2));
        this.m_iCenterY = (int) (this.m_fCoordY + (this.m_iHeight / 2));
    }
}
