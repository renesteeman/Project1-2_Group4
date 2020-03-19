package Screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;

public class SettingsMenu implements Screen {

    GameUI settingsMenuSettings;
    Texture settingsMenuSettingsButton;
    Texture settingsMenuBackButton;
    Texture settingsMenuOkButton;
    Texture settingsMenuLine;
    BitmapFont settingsMenuFont;

    private static final int settingsMenu_SETTINGS_BUTTON_SIZE = 80;
    private static final int settingsMenu_BACK_BUTTON_SIZE = 80;
    private static final int settingsMenu_OK_BUTTON_WIDTH = 90;
    private static final int settingsMenu_OK_BUTTON_HEIGHT = 90;


    public SettingsMenu(GameUI start){
        this.settingsMenuSettings = start;
        settingsMenuSettingsButton = new Texture(("settings.png"));
        settingsMenuBackButton = new Texture("back.png");
        settingsMenuOkButton = new Texture("ok.png");
        settingsMenuLine = new Texture("line.png");
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {

        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        settingsMenuSettings.gameUIBatch.begin();

        FreeTypeFontGenerator settingsMenuGenerator = new FreeTypeFontGenerator(Gdx.files.internal("Georgia Italic.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter settingsMenuParameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        settingsMenuParameter.size = 50;
        settingsMenuParameter.characters = "Settings";
        settingsMenuFont = settingsMenuGenerator.generateFont(settingsMenuParameter);
        settingsMenuFont.setColor(Color.FOREST);
        settingsMenuGenerator.dispose();
        settingsMenuFont.draw(settingsMenuSettings.gameUIBatch, "Settings", GameUI.gameUI_WINDOW_WIDTH/3, GameUI.gameUI_WINDOW_HEIGHT-100);

        if(Gdx.input.getX() > GameUI.gameUI_WINDOW_WIDTH-150 && Gdx.input.getX() < (GameUI.gameUI_WINDOW_WIDTH-150) + settingsMenu_OK_BUTTON_WIDTH && GameUI.gameUI_WINDOW_HEIGHT - Gdx.input.getY() < settingsMenu_OK_BUTTON_HEIGHT + GameUI.gameUI_WINDOW_HEIGHT/10 && GameUI.gameUI_WINDOW_HEIGHT - Gdx.input.getY() > GameUI.gameUI_WINDOW_HEIGHT/10 ){
            settingsMenuSettings.gameUIBatch.draw(settingsMenuLine, GameUI.gameUI_WINDOW_WIDTH-150, (GameUI.gameUI_WINDOW_HEIGHT/10)-60, settingsMenu_OK_BUTTON_WIDTH, 150);
            if(Gdx.input.justTouched()){
                this.dispose();
                settingsMenuSettings.setScreen(new MainMenuScreen(settingsMenuSettings));
            }
        }
        if(Gdx.input.getX() > GameUI.gameUI_WINDOW_WIDTH/10 && Gdx.input.getX() < (GameUI.gameUI_WINDOW_WIDTH/10) + settingsMenu_BACK_BUTTON_SIZE && GameUI.gameUI_WINDOW_HEIGHT - Gdx.input.getY() < settingsMenu_BACK_BUTTON_SIZE + GameUI.gameUI_WINDOW_HEIGHT-140 && GameUI.gameUI_WINDOW_HEIGHT - Gdx.input.getY() > GameUI.gameUI_WINDOW_HEIGHT-140 ){
            settingsMenuSettings.gameUIBatch.draw(settingsMenuLine, (GameUI.gameUI_WINDOW_WIDTH)/10, (GameUI.gameUI_WINDOW_HEIGHT-220), settingsMenu_BACK_BUTTON_SIZE, 150);
            if(Gdx.input.justTouched()){
                this.dispose();
                settingsMenuSettings.setScreen(new MainMenuScreen(settingsMenuSettings));
            }
        }


        settingsMenuSettings.gameUIBatch.draw(settingsMenuSettingsButton, GameUI.gameUI_WINDOW_WIDTH*2/3, GameUI.gameUI_WINDOW_HEIGHT - 150, settingsMenu_SETTINGS_BUTTON_SIZE, settingsMenu_SETTINGS_BUTTON_SIZE);
        settingsMenuSettings.gameUIBatch.draw(settingsMenuOkButton, GameUI.gameUI_WINDOW_WIDTH-150, GameUI.gameUI_WINDOW_HEIGHT/10, settingsMenu_OK_BUTTON_WIDTH, settingsMenu_OK_BUTTON_HEIGHT);
        settingsMenuSettings.gameUIBatch.draw(settingsMenuBackButton, GameUI.gameUI_WINDOW_WIDTH/10, GameUI.gameUI_WINDOW_HEIGHT-150, settingsMenu_BACK_BUTTON_SIZE, settingsMenu_BACK_BUTTON_SIZE);

        settingsMenuSettings.gameUIBatch.end();
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
