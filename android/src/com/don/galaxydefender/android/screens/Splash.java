package com.don.galaxydefender.android.screens;

import com.badlogic.gdx.Gdx;

import android.util.Log;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
import com.don.galaxydefender.android.GalaxyDefender;
import com.don.galaxydefender.android.controller.GameController;
import com.don.galaxydefender.android.logic.Enemy;
import com.don.galaxydefender.android.logic.Living;
import com.don.galaxydefender.android.logic.Player;
import com.don.galaxydefender.android.logic.Projectile;
import com.don.galaxydefender.android.logic.ProjectileType;
import java.util.Iterator;

import DatabaseAccess.LvlDataSource;

public class Splash implements Screen {

    private static final String TAG = "DATABASE";

    private static int SCREEN_WIDTH = Gdx.graphics.getWidth();
    private static int SCREEN_HEIGHT = Gdx.graphics.getHeight();

    private GalaxyDefender game;

    public Splash(GalaxyDefender game) {
        this.game = game;
    }

    int level = 1;
    Player player;
    GameController controller;
    private SpriteBatch batch;
    private Sprite playerSplash, background;
    Texture enemySplash, projectileSplash;
    //Living player;
    Living enemy;
    Living projectile;
    Array<Living> livingList;
    Array<Living> projectileList;
    Array<Enemy> toBeEnemyList = new Array<Enemy>();


    float[] player_speed = new float[2]; //Array der Player Geschwindigkeit
    char buttonPressed;

    long time, starttime;

