package Screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;


public class MainMenuScreen implements Screen {

    GameUI MainMenuScreengame;
    SettingsMenu MainMenuScreenTest;
    Texture MainMenuScreenExitButton;
    Texture MainMenuScreenStartButton;
    Texture MainMenuScreenSettingsButton;
    Texture MainMenuScreenLine;
    Texture MainMenuScreenGolf;
    BitmapFont MainMenuScreenFont;
    BitmapFont MainMenuScreenFont1;

    private static final int MainMenuScreenSTART_BUTTON_WIDTH = 400;
    private static final int MainMenuScreenSTART_BUTTON_HEIGHT = 400;
    private static final int MainMenuScreenEXIT_BUTTON_WIDTH = 100;
    private static final int MainMenuScreenEXIT_BUTTON_HEIGHT = 100;
    private static final int MainMenuScreenSETTINGS_BUTTON_SIZE = 80;
    private static final int MainMenuScreenGOLF_IMAGE_WIDTH = 200;
    private static final int MainMenuScreenGOLF_IMAGE_HEIGHT = 200;

    public MainMenuScreen(GameUI game){
        this.game = game;
        this.test = test;
        startButton = new Texture("start.png");
        exitButton = new Texture("exit.png");
        settingsButton = new Texture(("settings.png"));
        line = new Texture("line.png");
        golf = new Texture("golf.png");

    }
    @Override
    public void show() { }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("Georgia Italic.ttf"));

        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        FreeTypeFontGenerator.FreeTypeFontParameter group4 = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 90;
        parameter.characters = "Mini Golf";
        group4.characters = "Group 4";
        group4.size = 30;

        font = generator.generateFont(parameter);
        font1 = generator.generateFont(group4);
        font.setColor(Color.FOREST);
        font1.setColor(Color.FOREST);
        generator.dispose();

        game.batch.begin();
        font.draw(game.batch, " Mini Golf", GameUI.WINDOW_WIDTH/5, GameUI.WINDOW_HEIGHT-100);
        font1.draw(game.batch, "Group 4", GameUI.WINDOW_WIDTH / 10, GameUI.WINDOW_HEIGHT - 50);

        if(Gdx.input.getX() > GameUI.WINDOW_WIDTH/4 && Gdx.input.getX() < GameUI.WINDOW_WIDTH/5 + START_BUTTON_WIDTH && GameUI.WINDOW_HEIGHT - Gdx.input.getY() < START_BUTTON_HEIGHT + (GameUI.WINDOW_HEIGHT/7)-150 && GameUI.WINDOW_HEIGHT - Gdx.input.getY() > (GameUI.WINDOW_HEIGHT/7)+80){
            game.batch.draw(line, (GameUI.WINDOW_WIDTH/4) +50, (GameUI.WINDOW_HEIGHT/7)+80, START_BUTTON_WIDTH-120, 150);
            if(Gdx.input.justTouched()){
                this.dispose();
                game.setScreen(new StartMenu(game));
            }
        }
        if(Gdx.input.getX() > GameUI.WINDOW_WIDTH/10 && Gdx.input.getX() < GameUI.WINDOW_WIDTH/10 + EXIT_BUTTON_WIDTH && GameUI.WINDOW_HEIGHT - Gdx.input.getY() < EXIT_BUTTON_HEIGHT + GameUI.WINDOW_HEIGHT/10 && GameUI.WINDOW_HEIGHT - Gdx.input.getY() > GameUI.WINDOW_HEIGHT/10){
            game.batch.draw(line, (GameUI.WINDOW_WIDTH/10), (GameUI.WINDOW_HEIGHT/10)-50, EXIT_BUTTON_WIDTH, 150);
            if(Gdx.input.justTouched()){
                Gdx.app.exit();
            }
        }
        if(Gdx.input.getX() > GameUI.WINDOW_WIDTH-150 && Gdx.input.getX() < GameUI.WINDOW_WIDTH-150 + SETTINGS_BUTTON_SIZE && GameUI.WINDOW_HEIGHT - Gdx.input.getY() < SETTINGS_BUTTON_SIZE + GameUI.WINDOW_HEIGHT/10 && GameUI.WINDOW_HEIGHT - Gdx.input.getY() > GameUI.WINDOW_HEIGHT/10){
            game.batch.draw(line, (GameUI.WINDOW_WIDTH)-150, (GameUI.WINDOW_HEIGHT/10) - 55, SETTINGS_BUTTON_SIZE, 150);
            if(Gdx.input.justTouched()){
                this.dispose();
                game.setScreen(new SettingsMenu(game));
            }
        }

            game.batch.draw(startButton, GameUI.WINDOW_WIDTH/4,GameUI.WINDOW_HEIGHT/8, START_BUTTON_WIDTH, START_BUTTON_HEIGHT);
            game.batch.draw(exitButton, GameUI.WINDOW_WIDTH/10, GameUI.WINDOW_HEIGHT/10, EXIT_BUTTON_WIDTH, EXIT_BUTTON_HEIGHT);
            game.batch.draw(settingsButton, GameUI.WINDOW_WIDTH - 150, GameUI.WINDOW_HEIGHT / 10, SETTINGS_BUTTON_SIZE, SETTINGS_BUTTON_SIZE);
            game.batch.draw(golf, GameUI.WINDOW_WIDTH-480, GameUI.WINDOW_HEIGHT/2, GOLF_IMAGE_WIDTH, GOLF_IMAGE_HEIGHT);
            game.batch.end();
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
