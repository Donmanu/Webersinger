package com.don.galaxydefender.android.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
import com.don.galaxydefender.android.logic.Enemy;
import com.don.galaxydefender.android.logic.EnemyType;
import com.don.galaxydefender.android.logic.Living;
import com.don.galaxydefender.android.logic.Player;
import com.don.galaxydefender.android.logic.Projectile;
import com.don.galaxydefender.android.logic.ProjectileType;

import java.util.Iterator;

public class SplashTestSuite implements Screen {

    private static final String TAG = "package com.don.galaxydefender.android.screens;";

    Living player;
    Living enemy;
    Living projectile;
    Array<Living> livingList;
    Array<Living> projectileList;
    private SpriteBatch batch;
    private Texture playerSplash;
    private Texture enemySplash;
    private Texture projectileSplash;

    long time;

    @Override
    public void show() {
        batch = new SpriteBatch();

        player = new Player();
        playerSplash = new Texture(Gdx.files.internal("img/Spaceship_Start.png"));
        player.setTexture(playerSplash);

        enemySplash = new Texture(Gdx.files.internal("img/Enemy_Standard.png"));
        projectileSplash = new Texture(Gdx.files.internal("img/Shot_Red.png"));

        livingList = new Array<Living>();
        projectileList = new Array<Living>();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        batch.draw(playerSplash, player.getRectangle().x, player.getRectangle().y,
                player.getRectangle().width, player.getRectangle().height);
        for (Living projectile : projectileList) {
            batch.draw(projectileSplash, projectile.getRectangle().x, projectile.getRectangle().y,
                    projectile.getRectangle().width, projectile.getRectangle().height);
        }
        for (Living living : livingList) {
            batch.draw(enemySplash, living.getRectangle().x, living.getRectangle().y,
                    living.getRectangle().width, living.getRectangle().height);
        }
        batch.end();

        if (Gdx.input.isTouched()) {
            ((Game) Gdx.app.getApplicationListener()).setScreen(new FinishedMainMenu());
            dispose();
        }

        /**if (TimeUtils.nanoTime() - time > (1000000000 * 2)) {
            spawnEnemies();
        }*/

        Iterator<Living> iter = livingList.iterator();
        while (iter.hasNext()) {
            Living living = iter.next();
            living.move(living.getSpeedX(), 0);
            float posX = living.getRectangle().x;
            if ((living.getCenterX() - 100 <= player.getCenterX()) && !(living.isShotLock()))
                shoot(living);
            if (posX + living.getWidth() < 0)
                iter.remove();
        }

        Iterator<Living> iterShot = projectileList.iterator();
        while (iterShot.hasNext()) {
            Living living = iterShot.next();
            living.move(0, 5);
            float posX = living.getRectangle().x;
            if (posX + living.getWidth() < 0)
                iterShot.remove();
        }
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        batch.dispose();
        playerSplash.dispose();
        enemySplash.dispose();
        projectileSplash.dispose();
        livingList.clear();
        projectileList.clear();
    }

    public void shoot(Living living) {
        Gdx.app.log(TAG, "Shots fired!");
        living.setShotLock(true);
        projectile = new Projectile(ProjectileType.BULLET, living.getCoordX(), (living.getCoordY() + (living.getHeight() / 2)));
        Gdx.app.log(TAG, "Projectile X: " + projectile.getCoordX() + " Y: " + projectile.getCoordY());
        projectileList.add(projectile);
    }

    /**public void spawnEnemies() {
        enemy = new Enemy(EnemyType.TOWER);
        //enemy.setTexture(enemySplash);
        livingList.add(enemy);
        time = TimeUtils.nanoTime();
        Gdx.app.log(TAG, "new enemy spawned!");
    }*/
}