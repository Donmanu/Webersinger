package com.don.galaxydefender.android.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.don.galaxydefender.android.GalaxyDefender;

public class ShopMenuScreen implements Screen {

    private final int SCREEN_WIDTH = Gdx.graphics.getWidth();
    private final int SCREEN_HEIGHT = Gdx.graphics.getHeight();

    private final GalaxyDefender game;
    private Stage stage;

    private Table tabBar, tabBarContent;
    private TextButton firstTab, secondTab, thirdTab, fourthTab;
    private Skin buttonSkin;
    private TextureAtlas buttonAtlas;
    private BitmapFont whiteFont;

    public ShopMenuScreen(GalaxyDefender game) {
        this.game = game;
        this.stage = new Stage();
        this.buttonAtlas = new TextureAtlas("ui/button.pack");
        this.buttonSkin = new Skin(buttonAtlas);
        this.tabBar = new Table(buttonSkin);
        this.tabBarContent = new Table(); // different buttonSkin (maybe ImageButton)
        this.whiteFont = new BitmapFont(Gdx.files.internal("fonts/white.fnt"), false);
    }

    @Override
    public void show() {
        buttonSetup();
        setupTabBar();
        Gdx.input.setInputProcessor(stage);
        stage.addActor(tabBar);
    }

    private void setupTabBar() {

        tabBar.setBounds(0, 0, SCREEN_WIDTH, SCREEN_WIDTH);

        tabBar.add(firstTab);
        tabBar.add(secondTab);
        tabBar.add(thirdTab);
        tabBar.add(fourthTab);
    }

    private void buttonSetup() {
        int buttonWidth = SCREEN_WIDTH/4;
        int buttonHeight = SCREEN_HEIGHT/10;

        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.up = buttonSkin.getDrawable("button_black");
        textButtonStyle.down = buttonSkin.getDrawable("button_red");
        textButtonStyle.font = whiteFont;

        firstTab = new TextButton("FIRST", textButtonStyle);
        firstTab.setBounds(0, 0, buttonWidth, buttonHeight);
        secondTab = new TextButton("SECOND", textButtonStyle);
        secondTab.setBounds(buttonWidth, 0, buttonWidth, buttonHeight);
        thirdTab = new TextButton("THIRD", textButtonStyle);
        thirdTab.setBounds(buttonWidth * 2, 0 , buttonWidth, buttonHeight);
        fourthTab = new TextButton("FOURTH", textButtonStyle);
        fourthTab.setBounds(buttonWidth * 3, 0, buttonWidth, buttonHeight);
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
        buttonAtlas.dispose();
        whiteFont.dispose();
        stage.dispose();
        buttonSkin.dispose();
    }
}
