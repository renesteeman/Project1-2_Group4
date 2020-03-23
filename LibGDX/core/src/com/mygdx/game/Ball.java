package com.mygdx.game;

public class Ball implements Drawable{
	public Vector2d location;
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

	public void draw(){
		//Add it to the 3D scene
		Main.renderBall(location);
	}
}