package com.thechallengers.psagame.SinglePlayer.Objects;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputListener;

/**
 * Created by name on 27/9/2017.
 */

public class Worker extends Actor {
    Texture textureNormal = new Texture("textures/worker.png");
    Texture textureSad = new Texture("textures/workerSad.png");
    Texture instruction = new Texture("textures/instructionBubble.png");
    Texture warning = new Texture("textures/warningBubble.png");
    float actorX, actorY, bubbleX, bubbleY;
    private boolean sad = false;
    private int sadTime = 0;


    public Worker() {
        actorX = 0;
        actorY = 0;
        bubbleX = textureNormal.getWidth();
        bubbleY = textureNormal.getHeight()/2;
        setBounds(actorX, actorY, textureNormal.getWidth(), textureNormal.getHeight());
    }

    public void draw(Batch batch, float parentAlpha) {
        batch.setColor(this.getColor());
        // always make sure to only multiply by the parent alpha
        batch.getColor().a *= parentAlpha;

        // do your drawing
        if (sadTime == 80) {
            sad = false;
            sadTime = 0;
        }
        if (sad && sadTime < 80) {
            batch.draw(textureSad, actorX, actorY);
            batch.draw(warning, bubbleX,bubbleY);
            sadTime ++;
        } else {
            batch.draw(textureNormal, actorX, actorY);
            batch.draw(instruction, bubbleX, bubbleY);
        }

        batch.setColor(Color.WHITE); // reset the color
    }

    public void toSad() {
        sad = true;
        sadTime = 0;
    }
}
