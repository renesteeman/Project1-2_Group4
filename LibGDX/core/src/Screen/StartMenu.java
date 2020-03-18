package Screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;

public class StartMenu implements Screen{

    GameUI StartMenuStart;
    Texture StartMenuBackButton;
    Texture StartMenuOkButton;
    Texture StartMenuLine;
    BitmapFont StartMenuFont;
    BitmapFont StartMenuFont1;
    BitmapFont StartMenuChoice;
    BitmapFont StartMenuChoice1;
    BitmapFont StartMenuCustomize;

    private static final int StartMenuBACK_BUTTON_SIZE = 80;
    private static final int StartMenuOK_BUTTON_WIDTH = 90;
    private static final int StartMenuOK_BUTTON_HEIGHT = 90;

    public StartMenu(GameUI start){
        this.start = start;
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
        start.batch.begin();
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("Georgia Italic.ttf"));
        FreeTypeFontGenerator writingStyle = new FreeTypeFontGenerator(Gdx.files.internal("Courier New.ttf"));

        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        FreeTypeFontGenerator.FreeTypeFontParameter makeChoice = new FreeTypeFontGenerator.FreeTypeFontParameter();
        FreeTypeFontGenerator.FreeTypeFontParameter makeChoice1 = new FreeTypeFontGenerator.FreeTypeFontParameter();
        FreeTypeFontGenerator.FreeTypeFontParameter basic = new FreeTypeFontGenerator.FreeTypeFontParameter();
        FreeTypeFontGenerator.FreeTypeFontParameter custo = new FreeTypeFontGenerator.FreeTypeFontParameter();

        parameter.size = 90;
        makeChoice.size = 40;
        makeChoice1.size = 40;
        basic.size = 30;
        custo.size = 30;

        parameter.characters = "Start";
        makeChoice.characters = "Which set of parameters";
        makeChoice1.characters = "do you want to use?";
        basic.characters = "Default";
        custo.characters = "Customized";

        font = generator.generateFont(parameter);
        choice = writingStyle.generateFont(makeChoice);
        choice1 = writingStyle.generateFont(makeChoice1);
        font1 = writingStyle.generateFont(basic);
        customize = writingStyle.generateFont(custo);

        font.setColor(Color.FOREST);
        choice.setColor(Color.FOREST);
        choice1.setColor(Color.FOREST);
        font1.setColor(Color.FOREST);
        customize.setColor(Color.FOREST);

        generator.dispose();

        font.draw(start.batch, "Start", GameUI.WINDOW_WIDTH/3, GameUI.WINDOW_HEIGHT-100);
        choice.draw(start.batch, "Which set of parameters", GameUI.WINDOW_WIDTH/7, GameUI.WINDOW_HEIGHT-200);
        choice1.draw(start.batch, "do you want to use?", GameUI.WINDOW_WIDTH/7, GameUI.WINDOW_HEIGHT-250);
        font1.draw(start.batch, "Default", GameUI.WINDOW_WIDTH/7, GameUI.WINDOW_HEIGHT-350);
        customize.draw(start.batch, "Customized", GameUI.WINDOW_WIDTH/7, GameUI.WINDOW_HEIGHT-400);

        //Menu choice
        if(Gdx.input.getX() > GameUI.WINDOW_WIDTH/7 && Gdx.input.getX() < (GameUI.WINDOW_WIDTH/7) + 200 && GameUI.WINDOW_HEIGHT - Gdx.input.getY() <  GameUI.WINDOW_HEIGHT-350 && GameUI.WINDOW_HEIGHT - Gdx.input.getY() > GameUI.WINDOW_HEIGHT-400 ){
            start.batch.draw(line, (GameUI.WINDOW_WIDTH)/7, (GameUI.WINDOW_HEIGHT-435), 120, 150);
            if(Gdx.input.justTouched()){
                start.setScreen(new DefaultMenu(start));
            }
        }

        if(Gdx.input.getX() > GameUI.WINDOW_WIDTH/7 && Gdx.input.getX() < (GameUI.WINDOW_WIDTH/7) + 200 && GameUI.WINDOW_HEIGHT - Gdx.input.getY() <  GameUI.WINDOW_HEIGHT-400 && GameUI.WINDOW_HEIGHT - Gdx.input.getY() > GameUI.WINDOW_HEIGHT-450 ){
            start.batch.draw(line, (GameUI.WINDOW_WIDTH)/7, (GameUI.WINDOW_HEIGHT-485), 180, 150);
            if(Gdx.input.justTouched()){
                start.setScreen(new CustomizedMenu(start));
            }
        }

        //Drawing buttons
       /* if(Gdx.input.getX() > GameUI.WINDOW_WIDTH-150 && Gdx.input.getX() < (GameUI.WINDOW_WIDTH-150) + OK_BUTTON_WIDTH && GameUI.WINDOW_HEIGHT - Gdx.input.getY() < OK_BUTTON_HEIGHT + GameUI.WINDOW_HEIGHT/10 && GameUI.WINDOW_HEIGHT - Gdx.input.getY() > GameUI.WINDOW_HEIGHT/10 ){
            start.batch.draw(line, GameUI.WINDOW_WIDTH-150, (GameUI.WINDOW_HEIGHT/10)-60, OK_BUTTON_WIDTH, 150);
            if(Gdx.input.justTouched()){
                this.dispose();
                start.setScreen(new GamePlay(start));
            }
        }

        */
        if(Gdx.input.getX() > GameUI.WINDOW_WIDTH/10 && Gdx.input.getX() < (GameUI.WINDOW_WIDTH/10) + BACK_BUTTON_SIZE && GameUI.WINDOW_HEIGHT - Gdx.input.getY() < BACK_BUTTON_SIZE + GameUI.WINDOW_HEIGHT-140 && GameUI.WINDOW_HEIGHT - Gdx.input.getY() > GameUI.WINDOW_HEIGHT-140 ){
            start.batch.draw(line, (GameUI.WINDOW_WIDTH)/10, (GameUI.WINDOW_HEIGHT-220), BACK_BUTTON_SIZE, 150);
            if(Gdx.input.justTouched()){
                this.dispose();
                start.setScreen(new MainMenuScreen(start));
            }
        }
        //start.batch.draw(okButton, GameUI.WINDOW_WIDTH-150, GameUI.WINDOW_HEIGHT/10, OK_BUTTON_WIDTH, OK_BUTTON_HEIGHT);
        start.batch.draw(backButton, GameUI.WINDOW_WIDTH/10, GameUI.WINDOW_HEIGHT-150, BACK_BUTTON_SIZE, BACK_BUTTON_SIZE);

        start.batch.end();
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
