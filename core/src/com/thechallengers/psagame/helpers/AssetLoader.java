package com.thechallengers.psagame.helpers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.util.Hashtable;

/**
 * Created by Phung Tuan Hoang on 9/6/2017.
 */

public class AssetLoader {
    //textures - to be disposed after texture region is created
    public static Texture background_texture;
    public static Texture button_texture;
    public static Texture menu_crane_texture;
    public static Texture game_background;
    public static Sprite game_crane;
    public static Texture cloud_1, cloud_2;
    public static Texture containers;
    public static Texture play_button, play_button_pressed;
    public static Texture tutorial_button, tutorial_button_pressed;
    public static Texture single_player_button, single_player_button_pressed;
    public static Texture multi_player_button, multi_player_button_pressed;
    public static Texture overlay_button, overlay_button_pressed;
    public static Texture setting_button, setting_button_pressed;
    public static Texture setting_overlay;
    public static Texture setting_box;
    public static Texture slider_bg, slider_knob;
    public static Texture unitBlockTexture;
    public static Texture shop_button, shop_backButton;
    public static Texture shop_background;
    public static Texture level_selection_background;
    public static Texture level_backButton;


    //box sprites
    public static Sprite block_sprite_1_11, block_sprite_1_12, block_sprite_1_21, block_sprite_1_22, block_sprite_1_32, block_sprite_1_31;
    public static Sprite block_sprite_2_11, block_sprite_2_12, block_sprite_2_21, block_sprite_2_22, block_sprite_2_32, block_sprite_2_31;
    public static Sprite block_sprite_3_11, block_sprite_3_12, block_sprite_3_21, block_sprite_3_22, block_sprite_3_32, block_sprite_3_31;
    public static Sprite block_sprite_4_11, block_sprite_4_12, block_sprite_4_21, block_sprite_4_22, block_sprite_4_32, block_sprite_4_31;
    public static Sprite block_sprite_5_11, block_sprite_5_12, block_sprite_5_21, block_sprite_5_22, block_sprite_5_32, block_sprite_5_31;

    //texture region
    public static TextureRegion button_up, button_down;
    public static TextureRegion background;

    public static Hashtable<String, Sprite> spriteHashtable;

    //font
    public static BitmapFont arial;
    public static BitmapFont consolas_15;

    //position for game background (zoomed in from menu)
    public static int starting_x, starting_y, end_x, end_y;

    public static void loadMenuTexture() {

        background_texture = new Texture(Gdx.files.internal("textures/menubackground.png"));
        button_texture = new Texture(Gdx.files.internal("textures/button.png"));
        menu_crane_texture = new Texture(Gdx.files.internal("textures/menucrane.png"));
        cloud_1 = new Texture(Gdx.files.internal("textures/cloud_1.png"));
        cloud_2 = new Texture(Gdx.files.internal("textures/cloud_2.png"));
        containers = new Texture(Gdx.files.internal("textures/containers.png"));
        play_button = new Texture(Gdx.files.internal("textures/play_button.png"));
        play_button_pressed = new Texture(Gdx.files.internal("textures/play_button_pressed.png"));
        tutorial_button = new Texture(Gdx.files.internal("textures/tutorial_button.png"));
        tutorial_button_pressed = new Texture(Gdx.files.internal("textures/tutorial_button_pressed.png"));
        single_player_button = new Texture(Gdx.files.internal("textures/single_player_button.png"));
        single_player_button_pressed = new Texture(Gdx.files.internal("textures/single_player_button_pressed.png"));
        multi_player_button = new Texture(Gdx.files.internal("textures/multi_player_button.png"));
        multi_player_button_pressed = new Texture(Gdx.files.internal("textures/multi_player_button_pressed.png"));
        overlay_button = new Texture(Gdx.files.internal("textures/overlay_button.png"));
        overlay_button_pressed = new Texture(Gdx.files.internal("textures/overlay_button_pressed.png"));
        setting_button = new Texture(Gdx.files.internal("textures/setting_button.png"));
        setting_button_pressed = new Texture(Gdx.files.internal("textures/setting_button_pressed.png"));
        setting_overlay = new Texture(Gdx.files.internal("textures/setting_overlay.png"));
        setting_box = new Texture(Gdx.files.internal("textures/setting_box.png"));
        slider_bg = new Texture(Gdx.files.internal("textures/slider_bg.png"));
        slider_knob = new Texture(Gdx.files.internal("textures/slider_knob.png"));
        shop_button = new Texture(Gdx.files.internal("textures/shop_icon.png"));




        background = new TextureRegion(background_texture, 0, 0, 1080, 1920);
        button_up = new TextureRegion(button_texture, 0, 0, 200, 100);
        button_down = new TextureRegion(button_texture, 200, 0, 200, 100);

        arial = new BitmapFont(Gdx.files.internal("font/arial.fnt"));
        consolas_15 = new BitmapFont(Gdx.files.internal("font/consolas_15.fnt"));
    }

