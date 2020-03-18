package com.mygdx.game;

import com.mygdx.game.Vector2d;

public class Ball {
	//TODO why 2D?
	public Vector2d location, velocity;

	public Ball(Vector2d location, Vector2d velocity) {
		this.location = location;
		this.velocity = velocity;
	}

	//TO BE OVERRIDDDEN
	public void setLocation(Vector2d location) {
		this.location = location;
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

	//TO BE OVERRIDDEN
	public void draw() {
		
	}
}