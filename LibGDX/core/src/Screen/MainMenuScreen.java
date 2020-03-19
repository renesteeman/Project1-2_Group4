package Screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;


public class MainMenuScreen implements Screen {

    GameUI mainMenuScreenGame;
    SettingsMenu mainMenuScreenTest;
    Texture mainMenuScreenExitButton;
    Texture mainMenuScreenStartButton;
    Texture mainMenuScreenSettingsButton;
    Texture mainMenuScreenLine;
    Texture mainMenuScreenGolf;
    BitmapFont mainMenuScreenFont;
    BitmapFont mainMenuScreenFont1;

    private static final int mainMenuScreen_START_BUTTON_WIDTH = 400;
    private static final int mainMenuScreen_START_BUTTON_HEIGHT = 400;
    private static final int mainMenuScreen_EXIT_BUTTON_WIDTH = 100;
    private static final int mainMenuScreen_EXIT_BUTTON_HEIGHT = 100;
    private static final int mainMenuScreen_SETTINGS_BUTTON_SIZE = 80;
    private static final int mainMenuScreen_GOLF_IMAGE_WIDTH = 200;
    private static final int mainMenuScreen_GOLF_IMAGE_HEIGHT = 200;

    public MainMenuScreen(GameUI game){
        this.mainMenuScreenGame = game;
        this.mainMenuScreenTest = mainMenuScreenTest;
        mainMenuScreenStartButton = new Texture("start.png");
        mainMenuScreenExitButton = new Texture("exit.png");
        mainMenuScreenSettingsButton = new Texture(("settings.png"));
        mainMenuScreenLine = new Texture("line.png");
        mainMenuScreenGolf = new Texture("golf.png");
    }

    @Override
    public void show() { }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        FreeTypeFontGenerator mainMenuScreenGenerator = new FreeTypeFontGenerator(Gdx.files.internal("Georgia Italic.ttf"));

        FreeTypeFontGenerator.FreeTypeFontParameter mainMenuScreenParameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        FreeTypeFontGenerator.FreeTypeFontParameter mainMenuScreenGroup4 = new FreeTypeFontGenerator.FreeTypeFontParameter();
        mainMenuScreenParameter.size = 90;
        mainMenuScreenParameter.characters = "Mini Golf";
        mainMenuScreenGroup4.characters = "Group 4";
        mainMenuScreenGroup4.size = 30;

        mainMenuScreenFont = mainMenuScreenGenerator.generateFont(mainMenuScreenParameter);
        mainMenuScreenFont1 = mainMenuScreenGenerator.generateFont(mainMenuScreenGroup4);
        mainMenuScreenFont.setColor(Color.FOREST);
        mainMenuScreenFont1.setColor(Color.FOREST);
        mainMenuScreenGenerator.dispose();

        mainMenuScreenGame.gameUIBatch.begin();
        mainMenuScreenFont.draw(mainMenuScreenGame.gameUIBatch, " Mini Golf", GameUI.gameUI_WINDOW_WIDTH/5, GameUI.gameUI_WINDOW_HEIGHT-100);
        mainMenuScreenFont1.draw(mainMenuScreenGame.gameUIBatch, "Group 4", GameUI.gameUI_WINDOW_WIDTH / 10, GameUI.gameUI_WINDOW_HEIGHT - 50);

