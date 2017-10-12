package com.thechallengers.psagame.helpers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.util.ArrayList;
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
    public static Texture game_background_2;
    public static Sprite game_crane;
    public static Sprite destroy_X;
    public static Texture cloud_1, cloud_2;
    public static Texture containers;
    public static Texture play_button, play_button_pressed;
    public static Texture menu_button, replay_button, next_button;
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
    public static Texture level1_Button;
    public static Texture level_title;
    public static Texture star0, star1, star2, star3 ;
    public static Texture shop_title;
    public static Texture crane_lv1, crane_lv2, crane_lv3, crane_title, selection_bar;
    public static Texture purchase_message, purchase_success_message, purchase_fail_message, yes_button, no_button;
    public static Texture start_game;
    public static Texture next_block_frame;
    public static TextureRegion[] progress;


    public static Texture silhouette_1;
    public static Texture actual_1;

    //box sprites
    public static Sprite block_sprite_1_11, block_sprite_1_12, block_sprite_1_21, block_sprite_1_22, block_sprite_1_32, block_sprite_1_31;
    public static Sprite block_sprite_2_11, block_sprite_2_12, block_sprite_2_21, block_sprite_2_22, block_sprite_2_32, block_sprite_2_31;
    public static Sprite block_sprite_3_11, block_sprite_3_12, block_sprite_3_21, block_sprite_3_22, block_sprite_3_32, block_sprite_3_31;
    public static Sprite block_sprite_4_11, block_sprite_4_12, block_sprite_4_21, block_sprite_4_22, block_sprite_4_32, block_sprite_4_31;
    public static Sprite block_sprite_5_11, block_sprite_5_12, block_sprite_5_21, block_sprite_5_22, block_sprite_5_32, block_sprite_5_31;

    //texture region
    public static TextureRegion button_up, button_down;
    public static TextureRegion background;
    public static TextureRegion winningBG;
    public static TextureRegion losingBG;

    public static Hashtable<String, Sprite> spriteHashtable;
    public static ArrayList<Texture> silhouetteArrayList;
    public static ArrayList<Texture> actualArrayList;

    //font
    public static BitmapFont arial;
    public static BitmapFont consolas_15;
    public static BitmapFont consolas_60;

    //position for game background (zoomed in from menu)
    public static int starting_x, starting_y, end_x, end_y;

    //tutorial
    public static Texture instructor_welcome;
    public static Texture instructor_after_tilted;
    public static Texture instructor_aim;
    public static Texture instructor_destroy;
    public static Texture instructor_drop;
    public static Texture instructor_goodluck;
    public static Texture instructor_progress;
    public static Texture instructor_tilted;
    public static Texture balloon_after_tilted;
    public static Texture balloon_aim;
    public static Texture balloon_destroy;
    public static Texture balloon_drop;
    public static Texture balloon_goodluck;
    public static Texture balloon_progress;
    public static Texture balloon_tilted;
    public static Texture balloon_welcome;
    public static Texture pointer_drop;
    public static Texture pointer_tilted;
    public static Texture pointer_after_tilted;
    public static Texture pointer_destroy_1;
    public static Texture pointer_destroy_2;
    public static Texture destroy_mode;
    public static Texture bomb;
    public static Texture endGameBackground;
    public static Texture level_selection;
    public static Texture next_button_down;
    public static Texture level_selection_down;
    public static Texture menu_button_down;
    public static Texture replay_button_down;

    //animation
    public static Animation<TextureRegion> cooldown_animation;
    public static Animation<TextureRegion> clock_animation;
    public static Animation<TextureRegion> arrow_animation;

    //end game
    public static Texture[] tips = new Texture[5];
    public static Texture star_background;
    public static ArrayList<Texture> star;

    public static void loadMenuTexture() {

        background_texture = new Texture(Gdx.files.internal("textures/menubackground.png"));
        game_background_2 = new Texture("textures/game_background_2.png");
        button_texture = new Texture(Gdx.files.internal("textures/button.png"));
        menu_crane_texture = new Texture(Gdx.files.internal("textures/menucrane.png"));
        cloud_1 = new Texture(Gdx.files.internal("textures/cloud_1.png"));
        cloud_2 = new Texture(Gdx.files.internal("textures/cloud_2.png"));
        containers = new Texture(Gdx.files.internal("textures/containers.png"));
        play_button = new Texture(Gdx.files.internal("textures/play_button.png"));
        play_button_pressed = new Texture(Gdx.files.internal("textures/play_button_pressed.png"));
        menu_button = new Texture(Gdx.files.internal("textures/menu_button.png"));
        replay_button = new Texture(Gdx.files.internal("textures/replay_button.png"));
        next_button = new Texture(Gdx.files.internal("textures/next_button.png"));
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
        consolas_60 = new BitmapFont(Gdx.files.internal("font/consolas_60.fnt"));
    }

    public static void disposeMenuTexture() {
        background_texture.dispose();
        button_texture.dispose();
        arial.dispose();
    }

    public static void loadLevelSelectionTexture() {
        level_selection_background = new Texture(Gdx.files.internal("textures/level_selection_background.png"));
        level_backButton = new Texture(Gdx.files.internal("textures/shop_backButton.png"));
        level1_Button = new Texture(Gdx.files.internal("textures/level1.png"));
        level_title = new Texture(Gdx.files.internal("textures/level_title.png"));
        star0 = new Texture(Gdx.files.internal("textures/0_stars.png"));
        star1 = new Texture(Gdx.files.internal("textures/1_stars.png"));
        star2 = new Texture(Gdx.files.internal("textures/2_stars.png"));
        star3 = new Texture(Gdx.files.internal("textures/3_stars.png"));
    }

    public static void disposeLevelSelectionTexture() {
        level_selection_background.dispose();
        level_backButton.dispose();
        level1_Button.dispose();
        level_title.dispose();
        star0.dispose();
        star1.dispose();
        star2.dispose();
        star3.dispose();
    }

    public static void loadShopTexture() {
        shop_background = new Texture(Gdx.files.internal("textures/gamebackground.png"));
        shop_backButton = new Texture(Gdx.files.internal("textures/shop_backButton.png"));
        shop_title = new Texture(Gdx.files.internal("textures/shop_title.png"));
        crane_lv1 = new Texture(Gdx.files.internal("textures/crane_lv1.png"));
        crane_lv2 = new Texture(Gdx.files.internal("textures/crane_lv2.png"));
        crane_lv3 = new Texture(Gdx.files.internal("textures/crane_lv3.png"));
        crane_title = new Texture(Gdx.files.internal("textures/crane_title.png"));
        selection_bar = new Texture(Gdx.files.internal("textures/selection_bar.png"));
        purchase_message = new Texture(Gdx.files.internal("textures/purchasing_message.png"));
        purchase_fail_message =  new Texture(Gdx.files.internal("textures/purchasing_fail_message.png"));
        purchase_success_message =  new Texture(Gdx.files.internal("textures/purchasing_success_message.png"));
        yes_button = new Texture(Gdx.files.internal("textures/yes_button.png"));
        no_button = new Texture(Gdx.files.internal("textures/no_button.png"));

    }

    public static void disposeShopTexture() {
        shop_background.dispose();
        shop_backButton.dispose();
        shop_title.dispose();
        crane_lv1.dispose();
        crane_lv2.dispose();
        crane_lv3.dispose();
        crane_title.dispose();
        selection_bar.dispose();
        purchase_message.dispose();
        purchase_fail_message.dispose();
        purchase_success_message.dispose();
        yes_button.dispose();
        no_button.dispose();

    }

    public static void loadGameTexture() {
        game_crane = new Sprite(new Texture(Gdx.files.internal("textures/gamecrane.png")));
        destroy_X = new Sprite(new Texture(Gdx.files.internal("textures/destroy_X.png")));
        game_background = new Texture(Gdx.files.internal("textures/game_background.png"));
        unitBlockTexture = new Texture("textures/unitBlock.png");
        silhouette_1 = new Texture(Gdx.files.internal("textures/silhouette_1.png"));
        actual_1 = new Texture(Gdx.files.internal("textures/actual_1.png"));

        for (int i = 1; i <= 5; i++) {
            tips[i - 1] = new Texture("textures/tips_" + String.valueOf(i) +".png");
        }

        star_background = new Texture(Gdx.files.internal("textures/star_background.png"));
        star = new ArrayList<Texture>();
        star.add(new Texture(Gdx.files.internal("textures/star_1.png")));
        star.add(new Texture(Gdx.files.internal("textures/star_2.png")));
        star.add(new Texture(Gdx.files.internal("textures/star_3.png")));
        destroy_mode = new Texture("textures/destroy_mode.png");
        start_game = new Texture("textures/start_game.png");
        next_block_frame = new Texture("textures/next_block_frame.png");
        level_selection = new Texture("textures/level_selection.png");
        bomb = new Texture("textures/bomb.png");
        TextureRegion[][] tmp = TextureRegion.split(new Texture("textures/progress.png"),
                100, 100);

        progress = new TextureRegion[9];

        int index = 0;
        for (int i = 0; i < 9; i++) {
            progress[index++] = tmp[0][i];
        }

        cooldown_animation = createAnimation(new Texture("textures/cooldown_animation.png"), 1, 4, 1);
        clock_animation = createAnimation(new Texture("textures/clock.png"), 1, 4, 1);
        arrow_animation = createAnimation(new Texture("textures/arrow_indicator.png"), 1, 9, 0.1f);

        loadSpritesAndHashTable();

        endGameBackground = new Texture("textures/end_game_background.png");

        next_button_down = new Texture("textures/next_button_down.png");
        level_selection_down = new Texture("textures/level_selection_down.png");
        menu_button_down = new Texture("textures/menu_button_down.png");
        replay_button_down = new Texture("textures/replay_button_down.png");
        /*
        silhouetteArrayList = new ArrayList<Texture>();
        for (int i = 1; i <= 5; i++) {
            silhouetteArrayList.add(new Texture("textures/silhouette_" + String.valueOf(i)));
        }

        actualArrayList = new ArrayList<Texture>();
        for (int i = 1; i <= 5; i++) {
            actualArrayList.add(new Texture("textures/actual_" + String.valueOf(i)));
        }
        */
    }

    public static void updateGameBackGroundDetails(int _starting_x, int _starting_y, int _end_x, int _end_y) {
        starting_x = _starting_x;
        starting_y = _starting_y;
        end_x = _end_x;
        end_y = _end_y;
    }

    public static void loadTutorialTexture() {
        instructor_welcome = new Texture(Gdx.files.internal("textures/tutorial/instructor_welcome.png"));
        balloon_welcome = new Texture(Gdx.files.internal("textures/tutorial/balloon_welcome.png"));
        pointer_drop = new Texture(Gdx.files.internal("textures/tutorial/pointer_drop.png"));
        pointer_tilted = new Texture(Gdx.files.internal("textures/tutorial/pointer_tilted.png"));
        pointer_after_tilted = new Texture(Gdx.files.internal("textures/tutorial/pointer_after_tilted.png"));
        instructor_after_tilted = new Texture(Gdx.files.internal("textures/tutorial/instructor_after_tilted.png"));
        instructor_aim = new Texture(Gdx.files.internal("textures/tutorial/instructor_aim.png"));
        instructor_destroy = new Texture(Gdx.files.internal("textures/tutorial/instructor_destroy.png"));
        instructor_drop = new Texture(Gdx.files.internal("textures/tutorial/instructor_drop.png"));
        instructor_goodluck = new Texture(Gdx.files.internal("textures/tutorial/instructor_goodluck.png"));
        instructor_progress = new Texture(Gdx.files.internal("textures/tutorial/instructor_progress.png"));
        instructor_tilted = new Texture(Gdx.files.internal("textures/tutorial/instructor_tilted.png"));
        balloon_after_tilted = new Texture(Gdx.files.internal("textures/tutorial/balloon_after_tilted.png"));
        balloon_aim = new Texture(Gdx.files.internal("textures/tutorial/balloon_aim.png"));
        balloon_destroy = new Texture(Gdx.files.internal("textures/tutorial/balloon_destroy.png"));
        balloon_drop = new Texture(Gdx.files.internal("textures/tutorial/balloon_drop.png"));
        balloon_goodluck = new Texture(Gdx.files.internal("textures/tutorial/balloon_goodluck.png"));
        balloon_progress = new Texture(Gdx.files.internal("textures/tutorial/balloon_progress.png"));
        balloon_tilted = new Texture(Gdx.files.internal("textures/tutorial/balloon_tilted.png"));
        pointer_destroy_1 = new Texture(Gdx.files.internal("textures/tutorial/pointer_destroy_1.png"));
        pointer_destroy_2 = new Texture(Gdx.files.internal("textures/tutorial/pointer_destroy_2.png"));
    }

    public static Animation<TextureRegion> createAnimation(Texture texture, int row, int col, float interval) {
        TextureRegion[][] tmp = TextureRegion.split(texture,
                texture.getWidth() / col,
                texture.getHeight() / row);

        TextureRegion[] frames = new TextureRegion[row * col];

        int index = 0;
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                frames[index++] = tmp[i][j];
            }
        }

        Animation<TextureRegion> animation = new Animation<TextureRegion>(interval, frames);
        animation.setPlayMode(Animation.PlayMode.LOOP);

        return animation;
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
