package Screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;

public class SettingsMenu implements Screen {

    GameUI SettingsMenuSettings;
    Texture SettingsMenuSettingsButton;
    Texture SettingsMenuBackButton;
    Texture SettingsMenuOkButton;
    Texture SettingsMenuLine;
    BitmapFont SettingsMenuFont;

    private static final int SettingsMenuSETTINGS_BUTTON_SIZE = 80;
    private static final int SettingsMenuBACK_BUTTON_SIZE = 80;
    private static final int SettingsMenuOK_BUTTON_WIDTH = 90;
    private static final int SettingsMenuOK_BUTTON_HEIGHT = 90;


    public SettingsMenu(GameUI start){
        this.settings = start;
        settingsButton = new Texture(("settings.png"));
        backButton = new Texture("back.png");
        okButton = new Texture("ok.png");
        line = new Texture("line.png");
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {

        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        settings.batch.begin();

        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("Georgia Italic.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 50;
        parameter.characters = "Settings";
        font = generator.generateFont(parameter);
        font.setColor(Color.FOREST);
        generator.dispose();
        font.draw(settings.batch, "Settings", GameUI.WINDOW_WIDTH/3, GameUI.WINDOW_HEIGHT-100);

        if(Gdx.input.getX() > GameUI.WINDOW_WIDTH-150 && Gdx.input.getX() < (GameUI.WINDOW_WIDTH-150) + OK_BUTTON_WIDTH && GameUI.WINDOW_HEIGHT - Gdx.input.getY() < OK_BUTTON_HEIGHT + GameUI.WINDOW_HEIGHT/10 && GameUI.WINDOW_HEIGHT - Gdx.input.getY() > GameUI.WINDOW_HEIGHT/10 ){
            settings.batch.draw(line, GameUI.WINDOW_WIDTH-150, (GameUI.WINDOW_HEIGHT/10)-60, OK_BUTTON_WIDTH, 150);
            if(Gdx.input.justTouched()){
                this.dispose();
                settings.setScreen(new MainMenuScreen(settings));
            }
        }
        if(Gdx.input.getX() > GameUI.WINDOW_WIDTH/10 && Gdx.input.getX() < (GameUI.WINDOW_WIDTH/10) + BACK_BUTTON_SIZE && GameUI.WINDOW_HEIGHT - Gdx.input.getY() < BACK_BUTTON_SIZE + GameUI.WINDOW_HEIGHT-140 && GameUI.WINDOW_HEIGHT - Gdx.input.getY() > GameUI.WINDOW_HEIGHT-140 ){
            settings.batch.draw(line, (GameUI.WINDOW_WIDTH)/10, (GameUI.WINDOW_HEIGHT-220), BACK_BUTTON_SIZE, 150);
            if(Gdx.input.justTouched()){
                this.dispose();
                settings.setScreen(new MainMenuScreen(settings));
            }
        }


        settings.batch.draw(settingsButton, GameUI.WINDOW_WIDTH*2/3, GameUI.WINDOW_HEIGHT - 150, SETTINGS_BUTTON_SIZE, SETTINGS_BUTTON_SIZE);
        settings.batch.draw(okButton, GameUI.WINDOW_WIDTH-150, GameUI.WINDOW_HEIGHT/10, OK_BUTTON_WIDTH, OK_BUTTON_HEIGHT);
        settings.batch.draw(backButton, GameUI.WINDOW_WIDTH/10, GameUI.WINDOW_HEIGHT-150, BACK_BUTTON_SIZE, BACK_BUTTON_SIZE);

        settings.batch.end();
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
