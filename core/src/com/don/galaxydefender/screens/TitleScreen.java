package com.don.galaxydefender.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
import com.don.galaxydefender.GalaxyDefender;

import java.util.Iterator;

public class TitleScreen implements Screen {

    //TODO: Logo? / Title Image?

    private static final String TAG = "com.don.galaxydefender.screens";

    private final int SCREEN_WIDTH = Gdx.graphics.getWidth();
    private final int SCREEN_HEIGHT = Gdx.graphics.getHeight();
    private static final String[] METEOR_ANIMATION_NAMES = new String[] {
            "meteor_top", "meteor_top_right", "meteor_right", "meteor_bottom_right",
            "meteor_bottom", "meteor_bottom_left", "meteor_left", "meteor_top_left"};

    private final GalaxyDefender game;

    private static Animation meteorAnimation; // done
    private static Music titleScreenMusic; // done
    private static Sprite background; // done
    private static Stage stage; // done
    private static TextureAtlas buttonAtlas, meteorAtlas; // done
    private static Skin buttonSkin; // done
    private static Table table; // done
    private static TextButton buttonNewGame, buttonLoadGame, buttonCredits, buttonSettings, buttonExit; // done
    private static BitmapFont white; //, black; // done
    private static Array<Rectangle> meteors; // done
    private static long lastMeteorTime; // done
    private static float stateTime; // done

    public TitleScreen(GalaxyDefender game) {
        this.game = game;
        meteors = new Array<Rectangle>();
        stage = new Stage();
        background = new Sprite(new Texture(Gdx.files.internal("img/Title_Screen.png")));
        buttonAtlas = new TextureAtlas("ui/button.pack");
        buttonSkin = new Skin(buttonAtlas);
        table = new Table(buttonSkin);
        meteorAtlas = new TextureAtlas("img/meteor.pack");
        white = new BitmapFont(Gdx.files.internal("fonts/white.fnt"), false);
        //black = new BitmapFont(Gdx.files.internal("fonts/black.fnt"), false);
    }

    @Override
    public void show() {
        musicSetup();
        animationSetup();
        buttonSetup();
        tableSetup();
        Gdx.input.setInputProcessor(stage);
        stage.addActor(table);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stateTime += delta;

        stage.act(delta);
        stage.getBatch().begin();
        stage.getBatch().draw(background, 0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);
        for(Rectangle meteor : meteors){
            stage.getBatch().draw(meteorAnimation.getKeyFrame(stateTime, true), meteor.x, meteor.y);
        }
        stage.getBatch().end();
        stage.draw();

        if(TimeUtils.nanoTime() - lastMeteorTime > 1000000000){
            spawnMeteors();
        }

        backgroundAnimation();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {
        Gdx.app.log(TAG, "Title Screen paused");
    }

    @Override
    public void resume() {
        Gdx.app.log(TAG, "Title Screen resumed");
    }

    @Override
    public void hide() {
        Gdx.app.log(TAG, "Title Screen hidden");
    }

    @Override
    public void dispose() {
        titleScreenMusic.dispose();
        buttonAtlas.dispose();
        meteorAtlas.dispose();
        stage.dispose();
        buttonSkin.dispose();
        white.dispose();
        titleScreenMusic.dispose();
        Gdx.app.log(TAG, "Disposing...");
        //black.dispose();
    }

    private void musicSetup() {
        titleScreenMusic = Gdx.audio.newMusic(Gdx.files.internal("bgm/TitleScreenMusic.mp3"));
        titleScreenMusic.setLooping(true);
        titleScreenMusic.play();
    }

    private void animationSetup() {
        TextureRegion[] meteorFrames = new TextureRegion[METEOR_ANIMATION_NAMES.length];
        for(int i = 0; i < METEOR_ANIMATION_NAMES.length; i++){
            String path = METEOR_ANIMATION_NAMES[i];
            meteorFrames[i] = meteorAtlas.findRegion(path);
        }
        meteorAnimation = new Animation(0.1f, meteorFrames);
        stateTime = 0f;
    }

    private void buttonSetup() {
        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.up = buttonSkin.getDrawable("button_black");
        textButtonStyle.down = buttonSkin.getDrawable("button_red");
        textButtonStyle.font = white;

        //TODO: button functionality

        buttonNewGame = new TextButton("Start New Game", textButtonStyle);
        buttonNewGame.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.log(TAG, "Starting New Game");
                dispose();
                game.setScreen(new MainMenuScreen(game));
            }
        });

        buttonLoadGame = new TextButton("Load Game", textButtonStyle);
        buttonLoadGame.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.log(TAG, "Loading saved Games");
                // TODO: set up saved games dialog
            }
        });

        buttonSettings = new TextButton("Settings", textButtonStyle);
        buttonSettings.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.log(TAG, "Settings clicked");
                // TODO: popup dialog or separate screen?
            }
        });

        buttonCredits = new TextButton("Credits", textButtonStyle);
        buttonCredits.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.log(TAG, "Credits clicked");
                // TODO: popup dialog or separate screen?
            }
        });

        buttonExit = new TextButton("Exit Game", textButtonStyle);
        buttonExit.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
            }
        });
    }

    private void tableSetup(){
        int buttonWidth = SCREEN_WIDTH/2;
        int buttonHeight = SCREEN_HEIGHT/10;
        int buttonPad = SCREEN_HEIGHT/40;
        int buttonPadTop = SCREEN_HEIGHT / 4;

        table.setBounds(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);

        table.center();
        table.add(buttonNewGame).center().size(buttonWidth, buttonHeight).pad(buttonPad)
                .padTop(buttonPadTop);
        table.row();
        table.add(buttonLoadGame).center().size(buttonWidth, buttonHeight).pad(buttonPad);
        table.row();
        table.add(buttonSettings).center().size(buttonWidth, buttonHeight).pad(buttonPad);
        table.row();
        table.add(buttonCredits).center().size(buttonWidth, buttonHeight).pad(buttonPad);
        table.row();
        table.add(buttonExit).center().size(buttonWidth, buttonHeight).pad(buttonPad);
    }

    private void spawnMeteors() {
        Rectangle meteor = new Rectangle();
        meteor.x = MathUtils.random(-SCREEN_WIDTH, SCREEN_WIDTH);
        meteor.y = SCREEN_HEIGHT;
        meteor.width = SCREEN_WIDTH / 10;
        meteor.height = SCREEN_WIDTH / 10;
        meteors.add(meteor);
        lastMeteorTime = TimeUtils.nanoTime();
    }

    private void backgroundAnimation(){
        Iterator<Rectangle> iterator = meteors.iterator();
        while(iterator.hasNext()) {
            Rectangle meteor = iterator.next();
            meteor.x += 2;
            meteor.y -= 4;
            if(meteor.x > SCREEN_WIDTH) {
                iterator.remove();
            }
            if((meteor.y + meteor.height) < 0) {
                iterator.remove();
            }
        }
    }
}
