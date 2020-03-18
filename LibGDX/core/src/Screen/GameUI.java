package Screen;

import Screen.GameScreen;
import Screen.MainMenuScreen;
import Screen.SettingsMenu;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class GameUI extends Game {
	public SpriteBatch GameUIbatch;

	public static final int GameUIWINDOW_WIDTH = 750;
	public static final int GameUIWINDOW_HEIGHT = 750;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		this.setScreen(new MainMenuScreen(this));
		//this.setScreen((new SettingsMenu(this)));
	}

	@Override
	public void render () {
		super.render();
	}
	
	@Override
	public void dispose () { }
}
