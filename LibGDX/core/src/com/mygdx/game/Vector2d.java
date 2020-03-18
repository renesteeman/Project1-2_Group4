package com.mygdx.game;

import com.badlogic.gdx.math.Vector3;

//Note that y and z are inverted for the 2D vectors because of the required API
//A 2D vector is just a 3D vector, but the API requires it
public class Vector2d {
	public double x = 0.0, y = 0.0, z = 0.0;
	public static final double EPS = 1e-9; // NEED TO PLAY WITH THIS ONE

	public Vector2d(){

	}

	public Vector2d(double x, double y) {
		this.x = x;
		this.y = y;
	}

	public Vector2d(double x, double y, double z){
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public double get_x() { return x; }
	public double get_y() { return y; }
	public double get_z() { return z; }

	public static Vector2d multiplyVector(double constant, Vector2d vector){
		double x = vector.x * constant;
		double y = vector.y * constant;
		double z = vector.z * constant;

		return new Vector2d(x, y, z);
	}

	public static Vector2d addVectors(Vector2d first, Vector2d second){
		double x = first.x + second.x;
		double y = first.y + second.y;
		double z = first.z + second.z;

		return new Vector2d(x, y, z);
	}

	public static Vector2d getNormalized(Vector2d vector){
		double vectorSize = Math.sqrt(vector.x*vector.x + vector.y*vector.y + vector.z*vector.z);

		double x = vector.x / vectorSize;
		double y = vector.y / vectorSize;
		double z = vector.z / vectorSize;

		return new Vector2d(x, y, z);
	}

	public static Vector2d substract(Vector2d l, Vector2d r) {
		return new Vector2d(l.x - r.x, l.y - r.y);
	}

	public static double getDistance(Vector2d pointA, Vector2d pointB){
		return Math.sqrt((pointB.x-pointA.x)*(pointB.x-pointA.x)
				+ (pointB.y - pointA.y)*(pointB.y - pointA.y)
				+ (pointB.z - pointA.z)*(pointB.z - pointA.z));
	}

	public double len() {
		return Math.sqrt(x * x + y * y);
	} 

	@Override
	public String toString() {
		return String.format("(%d, %d)", x, y);
	}

	@Override
	public boolean equals(Object obj) {
		if ((obj == null) || (obj.getClass() != this.getClass()))
			return false;
		Vector2d v = (Vector2d)obj;
		return (Math.abs(v.x - x) <= EPS && Math.abs(v.y - y) <= EPS);
	}
}