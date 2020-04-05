public class EulerSolver implements PhysicsEngine {
	private double step = 1e-4; // RANDOM VALUE, NEED TO ASSESS IT FURTHER ACCORDING TO THE INPUT
	private PuttingCourse course;

	//TODO allow people to enter their prefered G value
	public final double __G = 9.81;

	public EulerSolver(PuttingCourse course) {
		this.course = course;
	}

	@Override 
	public void process(double dtime) {
		Vector2d p = course.ball.getCoords2();
		Vector2d v = course.ball.getVelocity2();

		for (double timer = 0; timer < dtime; timer += step) {
			double pnextx = p.x + step * v.x;
			double pnexty = p.y + step * v.y;

			Vector2d gradient = course.height.gradient(p);

			double vnextx = v.x - step * __G * (gradient.x + course.getFriction() * v.x / v.len());
			double vnexty = v.y - step * __G * (gradient.y + course.getFriction() * v.y / v.len());

			p = new Vector2d(pnextx, pnexty);
			v = new Vector2d(vnextx, vnexty);

			//System.out.println(p + " " + v + " " + gradient);
		}
		course.ball.setCoords2(p);
		course.ball.setVelocity2(v);
		course.ball.setCoords3(new Vector3d(p, course.height.evaluate(p)));
		course.ball.setVelocity3(new Vector3d(v, 0.));
	}

	@Override
	public void setStepSize(double h) {
		step = h;
	}

	@Override
	public double getStepSize() {
		return step;
	}

	public static void main(String args[]) {
		
	}
}