package Physics;

import org.joml.Vector3f;

import java.lang.String;


public class Vector2d {
	public double x = 0.0, y = 0.0;
	public static double MAX_DIFFERENCE = 1e-1; // NEED TO PLAY WITH THIS ONE

	public Vector2d(){ }

	public Vector2d(Vector2d another) {
		x = another.x;
		y = another.y;
	}

	public Vector2d(double x, double y) {
		this.x = x;
		this.y = y;
	}

	public Vector2d(String x, String y) {
		this.x = Double.parseDouble(x);
		this.y = Double.parseDouble(y);	
	}

	public double get_x() { return x; }
	public double get_y() { return y; }

	public Vector2d add(Vector2d l){
		return new Vector2d(this.x + l.x, this.y + l.y);
	}

	public Vector2d subtract(Vector2d l){
		return new Vector2d(this.x - l.x, this.y - l.y);
	}

	public Vector2d multiply(double constant){
		return new Vector2d(this.x * constant, this.y * constant);
	}

	public Vector2d divide(double constant){
		return new Vector2d(this.x / constant, this.y / constant);
	}

	public double length() {
		return Math.sqrt(x * x + y * y);
	}

	public Vector2d getNormalized() {
		double l = this.length();
		return new Vector2d(x / l, y / l);
	}

	public double dotProduct(Vector2d other) {
		return (this.x * other.x + this.y * other.y);
	}

	public static Vector2d add(Vector2d l, Vector2d r) {
		return new Vector2d(l.x + r.x, l.y + r.y);
	}

	public static Vector2d subtract(Vector2d l, Vector2d r) {
		return new Vector2d(l.x - r.x, l.y - r.y);
	}

	public static Vector2d divide(Vector2d v, double constant) {
		return new Vector2d(v.x / constant, v.y / constant);
	}

	public static Vector2d multiply(Vector2d v, double constant) {
		return new Vector2d(v.x * constant, v.y * constant);
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
		return (Math.abs(v.x - x) <= MAX_DIFFERENCE && Math.abs(v.y - y) <= MAX_DIFFERENCE);
	}

	public Vector2d rotate(double angle) {
		double rx = (this.x * Math.cos(angle)) - (this.y * Math.sin(angle));
		double ry = (this.x * Math.sin(angle)) + (this.y * Math.cos(angle));

		return new Vector2d(rx, ry);
	}

}
