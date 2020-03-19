//package com.mygdx.game;

public class Goal implements Drawable{
	protected Vector2d location;

	public Goal(Vector2d location) {
		this.location = location;
	}

	public Vector2d getLocation() {
		return location;
	}

	public void draw() {
		//Draw 3D
		//Main.renderGoal(location);
	}

	@Override
	public void updateLocation(Vector2d location) {
		this.location = location;

		//Update 3D
		//Main.renderGoal(location);
	}
}