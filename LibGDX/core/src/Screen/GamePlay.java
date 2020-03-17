package Screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;

public class GamePlay implements Screen {

    GameUI game;
    Texture backButton;
    Texture line;
    BitmapFont font;

    private static final int BACK_BUTTON_SIZE = 80;
    private static final int OK_BUTTON_WIDTH = 90;
    private static final int OK_BUTTON_HEIGHT = 90;

    public GamePlay(GameUI game){
        this.game = game;
        backButton = new Texture("back.png");
        line = new Texture("line.png");
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {

        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        game.batch.begin();
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("Georgia Italic.ttf"));

        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 100;
        parameter.characters = "Game";
        font = generator.generateFont(parameter);
        font.setColor(Color.FOREST);
        generator.dispose();
        font.draw(game.batch, "Game", GameUI.WINDOW_WIDTH/3, GameUI.WINDOW_HEIGHT-100);

        if(Gdx.input.getX() > GameUI.WINDOW_WIDTH/10 && Gdx.input.getX() < (GameUI.WINDOW_WIDTH/10) + BACK_BUTTON_SIZE && GameUI.WINDOW_HEIGHT - Gdx.input.getY() < BACK_BUTTON_SIZE + GameUI.WINDOW_HEIGHT-140 && GameUI.WINDOW_HEIGHT - Gdx.input.getY() > GameUI.WINDOW_HEIGHT-140 ){
            game.batch.draw(line, (GameUI.WINDOW_WIDTH)/10, (GameUI.WINDOW_HEIGHT-220), BACK_BUTTON_SIZE, 150);
            if(Gdx.input.justTouched()){
                this.dispose();
                game.setScreen(new StartMenu(game));
            }
        }

        game.batch.draw(backButton, GameUI.WINDOW_WIDTH/10, GameUI.WINDOW_HEIGHT-150, BACK_BUTTON_SIZE, BACK_BUTTON_SIZE);
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