    @Override
    public void show() {
        starttime = TimeUtils.millis();
        batch = new SpriteBatch();

        player = new Player();
        controller = new GameController(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        playerSplash = new Sprite(player.getTexture());

        background = new Sprite(new Texture(Gdx.files.internal("img/background.jpg")));

        enemySplash = new Texture(Gdx.files.internal("img/Enemy_Standard.png"));
        projectileSplash = new Texture(Gdx.files.internal("img/Shot_Red.png"));


        livingList = new Array<Living>();
        projectileList = new Array<Living>();

        //Datenbank öffnen und Enemy Array auslesen
        LvlDataSource dataSource = new LvlDataSource(game.getContext());

        Log.d(TAG, "Die Datenquelle wird geöffnet.");
        dataSource.open();

        //dataSource.insertEnemy(1, "TOWER", 2.0, 20, 20, "STRAIGHT");

        toBeEnemyList = dataSource.getEnemies(level);

        Log.d(TAG, "Die Datenquelle wird geschlossen.");
        dataSource.close();


    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if(player.getAlive()) {
            //Eingabeevents auswerten für Player-Aktionen
            player_speed = controller.checkInputMovement();
            buttonPressed = controller.checkInputButtons();

            //Player-Aktionen umsetzen
            player.move(player_speed[0], player_speed[1]);
            switch (buttonPressed) {
                case 'A':
                    player.checkShotLock();
                    if (!player.isShotLock())
                        shoot(player, ProjectileType.BULLET);
                    break;
                case 'B':
                    break;
                case 'X':
                    break;
                case 'Y':
                    break;
                case ' ':
                    break;
                default:
                    break;
            }
        }


        //Rendering pro Frame umsetzen
        batch.begin();

        //Background zeichen, unterste Layer
        batch.draw(background, 0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);

        //Projectiles zeichnen
        for (Living projectile : projectileList) {
            batch.draw(projectileSplash, projectile.getRectangle().x, projectile.getRectangle().y,
                    projectile.getRectangle().width, projectile.getRectangle().height);
        }
        //Gegner zeichnen
        for (Living living : livingList) {
            batch.draw(enemySplash, living.getRectangle().x, living.getRectangle().y,
                    living.getRectangle().width, living.getRectangle().height);
        }

        //Controller und Buttons zeichen, oberste Layer
        controller.render(batch);

        if(player.getAlive()) {
            // Player zeichnen
            batch.draw(playerSplash, player.getRectangle().x, player.getRectangle().y,
                    player.getWidth(), player.getHeight());
        }
        batch.end();

        //Auto Spawn Gegner
        Iterator<Enemy> enemyIterator = toBeEnemyList.iterator();
        while(enemyIterator.hasNext()) {
            Enemy spawnEnemy = enemyIterator.next();
            if (TimeUtils.millis() - starttime > spawnEnemy.getAppearanceTime() * 1000 ) {
                livingList.add(spawnEnemy);
                enemyIterator.remove();
            }

        }
        /**if (TimeUtils.millis() - time > 10 * 1000) {
            spawnEnemies();
        }*/

        //Gegner bewegen, Autoschuss
        Iterator<Living> iter = livingList.iterator();
        while (iter.hasNext()) {
            Living living = iter.next();
            living.move(living.getSpeedX(), 0);
            float posX = living.getRectangle().x;
            living.checkShotLock();
            if (!living.getAlive() || posX + living.getWidth() < 0)
                iter.remove();
            if(living.isShooter()) {
                if (!(living.isShotLock()))
                    shoot(living, ProjectileType.BULLET);
            }
        }

        //Schüsse bewegen
        Iterator<Living> iterShot = projectileList.iterator();
        while (iterShot.hasNext()) {
            Living living = iterShot.next();
            living.move(living.getSpeedX(), living.getSpeedY());
            float posX = living.getRectangle().x;
            if (!living.getAlive() || posX + living.getWidth() < 0)
                iterShot.remove();
        }
        if (player.getAlive()) {
            //Collision Detection
            iterShot = projectileList.iterator();
            boolean playerEnemy = false; //check zwischen Player und Enemys, nur einfachen Schleifendurchlauf
            //Projektile durchlaufen
            while (iterShot.hasNext()) {
                Living projectile = iterShot.next();
                //Prüfen Enemy-Projectiles mit Player
                if (!projectile.playerShot() && projectile.getRectangle().overlaps(player.getRectangle())) {
                    player.setHealthPoints(player.getHealthPoints() - projectile.getDamage());
                    projectile.setAlive(false);
                    if (player.getHealthPoints() <= 0) {
                        player.setAlive(false);
                    }
                }
                //Enemys durchlaufen
                iter = livingList.iterator();
                while (iter.hasNext()) {
                    Living enemy = iter.next();
                    //Enemy Player Collision prüfen
                    if (!playerEnemy && enemy.getRectangle().overlaps(player.getRectangle())) {
                        player.setHealthPoints(player.getHealthPoints() - enemy.getDamage());
                        enemy.setAlive(false);
                        if (player.getHealthPoints() <= 0) {
                            player.setAlive(false);
                        }
                    }
                    //Player Projektile Enemy prüfen
                    if (projectile.playerShot() && projectile.getRectangle().overlaps(enemy.getRectangle())) {
                        enemy.setHealthPoints(enemy.getHealthPoints() - projectile.getDamage());
                        projectile.setAlive(false);
                        if (enemy.getHealthPoints() <= 0)
                            enemy.setAlive(false);
                    }
                }
                playerEnemy = true;
            }
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
        playerSplash.getTexture().dispose();
    }
    /**public void spawnEnemies() {
        enemy = new Enemy(EnemyType.TOWER);
        //enemy.setTexture(enemySplash);
        livingList.add(enemy);
        time = TimeUtils.millis();
        Gdx.app.log(TAG, "new enemy spawned!");
    }*/

    public void shoot(Living living, ProjectileType proType) {
        Gdx.app.log(TAG, "Shots fired!");
        living.setShotLock(true);
        projectile = new Projectile(proType, living.getCoordX() + living.getWidth(), (living.getCenterY() - living.get_ShootingHeigth()));
        //projectile = new Projectile(proType, living.getCoordX(), (living.getCoordY() + (living.getHeight() / 2)));
        //projectile.setSpeedX(living.getSpeedX() * 2);
        Gdx.app.log(TAG, "Projectile X: " + projectile.getCoordX() + " Y: " + projectile.getCoordY());
        projectileList.add(projectile);
    }
}