        if(Gdx.input.getX() > GameUI.gameUI_WINDOW_WIDTH/4 && Gdx.input.getX() < GameUI.gameUI_WINDOW_WIDTH/5 + mainMenuScreen_START_BUTTON_WIDTH && GameUI.gameUI_WINDOW_HEIGHT - Gdx.input.getY() < mainMenuScreen_START_BUTTON_HEIGHT + (GameUI.gameUI_WINDOW_HEIGHT/7)-150 && GameUI.gameUI_WINDOW_HEIGHT - Gdx.input.getY() > (GameUI.gameUI_WINDOW_HEIGHT/7)+80){
            mainMenuScreenGame.gameUIBatch.draw(mainMenuScreenLine, (GameUI.gameUI_WINDOW_WIDTH/4) +50, (GameUI.gameUI_WINDOW_HEIGHT/7)+80, mainMenuScreen_START_BUTTON_WIDTH-120, 150);
            if(Gdx.input.justTouched()){
                this.dispose();
                mainMenuScreenGame.setScreen(new StartMenu(mainMenuScreenGame));
            }
        }
        if(Gdx.input.getX() > GameUI.gameUI_WINDOW_WIDTH/10 && Gdx.input.getX() < GameUI.gameUI_WINDOW_WIDTH/10 + mainMenuScreen_EXIT_BUTTON_WIDTH && GameUI.gameUI_WINDOW_HEIGHT - Gdx.input.getY() < mainMenuScreen_EXIT_BUTTON_HEIGHT + GameUI.gameUI_WINDOW_HEIGHT/10 && GameUI.gameUI_WINDOW_HEIGHT - Gdx.input.getY() > GameUI.gameUI_WINDOW_HEIGHT/10){
            mainMenuScreenGame.gameUIBatch.draw(mainMenuScreenLine, (GameUI.gameUI_WINDOW_WIDTH/10), (GameUI.gameUI_WINDOW_HEIGHT/10)-50, mainMenuScreen_EXIT_BUTTON_WIDTH, 150);
            if(Gdx.input.justTouched()){
                Gdx.app.exit();
            }
        }
        if(Gdx.input.getX() > GameUI.gameUI_WINDOW_WIDTH-150 && Gdx.input.getX() < GameUI.gameUI_WINDOW_WIDTH-150 + mainMenuScreen_SETTINGS_BUTTON_SIZE && GameUI.gameUI_WINDOW_HEIGHT - Gdx.input.getY() < mainMenuScreen_SETTINGS_BUTTON_SIZE + GameUI.gameUI_WINDOW_HEIGHT/10 && GameUI.gameUI_WINDOW_HEIGHT - Gdx.input.getY() > GameUI.gameUI_WINDOW_HEIGHT/10){
            mainMenuScreenGame.gameUIBatch.draw(mainMenuScreenLine, (GameUI.gameUI_WINDOW_WIDTH)-150, (GameUI.gameUI_WINDOW_HEIGHT/10) - 55, mainMenuScreen_SETTINGS_BUTTON_SIZE, 150);
            if(Gdx.input.justTouched()){
                this.dispose();
                mainMenuScreenGame.setScreen(new SettingsMenu(mainMenuScreenGame));
            }
        }

        mainMenuScreenGame.gameUIBatch.draw(mainMenuScreenStartButton, GameUI.gameUI_WINDOW_WIDTH/4,GameUI.gameUI_WINDOW_HEIGHT/8, mainMenuScreen_START_BUTTON_WIDTH, mainMenuScreen_START_BUTTON_HEIGHT);
        mainMenuScreenGame.gameUIBatch.draw(mainMenuScreenExitButton, GameUI.gameUI_WINDOW_WIDTH/10, GameUI.gameUI_WINDOW_HEIGHT/10, mainMenuScreen_EXIT_BUTTON_WIDTH, mainMenuScreen_EXIT_BUTTON_HEIGHT);
        mainMenuScreenGame.gameUIBatch.draw(mainMenuScreenSettingsButton, GameUI.gameUI_WINDOW_WIDTH - 150, GameUI.gameUI_WINDOW_HEIGHT / 10, mainMenuScreen_SETTINGS_BUTTON_SIZE, mainMenuScreen_SETTINGS_BUTTON_SIZE);
        mainMenuScreenGame.gameUIBatch.draw(mainMenuScreenGolf, GameUI.gameUI_WINDOW_WIDTH-480, GameUI.gameUI_WINDOW_HEIGHT/2, mainMenuScreen_GOLF_IMAGE_WIDTH, mainMenuScreen_GOLF_IMAGE_HEIGHT);
        mainMenuScreenGame.gameUIBatch.end();
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
