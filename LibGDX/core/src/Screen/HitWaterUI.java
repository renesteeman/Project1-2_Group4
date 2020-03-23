package Screen;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;


public class HitWaterUI extends Game implements InputProcessor, ApplicationListener{

    int hitWaterUIBackgroundWidth;

    SpriteBatch hitWaterUIBatch;
    private Stage hitWaterUIStage;
    private Skin hitWaterUISkin;

    double hitWaterUIDistanceFromStart;

    double hitWaterUIMaxDistanceFromStart;

    String hitWaterUIDistanceFromStartString;

    BitmapFont hitWaterUIFont;

    Sprite hitWaterUIWhiteBackground;

    TextButton hitWaterUIPositive;
    TextButton hitWaterUINegative;

    @Override
    public void create () {
        //Calculate how far the ball may be placed
        //TODO remove these setters
//        Ball.location = new Vector2d(0, 0, 0);
//        PuttingCourse.start = new Vector2d(10, 1, 1);
//        Vector2d ballLocation = Ball.location;
//        Vector2d startPoint = PuttingCourse.start;
//        hitWaterUIMaxDistanceFromStart = Vector2d.getDistance(ballLocation, startPoint);

        hitWaterUIBatch = new SpriteBatch();
        hitWaterUISkin = new Skin(Gdx.files.internal("uiskin.json"));
        hitWaterUIStage = new Stage(new ScreenViewport());

        hitWaterUIBackgroundWidth = Gdx.graphics.getWidth()/4;
        hitWaterUIWhiteBackground = new Sprite(new Texture(Gdx.files.internal("whiteBackground.png")));
        hitWaterUIWhiteBackground.setSize(hitWaterUIBackgroundWidth, Gdx.graphics.getHeight());
        hitWaterUIWhiteBackground.setPosition(Gdx.graphics.getWidth()-hitWaterUIBackgroundWidth,0);

        hitWaterUIFont = new BitmapFont(Gdx.files.internal("Arial.fnt"));
        hitWaterUIFont.getData().setScale(0.6f,0.6f);
        hitWaterUIDistanceFromStartString = "How far from the start do you want to set the ball?";

        hitWaterUIPositive = new TextButton(">", hitWaterUISkin, "default");
        hitWaterUIPositive.setWidth(25);
        hitWaterUIPositive.setHeight(25);
        hitWaterUIPositive.setPosition(Gdx.graphics.getWidth() * 18/20, Gdx.graphics.getHeight() * 3/5);

        hitWaterUINegative = new TextButton("<", hitWaterUISkin, "default");
        hitWaterUINegative.setWidth(25);
        hitWaterUINegative.setHeight(25);
        hitWaterUINegative.setPosition(Gdx.graphics.getWidth() * 17/20, Gdx.graphics.getHeight() * 3/5);

        final TextButton hitWaterUISetBallButton = new TextButton("Set", hitWaterUISkin, "default");
        hitWaterUISetBallButton.setWidth(100);
        hitWaterUISetBallButton.setHeight(30);
        hitWaterUISetBallButton.setPosition(Gdx.graphics.getWidth() * 8/10, Gdx.graphics.getHeight() * 6/20);

        hitWaterUIPositive.addListener(new ClickListener(){
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
        hitWaterUINegative.addListener(new ClickListener(){
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

        hitWaterUIStage.addActor(hitWaterUIPositive);
        hitWaterUIStage.addActor(hitWaterUINegative);
        hitWaterUIStage.addActor(hitWaterUISetBallButton);

        InputMultiplexer hitWaterUIInputMultiplexer = new InputMultiplexer(hitWaterUIStage, this);
        Gdx.input.setInputProcessor(hitWaterUIInputMultiplexer);
    }

    @Override
    public void render () {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        hitWaterUIStage.act(Gdx.graphics.getDeltaTime());

        if(hitWaterUIPositive.isPressed()){
            hitWaterUIDistanceFromStart += Gdx.graphics.getDeltaTime() *20;
            if(hitWaterUIDistanceFromStart >= hitWaterUIMaxDistanceFromStart){
                hitWaterUIDistanceFromStart = hitWaterUIMaxDistanceFromStart;
            }
        }

        if(hitWaterUINegative.isPressed()){
            hitWaterUIDistanceFromStart -= Gdx.graphics.getDeltaTime() *20;
            if(hitWaterUIDistanceFromStart < 0){
                hitWaterUIDistanceFromStart = 0;
            }
        }

        int distanceFromStartRounded = (int) hitWaterUIDistanceFromStart;

        hitWaterUIBatch.begin();
        hitWaterUIFont.setColor(Color.BLACK);
        hitWaterUIWhiteBackground.draw(hitWaterUIBatch);
        hitWaterUIFont.draw(hitWaterUIBatch, hitWaterUIDistanceFromStartString, Gdx.graphics.getWidth()-hitWaterUIBackgroundWidth,
                Gdx.graphics.getHeight() * 9/10, hitWaterUIBackgroundWidth, 1, true);
        hitWaterUIFont.draw(hitWaterUIBatch, String.valueOf(distanceFromStartRounded), Gdx.graphics.getWidth()-hitWaterUIBackgroundWidth,
                Gdx.graphics.getHeight() * 7/10, hitWaterUIBackgroundWidth, 1, true);
        hitWaterUIBatch.end();

        hitWaterUIStage.draw();
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
