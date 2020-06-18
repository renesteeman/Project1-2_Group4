package Physics;

public class EulerSolver implements PhysicsEngine {
	private double step = 1e-2; // RANDOM VALUE, NEED TO ASSESS IT FURTHER ACCORDING TO THE INPUT
	private PuttingCourse course;

	public final double GRAVITY = 9.81; //TODO allow people to enter their prefered G value

	public EulerSolver(PuttingCourse course, double step) {
		this.course = course;
		this.step = step;
	}

	/**
	 * Processes the shot using Euler's Method. The position and velocity are simultaneously updated using the current
	 * position and current velocity. This happens as follows:
	 * 		nextPosition = currentPosition + currentVelocity * step;
	 * 		nextVelocity = currentVelocity + currentAcceleration * step;
	 *
	 * @param dtime the interval over which the shot is processed
	 * @param shotInfo contains info about current position and current velocity
	 * @return info about the latest calculated position and velocity
	 */
	@Override 
	public ShotInfo process(double dtime, ShotInfo shotInfo) {
		Vector2d currentPosition = shotInfo.getPosition2D();
		Vector2d currentVelocity = shotInfo.getVelocity2D();

		for (double timer = 0; timer < dtime; timer += step) {
			if (currentVelocity.length() == 0) currentVelocity = currentVelocity.add(new Vector2d(1e-20,1e-20));
			Vector2d currentAcceleration = acceleration(currentPosition,currentVelocity);

			//Calculate next position and next velocity
			Vector2d nextPosition = currentPosition.add(currentVelocity.multiply(step));
			Vector2d nextVelocity = currentVelocity.add(currentAcceleration.multiply(step));

			//update position and velocity
			currentPosition = checkOutOfBounds(nextPosition);
			currentVelocity = limitVelocity(nextVelocity);
		}

		shotInfo.setPosition3D(new Vector3d(currentPosition.x, course.height.evaluate(currentPosition), currentPosition.y));
		shotInfo.setVelocity3D(new Vector3d(currentVelocity.x, 0, currentVelocity.y));

		return new ShotInfo(shotInfo);
	}

	/**
	 * Calculates the current acceleration given the position and velocity
	 * @param position the current position of the ball
	 * @param velocity the current velocity of the ball
	 * @return the current acceleration
	 */
	private Vector2d acceleration(Vector2d position, Vector2d velocity) {
		Vector2d gradient = course.height.gradient(position);
		double accelerationX =  -GRAVITY * (gradient.x + course.getFriction() * velocity.x / velocity.length());
		double accelerationY =  -GRAVITY * (gradient.y + course.getFriction() * velocity.y / velocity.length());
		return new Vector2d(accelerationX,accelerationY);
	}

	/**
	 * Checks if the position is out of bounds, if so, then the ball is set at the particular bound
	 * @param position
	 * @return the (not-out-of-bounds) position
	 */
	private Vector2d checkOutOfBounds(Vector2d position) {
		//Check for x
		if (position.x < 0) position.x = 0;
		if (position.x > course.TERRAIN_SIZE) position.x = course.TERRAIN_SIZE;
		//Check for y
		if (position.y < 0) position.y = 0;
		if (position.y > course.TERRAIN_SIZE) position.y = course.TERRAIN_SIZE;

		return new Vector2d(position.x,position.y);
	}

	/**
	 * Scale the velocity down to the maximum velocity if it is bigger than the maximum
	 * @param velocity
	 * @return the (scaled) velocity
	 */
	private Vector2d limitVelocity(Vector2d velocity) {
		double currentVelocity = velocity.length();
		if (course.maxVelocity < currentVelocity) {
			return velocity.divide(currentVelocity).multiply(course.maxVelocity);
		}
		return velocity;
	}

	@Override
	public void setStepSize(double h) {
		this.step = h;
	}

	@Override
	public double getStepSize() {
		return this.step;
	}
}
