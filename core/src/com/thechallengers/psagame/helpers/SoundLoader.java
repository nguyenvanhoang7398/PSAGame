package com.thechallengers.psagame.helpers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

import java.util.Hashtable;

/**
 * Created by Phung Tuan Hoang on 10/13/2017.
 */

public class SoundLoader {
    public static Hashtable<String, Sound> soundHashtable;
    public static Hashtable<String, Music> musicHashtable;

    public static void loadSFX() {
        String[] sfx_name = {"activate_destroymode.mp3", "block_destroyed.mp3", "click.wav", "crane_pulley.mp3",
                            "item_purchased.mp3", "last_few_seconds.mp3", "star_appearance.mp3", "start_game.mp3",
                            "win_sound.mp3"};

        soundHashtable = new Hashtable<String, Sound>();

        for (int i = 0; i < sfx_name.length; i++) {
            soundHashtable.put(sfx_name[i],
                    Gdx.audio.newSound(Gdx.files.internal("sfx/" + sfx_name[i])));
        }

        String[] music_name = {"ingame_bgm.mp3", "shop_bgm.mp3", "menu_bgm.mp3"};

        musicHashtable = new Hashtable<String, Music>();

        for (int i = 0; i < music_name.length; i++) {
            Music temp = Gdx.audio.newMusic(Gdx.files.internal("sfx/" + music_name[i]));
            temp.setLooping(true);
            musicHashtable.put(music_name[i], temp);
        }
    }
}
