package Screen;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.mygdx.game.Ball;
import com.mygdx.game.PuttingCourse;
import com.mygdx.game.Vector2d;


public class HitWaterUI extends Game implements InputProcessor, ApplicationListener{
    int HitWaterUIBackgroundWidth;

    SpriteBatch HitWaterUIBatch;
    private Stage HitWaterStage;
    private Skin HitWaterSkin;

    double HitWaterDistanceFromStart;

    double HitWaterMaxDistanceFromStart;

    String HitWaterDistanceFromStartString;

    BitmapFont HitWaterFont;

    Sprite HitWaterWhiteBackground;

    TextButton HitWaterPositive;
    TextButton HitWaterNegative;

    @Override
    public void create () {
        //Calculate how far the ball may be placed
        //TODO remove these setters
        Ball.location = new Vector2d(0, 0, 0);
        PuttingCourse.start = new Vector2d(10, 1, 1);
        Vector2d ballLocation = Ball.location;
        Vector2d startPoint = PuttingCourse.start;
        maxDistanceFromStart = Vector2d.getDistance(ballLocation, startPoint);

        batch = new SpriteBatch();
        skin = new Skin(Gdx.files.internal("uiskin.json"));
        stage = new Stage(new ScreenViewport());

        backgroundWidth = Gdx.graphics.getWidth()/4;
        whiteBackground = new Sprite(new Texture(Gdx.files.internal("whiteBackground.png")));
        whiteBackground.setSize(backgroundWidth, Gdx.graphics.getHeight());
        whiteBackground.setPosition(Gdx.graphics.getWidth()-backgroundWidth,0);

        font = new BitmapFont(Gdx.files.internal("Arial.fnt"));
        font.getData().setScale(0.6f,0.6f);
        distanceFromStartString = "How far from the start do you want to set the ball?";

        positive = new TextButton(">", skin, "default");
        positive.setWidth(25);
        positive.setHeight(25);
        positive.setPosition(Gdx.graphics.getWidth() * 18/20, Gdx.graphics.getHeight() * 3/5);

        negative = new TextButton("<", skin, "default");
        negative.setWidth(25);
        negative.setHeight(25);
        negative.setPosition(Gdx.graphics.getWidth() * 17/20, Gdx.graphics.getHeight() * 3/5);

        final TextButton setBallButton = new TextButton("Set", skin, "default");
        setBallButton.setWidth(100);
        setBallButton.setHeight(30);
        setBallButton.setPosition(Gdx.graphics.getWidth() * 8/10, Gdx.graphics.getHeight() * 6/20);

        positive.addListener(new ClickListener(){
			/*
			@Override
			public void clicked(InputEvent event, float x, float y) {
				rotation = rotation + 10;
				if(rotation >= 360){
					rotation = 0;
				}
			}
			 */

            @Override
            public boolean isPressed() {
                return super.isPressed();
            }
        });
        negative.addListener(new ClickListener(){
			/*
			@Override
			public void clicked(InputEvent event, float x, float y) {
				rotation = rotation - 10;
				if(rotation < 0){
					rotation = 350;
				}
			}
			 */

            @Override
            public boolean isPressed() {
                return super.isPressed();
            }
        });

        stage.addActor(positive);
        stage.addActor(negative);
        stage.addActor(setBallButton);

        InputMultiplexer inputMultiplexer = new InputMultiplexer(stage, this);
        Gdx.input.setInputProcessor(inputMultiplexer);
    }

    @Override
    public void render () {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act(Gdx.graphics.getDeltaTime());

        if(positive.isPressed()){
            distanceFromStart += Gdx.graphics.getDeltaTime() *20;
            if(distanceFromStart >= maxDistanceFromStart){
                distanceFromStart = maxDistanceFromStart;
            }
        }

        if(negative.isPressed()){
            distanceFromStart -= Gdx.graphics.getDeltaTime() *20;
            if(distanceFromStart < 0){
                distanceFromStart = 0;
            }
        }

        int distanceFromStartRounded = (int) distanceFromStart;

        batch.begin();
        font.setColor(Color.BLACK);
        whiteBackground.draw(batch);
        font.draw(batch, distanceFromStartString, Gdx.graphics.getWidth()-backgroundWidth,
                Gdx.graphics.getHeight() * 9/10, backgroundWidth, 1, true);
        font.draw(batch, String.valueOf(distanceFromStartRounded), Gdx.graphics.getWidth()-backgroundWidth,
                Gdx.graphics.getHeight() * 7/10, backgroundWidth, 1, true);
        batch.end();

        stage.draw();
    }

    @Override
    public void dispose () {
    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {

        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {

        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}
