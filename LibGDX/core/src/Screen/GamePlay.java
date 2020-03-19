package Screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;

public class GamePlay implements Screen {

    GameUI gamePlayGame;
    Texture gamePlayBackButton;
    Texture gamePlayLine;
    BitmapFont gamePlayFont;

    private static final int gamePlay_BACK_BUTTON_SIZE = 80;
    private static final int gamePlay_OK_BUTTON_WIDTH = 90;
    private static final int gamePlay_OK_BUTTON_HEIGHT = 90;

    public GamePlay(GameUI game){
        this.gamePlayGame = game;
        gamePlayBackButton = new Texture("back.png");
        gamePlayLine = new Texture("line.png");
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {

        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        gamePlayGame.gameUIBatch.begin();
        FreeTypeFontGenerator gamePlayGenerator = new FreeTypeFontGenerator(Gdx.files.internal("Georgia Italic.ttf"));

        FreeTypeFontGenerator.FreeTypeFontParameter gamePlayParameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        gamePlayParameter.size = 100;
        gamePlayParameter.characters = "Game";
        gamePlayFont = gamePlayGenerator.generateFont(gamePlayParameter);
        gamePlayFont.setColor(Color.FOREST);
        gamePlayGenerator.dispose();
        gamePlayFont.draw(gamePlayGame.gameUIBatch, "Game", GameUI.gameUI_WINDOW_WIDTH/3, GameUI.gameUI_WINDOW_HEIGHT-100);

        if(Gdx.input.getX() > GameUI.gameUI_WINDOW_WIDTH/10 && Gdx.input.getX() < (GameUI.gameUI_WINDOW_WIDTH/10) + gamePlay_BACK_BUTTON_SIZE && GameUI.gameUI_WINDOW_HEIGHT - Gdx.input.getY() < gamePlay_BACK_BUTTON_SIZE + GameUI.gameUI_WINDOW_HEIGHT-140 && GameUI.gameUI_WINDOW_HEIGHT - Gdx.input.getY() > GameUI.gameUI_WINDOW_HEIGHT-140 ){
            gamePlayGame.gameUIBatch.draw(gamePlayLine, (GameUI.gameUI_WINDOW_WIDTH)/10, (GameUI.gameUI_WINDOW_HEIGHT-220), gamePlay_BACK_BUTTON_SIZE, 150);
            if(Gdx.input.justTouched()){
                this.dispose();
                gamePlayGame.setScreen(new StartMenu(gamePlayGame));
            }
        }

        gamePlayGame.gameUIBatch.draw(gamePlayBackButton, GameUI.gameUI_WINDOW_WIDTH/10, GameUI.gameUI_WINDOW_HEIGHT-150, gamePlay_BACK_BUTTON_SIZE, gamePlay_BACK_BUTTON_SIZE);
        gamePlayGame.gameUIBatch.end();
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
