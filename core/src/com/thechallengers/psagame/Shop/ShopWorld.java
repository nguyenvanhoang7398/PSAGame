package com.thechallengers.psagame.Shop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.thechallengers.psagame.Menu.MenuScreen;
import com.thechallengers.psagame.base_classes_and_interfaces.ScreenWorld;
import com.thechallengers.psagame.game.PSAGame;
import com.thechallengers.psagame.helpers.AssetLoader;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.fadeIn;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.fadeOut;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.sequence;
import static com.thechallengers.psagame.game.PSAGame.CURRENT_SCREEN;

/**
 * Created by name on 3/10/2017.
 */

public class ShopWorld implements ScreenWorld{
    private Stage stage;
    private TextButton.TextButtonStyle back_button_style;
    private TextButton back_button;
    private ShopBackground shop_background;
    private PSAGame game;
    //private ImageButton shop_title;
    //private ImageButton.ImageButtonStyle shop_title_style;
    private ImageButton crane_lv1;
    private ImageButton.ImageButtonStyle crane_lv1_style;
    private ImageButton crane_lv2;
    private ImageButton.ImageButtonStyle crane_lv2_style;
    private ImageButton crane_lv3;
    private ImageButton.ImageButtonStyle crane_lv3_style;
//    private ImageButton crane_title;
//    private ImageButton.ImageButtonStyle crane_title_style;
    private ImageButton selection_bar;
    private ImageButton.ImageButtonStyle selection_bar_style;
    private ImageButton purchase_message;
    private ImageButton.ImageButtonStyle purchase_message_style;
    private ImageButton yes_button;
    private ImageButton.ImageButtonStyle yes_button_style;
    private ImageButton no_button;
    private ImageButton.ImageButtonStyle no_button_style;
    private ImageButton purchase_response;
    private ImageButton.ImageButtonStyle purchase_response_style;
    public boolean showPrice = true;


    //constructor
    public ShopWorld(PSAGame game) {
//        //for testing
//        Gdx.app.getPreferences("prefs").putInteger("moneyBalance", 100).flush();
//        Gdx.app.getPreferences("prefs").putBoolean("craneLv2_purchased", false).flush();
//        Gdx.app.getPreferences("prefs").putBoolean("craneLv3_purchased", false).flush();
//        Gdx.app.getPreferences("prefs").putInteger("crane_present", 1).flush();
        this.game = game;
        stage = new Stage();
        shop_background = new ShopBackground();
        createBackButton();
        //createTitle();
        createCraneSelection();


        stage.addActor(shop_background);
        stage.addActor(back_button);
        //stage.addActor(shop_title);
        //stage.addActor(crane_title);
        stage.addActor(crane_lv1);
        stage.addActor(crane_lv2);
        stage.addActor(crane_lv3);
        stage.addActor(selection_bar);
    }

    public void update(float delta) { stage.act(delta);}

    public void createBackButton() {
        back_button_style = new TextButton.TextButtonStyle();
        back_button_style.up = new TextureRegionDrawable(new TextureRegion(AssetLoader.shop_backButton));
        back_button_style.down = new TextureRegionDrawable(new TextureRegion(AssetLoader.shop_backButton));
        back_button_style.font = AssetLoader.arial;

        back_button = new TextButton("", back_button_style);
        back_button.setPosition(50, 1700);
        addListenerToBackButton();
    }



