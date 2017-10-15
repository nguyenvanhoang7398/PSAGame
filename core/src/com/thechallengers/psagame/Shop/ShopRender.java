package com.thechallengers.psagame.Shop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.thechallengers.psagame.base_classes_and_interfaces.ScreenRenderer;
import com.thechallengers.psagame.helpers.AssetLoader;

/**
 * Created by name on 3/10/2017.
 */

public class ShopRender extends ScreenRenderer {
    private ShopWorld world;
    SpriteBatch batch;
    BitmapFont moneyBalanceFont;
    String moneyBalance;

    //constructor
    public ShopRender(ShopWorld world) {
        super();
        this.world = world;
        stage = world.getStage();
        stage.setViewport(viewport);
        Gdx.input.setInputProcessor(stage);
        batch = new SpriteBatch();
        batch.setProjectionMatrix(cam.combined);
        moneyBalanceFont = AssetLoader.consolas_60;
        //moneyBalanceFont.getData().setScale(3.5f, 3.5f);
        moneyBalanceFont.setColor(1.0f,1.0f,1.0f,1.0f);
        moneyBalance = "";
    }

    public void render(float runTime) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.draw();
        moneyBalance = "" + Gdx.app.getPreferences("prefs").getInteger("moneyBalance");
        batch.begin();
        moneyBalanceFont.setColor(1.0f, 1.0f, 1.0f, 1.0f);
        moneyBalanceFont.draw(batch, moneyBalance, 750, 1700+120);
        if (world.showPrice) {
            moneyBalanceFont.draw(batch, "Owned", 100+100+400, 950-60+20+50);
            boolean crane2_purchased = Gdx.app.getPreferences("prefs").getBoolean("craneLv2_purchased");
            boolean crane3_purchased = Gdx.app.getPreferences("prefs").getBoolean("craneLv3_purchased");
            if (!crane2_purchased) {
                moneyBalanceFont.draw(batch, "10", 100+100+400, 950-270+40+50);
            } else {
                moneyBalanceFont.draw(batch, "Owned", 100+100+400, 950-270+40+50);
            }
            if (!crane3_purchased) {
                moneyBalanceFont.draw(batch, "20", 100+100+400, 950-480+50+50);
            } else {
                moneyBalanceFont.draw(batch, "Owned", 100+100+400, 950-480+50+50);
            }
        }

        batch.end();


    }

}
