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

public class StartMenu implements Screen{

    GameUI startMenuStart;
    Texture startMenuBackButton;
    Texture startMenuOkButton;
    Texture startMenuLine;
    BitmapFont startMenuFont;
    BitmapFont startMenuFont1;
    BitmapFont startMenuChoice;
    BitmapFont startMenuCustomize;
    BitmapFont startMenuOwnPath;

    Stage startMenuStage;

    TextField startMenuPath;

    private static final int startMenu_BACK_BUTTON_SIZE = 80;
    private static final int startMenu_OK_BUTTON_WIDTH = 90;
    private static final int startMenu_OK_BUTTON_HEIGHT = 90;

    public StartMenu(GameUI start){
        this.startMenuStart = start;
        startMenuBackButton = new Texture("back.png");
        startMenuOkButton = new Texture("ok.png");
        startMenuLine = new Texture("line.png");

        //TextField for the path input
        startMenuStage = new Stage();
        Gdx.input.setInputProcessor(startMenuStage);
        Skin startMenuSkin = new Skin(Gdx.files.internal("skins/uiskin.json"));

        startMenuPath = new TextField("", startMenuSkin);
        startMenuPath.setPosition((GameUI.gameUI_WINDOW_WIDTH/2)+60, GameUI.gameUI_WINDOW_HEIGHT-470);
        startMenuPath.setSize(200, 20);
        startMenuPath.setColor(Color.FOREST);
        startMenuPath.setText("Your path:");
        startMenuStage.addActor(startMenuPath);

    }

    @Override
    public void show() {
    }

