package Screen;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;


public class GameScreen implements Screen {

    Texture gameScreenImg;
    float gameScreenX;
    float gameScreenY;

    GameUI gameScreenGame;

    public GameScreen ( GameUI game){
        this.gameScreenGame = game;
    }

    @Override
    public void show() {
        gameScreenImg = new Texture("badlogic.jpg");
    }

    @Override
    public void render(float delta) { }

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
