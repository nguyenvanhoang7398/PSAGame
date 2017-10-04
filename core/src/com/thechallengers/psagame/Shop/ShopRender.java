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
        moneyBalanceFont = AssetLoader.arial;
        moneyBalanceFont.getData().setScale(1.5f, 1.5f);
        moneyBalance = "";
    }

    public void render(float runTime) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.draw();
        moneyBalance = "Balance: " + Gdx.app.getPreferences("prefs").getInteger("moneyBalance");
        batch.begin();
        moneyBalanceFont.setColor(1.0f, 1.0f, 1.0f, 1.0f);
        moneyBalanceFont.draw(batch, moneyBalance, 600, 1700);
        if (world.showPrice) {
            moneyBalanceFont.draw(batch, "Owned", 100, 850);
            boolean crane2_purchased = Gdx.app.getPreferences("prefs").getBoolean("craneLv2_purchased");
            boolean crane3_purchased = Gdx.app.getPreferences("prefs").getBoolean("craneLv3_purchased");
            if (!crane2_purchased) {
                moneyBalanceFont.draw(batch, "Price: 10", 450, 850);
            } else {
                moneyBalanceFont.draw(batch, "Owned", 450, 850);
            }
            if (!crane3_purchased) {
                moneyBalanceFont.draw(batch, "Price: 20", 800, 850);
            } else {
                moneyBalanceFont.draw(batch, "Owned", 800, 850);
            }
        }

        batch.end();


    }

}
