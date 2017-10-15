package com.thechallengers.psagame.base_classes_and_interfaces;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.StretchViewport;
/**
 * Created by Phung Tuan Hoang on 9/9/2017.
 * Base class for different renderer
 * Includes cam and batcher
 * Includes coordination-translating functions
 * Call translate function instead of directly getting coordinates from object
 * Only applicable for portrait orientation. Multiplayer must create new cam viewport and stage
 */

public class ScreenRenderer {
    protected OrthographicCamera cam;
    protected SpriteBatch batcher;
    protected Stage stage;
    protected StretchViewport viewport;

    //constructor
    public ScreenRenderer() {
        cam = new OrthographicCamera();
        cam.setToOrtho(false, 1080, 1920);
        viewport = new StretchViewport(1080, 1920, cam);

        stage = new Stage(viewport);

        batcher = new SpriteBatch();
        batcher.setProjectionMatrix(cam.combined);
    }
}