    public void addListenerToBackButton() {
        back_button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                back_button.addAction(sequence(fadeOut(0.6f), fadeIn(0.6f)));
                CURRENT_SCREEN = PSAGame.Screen.MenuScreen;
            }
        });
    }

    public void createCraneSelection() {
//        crane_title_style = new ImageButton.ImageButtonStyle();
//        crane_title_style.imageUp = new TextureRegionDrawable(new TextureRegion(AssetLoader.crane_title));
//        crane_title_style.imageDown = new TextureRegionDrawable(new TextureRegion(AssetLoader.crane_title));
//        crane_title = new ImageButton(crane_title_style);
//        crane_title.setPosition(100, 1200);

        crane_lv1_style = new ImageButton.ImageButtonStyle();
        crane_lv1_style.imageUp = new TextureRegionDrawable(new TextureRegion(AssetLoader.crane_lv1));
        crane_lv1_style.imageDown = new TextureRegionDrawable(new TextureRegion(AssetLoader.crane_lv1));
        crane_lv1 = new ImageButton(crane_lv1_style);
        crane_lv1.setPosition(100+100, 950-60);

        crane_lv2_style = new ImageButton.ImageButtonStyle();
        crane_lv2_style.imageUp = new TextureRegionDrawable(new TextureRegion(AssetLoader.crane_lv2));
        crane_lv2_style.imageDown = new TextureRegionDrawable(new TextureRegion(AssetLoader.crane_lv2));
        crane_lv2 = new ImageButton(crane_lv2_style);
        crane_lv2.setPosition(100+100, 950-270);

        crane_lv3_style = new ImageButton.ImageButtonStyle();
        crane_lv3_style.imageUp = new TextureRegionDrawable(new TextureRegion(AssetLoader.crane_lv3));
        crane_lv3_style.imageDown = new TextureRegionDrawable(new TextureRegion(AssetLoader.crane_lv3));
        crane_lv3 = new ImageButton(crane_lv3_style);
        crane_lv3.setPosition(100+100, 950-480);

        addListenerToCrane1();
        addListenerToCrane2();
        addListenerToCrane3();

        selection_bar_style = new ImageButton.ImageButtonStyle();
        selection_bar_style.imageUp = new TextureRegionDrawable(new TextureRegion(AssetLoader.selection_bar));
        selection_bar_style.imageDown = new TextureRegionDrawable(new TextureRegion(AssetLoader.selection_bar));
        selection_bar = new ImageButton(selection_bar_style);


        int crane_present = Gdx.app.getPreferences("prefs").getInteger("crane_present");
        switch (crane_present) {
            case 1: selection_bar.setPosition(100+100, 950-60);
                break;
            case 2: selection_bar.setPosition(100+100, 950-270);
                break;
            case 3: selection_bar.setPosition(100+100, 950-480);
                break;
            default: break;
        }

    }

    public void addListenerToCrane1() {
        crane_lv1.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.getPreferences("prefs").putInteger("crane_present", 1).flush();
                selection_bar.setPosition(100+100, 950-60);

            }
        });
    }


    public void addListenerToCrane2() {
        crane_lv2.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                boolean isBought =  Gdx.app.getPreferences("prefs").getBoolean("craneLv2_purchased");
                if (!isBought) {
                    createPurchaseMessage(2);
                } else {
                    Gdx.app.getPreferences("prefs").putInteger("crane_present", 2).flush();
                    selection_bar.setPosition(100+100, 950-270);
                }
            }
        });
    }

    public void addListenerToCrane3() {
        crane_lv3.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                boolean isBought =  Gdx.app.getPreferences("prefs").getBoolean("craneLv3_purchased");
                if (!isBought) {
                    createPurchaseMessage(3);
                } else {
                    Gdx.app.getPreferences("prefs").putInteger("crane_present", 3).flush();
                    selection_bar.setPosition(100+100, 950-480);
                }
            }
        });
    }

    public void createPurchaseFeedback(int status) {
        switch (status) {
            //fail purchase
            case 0: {
                purchase_response_style = new ImageButton.ImageButtonStyle();
                purchase_response_style.imageUp = new TextureRegionDrawable(new TextureRegion(AssetLoader.purchase_fail_message));
                purchase_response_style.imageDown = new TextureRegionDrawable(new TextureRegion(AssetLoader.purchase_fail_message));
                purchase_response = new ImageButton(purchase_response_style);
                purchase_response.setPosition(100, 850);
                stage.addActor(purchase_response);
                purchase_response.addListener(new ClickListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        purchase_response.remove();
                        showPrice = true;
                    }
                });

                break;
            }
            //success purchase
            case 1: {
                purchase_response_style = new ImageButton.ImageButtonStyle();
                purchase_response_style.imageUp = new TextureRegionDrawable(new TextureRegion(AssetLoader.purchase_success_message));
                purchase_response_style.imageDown = new TextureRegionDrawable(new TextureRegion(AssetLoader.purchase_success_message));
                purchase_response = new ImageButton(purchase_response_style);
                purchase_response.setPosition(100, 850);
                stage.addActor(purchase_response);
                purchase_response.addListener(new ClickListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        purchase_response.remove();
                        showPrice = true;
                    }
                });

                break;
            }

            default: break;
        }
    }

    public void createPurchaseMessage(final int craneLevel) {
        showPrice = false;

        purchase_message_style = new ImageButton.ImageButtonStyle();
        purchase_message_style.imageUp = new TextureRegionDrawable(new TextureRegion(AssetLoader.purchase_message));
        purchase_message_style.imageDown = new TextureRegionDrawable(new TextureRegion(AssetLoader.purchase_message));
        purchase_message = new ImageButton(purchase_message_style);
        purchase_message.setPosition(100, 850);

        yes_button_style = new ImageButton.ImageButtonStyle();
        yes_button_style.imageUp = new TextureRegionDrawable(new TextureRegion(AssetLoader.yes_button));
        yes_button_style.imageDown = new TextureRegionDrawable(new TextureRegion(AssetLoader.yes_button));
        yes_button = new ImageButton(yes_button_style);
        yes_button.setPosition(230, 900);

        no_button_style = new ImageButton.ImageButtonStyle();
        no_button_style.imageUp = new TextureRegionDrawable(new TextureRegion(AssetLoader.no_button));
        no_button_style.imageDown = new TextureRegionDrawable(new TextureRegion(AssetLoader.no_button));
        no_button = new ImageButton(no_button_style);
        no_button.setPosition(630, 900);

        stage.addActor(purchase_message);
        stage.addActor(yes_button);
        stage.addActor(no_button);

        yes_button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                switch (craneLevel) {
                    case 2: {
                        int curr_money = Gdx.app.getPreferences("prefs").getInteger("moneyBalance");
                        if (curr_money >= 10) {
                            Gdx.app.getPreferences("prefs").putInteger("moneyBalance", curr_money - 10).flush();
                            selection_bar.setPosition(100+100, 950-270);
                            Gdx.app.getPreferences("prefs").putInteger("crane_present", 2).flush();
                            Gdx.app.getPreferences("prefs").putBoolean("craneLv2_purchased", true).flush();
                            purchase_message.remove();
                            yes_button.remove();
                            no_button.remove();
                            createPurchaseFeedback(1);
                        } else {
                            purchase_message.remove();
                            yes_button.remove();
                            no_button.remove();
                            createPurchaseFeedback(0);
                        }

                        break;
                    }
                    case 3: {
                        int curr_money = Gdx.app.getPreferences("prefs").getInteger("moneyBalance");
                        if (curr_money >= 20) {
                            Gdx.app.getPreferences("prefs").putInteger("moneyBalance", curr_money - 20).flush();
                            selection_bar.setPosition(100+100, 950-480);
                            Gdx.app.getPreferences("prefs").putInteger("crane_present", 3).flush();
                            Gdx.app.getPreferences("prefs").putBoolean("craneLv3_purchased", true).flush();
                            purchase_message.remove();
                            yes_button.remove();
                            no_button.remove();
                            createPurchaseFeedback(1);
                        } else {
                            purchase_message.remove();
                            yes_button.remove();
                            no_button.remove();
                            createPurchaseFeedback(0);
                        }
                        break;
                    }
                    default: break;
                }
            }
        });

        no_button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                purchase_message.remove();
                yes_button.remove();
                no_button.remove();
                showPrice = true;
            }
        });

    }




    public Stage getStage() {return stage;}

}
