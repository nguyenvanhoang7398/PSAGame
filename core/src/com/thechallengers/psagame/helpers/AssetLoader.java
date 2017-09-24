package com.thechallengers.psagame.helpers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Created by Phung Tuan Hoang on 9/6/2017.
 */

public class AssetLoader {
    //textures - to be disposed after texture region is created
    public static Texture background_texture;
    public static Texture button_texture;
    public static Texture menu_crane_texture;
    public static TextureRegion game_background;
    public static Texture game_crane;
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

    //texture region
    public static TextureRegion button_up, button_down;
    public static TextureRegion background;

    //font
    public static BitmapFont arial;

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

        background = new TextureRegion(background_texture, 0, 0, 1080, 1920);
        button_up = new TextureRegion(button_texture, 0, 0, 200, 100);
        button_down = new TextureRegion(button_texture, 200, 0, 200, 100);

        arial = new BitmapFont(Gdx.files.internal("font/arial.fnt"));
    }

    public static void disposeMenuTexture() {
        background_texture.dispose();
        button_texture.dispose();
        arial.dispose();
    }

    public static void loadGameTexture() {
        game_crane = new Texture(Gdx.files.internal("textures/gamecrane.png"));
        game_background = new TextureRegion(new Texture(Gdx.files.internal("textures/menubackground.png")), starting_x, starting_y, end_x - starting_x, end_y - starting_y);
    }

    public static void updateGameBackGroundDetails(int _starting_x, int _starting_y, int _end_x, int _end_y) {
        starting_x = _starting_x;
        starting_y = _starting_y;
        end_x = _end_x;
        end_y = _end_y;
    }
}
