package Physics;

public class EulerSolver implements PhysicsEngine {
	private double step = 1e-2; // RANDOM VALUE, NEED TO ASSESS IT FURTHER ACCORDING TO THE INPUT
	private PuttingCourse course;
	private boolean passedFlag = false;

	//TODO allow people to enter their prefered G value
	public final double __G = 9.81;

	public EulerSolver(PuttingCourse course, double step) {
		this.course = course;
		this.step = step;
	}

	@Override
	public boolean passedFlag() {
		return passedFlag;
	}

	@Override 
	public void process(double dtime) {
		passedFlag = false;

		Vector2d p = course.ball.getPosition2();
		Vector2d v = course.ball.getVelocity2D();

		for (double timer = 0; timer < dtime; timer += step) {

			if (v.length() == 0) {
				v.x += 1e-20;
				v.y += 1e-20;
			}

			double pNextX = p.x + step * v.x;
			double pNextY = p.y + step * v.y;

			Vector2d gradient = course.height.gradient(p);

			double vNextX = v.x - step * __G * (gradient.x + course.getFriction() * v.x / v.length());
			double vNextY = v.y - step * __G * (gradient.y + course.getFriction() * v.y / v.length());

			p = new Vector2d(pNextX, pNextY);
			v = limitVelocity(new Vector2d(vNextX, vNextY));

			//TODO find better place to place this piece of code
			if (p.x < 0) p.x = 0;
			if (p.x > course.TERRAIN_SIZE) p.x = course.TERRAIN_SIZE;
			if (p.y < 0) p.y = 0;
			if (p.y > course.TERRAIN_SIZE) p.y = course.TERRAIN_SIZE;

			if (course.victoriousPosition3())
				passedFlag = true;

			//System.out.println(v.length());

			//System.out.println(p + " " + v + " " + gradient);
		}
		course.ball.setPosition(new Vector3d(p.x, course.height.evaluate(p), p.y));
		course.ball.setVelocity(new Vector3d(v.x, 0, v.y));
	}

	/**
	 * Scale the velocity down to the maximum velocity if it is bigger than the maximum
	 * @param velocity the velocity vector
	 * @return the (scaled) velocity vector
	 */
	private Vector2d limitVelocity(Vector2d velocity) {
		if (velocity.length() > course.maxVelocity) {
			double scalingFactor = course.maxVelocity / velocity.length();
			velocity = velocity.multiply(scalingFactor);
		}
		return velocity;
	}

	@Override
	public void setStepSize(double h) {
		step = h;
	}

	@Override
	public double getStepSize() {
		return step;
	}
}
