package com.mygdx.game;

public class Ball implements GameObject{
	public Vector2d location;
	public Vector2d velocity;

	public Ball(Vector2d location, Vector2d velocity) {
		this.location = location;
		this.velocity = velocity;

		render();
	}

	@Override
	public void render() {
		Main.renderBall(location);
	}

	public void updateLocation(Vector2d location) {
		this.location = location;

		//Update 3D UI
		Main.renderBall(location);
	}

	public void setVelocity(Vector2d velocity) {
		this.velocity = velocity;
	}

}