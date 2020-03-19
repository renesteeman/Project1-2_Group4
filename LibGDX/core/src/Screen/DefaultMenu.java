package Screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;

public class DefaultMenu implements Screen {

    GameUI defaultMenuGame;
    Texture defaultMenuBackButton;
    Texture defaultMenuLine;
    Texture defaultMenuOkButton;
    BitmapFont defaultMenuFont;
    BitmapFont defaultMenuVelocity;
    BitmapFont defaultMenuAcceleration;
    BitmapFont defaultMenuCoefficient;
    BitmapFont defaultMenuDistance;
    BitmapFont defaultMenuMass;
    BitmapFont defaultMenuHeight;

    //Variables that are used in the game
    BitmapFont defaultMenuVelocity1;
    BitmapFont defaultMenuAcceleration1;
    BitmapFont defaultMenuCoefficient1;
    BitmapFont defaultMenuDistance1;
    BitmapFont defaultMenuMass1;
    BitmapFont defaultMenuHeight1;

    private static final int defaultMenu_BACK_BUTTON_SIZE = 80;
    private static final int defaultMenu_OK_BUTTON_WIDTH = 90;
    private static final int defaultMenu_OK_BUTTON_HEIGHT = 90;

    public DefaultMenu(GameUI game){
        this.defaultMenuGame = game;
        defaultMenuBackButton = new Texture("back.png");
        defaultMenuLine = new Texture("line.png");
        defaultMenuOkButton =new Texture(("ok.png"));
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {

        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        defaultMenuGame.gameUIBatch.begin();

        FreeTypeFontGenerator defaultMenuGenerator = new FreeTypeFontGenerator(Gdx.files.internal("Georgia Italic.ttf"));
        FreeTypeFontGenerator defaultMenuWritingStyle = new FreeTypeFontGenerator(Gdx.files.internal("Courier New.ttf"));

        FreeTypeFontGenerator.FreeTypeFontParameter defaultMenuParameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        FreeTypeFontGenerator.FreeTypeFontParameter defaultMenuVelo = new FreeTypeFontGenerator.FreeTypeFontParameter();
        FreeTypeFontGenerator.FreeTypeFontParameter defaultMenuAcc = new FreeTypeFontGenerator.FreeTypeFontParameter();
        FreeTypeFontGenerator.FreeTypeFontParameter defaultMenuMu = new FreeTypeFontGenerator.FreeTypeFontParameter();
        FreeTypeFontGenerator.FreeTypeFontParameter defaultMenuDist = new FreeTypeFontGenerator.FreeTypeFontParameter();
        FreeTypeFontGenerator.FreeTypeFontParameter defaultMenuWeight = new FreeTypeFontGenerator.FreeTypeFontParameter();
        FreeTypeFontGenerator.FreeTypeFontParameter defaultMenuHauteur = new FreeTypeFontGenerator.FreeTypeFontParameter();

        defaultMenuParameter.size = 60;
        defaultMenuVelo.size = 20;
        defaultMenuAcc.size = 20;
        defaultMenuMu.size = 20;
        defaultMenuDist.size = 20;
        defaultMenuWeight.size = 20;
        defaultMenuHauteur.size = 20;

        defaultMenuParameter.characters = "Default Menu";
        defaultMenuVelo.characters = "Initial Velocity: ";
        defaultMenuAcc.characters = "Acceleration: ";
        defaultMenuMu.characters = "Coefficient of friction: ";
        defaultMenuDist.characters = "Distance from the Hole: ";
        defaultMenuWeight.characters = "Mass of the ball: ";
        defaultMenuHauteur.characters = "Equation of the height: ";

        defaultMenuFont = defaultMenuGenerator.generateFont(defaultMenuParameter);
        defaultMenuVelocity = defaultMenuWritingStyle.generateFont(defaultMenuVelo);
        defaultMenuAcceleration = defaultMenuWritingStyle.generateFont(defaultMenuAcc);
        defaultMenuCoefficient = defaultMenuWritingStyle.generateFont(defaultMenuMu);
        defaultMenuDistance = defaultMenuWritingStyle.generateFont(defaultMenuDist);
        defaultMenuMass = defaultMenuWritingStyle.generateFont(defaultMenuWeight);
        defaultMenuHeight = defaultMenuWritingStyle.generateFont(defaultMenuHauteur);

        defaultMenuFont.setColor(Color.FOREST);
        defaultMenuVelocity.setColor(Color.FOREST);
        defaultMenuAcceleration.setColor(Color.FOREST);
        defaultMenuCoefficient.setColor(Color.FOREST);
        defaultMenuDistance.setColor(Color.FOREST);
        defaultMenuMass.setColor(Color.FOREST);
        defaultMenuHeight.setColor(Color.FOREST);

        defaultMenuGenerator.dispose();

        defaultMenuFont.draw(defaultMenuGame.gameUIBatch, "Default Menu", GameUI.gameUI_WINDOW_WIDTH/3, GameUI.gameUI_WINDOW_HEIGHT-100);
        defaultMenuVelocity.draw(defaultMenuGame.gameUIBatch, "Initial Velocity: ", GameUI.gameUI_WINDOW_WIDTH/8, GameUI.gameUI_WINDOW_HEIGHT-300);
        defaultMenuAcceleration.draw(defaultMenuGame.gameUIBatch, "Acceleration: ", GameUI.gameUI_WINDOW_WIDTH/8, GameUI.gameUI_WINDOW_HEIGHT-350);
        defaultMenuCoefficient.draw(defaultMenuGame.gameUIBatch, "Coefficient of friction: ", GameUI.gameUI_WINDOW_WIDTH/8, GameUI.gameUI_WINDOW_HEIGHT-400);
        defaultMenuDistance.draw(defaultMenuGame.gameUIBatch, "Distance from the Hole: ", GameUI.gameUI_WINDOW_WIDTH/8, GameUI.gameUI_WINDOW_HEIGHT-450);
        defaultMenuMass.draw(defaultMenuGame.gameUIBatch, "Mass of the ball: ", GameUI.gameUI_WINDOW_WIDTH/8, GameUI.gameUI_WINDOW_HEIGHT-500);
        defaultMenuHeight.draw(defaultMenuGame.gameUIBatch, "Equation of the height: ", GameUI.gameUI_WINDOW_WIDTH/8, GameUI.gameUI_WINDOW_HEIGHT-550);


        //Default Input
        FreeTypeFontGenerator.FreeTypeFontParameter defaultMenuVelo1 = new FreeTypeFontGenerator.FreeTypeFontParameter();
        FreeTypeFontGenerator.FreeTypeFontParameter defaultMenuAcc1 = new FreeTypeFontGenerator.FreeTypeFontParameter();
        FreeTypeFontGenerator.FreeTypeFontParameter defaultMenuMu1 = new FreeTypeFontGenerator.FreeTypeFontParameter();
        FreeTypeFontGenerator.FreeTypeFontParameter defaultMenuDist1 = new FreeTypeFontGenerator.FreeTypeFontParameter();
        FreeTypeFontGenerator.FreeTypeFontParameter defaultMenuWeight1 = new FreeTypeFontGenerator.FreeTypeFontParameter();
        FreeTypeFontGenerator.FreeTypeFontParameter defaultMenuHauteur1 = new FreeTypeFontGenerator.FreeTypeFontParameter();

        defaultMenuVelo1.size = 20;
        defaultMenuAcc1.size = 20;
        defaultMenuMu1.size = 20;
        defaultMenuDist1.size = 20;
        defaultMenuWeight1.size = 20;
        defaultMenuHauteur1.size = 20;

        defaultMenuVelo1.characters = "0 m/s";
        defaultMenuAcc1.characters = "9.81 m/s^2";
        defaultMenuMu1.characters = "0.131";
        defaultMenuDist1.characters = "0.02 m";
        defaultMenuWeight1.characters = "45.93 g";
        defaultMenuHauteur1.characters = "-0.01*x + 0.003*x^2 + 0.04 * y";

        defaultMenuVelocity1 = defaultMenuWritingStyle.generateFont(defaultMenuVelo1);
        defaultMenuAcceleration1 = defaultMenuWritingStyle.generateFont(defaultMenuAcc1);
        defaultMenuCoefficient1 = defaultMenuWritingStyle.generateFont(defaultMenuMu1);
        defaultMenuDistance1 = defaultMenuWritingStyle.generateFont(defaultMenuDist1);
        defaultMenuMass1 = defaultMenuWritingStyle.generateFont(defaultMenuWeight1);
        defaultMenuHeight1 = defaultMenuWritingStyle.generateFont(defaultMenuHauteur1);

        defaultMenuVelocity1.setColor(Color.FOREST);
        defaultMenuAcceleration1.setColor(Color.FOREST);
        defaultMenuCoefficient1.setColor(Color.FOREST);
        defaultMenuDistance1.setColor(Color.FOREST);
        defaultMenuMass1.setColor(Color.FOREST);
        defaultMenuHeight1.setColor(Color.FOREST);

        defaultMenuVelocity1.draw(defaultMenuGame.gameUIBatch, "0 m/s", GameUI.gameUI_WINDOW_WIDTH/3 + 50, GameUI.gameUI_WINDOW_HEIGHT-300);
        defaultMenuAcceleration1.draw(defaultMenuGame.gameUIBatch, "9.81 m/s^2", GameUI.gameUI_WINDOW_WIDTH/3+50, GameUI.gameUI_WINDOW_HEIGHT-350);
        defaultMenuCoefficient1.draw(defaultMenuGame.gameUIBatch, "0.131", (GameUI.gameUI_WINDOW_WIDTH/3) + 50, GameUI.gameUI_WINDOW_HEIGHT-400);
        defaultMenuDistance1.draw(defaultMenuGame.gameUIBatch, "0.02 m", (GameUI.gameUI_WINDOW_WIDTH/3) + 50, GameUI.gameUI_WINDOW_HEIGHT-450);
        defaultMenuMass1.draw(defaultMenuGame.gameUIBatch, "45.93 g", (GameUI.gameUI_WINDOW_WIDTH/3) + 50, GameUI.gameUI_WINDOW_HEIGHT-500);
        defaultMenuHeight1.draw(defaultMenuGame.gameUIBatch, "-0.01*x + 0.003*x^2 + 0.04 * y", (GameUI.gameUI_WINDOW_WIDTH/3)+50, GameUI.gameUI_WINDOW_HEIGHT-550);

        //Draw the line under the Back Button
        if(Gdx.input.getX() > GameUI.gameUI_WINDOW_WIDTH/10 && Gdx.input.getX() < (GameUI.gameUI_WINDOW_WIDTH/10) + defaultMenu_BACK_BUTTON_SIZE && GameUI.gameUI_WINDOW_HEIGHT - Gdx.input.getY() < defaultMenu_BACK_BUTTON_SIZE + GameUI.gameUI_WINDOW_HEIGHT-140 && GameUI.gameUI_WINDOW_HEIGHT - Gdx.input.getY() > GameUI.gameUI_WINDOW_HEIGHT-140 ){
            defaultMenuGame.gameUIBatch.draw(defaultMenuLine, (GameUI.gameUI_WINDOW_WIDTH)/10, (GameUI.gameUI_WINDOW_HEIGHT-220), defaultMenu_BACK_BUTTON_SIZE, 150);
            if(Gdx.input.justTouched()){
                this.dispose();
                defaultMenuGame.setScreen(new StartMenu(defaultMenuGame));
            }
        }
        //Draw the line under the Ok button
        if(Gdx.input.getX() > GameUI.gameUI_WINDOW_WIDTH-150 && Gdx.input.getX() < (GameUI.gameUI_WINDOW_WIDTH-150) + defaultMenu_OK_BUTTON_WIDTH && GameUI.gameUI_WINDOW_HEIGHT - Gdx.input.getY() < defaultMenu_OK_BUTTON_HEIGHT + GameUI.gameUI_WINDOW_HEIGHT/10 && GameUI.gameUI_WINDOW_HEIGHT - Gdx.input.getY() > GameUI.gameUI_WINDOW_HEIGHT/10 ){
            defaultMenuGame.gameUIBatch.draw(defaultMenuLine, GameUI.gameUI_WINDOW_WIDTH-150, (GameUI.gameUI_WINDOW_HEIGHT/10)-60, defaultMenu_OK_BUTTON_WIDTH, 150);
            if(Gdx.input.justTouched()){
                this.dispose();
                defaultMenuGame.setScreen(new ChoiceModeScreen(defaultMenuGame));
            }
        }

        //Draw the buttons
        defaultMenuGame.gameUIBatch.draw(defaultMenuBackButton, GameUI.gameUI_WINDOW_WIDTH/10, GameUI.gameUI_WINDOW_HEIGHT-150, defaultMenu_BACK_BUTTON_SIZE, defaultMenu_BACK_BUTTON_SIZE);
        defaultMenuGame.gameUIBatch.draw(defaultMenuOkButton, GameUI.gameUI_WINDOW_WIDTH-150, GameUI.gameUI_WINDOW_HEIGHT/10, defaultMenu_OK_BUTTON_WIDTH, defaultMenu_OK_BUTTON_HEIGHT);

        defaultMenuGame.gameUIBatch.end();
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
}
