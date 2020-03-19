package Screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;

import java.util.*;

public class CustomizedMenu implements Screen {

    GameUI customizedMenuGame;
    Texture customizedMenuBackButton;
    Texture customizedMenuLine;
    Texture customizedMenuOkButton;
    BitmapFont customizedMenuFont;
    BitmapFont customizedMenuVelocity;
    BitmapFont customizedMenuAcceleration;
    BitmapFont customizedMenuCoefficient;
    BitmapFont customizedMenuDistance;
    BitmapFont customizedMenuMass;
    BitmapFont customizedMenuHeight;
    BitmapFont customizedMenuHeight2;
    BitmapFont customizedMenuHeight3;
    Stage customizedMenuStage;

    TextField customizedMenuVel;
    TextField customizedMenuAcc;
    TextField customizedMenuMu;
    TextField customizedMenuDis;
    TextField customizedMenuMas;
    TextField customizedMenuHei;

    //Variables fot the answers
    BitmapFont customizedMenuVelocity1;

    private static final int customizedMenu_BACK_BUTTON_SIZE = 80;
    private static final int customizedMenu_OK_BUTTON_WIDTH = 90;
    private static final int customizedMenu_OK_BUTTON_HEIGHT = 90;

    //Constructor
    public CustomizedMenu(GameUI game){
        this.customizedMenuGame = game;
        customizedMenuBackButton = new Texture("back.png");
        customizedMenuLine = new Texture("line.png");
        customizedMenuOkButton = new Texture("ok.png");


        customizedMenuStage = new Stage();
        Gdx.input.setInputProcessor(customizedMenuStage);
        Skin customizedMenuSkin = new Skin(Gdx.files.internal("skins/uiskin.json"));

        //Different TextFields
        //Velocity
        customizedMenuVel = new TextField("", customizedMenuSkin);
        customizedMenuVel.setPosition((GameUI.gameUI_WINDOW_WIDTH/3) - 40, GameUI.gameUI_WINDOW_HEIGHT-317);
        customizedMenuVel.setSize(200, 20);
        customizedMenuVel.setColor(Color.FOREST);
        //Acceleration
        customizedMenuAcc = new TextField("", customizedMenuSkin);
        customizedMenuAcc.setPosition((GameUI.gameUI_WINDOW_WIDTH/3) - 40, GameUI.gameUI_WINDOW_HEIGHT-367);
        customizedMenuAcc.setSize(200, 20);
        customizedMenuAcc.setColor(Color.FOREST);
        //Mu
        customizedMenuMu = new TextField("", customizedMenuSkin);
        customizedMenuMu.setPosition((GameUI.gameUI_WINDOW_WIDTH/3) + 40, GameUI.gameUI_WINDOW_HEIGHT-417);
        customizedMenuMu.setSize(200, 20);
        customizedMenuMu.setColor(Color.FOREST);
        //Distance
        customizedMenuDis = new TextField("", customizedMenuSkin);
        customizedMenuDis.setPosition((GameUI.gameUI_WINDOW_WIDTH/3) + 40, GameUI.gameUI_WINDOW_HEIGHT-467);
        customizedMenuDis.setSize(200, 20);
        customizedMenuDis.setColor(Color.FOREST);
        //Mass
        customizedMenuMas = new TextField("", customizedMenuSkin);
        customizedMenuMas.setPosition((GameUI.gameUI_WINDOW_WIDTH/3) - 40, GameUI.gameUI_WINDOW_HEIGHT-517);
        customizedMenuMas.setSize(200, 20);
        customizedMenuMas.setColor(Color.FOREST);
        //Height Equation
        customizedMenuHei = new TextField("", customizedMenuSkin);
        customizedMenuHei.setPosition((GameUI.gameUI_WINDOW_WIDTH/3) + 40, GameUI.gameUI_WINDOW_HEIGHT-567);
        customizedMenuHei.setSize(200, 20);
        customizedMenuHei.setColor(Color.FOREST);

        customizedMenuStage.addActor(customizedMenuVel);
        customizedMenuStage.addActor(customizedMenuAcc);
        customizedMenuStage.addActor(customizedMenuMu);
        customizedMenuStage.addActor(customizedMenuDis);
        customizedMenuStage.addActor(customizedMenuMas);
        customizedMenuStage.addActor(customizedMenuHei);


    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {

        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        customizedMenuGame.gameUIBatch.begin();

        FreeTypeFontGenerator customizedMenuGenerator = new FreeTypeFontGenerator(Gdx.files.internal("Georgia Italic.ttf"));
        FreeTypeFontGenerator customizedMenuWritingStyle = new FreeTypeFontGenerator(Gdx.files.internal("Courier New.ttf"));

        //Different texts
        FreeTypeFontGenerator.FreeTypeFontParameter customizedMenuParameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        FreeTypeFontGenerator.FreeTypeFontParameter customizedMenuVelo = new FreeTypeFontGenerator.FreeTypeFontParameter();
        FreeTypeFontGenerator.FreeTypeFontParameter customizedMenuAcc = new FreeTypeFontGenerator.FreeTypeFontParameter();
        FreeTypeFontGenerator.FreeTypeFontParameter customizedMenuMu = new FreeTypeFontGenerator.FreeTypeFontParameter();
        FreeTypeFontGenerator.FreeTypeFontParameter customizedMenuDist = new FreeTypeFontGenerator.FreeTypeFontParameter();
        FreeTypeFontGenerator.FreeTypeFontParameter customizedMenuWeight = new FreeTypeFontGenerator.FreeTypeFontParameter();
        FreeTypeFontGenerator.FreeTypeFontParameter customizedMenuHauteur = new FreeTypeFontGenerator.FreeTypeFontParameter();
        FreeTypeFontGenerator.FreeTypeFontParameter customizedMenuHauteur2 = new FreeTypeFontGenerator.FreeTypeFontParameter();
        FreeTypeFontGenerator.FreeTypeFontParameter customizedMenuHauteur3 = new FreeTypeFontGenerator.FreeTypeFontParameter();

        customizedMenuParameter.size = 60;
        customizedMenuVelo.size = 20;
        customizedMenuAcc.size = 20;
        customizedMenuMu.size = 20;
        customizedMenuDist.size = 20;
        customizedMenuWeight.size = 20;
        customizedMenuHauteur.size = 20;
        customizedMenuHauteur2.size = 20;
        customizedMenuHauteur3.size = 20;

        customizedMenuParameter.characters = "customized Menu";
        customizedMenuVelo.characters = "Initial Velocity: ";
        customizedMenuAcc.characters = "Acceleration: ";
        customizedMenuMu.characters = "Coefficient of friction: ";
        customizedMenuDist.characters = "Distance from the Hole: ";
        customizedMenuWeight.characters = "Mass of the ball: ";
        customizedMenuHauteur.characters = "Equation of the height: ";
        customizedMenuHauteur2.characters = "Write the equation in that form please: ";
        customizedMenuHauteur3.characters = "-0.01*x + 0.003*x^2 + 0.04 * y";

        customizedMenuFont = customizedMenuGenerator.generateFont(customizedMenuParameter);
        customizedMenuVelocity = customizedMenuWritingStyle.generateFont(customizedMenuVelo);
        customizedMenuAcceleration = customizedMenuWritingStyle.generateFont(customizedMenuAcc);
        customizedMenuCoefficient = customizedMenuWritingStyle.generateFont(customizedMenuMu);
        customizedMenuDistance = customizedMenuWritingStyle.generateFont(customizedMenuDist);
        customizedMenuMass = customizedMenuWritingStyle.generateFont(customizedMenuWeight);
        customizedMenuHeight = customizedMenuWritingStyle.generateFont(customizedMenuHauteur);
        customizedMenuHeight2 = customizedMenuWritingStyle.generateFont(customizedMenuHauteur2);
        customizedMenuHeight3 = customizedMenuWritingStyle.generateFont(customizedMenuHauteur3);

        customizedMenuFont.setColor(Color.FOREST);
        customizedMenuVelocity.setColor(Color.FOREST);
        customizedMenuAcceleration.setColor(Color.FOREST);
        customizedMenuCoefficient.setColor(Color.FOREST);
        customizedMenuDistance.setColor(Color.FOREST);
        customizedMenuMass.setColor(Color.FOREST);
        customizedMenuHeight.setColor(Color.FOREST);
        customizedMenuHeight2.setColor(Color.FOREST);
        customizedMenuHeight3.setColor(Color.FOREST);


        customizedMenuGenerator.dispose();

        customizedMenuFont.draw(customizedMenuGame.gameUIBatch, "customized Menu", GameUI.gameUI_WINDOW_WIDTH/3, GameUI.gameUI_WINDOW_HEIGHT-100);
        customizedMenuVelocity.draw(customizedMenuGame.gameUIBatch, "Initial Velocity: ", GameUI.gameUI_WINDOW_WIDTH/8, GameUI.gameUI_WINDOW_HEIGHT-300);
        customizedMenuAcceleration.draw(customizedMenuGame.gameUIBatch, "Acceleration: ", GameUI.gameUI_WINDOW_WIDTH/8, GameUI.gameUI_WINDOW_HEIGHT-350);
        customizedMenuCoefficient.draw(customizedMenuGame.gameUIBatch, "Coefficient of friction: ", GameUI.gameUI_WINDOW_WIDTH/8, GameUI.gameUI_WINDOW_HEIGHT-400);
        customizedMenuDistance.draw(customizedMenuGame.gameUIBatch, "Distance from the Hole: ", GameUI.gameUI_WINDOW_WIDTH/8, GameUI.gameUI_WINDOW_HEIGHT-450);
        customizedMenuMass.draw(customizedMenuGame.gameUIBatch, "Mass of the ball: ", GameUI.gameUI_WINDOW_WIDTH/8, GameUI.gameUI_WINDOW_HEIGHT-500);
        customizedMenuHeight.draw(customizedMenuGame.gameUIBatch, "Equation of the height: ", GameUI.gameUI_WINDOW_WIDTH/8, GameUI.gameUI_WINDOW_HEIGHT-550);
        customizedMenuHeight2.draw(customizedMenuGame.gameUIBatch, "Write the equation in that form please:", GameUI.gameUI_WINDOW_WIDTH/8, GameUI.gameUI_WINDOW_HEIGHT-600);
        customizedMenuHeight3.draw(customizedMenuGame.gameUIBatch, "-0.01*x + 0.003*x^2 + 0.04 * y", GameUI.gameUI_WINDOW_WIDTH/2, GameUI.gameUI_WINDOW_HEIGHT-600);


        //Drawing the line under the Back Button
        if(Gdx.input.getX() > GameUI.gameUI_WINDOW_WIDTH/10 && Gdx.input.getX() < (GameUI.gameUI_WINDOW_WIDTH/10) + customizedMenu_BACK_BUTTON_SIZE && GameUI.gameUI_WINDOW_HEIGHT - Gdx.input.getY() < customizedMenu_BACK_BUTTON_SIZE + GameUI.gameUI_WINDOW_HEIGHT-140 && GameUI.gameUI_WINDOW_HEIGHT - Gdx.input.getY() > GameUI.gameUI_WINDOW_HEIGHT-140 ){
            customizedMenuGame.gameUIBatch.draw(customizedMenuLine, (GameUI.gameUI_WINDOW_WIDTH)/10, (GameUI.gameUI_WINDOW_HEIGHT-220), customizedMenu_BACK_BUTTON_SIZE, 150);
            if(Gdx.input.justTouched()){
                this.dispose();
                customizedMenuGame.setScreen(new StartMenu(customizedMenuGame));
            }
        }

        //Drawing the line under the Ok button
        if(Gdx.input.getX() > GameUI.gameUI_WINDOW_WIDTH-150 && Gdx.input.getX() < (GameUI.gameUI_WINDOW_WIDTH-150) + customizedMenu_OK_BUTTON_WIDTH && GameUI.gameUI_WINDOW_HEIGHT - Gdx.input.getY() < customizedMenu_OK_BUTTON_HEIGHT + GameUI.gameUI_WINDOW_HEIGHT/10 && GameUI.gameUI_WINDOW_HEIGHT - Gdx.input.getY() > GameUI.gameUI_WINDOW_HEIGHT/10 ){
            customizedMenuGame.gameUIBatch.draw(customizedMenuLine, GameUI.gameUI_WINDOW_WIDTH-150, (GameUI.gameUI_WINDOW_HEIGHT/10)-60, customizedMenu_OK_BUTTON_WIDTH, 150);
            if(Gdx.input.justTouched()){
                this.dispose();
                customizedMenuGame.setScreen(new ChoiceModeScreen(customizedMenuGame));
//                System.out.println("Velocity: " + getcustomizedMenuVel());
//                System.out.println("Acceleration: " + getcustomizedMenuAcc());
//                System.out.println("Coefficient: " + getcustomizedMenuMu());
//                System.out.println("Distance: " + getcustomizedMenuDis());
//                System.out.println("Mass: " + getcustomizedMenuMas());
//                System.out.println("Height Equation: " + getcustomizedMenuHei());
            }
        }
        //Drawing the different buttons
        customizedMenuGame.gameUIBatch.draw(customizedMenuBackButton, GameUI.gameUI_WINDOW_WIDTH/10, GameUI.gameUI_WINDOW_HEIGHT-150, customizedMenu_BACK_BUTTON_SIZE, customizedMenu_BACK_BUTTON_SIZE);
        customizedMenuGame.gameUIBatch.draw(customizedMenuOkButton, GameUI.gameUI_WINDOW_WIDTH-150, GameUI.gameUI_WINDOW_HEIGHT/10, customizedMenu_OK_BUTTON_WIDTH, customizedMenu_OK_BUTTON_HEIGHT);

        //Drawing the line under the different texts
        //And get the input for each value when they're clicked

        FreeTypeFontGenerator.FreeTypeFontParameter customizedMenuVelo2 = new FreeTypeFontGenerator.FreeTypeFontParameter();
        customizedMenuVelo2.size = 100;
        customizedMenuVelo2.characters = " ";
        customizedMenuVelocity1 = customizedMenuWritingStyle.generateFont(customizedMenuVelo2);
        customizedMenuVelocity1.setColor(Color.FOREST);
        customizedMenuWritingStyle.dispose();
        customizedMenuVelocity1.draw(customizedMenuGame.gameUIBatch, " ", GameUI.gameUI_WINDOW_WIDTH/6, GameUI.gameUI_WINDOW_HEIGHT-380);

        //Velocity
        if(Gdx.input.getX() > GameUI.gameUI_WINDOW_WIDTH/8 && Gdx.input.getX() < (GameUI.gameUI_WINDOW_WIDTH/8) + 200 && GameUI.gameUI_WINDOW_HEIGHT - Gdx.input.getY() <  GameUI.gameUI_WINDOW_HEIGHT-300 && GameUI.gameUI_WINDOW_HEIGHT - Gdx.input.getY() > GameUI.gameUI_WINDOW_HEIGHT-330 ){
            customizedMenuGame.gameUIBatch.draw(customizedMenuLine, GameUI.gameUI_WINDOW_WIDTH/8, (GameUI.gameUI_WINDOW_HEIGHT)-380, 200, 150);
        }
        //Acceleration
        if(Gdx.input.getX() > GameUI.gameUI_WINDOW_WIDTH/8 && Gdx.input.getX() < (GameUI.gameUI_WINDOW_WIDTH/8) + 200 && GameUI.gameUI_WINDOW_HEIGHT - Gdx.input.getY() <  GameUI.gameUI_WINDOW_HEIGHT-350 && GameUI.gameUI_WINDOW_HEIGHT - Gdx.input.getY() > GameUI.gameUI_WINDOW_HEIGHT-380 ){
            customizedMenuGame.gameUIBatch.draw(customizedMenuLine, GameUI.gameUI_WINDOW_WIDTH/8, (GameUI.gameUI_WINDOW_HEIGHT)-430, 150, 150);
        }
        //Mu
        if(Gdx.input.getX() > GameUI.gameUI_WINDOW_WIDTH/8 && Gdx.input.getX() < (GameUI.gameUI_WINDOW_WIDTH/8) + 200 && GameUI.gameUI_WINDOW_HEIGHT - Gdx.input.getY() <  GameUI.gameUI_WINDOW_HEIGHT-400 && GameUI.gameUI_WINDOW_HEIGHT - Gdx.input.getY() > GameUI.gameUI_WINDOW_HEIGHT-430 ){
            customizedMenuGame.gameUIBatch.draw(customizedMenuLine, GameUI.gameUI_WINDOW_WIDTH/8, (GameUI.gameUI_WINDOW_HEIGHT)-480, 290, 150);
        }
        //Distance
        if(Gdx.input.getX() > GameUI.gameUI_WINDOW_WIDTH/8 && Gdx.input.getX() < (GameUI.gameUI_WINDOW_WIDTH/8) + 200 && GameUI.gameUI_WINDOW_HEIGHT - Gdx.input.getY() <  GameUI.gameUI_WINDOW_HEIGHT-450 && GameUI.gameUI_WINDOW_HEIGHT - Gdx.input.getY() > GameUI.gameUI_WINDOW_HEIGHT-480 ){
            customizedMenuGame.gameUIBatch.draw(customizedMenuLine, GameUI.gameUI_WINDOW_WIDTH/8, (GameUI.gameUI_WINDOW_HEIGHT)-530, 290, 150);
        }
        //Mass
        if(Gdx.input.getX() > GameUI.gameUI_WINDOW_WIDTH/8 && Gdx.input.getX() < (GameUI.gameUI_WINDOW_WIDTH/8) + 200 && GameUI.gameUI_WINDOW_HEIGHT - Gdx.input.getY() <  GameUI.gameUI_WINDOW_HEIGHT-500 && GameUI.gameUI_WINDOW_HEIGHT - Gdx.input.getY() > GameUI.gameUI_WINDOW_HEIGHT-530 ){
            customizedMenuGame.gameUIBatch.draw(customizedMenuLine, GameUI.gameUI_WINDOW_WIDTH/8, (GameUI.gameUI_WINDOW_HEIGHT)-580, 200, 150);
        }
        //Height equation
        if(Gdx.input.getX() > GameUI.gameUI_WINDOW_WIDTH/8 && Gdx.input.getX() < (GameUI.gameUI_WINDOW_WIDTH/8) + 200 && GameUI.gameUI_WINDOW_HEIGHT - Gdx.input.getY() <  GameUI.gameUI_WINDOW_HEIGHT-550 && GameUI.gameUI_WINDOW_HEIGHT - Gdx.input.getY() > GameUI.gameUI_WINDOW_HEIGHT-580 ){
            customizedMenuGame.gameUIBatch.draw(customizedMenuLine, GameUI.gameUI_WINDOW_WIDTH/8, (GameUI.gameUI_WINDOW_HEIGHT)-630, 290, 150);
        }
        customizedMenuGame.gameUIBatch.end();

        customizedMenuStage.draw();

    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }

    public float getcustomizedMenuAcc() {
        return Float.parseFloat(customizedMenuAcc.getText());
    }

    public float getcustomizedMenuDis() {
        return Float.parseFloat(customizedMenuDis.getText());
    }

    public String getcustomizedMenuHei() {
        return customizedMenuHei.getText();
    }

    public float getcustomizedMenuMas() {
        return Float.parseFloat(customizedMenuMas.getText());
    }

    public float getcustomizedMenuMu() {
        return Float.parseFloat(customizedMenuMu.getText());
    }

    public float getcustomizedMenuVel() {
        return Float.parseFloat(customizedMenuVel.getText());
    }
}
