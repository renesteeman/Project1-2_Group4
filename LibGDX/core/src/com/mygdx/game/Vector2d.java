//package com.mygdx.game;

//import com.badlogic.gdx.math.Vector3;

//Note that y and z are inverted for the 2D vectors because of the required API
//A 2D vector is just a 3D vector, but the API requires it
public class Vector2d {
	public double x = 0.0, y = 0.0, z = 0.0;
	public static final double EPS = 1e-1; // NEED TO PLAY WITH THIS ONE

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

	public static Vector2d add(Vector2d l, Vector2d r) {
		return new Vector2d(l.x + r.x, l.y + r.y, l.z + r.z);
	}

	public static Vector2d substract(Vector2d l, Vector2d r) {
		return new Vector2d(l.x - r.x, l.y - r.y, l.z - r.z);
	}

	public static Vector2d mul(Vector2d v, double constant) {
		return new Vector2d(v.x * constant, v.y * constant, v.z * constant);
	}

	public Vector2d getNormalized() {
		double l = len();
		return new Vector2d(x / l, y / l, z / l);
	}

	public static double getDistance(Vector2d pointA, Vector2d pointB) {
		Vector2d res = Vector2d.substract(pointA, pointB);
		return res.len();
	}

	public double len() {
		return Math.sqrt(x * x + y * y + z * z);
	} 

	@Override
	public String toString() {
		return String.format("(%f, %f, %f)", x, y, z);
	}

	@Override
	public boolean equals(Object obj) {
		if ((obj == null) || (obj.getClass() != this.getClass()))
			return false;
		Vector2d v = (Vector2d)obj;
		return (Math.abs(v.x - x) <= EPS && Math.abs(v.y - y) <= EPS && Math.abs(v.z - z) <= EPS);
	}
}