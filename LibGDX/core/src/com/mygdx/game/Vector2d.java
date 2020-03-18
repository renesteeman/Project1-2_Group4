public class Vector2d {
	public double x = 0.0, y = 0.0;
	public static final double EPS = 1e-9; // NEED TO PLAY WITH THIS ONE

	public Vector2d() {}

	public Vector2d(double x, double y) {
		this.x = x;
		this.y = y;
	}

	public double get_x() { return x; }
	public double get_y() { return y; }

	public static Vector2d substract(Vector2d l, Vector2d r) {
		return new Vector2d(l.x - r.x, l.y - r.y);
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