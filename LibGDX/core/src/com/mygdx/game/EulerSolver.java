package com.mygdx.game;

public class EulerSolver implements PhysicsEngine {
	private double step = 1e-2; // RANDOM VALUE, NEED TO ASSESS IT FURTHER ACCORDING TO THE INPUT
	private Function2d height;
	private PuttingCourse course;
	private Vector2d location, velocity;

	//TODO allow people to enter their prefered G value
	public final double __G = 10; //9.81;

	public EulerSolver(Function2d height, PuttingCourse course) {
		this.height = height;
		this.course = course;
	}

	@Override 
	public void process(Vector2d p, Vector2d v, double dtime) {
		for (double timer = 0; timer < dtime; timer += step) {
			double pnextx = p.x + step * v.x;
			double pnexty = p.y + step * v.y;

			Vector2d gradient = height.gradient(p);

			double vnextx = v.x - step * __G * (gradient.x + course.get_friction_coefficient() * v.x / v.len());
			double vnexty = v.y - step * __G * (gradient.y + course.get_friction_coefficient() * v.y / v.len());

			p = new Vector2d(pnextx, pnexty);
			v = new Vector2d(vnextx, vnexty);

			System.out.println(p + " " + v + " " + gradient);
		}
		location = p;
		velocity = v;
	}

	@Override 
	public Vector2d getLocation() {
		return location;
	}

	@Override 
	public Vector2d getVelocity() {
		return velocity;
	}

	@Override
	public void set_step_size(double h) {
		step = h;
	}

	@Override
	public double getStepSize() {
		return step;
	}

	public static void main(String args[]) {
		Function2d f = new Function("x^2");
		PuttingCourse course = new PuttingCourse("courses\\course0.txt");
		EulerSolver engine = new EulerSolver(f, course);

		Vector2d position = new Vector2d(-25, 0);
		Vector2d velocity = new Vector2d(1, 0);
		engine.process(position, velocity, 1);
		System.out.println(engine.getLocation());
		System.out.println(engine.getVelocity());
	}
}