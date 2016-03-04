package com.don.galaxydefender;


import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class StageSelectScreen implements Screen {

    final GalaxyDefender game;
    OrthographicCamera camera;
    Stage stage;
    TextButton button;
    TextButtonStyle buttonStyle;
    Skin buttonSkin;
    TextureAtlas textureAtlas;

    public StageSelectScreen(GalaxyDefender game) {
        this.game = game;

        camera = new OrthographicCamera();
        camera.setToOrtho(false);

        stage = new Stage();
        Gdx.input.setInputProcessor(stage);
        buttonSkin = new Skin();
        textureAtlas = new TextureAtlas(Gdx.files.internal("walker.atlas"));
        buttonSkin.addRegions(textureAtlas);
        buttonStyle = new TextButtonStyle();
        buttonStyle.font = this.game.getFont();
        buttonStyle.up = buttonSkin.getDrawable("walker");
        button = new TextButton("Click me!", buttonStyle);
        button.setX(300);
        button.setY(200);
        button.setWidth(100);
        button.setHeight(100);
        button.setColor(Color.GREEN);
        stage.addActor(button);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        game.getBatch().begin();
        stage.draw();
        game.getBatch().end();
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
