package com.don.galaxydefender.android.logic;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;

public class Enemy extends Living {
    private EnemyType enemyType;
    private double appearanceTime;

    public Enemy(EnemyType enemyType, int posX, int posY, MovementType movType, double time) {
        appearanceTime = time;
        switch (enemyType){
            case TOWER:
                this.enemyType = enemyType;
                this.m_iWidth = 50;
                this.m_iHeight = 50;
                this.m_fCoordX = Gdx.graphics.getWidth() - posX;
                this.m_fCoordY = Gdx.graphics.getHeight() / 2 - posY;
                this.m_iCenterX = (int)(this.m_fCoordX + (m_iWidth / 2));
                this.m_iCenterY = (int)(this.m_fCoordY + (m_iHeight / 2));
                this.m_Rectangle = new Rectangle(this.m_fCoordX, this.m_fCoordY, this.m_iWidth, this.m_iHeight);
                this.m_bIsAlive = true;
                this.m_fSpeedX = -5;
                this.m_fSpeedY = 10;
                this.m_iDamage = 50;
                this.m_iHealthPoints = 5;
                this.m_iShieldPoints= 100;
                this.m_bShooter = false;
                this.m_bShotLock = false;
                this.shootingInterval = 1000000000 * 2;
                this.m_iShootingHeight = (int) (this.m_fCoordX + (this.m_iWidth / 2));
                break;
            case TYPE2:
                break;
            case TYPE3:
                break;
            case TYPE4:
                break;
            case TYPE5:
                break;
            case TYPE6:
                break;
            case TYPE7:
                break;
            case TYPE8:
                break;
            case TYPE9:
                break;
            case TYPE10:
                break;
            case TYPE11:
                break;
            case BOSS:
                break;
            default:
                break;
        }
    }

    public EnemyType getEnemyType() { return this.enemyType; }

    public double getAppearanceTime() { return this.appearanceTime; }
}
