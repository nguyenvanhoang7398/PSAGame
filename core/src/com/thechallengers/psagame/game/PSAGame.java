package com.thechallengers.psagame.game;

import com.badlogic.gdx.Game;
import com.thechallengers.psagame.Dummy.SinglePlayerGameScreen;
import com.thechallengers.psagame.Menu.MenuScreen;

public class PSAGame extends Game {
	public static final float LONG_EDGE = 1920;
	public static final float SHORT_EDGE = 1080;
	public static enum Screen {
		MenuScreen, SP_GAME, MP_GAME, SHOP, SETTING, TUTORIAL, SinglePlayer
	}
	public static Screen CURRENT_SCREEN;

	@Override
	public void create () {
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
				case SinglePlayer: {
					setScreen(new SinglePlayerGameScreen(this));
				}
				default:
			}
		}
	}

}
