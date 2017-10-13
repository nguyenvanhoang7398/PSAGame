package com.thechallengers.psagame.helpers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

/**
 * Created by Phung Tuan Hoang on 10/13/2017.
 */

public class SoundLoader {
    public static Sound activate_destroymode;
    public static Sound block_destroyed;
    public static Sound click;
    public static Sound crane_pulley;
    public static Sound item_purchased;
    public static Sound last_few_second;
    public static Sound star_appearance;
    public static Sound start_game;
    public static Sound win_sound;

    public static Music ingame_bgm;
    public static Music menu_bgm;
    public static Music shop_bgm;

    public static void loadSFX() {
        activate_destroymode = Gdx.audio.newSound(Gdx.files.internal("sfx/activate_destroymode.mp3"));
        block_destroyed = Gdx.audio.newSound(Gdx.files.internal("sfx/block_destroyed.mp3"));
        click = Gdx.audio.newSound(Gdx.files.internal("sfx/click.wav"));
        crane_pulley = Gdx.audio.newSound(Gdx.files.internal("sfx/crane_pulley.mp3"));
        item_purchased = Gdx.audio.newSound(Gdx.files.internal("sfx/item_purchased.mp3"));
        last_few_second = Gdx.audio.newSound(Gdx.files.internal("sfx/last_few_seconds.mp3"));
        star_appearance = Gdx.audio.newSound(Gdx.files.internal("sfx/star_appearance.mp3"));
        start_game = Gdx.audio.newSound(Gdx.files.internal("sfx/start_game.mp3"));
        win_sound = Gdx.audio.newSound(Gdx.files.internal("sfx/win_sound.mp3"));
    }

    public static void loadInGameMusic() {
        ingame_bgm = Gdx.audio.newMusic(Gdx.files.internal("sfx/ingame_bgm.mp3"));
        ingame_bgm.setLooping(true);
    }

    public static void disposeInGameMusic() {
        ingame_bgm.dispose();
    }

    public static void loadShopMusic() {
        shop_bgm = Gdx.audio.newMusic(Gdx.files.internal("sfx/shop_bgm.mp3"));
        shop_bgm.setLooping(true);
    }

    public static void disposeShopMusic() {
        shop_bgm.dispose();
    }

    public static void loadMenuMusic() {
        menu_bgm = Gdx.audio.newMusic(Gdx.files.internal("sfx/menu_bgm.mp3"));
        menu_bgm.setLooping(true);
    }

    public static void disposeMenuMusic() {
        menu_bgm.dispose();
    }
}
