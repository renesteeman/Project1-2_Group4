package Screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;


public class WonScreen implements Screen {

    GameUI wonScreenGame;
    BitmapFont wonScreenFont;


    public WonScreen(GameUI game){
        this.wonScreenGame = game;
    }

    @Override
    public void show() {

    }


    @Override
    public void render(float delta) {

        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        wonScreenGame.gameUIBatch.begin();
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("Georgia Italic.ttf"));

        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 100;
        parameter.characters = "YOU WON!";
        wonScreenFont = generator.generateFont(parameter);
        wonScreenFont.setColor(Color.FOREST);
        generator.dispose();
        wonScreenFont.draw(wonScreenGame.gameUIBatch, "YOU WON!", GameUI.gameUI_WINDOW_WIDTH/3-50, GameUI.gameUI_WINDOW_HEIGHT-300);

        wonScreenGame.gameUIBatch.end();
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
