package com.don.galaxydefender.android.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.don.galaxydefender.android.GalaxyDefender;

public class MainMenuScreen implements Screen {

    private static final String TAG = "com.don.galaxydefender.android.screens";

    private final int SCREEN_WIDTH = Gdx.graphics.getWidth();
    private final int SCREEN_HEIGHT = Gdx.graphics.getHeight();

    private final GalaxyDefender game;

    private static Sprite background;
    private static Stage stage;
    private static TextureAtlas buttonAtlas;
    private static Skin buttonSkin;
    private static Table table;
    private static TextButton stageSelect, shopMenu, backToTitle;
    private static BitmapFont white; //, black;


    public MainMenuScreen(GalaxyDefender game) {
        this.game = game;
        stage = new Stage();
        background = new Sprite(new Texture(Gdx.files.internal("img/background.jpg")));
        buttonAtlas = new TextureAtlas(Gdx.files.internal("ui/button.pack"));
        buttonSkin = new Skin(buttonAtlas);
        table = new Table(buttonSkin);
        white = new BitmapFont(Gdx.files.internal("fonts/white.fnt"), false);
    }

    private void buttonSetup() {
        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.up = buttonSkin.getDrawable("button_black");
        textButtonStyle.down = buttonSkin.getDrawable("button_red");
        textButtonStyle.font = white;

        stageSelect = new TextButton("Stage Select", textButtonStyle);
        stageSelect.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.log(TAG, "Stage Select Button Clicked");
            }
        });

        shopMenu = new TextButton("Shop", textButtonStyle);
        shopMenu.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.log(TAG, "Shop Button Clicked");
            }
        });

        backToTitle = new TextButton("Back To Title", textButtonStyle);
        backToTitle.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.log(TAG, "Back To Title Button Clicked");
                game.setScreen(new TitleScreen(game));
            }
        });
    }

    private void tableSetup() {
        int buttonWidth = SCREEN_WIDTH/2;
        int buttonHeight = SCREEN_HEIGHT/10;
        int buttonPad = SCREEN_HEIGHT/40;
        int buttonPadTop = SCREEN_HEIGHT / 4;

        table.setBounds(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);

        table.center();
        table.add(stageSelect).center().size(buttonWidth, buttonHeight).pad(buttonPad)
                .padTop(buttonPadTop);
        table.row();
        table.add(shopMenu).center().size(buttonWidth, buttonHeight).pad(buttonPad);
        table.row();
        table.add(backToTitle).center().size(buttonWidth, buttonHeight).pad(buttonPad);
    }

    @Override
    public void show() {
        buttonSetup();
        tableSetup();
        Gdx.input.setInputProcessor(stage);
        stage.addActor(table);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act(delta);
        stage.getBatch().begin();
        stage.getBatch().draw(background, 0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);
        stage.getBatch().end();
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
        buttonAtlas.dispose();
        stage.dispose();
        buttonSkin.dispose();
        white.dispose();
    }
}
