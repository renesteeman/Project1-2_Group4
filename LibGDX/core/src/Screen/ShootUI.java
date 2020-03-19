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

	private Stage ShootUIStage;
	private Skin ShootUISkin;
	private double ShootUIRotation = 0;
	private double ShootUIpower = 1;
	private int ShootUIScore = 0;
	private String ShootUIScoreString;
	private String ShootUIRotationString;
	private String ShootUIPowerString;
	BitmapFont ShootUIFont;

	Sprite ShootUIWhiteBackground;

	public boolean ShootUIIsPressed;
	TextButton ShootUIPositive;
	TextButton ShootUINegative;
	TextButton ShootUIPositivePower;
	TextButton ShootUINegativePower;

	@Override
	public void create () {
		batch = new SpriteBatch();

		ShootUISkin = new Skin(Gdx.files.internal("uiskin.json"));
		ShootUIStage = new Stage(new ScreenViewport());

		final int backgroundWidth = Gdx.graphics.getWidth()/4;
		ShootUIWhiteBackground = new Sprite(new Texture(Gdx.files.internal("whiteBackground.png")));
		ShootUIWhiteBackground.setSize(backgroundWidth, Gdx.graphics.getHeight());
		ShootUIWhiteBackground.setPosition(Gdx.graphics.getWidth()-backgroundWidth,0);

		ShootUIFont = new BitmapFont(Gdx.files.internal("Arial.fnt"));
		ShootUIFont.getData().setScale(0.6f,0.6f);
		ShootUIScoreString = "Your score: ";
		ShootUIRotationString = "Angle: ";
		ShootUIPowerString = "Power (m/s): ";

		ShootUIPositive = new TextButton(">", ShootUISkin, "default");
		ShootUIPositive.setWidth(25);
		ShootUIPositive.setHeight(25);
		ShootUIPositive.setPosition(Gdx.graphics.getWidth() * 18/20, Gdx.graphics.getHeight() * 3/5);

		ShootUINegative = new TextButton("<", ShootUISkin, "default");
		ShootUINegative.setWidth(25);
		ShootUINegative.setHeight(25);
		ShootUINegative.setPosition(Gdx.graphics.getWidth() * 17/20, Gdx.graphics.getHeight() * 3/5);

		ShootUIPositivePower = new TextButton("+", ShootUISkin, "default");
		ShootUIPositivePower.setWidth(25);
		ShootUIPositivePower.setHeight(25);
		ShootUIPositivePower.setPosition(Gdx.graphics.getWidth() * 9/10, Gdx.graphics.getHeight() * 10/20);

		ShootUINegativePower = new TextButton("-", ShootUISkin, "default");
		ShootUINegativePower.setWidth(25);
		ShootUINegativePower.setHeight(25);
		ShootUINegativePower.setPosition(Gdx.graphics.getWidth() * 9/10, Gdx.graphics.getHeight() * 9/20);

		final TextButton ShootUIShootButton = new TextButton("Shoot!", ShootUISkin, "default");
		ShootUIShootButton.setWidth(100);
		ShootUIShootButton.setHeight(30);
		ShootUIShootButton.setPosition(Gdx.graphics.getWidth() * 8/10, Gdx.graphics.getHeight() * 6/20);

		ShootUIPositive.addListener(new ClickListener(){
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

		ShootUINegative.addListener(new ClickListener(){
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

		ShootUIPositivePower.addListener(new ClickListener(){
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

		ShootUINegativePower.addListener(new ClickListener(){
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

		ShootUIShootButton.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {

			}
		});

		ShootUIStage.addActor(ShootUIPositive);
		ShootUIStage.addActor(ShootUINegative);
		ShootUIStage.addActor(ShootUIPositivePower);
		ShootUIStage.addActor(ShootUINegativePower);
		ShootUIStage.addActor(ShootUIShootButton);

		InputMultiplexer inputMultiplexer = new InputMultiplexer(ShootUIStage, this);
		Gdx.input.setInputProcessor(inputMultiplexer);

	}

	@Override
	public void render () {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		ShootUIStage.act(Gdx.graphics.getDeltaTime());

		if(ShootUIPositive.isPressed()){
			ShootUIRotation += Gdx.graphics.getDeltaTime() *20;
			if(ShootUIRotation >= 360){
				ShootUIRotation = 0;
			}
		}

		if(ShootUINegative.isPressed()){
			ShootUIRotation -= Gdx.graphics.getDeltaTime() *20;
			if(ShootUIRotation < 0){
				ShootUIRotation = 350;
			}
		}

		if(ShootUIPositivePower.isPressed()) {
			ShootUIpower += Gdx.graphics.getDeltaTime() *2;
			if (ShootUIpower >= 5) {
				ShootUIpower = 5;
			}
		}

		if(ShootUINegativePower.isPressed()) {
			ShootUIpower -= Gdx.graphics.getDeltaTime() *2;
			if (ShootUIpower <= 1) {
				ShootUIpower = 1;
			}
		}

		int rotationRounded = (int) ShootUIRotation;
		int powerRounded = (int) ShootUIpower;

		batch.begin();
		ShootUIFont.setColor(Color.BLACK);
		ShootUIWhiteBackground.draw(batch);
		ShootUIFont.draw(batch, ShootUIScoreString + ShootUIScore, Gdx.graphics.getWidth() * 8/10, Gdx.graphics.getHeight() * 9/10);
		ShootUIFont.draw(batch, ShootUIRotationString + rotationRounded, Gdx.graphics.getWidth() * 8/10, Gdx.graphics.getHeight() * 7/10);
		ShootUIFont.draw(batch, ShootUIPowerString + powerRounded, Gdx.graphics.getWidth() * 8/10, Gdx.graphics.getHeight() * 6/10);
		batch.end();

		ShootUIStage.draw();
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
			ShootUIIsPressed = true;
		}
		return true;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		if (button == Input.Buttons.LEFT){
			ShootUIIsPressed = false;
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
