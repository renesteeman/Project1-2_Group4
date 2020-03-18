package com.mygdx.game;

public class Goal implements Drawable{
	//TODO why 2D?
	protected Vector2d location;

	public Goal(Vector2d location) {
		this.location = location;
	}

	//TO BE OVERRIDDDEN
	public void setLocation(Vector2d location) {
		this.location = location;
	}

	public Vector2d getLocation() {
		return location;
	}

	//TO BE OVERRIDDEN
	public void draw() {
		
	}
}