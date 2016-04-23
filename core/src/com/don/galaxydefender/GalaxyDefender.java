package com.don.galaxydefender;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.don.galaxydefender.screens.TitleScreen;

public class GalaxyDefender extends Game implements ApplicationListener {

	public static final String TITLE = "Galaxy Defender", VERSION = "0.0.0.0";
	private SpriteBatch batch;
	private BitmapFont font;

	@Override
	public void create() {
		batch = new SpriteBatch();
		font = new BitmapFont();
		font.setColor(0, 0, 0, 1);
		this.setScreen(new TitleScreen(this));
	}

	@Override
	public void render() {
		super.render();
	}

	@Override
	public void dispose() {
		batch.dispose();
		font.dispose();
	}

	public SpriteBatch getBatch(){
		return batch;
	}

	public BitmapFont getFont(){
		return font;
	}
}