    @Override
    public void render(float delta) {

        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        startMenuStart.gameUIBatch.begin();
        FreeTypeFontGenerator startMenuGenerator = new FreeTypeFontGenerator(Gdx.files.internal("Georgia Italic.ttf"));
        FreeTypeFontGenerator startMenuWritingStyle = new FreeTypeFontGenerator(Gdx.files.internal("Courier New.ttf"));

        FreeTypeFontGenerator.FreeTypeFontParameter startMenuParameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        FreeTypeFontGenerator.FreeTypeFontParameter startMenuMakeChoice = new FreeTypeFontGenerator.FreeTypeFontParameter();
        FreeTypeFontGenerator.FreeTypeFontParameter startMenuBasic = new FreeTypeFontGenerator.FreeTypeFontParameter();
        FreeTypeFontGenerator.FreeTypeFontParameter startMenuCusto = new FreeTypeFontGenerator.FreeTypeFontParameter();
        FreeTypeFontGenerator.FreeTypeFontParameter startMenuOwn = new FreeTypeFontGenerator.FreeTypeFontParameter();

        startMenuParameter.size = 90;
        startMenuMakeChoice.size = 40;
        startMenuBasic.size = 30;
        startMenuCusto.size = 30;
        startMenuOwn.size = 30;

        startMenuParameter.characters = "Start";
        startMenuMakeChoice.characters = "Which set of parameters do you want to use?";
        startMenuBasic.characters = "Default";
        startMenuCusto.characters = "Customized";
        startMenuOwn.characters = "Path to your startMenuOwn parameters:";

        startMenuFont = startMenuGenerator.generateFont(startMenuParameter);
        startMenuChoice = startMenuWritingStyle.generateFont(startMenuMakeChoice);
        startMenuFont1 = startMenuWritingStyle.generateFont(startMenuBasic);
        startMenuCustomize = startMenuWritingStyle.generateFont(startMenuCusto);
        startMenuOwnPath = startMenuWritingStyle.generateFont(startMenuOwn);

        startMenuFont.setColor(Color.FOREST);
        startMenuChoice.setColor(Color.FOREST);
        startMenuFont1.setColor(Color.FOREST);
        startMenuCustomize.setColor(Color.FOREST);
        startMenuOwnPath.setColor(Color.FOREST);

        startMenuGenerator.dispose();

        startMenuFont.draw(startMenuStart.gameUIBatch, "Start", GameUI.gameUI_WINDOW_WIDTH-750, GameUI.gameUI_WINDOW_HEIGHT-100);
        startMenuChoice.draw(startMenuStart.gameUIBatch, "Which set of parameters do you want to use?", GameUI.gameUI_WINDOW_WIDTH/7, GameUI.gameUI_WINDOW_HEIGHT-200);
        startMenuFont1.draw(startMenuStart.gameUIBatch, "Default", GameUI.gameUI_WINDOW_WIDTH/7, GameUI.gameUI_WINDOW_HEIGHT-350);
        startMenuCustomize.draw(startMenuStart.gameUIBatch, "Customized", GameUI.gameUI_WINDOW_WIDTH/7, GameUI.gameUI_WINDOW_HEIGHT-400);
        startMenuOwnPath.draw(startMenuStart.gameUIBatch, "Path to your startMenuOwn parameters:", GameUI.gameUI_WINDOW_WIDTH/7, GameUI.gameUI_WINDOW_HEIGHT-450);

        //Menu choice
        if(Gdx.input.getX() > GameUI.gameUI_WINDOW_WIDTH/7 && Gdx.input.getX() < (GameUI.gameUI_WINDOW_WIDTH/7) + 200 && GameUI.gameUI_WINDOW_HEIGHT - Gdx.input.getY() <  GameUI.gameUI_WINDOW_HEIGHT-350 && GameUI.gameUI_WINDOW_HEIGHT - Gdx.input.getY() > GameUI.gameUI_WINDOW_HEIGHT-400 ){
            startMenuStart.gameUIBatch.draw(startMenuLine, (GameUI.gameUI_WINDOW_WIDTH)/7, (GameUI.gameUI_WINDOW_HEIGHT-435), 120, 150);
            if(Gdx.input.justTouched()){
                startMenuStart.setScreen(new DefaultMenu(startMenuStart));
            }
        }

        if(Gdx.input.getX() > GameUI.gameUI_WINDOW_WIDTH/7 && Gdx.input.getX() < (GameUI.gameUI_WINDOW_WIDTH/7) + 200 && GameUI.gameUI_WINDOW_HEIGHT - Gdx.input.getY() <  GameUI.gameUI_WINDOW_HEIGHT-400 && GameUI.gameUI_WINDOW_HEIGHT - Gdx.input.getY() > GameUI.gameUI_WINDOW_HEIGHT-450 ){
            startMenuStart.gameUIBatch.draw(startMenuLine, (GameUI.gameUI_WINDOW_WIDTH)/7, (GameUI.gameUI_WINDOW_HEIGHT-485), 180, 150);
            if(Gdx.input.justTouched()){
                startMenuStart.setScreen(new CustomizedMenu(startMenuStart));
            }
        }

        if(Gdx.input.getX() > GameUI.gameUI_WINDOW_WIDTH/10 && Gdx.input.getX() < (GameUI.gameUI_WINDOW_WIDTH/10) + startMenu_BACK_BUTTON_SIZE && GameUI.gameUI_WINDOW_HEIGHT - Gdx.input.getY() < startMenu_BACK_BUTTON_SIZE + GameUI.gameUI_WINDOW_HEIGHT-140 && GameUI.gameUI_WINDOW_HEIGHT - Gdx.input.getY() > GameUI.gameUI_WINDOW_HEIGHT-140 ){
            startMenuStart.gameUIBatch.draw(startMenuLine, (GameUI.gameUI_WINDOW_WIDTH)/10, (GameUI.gameUI_WINDOW_HEIGHT-220), startMenu_BACK_BUTTON_SIZE, 150);
            if(Gdx.input.justTouched()){
                this.dispose();
                startMenuStart.setScreen(new MainMenuScreen(startMenuStart));
            }
        }

        //start.batch.draw(okButton, GameUI.WINDOW_WIDTH-150, GameUI.WINDOW_HEIGHT/10, OK_BUTTON_WIDTH, OK_BUTTON_HEIGHT);
        startMenuStart.gameUIBatch.draw(startMenuBackButton, GameUI.gameUI_WINDOW_WIDTH/10, GameUI.gameUI_WINDOW_HEIGHT-150, startMenu_BACK_BUTTON_SIZE, startMenu_BACK_BUTTON_SIZE);
        startMenuStart.gameUIBatch.end();
        startMenuStage.draw();
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

    public String getStartMenuPath() {
        return startMenuPath.getText();
    }
}
