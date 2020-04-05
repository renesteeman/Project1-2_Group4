import java.lang.String;

public class Vector3d {
	public double x = 0.0, y = 0.0, z = 0.0;
	public static final double EPS = 1e-1; // NEED TO PLAY WITH THIS ONE

	public Vector3d(){

	}

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


	public Vector3d(Vector3d v) {
		this.x = v.x;
		this.y = v.y;
		this.z = v.z;
	}

	public Vector3d(Vector2d v) {
		this.x = v.x;
		this.y = v.y;
		this.z = 0.0;
	}

	public Vector3d(Vector2d v, double z) {
		this.x = v.x;
		this.y = v.y;
		this.z = z;
	}

	public double get_x() { return x; }
	public double get_y() { return y; }
	public double get_z() { return z; }

	public static Vector3d add(Vector3d l, Vector3d r) {
		return new Vector3d(l.x + r.x, l.y + r.y, l.z + r.z);
	}

	public static Vector3d substract(Vector3d l, Vector3d r) {
		return new Vector3d(l.x - r.x, l.y - r.y, l.z - r.z);
	}

	public static Vector3d mul(Vector3d v, double constant) {
		return new Vector3d(v.x * constant, v.y * constant, v.z * constant);
	}

	public static Vector3d div(Vector3d v, double constant) {
		return new Vector3d(v.x / constant, v.y / constant, v.z / constant);
	}

	public static double scalar(Vector3d l, Vector3d r) {
		return l.x * r.x + l.y * r.y + l.z * r.z;
	}

	public double len() {
		return Math.sqrt(x * x + y * y + z * z);
	} 

	public Vector3d getNormalized() {
		double l = len();
		return new Vector3d(x / l, y / l, z / l);
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
		return (Math.abs(v.x - x) <= EPS && Math.abs(v.y - y) <= EPS && Math.abs(v.z - z) <= EPS);
	}
}