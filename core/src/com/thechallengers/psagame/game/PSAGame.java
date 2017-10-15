package com.thechallengers.psagame.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.audio.Music;
import com.thechallengers.psagame.EndGame.EndGameScreen;
import com.thechallengers.psagame.Leaderboard.LeaderboardScreen;
import com.thechallengers.psagame.LevelSelection.LevelSelectionScreen;
import com.thechallengers.psagame.Menu.MenuScreen;
import com.thechallengers.psagame.Shop.ShopScreen;
import com.thechallengers.psagame.SinglePlayer.SinglePlayerGameScreen;
import com.thechallengers.psagame.Tutorial.TutorialScreen;
import com.thechallengers.psagame.helpers.SoundLoader;

public class PSAGame extends Game {
    public static final int AVAILABLE_LEVEL = 5;
	public static final float LONG_EDGE = 1920;
	public static final float SHORT_EDGE = 1080;
	public static float CRANE_VELOCITY;
	public static enum Screen {
		MenuScreen, SinglePlayerGameScreen, ShopScreen, TutorialScreen, EndGameScreen, LevelSelectionScreen, LeaderboardScreen
	}
	public static Screen CURRENT_SCREEN;
	public static int LEVEL;
	public static float SFX_VOLUME = 0;

	@Override
	public void create() {
		//Load game preferences

		Preferences prefs = Gdx.app.getPreferences("prefs");
		if (!prefs.contains("music volume")) prefs.putFloat("music volume", 1);
		if (!prefs.contains("sfx volume")) prefs.putFloat("sfx volume", 0.5f);
		if (!prefs.contains("level1_starLevel")) prefs.putInteger("level1_starLevel", 0);
		if (!prefs.contains("crane_level")) prefs.putInteger("crane_level", 1);
		if (!prefs.contains("moneyBalance")) prefs.putInteger("moneyBalance", 100);
		if (!prefs.contains("crane_present")) prefs.putInteger("crane_present", 1);
		if (!prefs.contains("craneLv2_purchased")) prefs.putBoolean("craneLv2_purchased", false);
		if (!prefs.contains("craneLv3_purchased")) prefs.putBoolean("craneLv3_purchased", false);
		if (!prefs.contains("level")) prefs.putInteger("level", 1);
		if (!prefs.contains("level1star")) prefs.putInteger("level1star", 0);
		if (!prefs.contains("level2star")) prefs.putInteger("level2star", 0);
		if (!prefs.contains("level3star")) prefs.putInteger("level3star", 0);
		if (!prefs.contains("level4star")) prefs.putInteger("level4star", 0);
		if (!prefs.contains("level5star")) prefs.putInteger("level5star", 0);
		if (!prefs.contains("crane speed")) prefs.putFloat("crane speed", 10f);
        if (!prefs.contains("best_time_level1")) prefs.putString("best_time_level1", "0:00");
        if (!prefs.contains("best_time_level2")) prefs.putString("best_time_level2", "0:00");
        if (!prefs.contains("best_time_level3")) prefs.putString("best_time_level3", "0:00");
        if (!prefs.contains("best_time_level4")) prefs.putString("best_time_level4", "0:00");
        if (!prefs.contains("best_time_level5")) prefs.putString("best_time_level5", "0:00");
		prefs.flush();

        Gdx.input.setCatchBackKey(true);

		//Load sfx
		SoundLoader.loadSFX();

		//Open menu
		CURRENT_SCREEN = Screen.MenuScreen;
		setScreen(new MenuScreen(this));
	}

	@Override
	public void render() {
		super.render();
		updateScreen();

		for (Music music : SoundLoader.musicHashtable.values()) {
			music.setVolume(Gdx.app.getPreferences("prefs").getFloat("music volume"));
		}

		SFX_VOLUME = Gdx.app.getPreferences("prefs").getFloat("sfx volume");
		CRANE_VELOCITY = Gdx.app.getPreferences("prefs").getFloat("crane speed");
	}

	@Override
	public void dispose() {

	}

	public void updateScreen() {
		if (!this.getScreen().getClass().getSimpleName().equals(CURRENT_SCREEN.toString())) {
			this.getScreen().dispose();
			switch (CURRENT_SCREEN) {
				case MenuScreen: {
					setScreen(new MenuScreen(this));
					break;
				}
				case SinglePlayerGameScreen: {
					setScreen(new SinglePlayerGameScreen(LEVEL));
					break;
				}
				case ShopScreen: {
					setScreen(new ShopScreen(this));
					break;
				}
				case LeaderboardScreen: {
					setScreen(new LeaderboardScreen(this));
					break;
				}
				case TutorialScreen: {
					setScreen(new TutorialScreen(this));
					break;
				}
				case EndGameScreen: {
					setScreen(new EndGameScreen());
					break;
				}
                case LevelSelectionScreen:
                    setScreen(new LevelSelectionScreen());
                    break;
                default:
			}
		}
	}

	public static void playSound(String s) {
		System.out.println("------>Sound " + s);
		SoundLoader.soundHashtable.get(s).play(Gdx.app.getPreferences("prefs").getFloat("sfx volume"));
	}
}
