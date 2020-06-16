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
	public ShotInfo process(double dtime, ShotInfo shotInfo) {
		passedFlag = false;

		Vector2d p = shotInfo.getPosition2D();
		Vector2d v = shotInfo.getVelocity2D();

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

			p = checkOutOfBounds(new Vector2d(pNextX, pNextY));
			v = limitVelocity(new Vector2d(vNextX, vNextY));

			if (course.victoriousPosition3())
				passedFlag = true;

		}
		shotInfo.setPosition3D(new Vector3d(p.x, course.height.evaluate(p), p.y));
		shotInfo.setVelocity3D(new Vector3d(v.x, 0, v.y));
		return new ShotInfo(shotInfo);
	}

	/**
	 * Checks if the position is out of bounds, if so, then the ball is set at the particular bound
	 * @param position the position
	 * @return the (not-out-of-bounds) position
	 */
	private Vector2d checkOutOfBounds(Vector2d position) {
		//Check for x
		if (position.x < 0) {
			position.x = 0;
		} else if (position.x > course.TERRAIN_SIZE) {
			position.x = course.TERRAIN_SIZE;
		}
		//Check for y
		if (position.y < 0) {
			position.y = 0;
		} else if (position.y > course.TERRAIN_SIZE) {
			position.y = course.TERRAIN_SIZE;
		}
		return position;
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
