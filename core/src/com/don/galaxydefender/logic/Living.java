package com.don.galaxydefender.logic;
import com.badlogic.gdx.utils.TimeUtils;

public class Living extends Moveable {

    protected int m_iHealthPoints;
    protected int m_iShieldPoints;
    protected boolean m_bShooter;
    protected boolean m_bShotLock;

    protected long shootingTime;

    protected long shootingInterval;

    public Living(){ }

    public int getHealthPoints(){
        return m_iHealthPoints;
    }

    public void setHealthPoints(int healthPoints){
        this.m_iHealthPoints = healthPoints;
    }

    public int getShieldPoints(){
        return m_iShieldPoints;
    }

    public void setShieldPoints(int shieldPoints){
        this.m_iShieldPoints = shieldPoints;
    }

    public boolean isShooter(){
        return m_bShooter;
    }

    public void setShooter(boolean shooter){
        this.m_bShooter = shooter;
    }

    public boolean isShotLock(){
        return m_bShotLock;
    }

    public void setShotLock(boolean shotLock){
        if (shotLock == true) {
            shootingTime = TimeUtils.nanoTime();
        }
        this.m_bShotLock = shotLock;
    }
    public void checkShotLock() {
        if ((TimeUtils.nanoTime() - shootingTime ) >= shootingInterval) {
            this.m_bShotLock = false;
        }
    }
    public boolean playerShot() {return false; }
}
