package com.don.galaxydefender;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;

public class MainMenuScreen implements Screen {

    final GalaxyDefender game;
    OrthographicCamera camera;

    public MainMenuScreen(final GalaxyDefender game) {
        this.game = game;

        camera = new OrthographicCamera();
        camera.setToOrtho(false);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();
        game.getBatch().setProjectionMatrix(camera.combined);

        game.getBatch().begin();
        game.getFont().draw(game.getBatch(), "Galaxy Defender E Youkoso!", 100, 150);
        game.getFont().draw(game.getBatch(), "Tap anywhere to start!", 100, 100);
        game.getBatch().end();

        if(Gdx.input.isTouched()){
            game.setScreen(new StageSelectScreen(game));
            dispose();
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

    }
}
