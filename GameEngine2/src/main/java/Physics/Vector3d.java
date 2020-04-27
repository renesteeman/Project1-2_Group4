package Physics;

import java.lang.String;
import org.joml.Vector3f;


public class Vector3d {
	public double x = 0.0, y = 0.0, z = 0.0;
	public static final double MAX_DIFFERENCE = 1e-1; // NEED TO PLAY WITH THIS ONE

	public Vector3d(){ }

	public Vector3d(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public Vector3d(String x, String y, String z) {
		this.x = Double.parseDouble(x);
		this.y = Double.parseDouble(y);	
		this.z = Double.parseDouble(z);
	}

	public static Vector3f convertF(Vector3d v) {
		return new Vector3f((float)v.x, (float)v.y, (float)v.z);
	}

	public double get_x() { return x; }
	public double get_y() { return y; }
	public double get_z() { return z; }


	public Vector3d add(Vector3d l) {
		return new Vector3d(this.x + l.x, this.y + l.y, this.z +l.z);
	}

	public Vector3d subtract(Vector3d l) {
		return new Vector3d(this.x + l.x, this.y + l.y, this.z + l.z);
	}

	public Vector3d multiply(double constant) {
		return new Vector3d(this.x * constant, this.y * constant, this.z * constant);
	}

	public Vector3d divide(double constant) {
		return new Vector3d(this.x / constant, this.y / constant, this.z / constant);
	}

	public double length() {
		return Math.sqrt(x * x + y * y + z * z);
	}

	public Vector3d getNormalized() {
		double l = length();
		return new Vector3d(x / l, y / l, z / l);
	}


	public static Vector3d add(Vector3d l, Vector3d r) {
		return new Vector3d(l.x + r.x, l.y + r.y, l.z + r.z);
	}

	public static Vector3d subtract(Vector3d l, Vector3d r) {
		return new Vector3d(l.x - r.x, l.y - r.y, l.z - r.z);
	}

	public static Vector3d multiply(Vector3d v, double constant) {
		return new Vector3d(v.x * constant, v.y * constant, v.z * constant);
	}

	public static Vector3d divide(Vector3d v, double constant) {
		return new Vector3d(v.x / constant, v.y / constant, v.z / constant);
	}

	public static double scalar(Vector3d l, Vector3d r) {
		return l.x * r.x + l.y * r.y + l.z * r.z;
	}


	@Override
	public String toString() {
		return String.format("(%f, %f, %f)", x, y, z);
	}

	@Override
	public boolean equals(Object obj) {
		if ((obj == null) || (obj.getClass() != this.getClass()))
			return false;
		Vector3d v = (Vector3d)obj;
		return (Math.abs(v.x - x) <= MAX_DIFFERENCE && Math.abs(v.y - y) <= MAX_DIFFERENCE && Math.abs(v.z - z) <= MAX_DIFFERENCE);
	}
}
