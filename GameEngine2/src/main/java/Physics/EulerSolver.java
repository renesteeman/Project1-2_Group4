package Physics;

public class EulerSolver implements PhysicsEngine {
	private double step = 1e-4; // RANDOM VALUE, NEED TO ASSESS IT FURTHER ACCORDING TO THE INPUT
	private PuttingCourse course;

	//TODO allow people to enter their prefered G value
	public final double __G = 9.81;

	public EulerSolver(PuttingCourse course) {
		this.course = course;
	}

	private boolean passedFlag = false;

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
			double pNextX = p.x + step * v.x;
			double pNextY = p.y + step * v.y;

			Vector2d gradient = course.height.gradient(p);

			double vNextX = v.x - step * __G * (gradient.x + course.getFriction() * v.x / v.length());
			double vNextY = v.y - step * __G * (gradient.y + course.getFriction() * v.y / v.length());

			p = new Vector2d(pNextX, pNextY);
			v = new Vector2d(vNextX, vNextY);

			if (v.length() > course.getMaxVelocity()) {
				v = Vector2d.divide(v, v.length());
				v = Vector2d.multiply(v, course.getMaxVelocity());
			}

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
