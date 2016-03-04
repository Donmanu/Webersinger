package com.don.galaxydefender.logic;

import com.badlogic.gdx.math.Rectangle;


public class Projectile extends Living {
    private ProjectileType proType;

    public Projectile(ProjectileType projectileType, float xPos, float yPos) {
        switch (projectileType) {
            case BULLET:
                this.proType = projectileType;
                this.m_iWidth = 50;
                this.m_iHeight = 50;
                this.m_fCoordX = xPos;
                this.m_fCoordY = yPos;
                this.m_iCenterX = (int) (this.m_fCoordX + (this.m_iWidth / 2));
                this.m_iCenterY = (int) (this.m_fCoordY + (this.m_iHeight / 2));
                this.m_Rectangle = new Rectangle(this.m_fCoordX, this.m_fCoordY, this.m_iWidth, this.m_iHeight);
                this.m_bIsAlive = true;
                this.m_fSpeedX = 10;
                this.m_fSpeedY = 0;
                this.m_iDamage = 10;
                this.m_iHealthPoints = 50;
                break;

            case ENEMY_BULLET:
                this.proType = projectileType;
                this.m_iWidth = 50;
                this.m_iHeight = 50;
                this.m_fCoordX = xPos;
                this.m_fCoordY = yPos;
                this.m_iCenterX = (int) (this.m_fCoordX + (this.m_iWidth / 2));
                this.m_iCenterY = (int) (this.m_fCoordY + (this.m_iHeight / 2));
                this.m_Rectangle = new Rectangle(this.m_fCoordX, this.m_fCoordY, this.m_iWidth, this.m_iHeight);
                this.m_bIsAlive = true;
                this.m_fSpeedX = -10;
                this.m_fSpeedY = 0;
                this.m_iDamage = 10;
                this.m_iHealthPoints = 50;
                break;
            case LASER:
                break;
            case FLAME:
                break;
            case PLASMA:
                break;
            case ROCKET:
                break;
            case BOMB:
                break;
            case ENERGY:
                break;
            case IMPLOSION:
                break;
            case DISINTEGRATION:
                break;
            case EMP:
                break;
            case ATOMIC:
                break;
            case PARTICLE:
                break;
            default:
                break;
        }
    }

    @Override
    public boolean playerShot() {
        switch (this.proType) {
            case BULLET:
                return true;
            default:
                return false;
        }
    }
}