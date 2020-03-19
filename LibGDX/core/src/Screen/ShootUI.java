package com.mygdx.game;

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


public class ShootUI extends Game implements InputProcessor, ApplicationListener{

	SpriteBatch batch;
	private Stage stage;
	private Skin skin;
	private double rotation = 0;
	private double power = 1;
	private int score = 0;
	private String ScoreString;
	private String RotationString;
	private String PowerString;
	BitmapFont font;

	Sprite whiteBackground;

	public boolean isPressed;
	TextButton positive;
	TextButton negative;
	TextButton positivePower;
	TextButton negativePower;

	@Override
	public void create () {
		batch = new SpriteBatch();
		skin = new Skin(Gdx.files.internal("uiskin.json"));
		stage = new Stage(new ScreenViewport());

		final int backgroundWidth = Gdx.graphics.getWidth()/4;
		whiteBackground = new Sprite(new Texture(Gdx.files.internal("whiteBackground.png")));
		whiteBackground.setSize(backgroundWidth, Gdx.graphics.getHeight());
		whiteBackground.setPosition(Gdx.graphics.getWidth()-backgroundWidth,0);

		font = new BitmapFont(Gdx.files.internal("Arial.fnt"));
		font.getData().setScale(0.6f,0.6f);
		ScoreString = "Your score: ";
		RotationString = "Angle: ";
		PowerString = "Power (m/s): ";

		positive = new TextButton(">", skin, "default");
		positive.setWidth(25);
		positive.setHeight(25);
		positive.setPosition(Gdx.graphics.getWidth() * 18/20, Gdx.graphics.getHeight() * 3/5);

		negative = new TextButton("<", skin, "default");
		negative.setWidth(25);
		negative.setHeight(25);
		negative.setPosition(Gdx.graphics.getWidth() * 17/20, Gdx.graphics.getHeight() * 3/5);

		positivePower = new TextButton("+", skin, "default");
		positivePower.setWidth(25);
		positivePower.setHeight(25);
		positivePower.setPosition(Gdx.graphics.getWidth() * 9/10, Gdx.graphics.getHeight() * 10/20);

		negativePower = new TextButton("-", skin, "default");
		negativePower.setWidth(25);
		negativePower.setHeight(25);
		negativePower.setPosition(Gdx.graphics.getWidth() * 9/10, Gdx.graphics.getHeight() * 9/20);

		final TextButton shoot = new TextButton("Shoot!", skin, "default");
		shoot.setWidth(100);
		shoot.setHeight(30);
		shoot.setPosition(Gdx.graphics.getWidth() * 8/10, Gdx.graphics.getHeight() * 6/20);

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

		positivePower.addListener(new ClickListener(){
			/*
			@Override
			public void clicked(InputEvent event, float x, float y) {
				power = power + 1;
				if(power >= 5){
					power = 5;
				}
			}
			 */

			@Override
			public boolean isPressed() {
				return super.isPressed();
			}
		});

		negativePower.addListener(new ClickListener(){
			/*
			@Override
			public void clicked(InputEvent event, float x, float y) {
				power = power - 1;
				if(power <= 1){
					power = 1;
				}
			}
			 */

			@Override
			public boolean isPressed() {
				return super.isPressed();
			}
		});

		shoot.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {

			}
		});

		stage.addActor(positive);
		stage.addActor(negative);
		stage.addActor(positivePower);
		stage.addActor(negativePower);
		stage.addActor(shoot);

		InputMultiplexer inputMultiplexer = new InputMultiplexer(stage, this);
		Gdx.input.setInputProcessor(inputMultiplexer);

	}

	@Override
	public void render () {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		stage.act(Gdx.graphics.getDeltaTime());

		if(positive.isPressed()){
			rotation += Gdx.graphics.getDeltaTime() *20;
			if(rotation >= 360){
				rotation = 0;
			}
		}

		if(negative.isPressed()){
			rotation -= Gdx.graphics.getDeltaTime() *20;
			if(rotation < 0){
				rotation = 350;
			}
		}

		if(positivePower.isPressed()) {
			power += Gdx.graphics.getDeltaTime() *2;
			if (power >= 5) {
				power = 5;
			}
		}

		if(negativePower.isPressed()) {
			power -= Gdx.graphics.getDeltaTime() *2;
			if (power <= 1) {
				power = 1;
			}
		}

		int rotationRounded = (int) rotation;
		int powerRounded = (int) power;

		batch.begin();
		font.setColor(Color.BLACK);
		whiteBackground.draw(batch);
		font.draw(batch, ScoreString + score, Gdx.graphics.getWidth() * 8/10, Gdx.graphics.getHeight() * 9/10);
		font.draw(batch, RotationString + rotationRounded, Gdx.graphics.getWidth() * 8/10, Gdx.graphics.getHeight() * 7/10);
		font.draw(batch, PowerString + powerRounded, Gdx.graphics.getWidth() * 8/10, Gdx.graphics.getHeight() * 6/10);
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
		if (button == Input.Buttons.LEFT) {
			isPressed = true;
		}
		return true;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		if (button == Input.Buttons.LEFT){
			isPressed = false;
		}
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
