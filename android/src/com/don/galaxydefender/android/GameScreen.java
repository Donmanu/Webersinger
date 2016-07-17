package com.don.galaxydefender.android;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;

import java.util.Iterator;

/*
 *  This Class defines the screen that is shown as the game is running.
 */
public class GameScreen implements Screen {

    final GalaxyDefender game;


    Texture spaceShipImage;
    Texture dropImage;
    Sound dropSound;
    Music bgm;
    OrthographicCamera camera;
    Rectangle spaceShip;
    Array<Rectangle> raindrops;
    long lastDropTime;
    int score;
    int screenWidth, screenHeight;

    public GameScreen(GalaxyDefender game) {
        this.game = game;

        this.game.getFont().setColor(Color.WHITE);

        screenWidth = Gdx.graphics.getWidth();
        screenHeight = Gdx.graphics.getHeight();

        dropImage = new Texture(Gdx.files.internal("badlogic.jpg"));
        spaceShipImage = new Texture(Gdx.files.internal("img/mikasa.png"));

        dropSound = Gdx.audio.newSound(Gdx.files.internal("sounds/file1.wav"));
        bgm = Gdx.audio.newMusic(Gdx.files.internal("sounds/file1.wav"));
        bgm.setLooping(true);

        camera = new OrthographicCamera();
        camera.setToOrtho(false);

        spaceShip = new Rectangle();
        spaceShip.x = screenWidth/2 - spaceShip.width/2;
        spaceShip.y = 20;
        spaceShip.width = 64;
        spaceShip.height = 64;

        raindrops = new Array<Rectangle>();
        spawnRaindrop();
    }

    private void spawnRaindrop(){
        Rectangle raindrop = new Rectangle();
        raindrop.x = MathUtils.random(0, 800 - 64);
        raindrop.y = 480;
        raindrop.width = 64;
        raindrop.height = 64;
        raindrops.add(raindrop);
        lastDropTime = TimeUtils.nanoTime();
    }

    @Override
    public void show() {
        bgm.play();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();
        game.getBatch().setProjectionMatrix(camera.combined);

        game.getBatch().begin();
        game.getFont().draw(game.getBatch(), "Score" + score, 0, screenHeight);
        game.getFont().draw(game.getBatch(), "Width: " + screenWidth + " " + "Height: " + screenHeight, 100, screenHeight);
        game.getBatch().draw(spaceShipImage, spaceShip.x, spaceShip.y);
        for(Rectangle raindrop : raindrops){
            game.getBatch().draw(dropImage, raindrop.x, raindrop.y);
        }
        game.getBatch().end();

        if(Gdx.input.isTouched()){
            Vector3 touchPos = new Vector3();
            touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(touchPos);
            spaceShip.x = touchPos.x - spaceShip.width/2;
        }
        if(Gdx.input.isKeyPressed(Keys.LEFT))
            spaceShip.x -= 200 * Gdx.graphics.getDeltaTime();
        if(Gdx.input.isKeyPressed(Keys.RIGHT))
            spaceShip.x += 200 * Gdx.graphics.getDeltaTime();

        if(spaceShip.x < 0)
            spaceShip.x = 0;
        if(spaceShip.x > screenWidth - spaceShip.width)
            spaceShip.x = screenWidth - spaceShip.width;

        if(TimeUtils.nanoTime() - lastDropTime > 100000000)
            spawnRaindrop();

        Iterator<Rectangle> iter = raindrops.iterator();
        while(iter.hasNext()){
            Rectangle raindrop = iter.next();
            raindrop.y -= 200* Gdx.graphics.getDeltaTime();
            if(raindrop.y + 64 < 0)
                iter.remove();
            if(raindrop.overlaps(spaceShip)){
                score++;
                dropSound.play();
                iter.remove();
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
        spaceShipImage.dispose();
        dropImage.dispose();
        dropSound.dispose();
        bgm.dispose();
    }
}
