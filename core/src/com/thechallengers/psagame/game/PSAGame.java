package com.thechallengers.psagame.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.thechallengers.psagame.Menu.MenuScreen;
import com.thechallengers.psagame.SinglePlayer.SinglePlayerGameScreen;

import Shop.ShopScreen;

public class PSAGame extends Game {
	public static final float LONG_EDGE = 1920;
	public static final float SHORT_EDGE = 1080;
	public static enum Screen {
		MenuScreen, SinglePlayerGameScreen, ShopScreen
	}
	public static Screen CURRENT_SCREEN;

	@Override
	public void create () {
		//Load game preferences

		Preferences prefs = Gdx.app.getPreferences("prefs");
		if (!prefs.contains("music volume")) prefs.putFloat("music volume", 1);
		if (!prefs.contains("sfx volume")) prefs.putFloat("sfx volume", 0.5f);
		prefs.flush();

		//Open menu
		CURRENT_SCREEN = Screen.MenuScreen;
		setScreen(new MenuScreen(this));
	}

	@Override
	public void render () {
		super.render();
		updateScreen();
	}
	
	@Override
	public void dispose () {

	}

	public void updateScreen() {
		if (!this.getScreen().getClass().getSimpleName().equals(CURRENT_SCREEN.toString())) {
			this.getScreen().dispose();
			switch (CURRENT_SCREEN) {
				case MenuScreen: {
					setScreen(new MenuScreen(this));
				}
				case SinglePlayerGameScreen: {
					setScreen(new SinglePlayerGameScreen(this));
				}
				case ShopScreen: {
					setScreen(new ShopScreen(this));
				}
				default:
			}
		}
	}
}
