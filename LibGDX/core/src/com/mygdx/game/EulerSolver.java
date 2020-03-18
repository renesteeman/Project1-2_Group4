package com.mygdx.game;

public class EulerSolver implements PhysicsEngine {
	private double step = 1e-4; // RANDOM VALUE, NEED TO ASSESS IT FURTHER ACCORDING TO THE INPUT
	private Function2d height;
	private PuttingCourse course;

	//TODO allow people to enter their prefered G value
	public final double __G = 9.81;

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
		}
	}

	@Override
	public void set_step_size(double h) {
		step = h;
	}

	@Override
	public double getStepSize() {
		return step;
	}
}