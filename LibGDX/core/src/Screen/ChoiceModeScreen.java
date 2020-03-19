package Screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class ChoiceModeScreen implements Screen {

    GameUI choiceModeScreenGame;
    BitmapFont choiceModeScreenTitle;
    Stage choiceModeScreenStage;
    TextButton choiceModeScreenHuman;
    TextButton choiceModeScreenBot;
    TextField choiceModeScreenPath;

    public ChoiceModeScreen(final GameUI game){
        this.choiceModeScreenGame = game;
        choiceModeScreenStage = new Stage();
        Gdx.input.setInputProcessor(choiceModeScreenStage);
        Skin choiceModeSkin = new Skin(Gdx.files.internal("skins/uiskin.json"));

        choiceModeScreenHuman = new TextButton("Play as a human", choiceModeSkin);
        choiceModeScreenHuman.setPosition(GameUI.gameUI_WINDOW_WIDTH/6, GameUI.gameUI_WINDOW_HEIGHT - 300);
        choiceModeScreenHuman.setSize(200, 50);
        choiceModeScreenHuman.setColor(Color.FOREST);

        choiceModeScreenHuman.addListener(new ClickListener(){
            @Override
           public void touchUp(InputEvent e, float x, float y, int point, int button){
                game.setScreen(new GamePlay(game));
            }
       });

        choiceModeScreenBot = new TextButton("Play using your own file", choiceModeSkin);
        choiceModeScreenBot.setPosition(GameUI.gameUI_WINDOW_WIDTH/6, GameUI.gameUI_WINDOW_HEIGHT - 400);
        choiceModeScreenBot.setSize(200, 50);
        choiceModeScreenBot.setColor(Color.FOREST);

        choiceModeScreenBot.addListener(new ClickListener(){
            @Override
            public void touchUp(InputEvent e, float x, float y, int point, int button){
                game.setScreen(new GamePlay(game));
            }
        });

        choiceModeScreenPath = new TextField("", choiceModeSkin);
        choiceModeScreenPath.setPosition((GameUI.gameUI_WINDOW_WIDTH/6) + choiceModeScreenBot.getWidth() + 50, GameUI.gameUI_WINDOW_HEIGHT-400);
        choiceModeScreenPath.setSize(200, 50);
        choiceModeScreenPath.setColor(Color.FOREST);
        choiceModeScreenPath.setText("Your path:");

        choiceModeScreenStage.addActor(choiceModeScreenHuman);
        choiceModeScreenStage.addActor(choiceModeScreenBot);
        choiceModeScreenStage.addActor(choiceModeScreenPath);
    }


    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {

        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        choiceModeScreenGame.gameUIBatch.begin();
        FreeTypeFontGenerator choiceModeScreenGenerator = new FreeTypeFontGenerator(Gdx.files.internal("Georgia Italic.ttf"));

        FreeTypeFontGenerator.FreeTypeFontParameter choiceModeScreenParameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        choiceModeScreenParameter.size = 70;
        choiceModeScreenParameter.characters = "Choose Your Game Mode";
        choiceModeScreenTitle = choiceModeScreenGenerator.generateFont(choiceModeScreenParameter);
        choiceModeScreenTitle.setColor(Color.FOREST);
        choiceModeScreenGenerator.dispose();
        choiceModeScreenTitle.draw(choiceModeScreenGame.gameUIBatch, "Choose Your Game Mode", GameUI.gameUI_WINDOW_WIDTH/4-50, GameUI.gameUI_WINDOW_HEIGHT-100);

        choiceModeScreenGame.gameUIBatch.end();
        choiceModeScreenStage.draw();
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

    public String getPath() {
        return choiceModeScreenPath.getText();
    }

}
