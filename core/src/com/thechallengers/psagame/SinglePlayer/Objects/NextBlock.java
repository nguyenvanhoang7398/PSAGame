package com.thechallengers.psagame.SinglePlayer.Objects;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.thechallengers.psagame.SinglePlayer.Physics.Block;
import com.thechallengers.psagame.helpers.AssetLoader;

import javax.accessibility.AccessibleStateSet;

/**
 * Created by Phung Tuan Hoang on 10/12/2017.
 */

public class NextBlock extends Actor {
    private Texture nextBlockFrame;
    private Texture nextBlock;
    private Vector2 relativePosition;
    public String type;

    public NextBlock(Block block) {
        nextBlock = (AssetLoader.spriteHashtable.get(block.blockType)).getTexture();
        nextBlockFrame = AssetLoader.next_block_frame;
        relativePosition = getRelativePosition(block);
        type = block.blockType;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.setColor(this.getColor());
        // always make sure to only multiply by the parent alpha
        batch.getColor().a *= parentAlpha;

        // do your drawing
        batch.draw(nextBlockFrame, (int) this.getX(), (int) this.getY());
        batch.draw(nextBlock, (int) (this.getX() + relativePosition.x), (int) (this.getY() + relativePosition.y));

        batch.setColor(Color.WHITE); // reset the color
    }

    @Override
    public void act(float delta) {
        super.act(delta);

    }

    public Vector2 getRelativePosition(Block block) {
        int width = block.width;
        int height = block.height;

        float relativeX = (nextBlockFrame.getWidth() - width * 100) / 2f;
        float relativeY = (nextBlockFrame.getHeight() - height * 100) / 2f;

        return new Vector2(relativeX, relativeY);
    }
}