    public static void disposeMenuTexture() {
        background_texture.dispose();
        button_texture.dispose();
        arial.dispose();
    }

    public static void loadLevelSelectionTexture() {
        level_selection_background = new Texture(Gdx.files.internal("textures/level_selection_background.png"));
        level_backButton = new Texture(Gdx.files.internal("textures/shop_backButton.png"));
    }

    public static void disposeLevelSelectionTexture() {
        level_selection_background.dispose();
        level_backButton.dispose();
    }

    public static void loadShopTexture() {
        shop_background = new Texture(Gdx.files.internal("textures/gamebackground.png"));
        shop_backButton = new Texture(Gdx.files.internal("textures/shop_backButton.png"));
    }

    public static void disposeShopTexture() {
        shop_background.dispose();
        shop_backButton.dispose();
    }

    public static void loadGameTexture() {
        game_crane = new Sprite(new Texture(Gdx.files.internal("textures/gamecrane.png")));
        game_background = new Texture(Gdx.files.internal("textures/game_background.png"));
        unitBlockTexture = new Texture("textures/unitBlock.png");

        loadSpritesAndHashTable();
    }

    public static void updateGameBackGroundDetails(int _starting_x, int _starting_y, int _end_x, int _end_y) {
        starting_x = _starting_x;
        starting_y = _starting_y;
        end_x = _end_x;
        end_y = _end_y;
    }

