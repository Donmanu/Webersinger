package com.don.galaxydefender.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.don.galaxydefender.GalaxyDefender;

public class TitleScreen implements Screen {

    //TODO: Background Image, Animation, Logo? / Title Image?

    private Stage stage; //done
    private TextureAtlas atlas; //done
    private Skin skin; //done
    private Table table; //done
    private TextButton buttonNewGame, buttonLoadGame, buttonCredits, buttonOptions, buttonExit;
    private BitmapFont white, black; //done
    private Label heading;

    @Override
    public void show() {
        stage = new Stage();

        Gdx.input.setInputProcessor(stage);

        atlas = new TextureAtlas("ui/button.pack");
        skin = new Skin(atlas);
        table = new Table(skin);
        table.setBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        white = new BitmapFont(Gdx.files.internal("fonts/white.fnt"), false);
        black = new BitmapFont(Gdx.files.internal("fonts/black.fnt"), false);

        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.up = skin.getDrawable("button_black");
        textButtonStyle.down = skin.getDrawable("button_red");
        textButtonStyle.font = black;

        //TODO: Button layout and functionality

        buttonNewGame = new TextButton("Start New Game", textButtonStyle);
        buttonNewGame.pad(20);

        buttonLoadGame = new TextButton("Load Game", textButtonStyle);
        buttonLoadGame.pad(20);

        buttonOptions = new TextButton("Options", textButtonStyle);
        buttonOptions.pad(20);

        buttonCredits = new TextButton("Credits", textButtonStyle);
        buttonCredits.pad(20);

        buttonExit = new TextButton("Exit Game", textButtonStyle);
        buttonExit.pad(20);

        Label.LabelStyle labelStyle = new Label.LabelStyle(white, Color.WHITE);
        heading = new Label(GalaxyDefender.TITLE, labelStyle);
        heading.scaleBy(2);

        table.add(heading);
        table.row();
        table.add(buttonNewGame, buttonLoadGame, buttonOptions, buttonCredits, buttonExit);
        stage.addActor(table);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act(delta);
        stage.draw();
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
        atlas.dispose();
        stage.dispose();
        skin.dispose();
        white.dispose();
        black.dispose();
    }
}
