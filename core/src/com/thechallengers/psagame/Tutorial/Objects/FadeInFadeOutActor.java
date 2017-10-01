package com.thechallengers.psagame.Tutorial.Objects;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.fadeIn;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.run;

/**
 * Created by Phung Tuan Hoang on 10/1/2017.
 */

public class FadeInFadeOutActor extends Actor {
    private Texture texture;
    private FadeInFadeOutActor thisActor;
    private boolean isClicked;

    public FadeInFadeOutActor(Texture texture, int x, int y) {
        this.texture = texture;
        this.isClicked = false;
        this.thisActor = this;
        this.getColor().a = 0;
        this.addAction(fadeIn(0.5f));
        this.setPosition(x, y);
    }

    @Override
    public void act(float delta) {
        super.act(delta);

        if (isClicked) {
            System.out.println("CLICKED");
            this.addAction(Actions.sequence(Actions.fadeOut(0.5f), run(new Runnable() {
                @Override
                public void run() {
                    thisActor.remove();
                }
            })));
            isClicked = false;
        }
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

    public void clicked() {
        isClicked = true;
    }
}
