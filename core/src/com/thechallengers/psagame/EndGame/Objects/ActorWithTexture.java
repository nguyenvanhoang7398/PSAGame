package com.thechallengers.psagame.EndGame.Objects;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.delay;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.fadeIn;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.run;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.sequence;

/**
 * Created by Phung Tuan Hoang on 10/1/2017.
 */

public class ActorWithTexture extends Actor {
    private TextureRegion texture;
    private ActorWithTexture thisActor;

    public ActorWithTexture(TextureRegion texture, int x, int y) {
        this.texture = texture;
        this.thisActor = this;
        this.getColor().a = 0;
        this.setPosition(x, y);
    }

    public ActorWithTexture(Texture texture, int x, int y) {
        this.texture = new TextureRegion(texture);
        this.thisActor = this;
        this.getColor().a = 0;
        this.setPosition(x, y);
    }

    public ActorWithTexture(TextureRegion texture, int x, int y, float delayTime) {
        this.texture = texture;
        this.thisActor = this;
        this.getColor().a = 0;
        this.addAction(delay(delayTime));
        this.setPosition(x, y);
    }

    public ActorWithTexture(Texture texture, int x, int y, float delayTime) {
        this.texture = new TextureRegion(texture);
        this.thisActor = this;
        this.getColor().a = 0;
        this.addAction(delay(delayTime));
        this.setPosition(x, y);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.setColor(this.getColor());
        // always make sure to only multiply by the parent alpha
        batch.getColor().a *= parentAlpha;

        // do your drawing
        batch.draw(texture, (int) this.getX(), (int) this.getY());

        batch.setColor(Color.WHITE); // reset the color
    }
}