    public static void loadSpritesAndHashTable() {
        block_sprite_1_11 = new Sprite(new Texture(Gdx.files.internal("textures/block/1_11.png")));
        block_sprite_1_12 = new Sprite(new Texture(Gdx.files.internal("textures/block/1_12.png")));
        block_sprite_1_21 = new Sprite(new Texture(Gdx.files.internal("textures/block/1_21.png")));
        block_sprite_1_22 = new Sprite(new Texture(Gdx.files.internal("textures/block/1_22.png")));
        block_sprite_1_32 = new Sprite(new Texture(Gdx.files.internal("textures/block/1_32.png")));
        block_sprite_1_31 = new Sprite(new Texture(Gdx.files.internal("textures/block/1_31.png")));

        block_sprite_2_11 = new Sprite(new Texture(Gdx.files.internal("textures/block/2_11.png")));
        block_sprite_2_12 = new Sprite(new Texture(Gdx.files.internal("textures/block/2_12.png")));
        block_sprite_2_21 = new Sprite(new Texture(Gdx.files.internal("textures/block/2_21.png")));
        block_sprite_2_22 = new Sprite(new Texture(Gdx.files.internal("textures/block/2_22.png")));
        block_sprite_2_32 = new Sprite(new Texture(Gdx.files.internal("textures/block/2_32.png")));
        block_sprite_2_31 = new Sprite(new Texture(Gdx.files.internal("textures/block/2_31.png")));

        block_sprite_3_11 = new Sprite(new Texture(Gdx.files.internal("textures/block/3_11.png")));
        block_sprite_3_12 = new Sprite(new Texture(Gdx.files.internal("textures/block/3_12.png")));
        block_sprite_3_21 = new Sprite(new Texture(Gdx.files.internal("textures/block/3_21.png")));
        block_sprite_3_22 = new Sprite(new Texture(Gdx.files.internal("textures/block/3_22.png")));
        block_sprite_3_32 = new Sprite(new Texture(Gdx.files.internal("textures/block/3_32.png")));
        block_sprite_3_31 = new Sprite(new Texture(Gdx.files.internal("textures/block/3_31.png")));

        block_sprite_4_11 = new Sprite(new Texture(Gdx.files.internal("textures/block/4_11.png")));
        block_sprite_4_12 = new Sprite(new Texture(Gdx.files.internal("textures/block/4_12.png")));
        block_sprite_4_21 = new Sprite(new Texture(Gdx.files.internal("textures/block/4_21.png")));
        block_sprite_4_22 = new Sprite(new Texture(Gdx.files.internal("textures/block/4_22.png")));
        block_sprite_4_32 = new Sprite(new Texture(Gdx.files.internal("textures/block/4_32.png")));
        block_sprite_4_31 = new Sprite(new Texture(Gdx.files.internal("textures/block/4_31.png")));

        block_sprite_5_11 = new Sprite(new Texture(Gdx.files.internal("textures/block/5_11.png")));
        block_sprite_5_12 = new Sprite(new Texture(Gdx.files.internal("textures/block/5_12.png")));
        block_sprite_5_21 = new Sprite(new Texture(Gdx.files.internal("textures/block/5_21.png")));
        block_sprite_5_22 = new Sprite(new Texture(Gdx.files.internal("textures/block/5_22.png")));
        block_sprite_5_32 = new Sprite(new Texture(Gdx.files.internal("textures/block/5_32.png")));
        block_sprite_5_31 = new Sprite(new Texture(Gdx.files.internal("textures/block/5_31.png")));

        spriteHashtable = new Hashtable<String, Sprite>();
        spriteHashtable.put("111", block_sprite_1_11);
        spriteHashtable.put("112", block_sprite_1_12);
        spriteHashtable.put("121", block_sprite_1_21);
        spriteHashtable.put("122", block_sprite_1_22);
        spriteHashtable.put("131", block_sprite_1_31);
        spriteHashtable.put("132", block_sprite_1_32);

        spriteHashtable.put("211", block_sprite_2_11);
        spriteHashtable.put("212", block_sprite_2_12);
        spriteHashtable.put("221", block_sprite_2_21);
        spriteHashtable.put("222", block_sprite_2_22);
        spriteHashtable.put("231", block_sprite_2_31);
        spriteHashtable.put("232", block_sprite_2_32);

        spriteHashtable.put("311", block_sprite_3_11);
        spriteHashtable.put("312", block_sprite_3_12);
        spriteHashtable.put("321", block_sprite_3_21);
        spriteHashtable.put("322", block_sprite_3_22);
        spriteHashtable.put("331", block_sprite_3_31);
        spriteHashtable.put("332", block_sprite_3_32);

        spriteHashtable.put("411", block_sprite_4_11);
        spriteHashtable.put("412", block_sprite_4_12);
        spriteHashtable.put("421", block_sprite_4_21);
        spriteHashtable.put("422", block_sprite_4_22);
        spriteHashtable.put("431", block_sprite_4_31);
        spriteHashtable.put("432", block_sprite_4_32);

        spriteHashtable.put("511", block_sprite_5_11);
        spriteHashtable.put("512", block_sprite_5_12);
        spriteHashtable.put("521", block_sprite_5_21);
        spriteHashtable.put("522", block_sprite_5_22);
        spriteHashtable.put("531", block_sprite_5_31);
        spriteHashtable.put("532", block_sprite_5_32);
    }
}
