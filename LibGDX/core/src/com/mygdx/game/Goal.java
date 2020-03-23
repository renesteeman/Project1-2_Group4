package com.mygdx.game;

public class Goal implements GameObject{
	public Vector2d location;

	public Goal(Vector2d location) {
		this.location = location;
	}

	public void render() {
		//Draw 3D
		Main.renderGoal(location);
	}

	@Override
	public void updateLocation(Vector2d location) {
		this.location = location;

		//Update 3D
		Main.renderGoal(location);
	}
}