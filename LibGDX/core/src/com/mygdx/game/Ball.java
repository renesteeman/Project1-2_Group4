package com.mygdx.game;

import com.badlogic.gdx.math.Vector3;
import com.mygdx.game.Vector2d;

public class Ball implements Drawable{
	public static Vector2d location;
	public Vector2d velocity;

	public Ball(Vector2d location, Vector2d velocity) {
		this.location = location;
		this.velocity = velocity;

		draw();
	}

	public void updateLocation(Vector2d location) {
		this.location = location;

		//Update 3D UI
		Main.renderBall(location);
	}

	public void setVelocity(Vector2d velocity) {
		this.velocity = velocity;
	}

	public Vector2d getLocation() {
		return location;
	}

	public Vector2d getVelocity() {
		return velocity;
	}

	public void draw(){
		//Add it to the 3D scene
		Main.renderBall(location);
	}
}