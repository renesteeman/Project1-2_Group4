package game;

import org.joml.Vector3f;
import java.lang.String;

public class Vector2d {
	public double x = 0.0, y = 0.0;
	public static final double EPS = 1e-1; // NEED TO PLAY WITH THIS ONE

	public Vector2d(){

	}

	public Vector2d(double x, double y) {
		this.x = x;
		this.y = y;
	}

	public Vector2d(String x, String y) {
		this.x = Double.parseDouble(x);
		this.y = Double.parseDouble(y);	
	}

	public Vector2d(Vector3f v) {
		this.x = v.x;
		this.y = v.y;
	}

	public Vector2d(Vector2d v) {
		this.x = v.x;
		this.y = v.y;
	}

	public Vector2d(Vector3d v) {
		this.x = v.x;
		this.y = v.y;
	}

	public double get_x() { return x; }
	public double get_y() { return y; }

	public static Vector2d add(Vector2d l, Vector2d r) {
		return new Vector2d(l.x + r.x, l.y + r.y);
	}

	public static Vector2d substract(Vector2d l, Vector2d r) {
		return new Vector2d(l.x - r.x, l.y - r.y);
	}

	public static Vector2d mul(Vector2d v, double constant) {
		return new Vector2d(v.x * constant, v.y * constant);
	}

	public static Vector2d div(Vector2d v, double constant) {
		return new Vector2d(v.x / constant, v.y / constant);
	}

	public static double scalar(Vector2d l, Vector2d r) {
		return l.x * r.x + l.y * r.y;
	}

	public static double pseudoScalar(Vector2d l, Vector2d r) {
		return l.x * r.y - l.y * r.x;
	}

	public double len() {
		return Math.sqrt(x * x + y * y);
	} 

	public Vector2d getNormalized() {
		double l = len();
		return new Vector2d(x / l, y / l);
	}

	@Override
	public String toString() {
		return String.format("(%f, %f)", x, y);
	}

	@Override
	public boolean equals(Object obj) {
		if ((obj == null) || (obj.getClass() != this.getClass()))
			return false;
		Vector2d v = (Vector2d)obj;
		return (Math.abs(v.x - x) <= EPS && Math.abs(v.y - y) <= EPS);
	}
}
